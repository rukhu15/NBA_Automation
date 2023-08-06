package application.apiPageObjects;

import apiPojo.apiReq.formulaBuilder.*;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.KPIBuilderAPIBean;
import framework.utility.common.Assertion;
import framework.utility.common.AthenaDataUtil;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.StatusCode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import scripts.BaseAPI;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * @author Prateek Sethi
 */
public class KPI_API extends BaseAPI {
    private static ExtentTest pNode;

    public KPI_API(ExtentTest pNode) {
        super();
    }

    public static KPI_API init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new KPI_API(pNode);
    }

    public Formula generateFormulaObject(KPIBuilderAPIBean fb){

        List<String> arrListTags=new ArrayList<>();
        Formula formulaObj = new Formula();
        formulaObj.setKpi_name(fb.kpiName);
        formulaObj.setKpi_desc(fb.kpi_desc);

        int category_id=DatabaseUtil.getDataSetCategoryId(fb.category_name);
        formulaObj.setKpi_category(category_id);

        int subcategory_id=DatabaseUtil.getSubCategoryDataSetTypeId(fb.subcategory_name);
        formulaObj.setKpi_subcategory(subcategory_id);

        formulaObj.setKpi_syntax(fb.kpiSyntax);

        String[] tagArr=fb.kpi_tags.split("\\|");
        Collections.addAll(arrListTags,tagArr);
        formulaObj.setKpi_tags(arrListTags);
        formulaObj.setKpi_view(fb.kpi_view);
        formulaObj.setIs_deleted(Boolean.valueOf(fb.is_deleted));
        formulaObj.setIs_active(false);
        formulaObj.setUse_as_rule(Boolean.valueOf(fb.use_as_trigger));
        formulaObj.setUse_as_trigger(Boolean.valueOf(fb.use_as_rule));
        formulaObj.setKpi_time_range(3);


        FormulaExprn formulaExprObj=getFormulaExprObj(fb);
        formulaObj.setKpi_exprn(formulaExprObj);
        Kpi_preview previewObj=generateKPIPreview(fb);
        formulaObj.setKpi_preview(previewObj);
        return formulaObj;
    }


    public Kpi_preview generateKPIPreview(KPIBuilderAPIBean fb){
        PreviewTypeObj obj1 = new PreviewTypeObj();
        Preview_Display obj2 = new Preview_Display();
        Kpi_preview obj3 = new Kpi_preview();

        obj1.setDesc(fb.kpiName);
        if(Boolean.valueOf(fb.isSingleVariable)) {
            obj2.setDisplay_name(fb.column_display_name);
        }else{
            obj2.setDisplay_name(fb.kpiName);
        }
        List<Preview_Display> list = new ArrayList<>();
        list.add(obj2);
        obj1.setChild(list);

        obj3.setKpi_preview(fb.kpiName,obj1);

return obj3;
    }


    public Formula generateFormulaUpdateObject(KPIBuilderAPIBean fb){
        List<String> arrListTags=new ArrayList<>();
        Formula formulaObjUpdate = new Formula();
        formulaObjUpdate.setKpi_name(fb.kpiName);

        int category_id=DatabaseUtil.getDataSetCategoryId(fb.category_name);
        formulaObjUpdate.setKpi_category(category_id);
        int subcategory_id=DatabaseUtil.getSubCategoryDataSetTypeId(fb.subcategory_name);
        formulaObjUpdate.setKpi_subcategory(subcategory_id);
        formulaObjUpdate.setKpi_syntax(fb.kpiSyntax);
        String[] tagArr=fb.kpi_tags.split("\\|");
        for(String tag:tagArr){
            arrListTags.add(tag);
        }
        formulaObjUpdate.setKpi_tags(arrListTags);

        formulaObjUpdate.setKpi_view(fb.kpi_view);

        formulaObjUpdate.setIs_deleted(Boolean.valueOf(fb.is_deleted));
        formulaObjUpdate.setIs_active(Boolean.valueOf(fb.is_active));

        FormulaExprn formulaExprObj=getFormulaExprObj(fb);
        formulaObjUpdate.setKpi_exprn(formulaExprObj);
        return formulaObjUpdate;
    }

    public FormulaExprn getFormulaExprObj(KPIBuilderAPIBean fb){

        FormulaExprn formulaExprObj= new FormulaExprn();
        List<String> arrListGroupBy=new ArrayList<>();
        String[] groupByArr=fb.group_by.split("\\|");

        Collections.addAll(arrListGroupBy,groupByArr);
        char value = 'A';
        int formulaVariableValue = (int)value;
        System.out.println("The ascii value is: *************"+formulaVariableValue);
        int counter=1;
        String formulaVariable=null;
        Map<String, Object> formulaVariableKeysMap= new LinkedHashMap<>();
        JSONObject formulaExprJsonObj = new JSONObject(fb.kpiExpr);
        Iterator<String> formulaVariableJsonItr=formulaExprJsonObj.keys();

        while(formulaVariableJsonItr.hasNext()){
            JSONObject formulaVariableJsonObj=formulaExprJsonObj.getJSONObject(formulaVariableJsonItr.next());

            if(counter!=1){
                formulaVariableValue=formulaVariableValue+1;
                formulaVariable =Character. toString((char) formulaVariableValue);
            }else{
                formulaVariable =Character. toString((char) formulaVariableValue);
            }
            if(formulaVariableJsonObj.getString("variable_type").equalsIgnoreCase("dataset")
                    && formulaVariable.equals("A")) {
                A dataSetObjA= new A();
                dataSetObjA.setVariable_type("dataset");
                dataSetObjA.setData_source(formulaVariableJsonObj.getString("data_source"));
                dataSetObjA.setFile_name(formulaVariableJsonObj.getString("file_name"));
                dataSetObjA.setColumn_name(formulaVariableJsonObj.getString("column_name"));
                dataSetObjA.setOperation(formulaVariableJsonObj.getString("operation"));
                dataSetObjA.setGroup_by(arrListGroupBy);
                dataSetObjA.setTime_period(formulaVariableJsonObj.getString("time_period"));
                dataSetObjA.setTime_grain(formulaVariableJsonObj.getString("time_grain"));

                List<Filter> listFilter= new ArrayList<>();
                JSONArray arrFilter = formulaVariableJsonObj.getJSONArray("filter");
                for(int index=0;index<arrFilter.length();index++){
                    Filter filterObj = new Filter();
                    JSONObject filterJsonObj= arrFilter.getJSONObject(index);
                    filterObj.setColumn_name(filterJsonObj.getString("column_name"));
                    filterObj.setValue(filterJsonObj.getString("value"));
                    listFilter.add(filterObj);
                }
                dataSetObjA.setFilter(listFilter);
                formulaExprObj.setA(dataSetObjA);
            } else if(formulaVariableJsonObj.getString("variable_type").equalsIgnoreCase("dataset")
                    && formulaVariable.equals("B")){
                B dataSetObjB= new B();
                dataSetObjB.setVariable_type("dataset");
                dataSetObjB.setData_source(formulaVariableJsonObj.getString("data_source"));
                dataSetObjB.setFile_name(formulaVariableJsonObj.getString("file_name"));
                dataSetObjB.setColumn_name(formulaVariableJsonObj.getString("column_name"));
                dataSetObjB.setOperation(formulaVariableJsonObj.getString("operation"));
                dataSetObjB.setGroup_by(arrListGroupBy);
                dataSetObjB.setTime_period(formulaVariableJsonObj.getString("time_period"));
                dataSetObjB.setTime_grain(formulaVariableJsonObj.getString("time_grain"));

                List<Filter> listFilter= new ArrayList<>();
                JSONArray arrFilter = formulaVariableJsonObj.getJSONArray("filter");
                for(int index=0;index<arrFilter.length();index++){
                    Filter filterObj = new Filter();
                    JSONObject filterJsonObj= arrFilter.getJSONObject(index);
                    filterObj.setColumn_name(filterJsonObj.getString("column_name"));
                    filterObj.setValue(filterJsonObj.getString("value"));
                    listFilter.add(filterObj);
                }
                dataSetObjB.setFilter(listFilter);
                formulaExprObj.setB(dataSetObjB);
            }

            else if(formulaVariableJsonObj.getString("variable_type").equalsIgnoreCase("dataset")){
                C dataSetObjC = new C();
                dataSetObjC.setVariable_type("dataset");
                dataSetObjC.setData_source(formulaVariableJsonObj.getString("data_source"));
                dataSetObjC.setFile_name(formulaVariableJsonObj.getString("file_name"));
                dataSetObjC.setColumn_name(formulaVariableJsonObj.getString("column_name"));
                dataSetObjC.setOperation(formulaVariableJsonObj.getString("operation"));
                dataSetObjC.setGroup_by(arrListGroupBy);
                dataSetObjC.setTime_period(formulaVariableJsonObj.getString("time_period"));
                dataSetObjC.setTime_grain(formulaVariableJsonObj.getString("time_grain"));

                List<Filter> listFilter= new ArrayList<>();
                JSONArray arrFilter = formulaVariableJsonObj.getJSONArray("filter");
                for(int index=0;index<arrFilter.length();index++){
                    Filter filterObj = new Filter();
                    JSONObject filterJsonObj= arrFilter.getJSONObject(index);
                    filterObj.setColumn_name(filterJsonObj.getString("column_name"));
                    filterObj.setValue(filterJsonObj.getString("value"));
                    listFilter.add(filterObj);
                }
                dataSetObjC.setFilter(listFilter);
                formulaExprObj.setC(dataSetObjC);
            }
            counter++;
        }

        return formulaExprObj;
    }

    public Response createFormula(KPIBuilderAPIBean fb) {
        int workSpaceID=DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        Formula formulaObj=generateFormulaObject(fb);
        Map<String,?> queryMap= Collections.singletonMap("workspaceId",workSpaceID);
        RequestSpecification formulaReq=getRequestSpecification("/nbadesigner-backend/nba/model/kpi",queryMap,formulaObj);

        pNode.info("########  The decorated api request is: "+formulaReq.log().body(true));
        Response resp=formulaReq.when()
                .post();
        return resp;
    }

    public Response deactivateActivateKPI(KPIBuilderAPIBean kpiBean, boolean result){
        int workSpaceID=DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        int formulaID=DatabaseUtil.getKPiId(kpiBean.kpiName);
        int user_id=DatabaseUtil.getUserID(ConfigInput.apiUserName);
        Formula formulaObj=generateFormulaObject(kpiBean);
        Map<String,? super Object> queryMap= new HashMap<>();
        queryMap.put("workspaceId",workSpaceID);
        queryMap.put("kpiId",formulaID);
        queryMap.put("userId",user_id);
        queryMap.put("isToggle",true);
        if(result) {
            formulaObj.setIs_active(false);
        }else{
            formulaObj.setIs_active(Boolean.valueOf(kpiBean.is_active));
        }

        RequestSpecification formulaReq=getRequestSpecification("/nbadesigner-backend/nba/model/kpi",queryMap,formulaObj);

        pNode.info("########  The decorated api request is: "+formulaReq.log().body(true));

        Response resp=formulaReq.when()
                .put();
       return resp;
    }



    public Response updateFormula(KPIBuilderAPIBean fb) {
        int formulaID=DatabaseUtil.getKPiId(fb.kpiName);
        int workspaceId=DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        Formula formulaObjUpdate=generateFormulaUpdateObject(fb);
        RequestSpecification genericReq = initAPIReq();
        RequestSpecification formulaReq=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("/nbadesigner-backend/nba/model/formula")
                .queryParam("formulaId",formulaID)
                .queryParam("workspaceId",workspaceId)
                .body(formulaObjUpdate);
        pNode.info("########  The decorated api request is: "+formulaReq.log().all());

        Response resp=formulaReq.when()
                .put();
        return resp;
    }


    public void validateCreatedFormula(Response resp, KPIBuilderAPIBean fb,boolean isKPIExists) throws IOException {

        pNode.info("########  The received api response is: ##################### ");
        pNode.info("########  The received api response is: "+resp.prettyPrint());
        JSONObject kpiOutputRespObj = new JSONObject(resp.asString());
        JSONObject respObj=kpiOutputRespObj.getJSONObject("response");
if(isKPIExists) {
    if (resp.getStatusCode() == StatusCode.CODE_200.getCode()) {
        pNode.pass("KPI saved successfully");
    } else {
        Assertion.raiseExceptionAndStop(new Exception("\"KPI could not be saved successfully as status code is: " + resp.getStatusCode()), pNode);
    }

    String instance=(respObj.getString("instance"));
    if(instance!=null && instance.equalsIgnoreCase("Kpi Details saved")){
        pNode.pass("KPI details save successfully");
    }else{
        Assertion.raiseExceptionAndStop(new Exception("KPI details not saved successfully as instance value in response is "+respObj.getString("instance")),pNode);
    }
}else{
    if (resp.getStatusCode() == StatusCode.CODE_201.getCode()) {
        pNode.pass("KPI created successfully");
    } else {
        Assertion.raiseExceptionAndStop(new Exception("\"KPI could not be created successfully as status code is: " + resp.getStatusCode()), pNode);
    }

    String instance=(respObj.getString("instance"));
    if(instance!=null && instance.equalsIgnoreCase("KPI Has Been Created Successfully")){
        pNode.pass("KPI save successfully");
    }else{
        Assertion.raiseExceptionAndStop(new Exception("KPI could not created successfully as instance value in response is "+respObj.getString("instance")),pNode);
    }
}


    }

    public void deleteExistingKPI(String workspaceName,String kpi_name) {
       int id= DatabaseUtil.getUserWorkspaceID(workspaceName);
        System.out.println("Going to delete all formulas for given user");
//        String query="UPDATE "+ ConfigInput.dbSchemaName +".nba_kpi a\n" +
//                "SET is_deleted=true \n" +
//                "WHERE a.wrkspc_id="+id+" and a.kpi_name like "+"'"+kpi_name+"'";
        String query = "update "+ ConfigInput.dbSchemaName +".nba_xref_workspace_kpi nxwk set is_deleted = true "
        		+ "where kpi_id in (select kpi_id from "+ ConfigInput.dbSchemaName +".nba_kpi kpi"
        				+ " where kpi.kpi_name like "+"'"+kpi_name+"') and nxwk.is_deleted=false and nxwk.wrkspc_id=10";
        System.out.println(query);
        DatabaseUtil.runUpdateQuery(query);
    }

    public Response saveKPI(KPIBuilderAPIBean fb) {
        int workSpaceID=DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        int user_id=DatabaseUtil.getUserID(ConfigInput.apiUserName);
        int company_id=DatabaseUtil.getValueFromColumn("company_id",ConfigInput.dbAppSchemaName,
				"multitenant_config","project_id="+workSpaceID);
        FormulaSave formulaObjSave=generateDagCallFormulaObject(fb);
        Map<String,? super Object> queryMap= new HashMap<>();
        queryMap.put("workspace_id",workSpaceID);
        queryMap.put("userId",user_id);
        queryMap.put("companyId",company_id);
        RequestSpecification formulaReq=getRequestSpecification("/nbadesigner-backend/nba/dagCallSaveData",queryMap,formulaObjSave);
        pNode.info("########  The decorated api URL is: "+formulaReq.log().uri());
        pNode.info("########  The decorated api request is: "+formulaReq.log().body(true));
        Response resp=formulaReq.when()
                .post();
        return resp;

    }

    private FormulaSave generateDagCallFormulaObject(KPIBuilderAPIBean fb) {
        int kpi_id=DatabaseUtil.getKPiId(fb.kpiName);
        FormulaSave formulaObj = new FormulaSave();
        formulaObj.setKpi_id(kpi_id);
        formulaObj.setIs_toggle("false");
        formulaObj.setKpi_name(fb.kpiName);
        return formulaObj;
    }

    public void validateKPIOutput(KPIBuilderAPIBean fb) throws IOException {
        int kpi_id = DatabaseUtil.getKPiId(fb.kpiName);
        String kpi_execution_message=DatabaseUtil.getValueFromColumn
                ("kpi_execution_message",ConfigInput.dbSchemaName,"nba_xref_workspace_kpi","kpi_id="+kpi_id);

        //Number of kpi output records : 13179
        String kpi_op_records = kpi_execution_message.split("\\:")[1];
        int actualOp=Integer.valueOf(kpi_op_records.trim());

        //Fetch the expected kpi count
        String queryExecutionId=AthenaDataUtil.submitAthenaQuery(fb.distinctCustomerCountQuery);
        List<Object[]> list=AthenaDataUtil.processResultRows(queryExecutionId);
       int expectedOp= Integer.parseInt((String) list.get(1)[0]);


        //compare actual vs expected Pass/Fail the test

        if(actualOp==expectedOp){
            pNode.pass("Expected Count matches with actual");
        }else{
            Assertion.raiseExceptionAndStop(new Exception("Actual count and expected count does not match for KPI Id: "+kpi_id+" actual_count: "+actualOp+" expectedOp: "+expectedOp),pNode);
        }
    }
}