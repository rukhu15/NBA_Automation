package application.pageObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.beans.ModelPageBean;
import framework.utility.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import com.aventstack.extentreports.ExtentTest;

import framework.utility.globalConst.Constants;

public class ModelPage  extends PageInit{
	
	private static ExtentTest pNode;
	
	@FindBy(xpath="//div[contains(text(),'Model')]")
	WebElement lnk_model;

	@FindBy(xpath = "//span[contains(text(),'Model')]")
	WebElement stepperModelTab;

	@FindBy(xpath = "//button[@class='new-workflow btn -primary']")
	WebElement btnNewWorkflow;

	@FindBy(xpath = "//span[text()='Create Workflow']")
	WebElement tabCreateWorkflow;

	@FindBy(xpath = "//button[text()='Cancel']")
	WebElement clickOnCancel;

	@FindBy(xpath = "//button/span[text()='OK']")
	WebElement btnOK;

	@FindBy(xpath = "//input[@placeholder='Workflow Name']")
	WebElement inputForWfName;

	@FindBy(xpath = "//textarea[@placeholder='Description']")
	WebElement inputForWfDesc;

	@FindBy(xpath = "//button[@id='businessUnitFormnext' and text()='Create']")
	WebElement btnCreate;
	
	@FindBy(xpath="//button[contains(text(),'Create Workflow')]")
	WebElement btn_createWorkflow;

	@FindBy(xpath = "//p[text()='Add your first trigger']")
	WebElement textVerify;

	@FindBy(xpath = "//p[text()='Add your first rule']")
	WebElement txtVerifyRules;

	@FindBy(xpath = "//button/span[text()='Add Trigger']")
	WebElement btnAddTrigger;

	@FindBy(xpath = "//button/span[text()='Add Rules']")
	WebElement btnAddRule;

	@FindBy(xpath = "//div[contains(@class,'content__wrap')]")
	WebElement inputFormTrigger;

	@FindBy(xpath = "//div[@class='slide-title-div']/p[text()='Select Trigger Rule']")
	WebElement txt_selectTriggerRule;

	@FindBy(xpath = "//div[@class='slide-title-div']/p[text()='Select Rules']")
	WebElement txt_selectRule;

	@FindBy(xpath = "//input[@placeholder='Search triggers']")
	WebElement inputSearchTrigger;

	@FindBy(xpath = "//input[@placeholder='Search Rule']")
	WebElement inputSearchRule;

	@FindBy(xpath = "//button[@aria-label='search']")
	WebElement btnSearch;

	@FindBy(xpath = "//button//span[text()='General']")
	WebElement btnGeneralTab;

	@FindBy(xpath = "//button[@class='add']")
	WebElement btnAdd;

	@FindBy(xpath = "//input[@placeholder='Search Workflow']")
	WebElement inputSearch;

	@FindBy(xpath = "//button[@aria-label='search']")
	WebElement btnSearchModelPage;

	@FindBy(xpath = "//button[@aria-label='clear']")
	WebElement btnClearModelPage;

	@FindBy(xpath = "//div[@class='spinner']")
	WebElement spinner;

	@FindBy(xpath = "//select[@name='operator']")
	WebElement selectOperator;

	@FindBy(xpath = "//select[@name='duration']")
	WebElement selectDuration;

	@FindBy(xpath = "//input[@name='value']")
	WebElement input_Value;

	@FindBy(xpath = "//span[text()='Create Group']")
	WebElement txt_CreateGroup;

	@FindBy(xpath = "//span[contains(text(),'Group Name')]//ancestor::div[@class='row']//input")
	WebElement input_GroupName;

	@FindBy(xpath = "//button/span[text()='Create']")
	WebElement btn_Create;

	@FindBy(xpath = "//span[text()='Rename']//ancestor::div[contains(@class,'base draggable')]//select[@id='demo-customized-select-native']")
	WebElement slect_AndOr;

	@FindBy(xpath = "//input[@name='period']")
	WebElement input_Period;

	@FindBy(xpath = "//button/span[text()='Save']")
	WebElement btn_Save;

	@FindBy(xpath = "//button/span[text()='Continue to ' or text()='Rules' or text()='Actions']")
	WebElement btn_ContineToRulesAndActions;

	@FindBy(xpath="//*[@id=\"bootstrap-input\"]")
	WebElement txt_scenarioName;
	
	@FindBy(xpath="//body/div[3]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/div[1]/div[3]/div[1]/form[1]/div[1]/div[2]/div[1]/div[1]/input[1]")
	WebElement txt_scenarioDescription;
	
	@FindBy(xpath="//*[@id=\"businessUnitFormnext\"]")
	WebElement btn_scenarioCreateButton;

	public ModelPage(ExtentTest t3) {
		super(t3);
	}
	
	public static ModelPage init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new ModelPage(pNode);
    }
	
	
	
	public void createWorkflow() throws  Exception {
		clickOnElement(btn_createWorkflow, "create workflow button");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}
	
	public void enterWorkflowName() throws  Exception {
		setTextInTextAreaReferenceIsInput(txt_scenarioName, "set.scenario.name", "AutomatedScenario_13","Scenario Name");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}
	
	public void enterScenarioDescription() throws  Exception {
		//clickOnElement(txt_scenarioDescription, "enter scenario description");
		setTextInTextAreaReferenceIsBody(txt_scenarioDescription, "set.scenario.description", "Description for AutomatedScenario","Scenario Description");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}
	
	public void saveScenario() throws  Exception {
		clickOnElement(btn_scenarioCreateButton, "save scenario");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void toNavigateModel(ModelPageBean mg)  {
		try {
			Utils.captureScreen(pageInfo);
			clickOnElement(stepperModelTab,"To click on Model Stepper");
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void createWorkFlow(ModelPageBean mg) {
		try {
			pageInfo.info("No workflow present create new");
			waitTillDisappearingOfSpinner();
			Utils.putThreadSleep(2000);
			if (elementIsDisplayed(btnNewWorkflow)){
				clickOnElement(btnNewWorkflow,"To click on New workflow model");
			}else {
				pageInfo.fail("New Workflow button is not present");
			}
			inputFormModel(mg);
		}catch (Exception e){
			logInfo(e.getMessage());
		}

	}

	public void inputFormModel(ModelPageBean mg){
		try {
			WebElement formOpenAreNot = driver.findElement(By.xpath("//div[@class='content__body model-slidepane row no-gutters']"));
			if (elementIsDisplayed(formOpenAreNot)){
				clickOnElement(tabCreateWorkflow,"to click on create Workflow");
				setText(inputForWfName,mg.workflowName,"To enter Workflow name");
				setText(inputForWfDesc,mg.workflowDescription,"To enter Description");
				clickOnElement(btnCreate,"To enter click for create workflow");
			}else {
				pageInfo.fail("Input form has not opened");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void enterIntoWorkFlow(ModelPageBean mg){
		try {
			waitTillDisappearingOfSpinner();
			WebElement clickOnWF = driver.findElement(By.xpath("//div[@class='hyperlink-text' or text()='"+mg.workflowName+"']"));
			if (elementIsDisplayed(clickOnWF)){
				searchWorkFlow(mg);
				checkWFStatusInput(mg);
				Utils.captureScreen(pageInfo);
				clickOnWF = driver.findElement(By.xpath("//div[@class='hyperlink-text' or text()='"+mg.workflowName+"']"));
				clickOnElement(clickOnWF,"Enter into workflow");
			}else {
				pageInfo.fail("Workflow is not present");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void searchWorkFlow(ModelPageBean mg){
		try {
			if (elementIsDisplayed(inputSearch)){
				setText(inputSearch,mg.workflowName,"To search workflow");
				clickOnElement(btnSearchModelPage,"To click on the search button");
				waitTillDisappearingOfSpinner();
				WebElement checkWFPresent = driver.findElement(By.xpath("//div[@class='hyperlink-text' or text()='"+mg.workflowName+"']"));
				if (elementIsDisplayed(checkWFPresent)){
					pageInfo.info("Workflow present after search");
					clickOnElement(btnClearModelPage,"Clearing the input");
				}
			}else {
				pageInfo.fail("Search is not Present");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void checkWFStatusInput(ModelPageBean mg){
		try {
			List<WebElement> isActiveOrNot = driver.findElements(By.xpath("//div[text()='"+mg.workflowName+"']/ancestor::div[@role='row']//span[contains(@class,'Mui-checked')]"));
			int count = isActiveOrNot.size();
			if (count>0){
				pageInfo.info("Workflow is in Active state");
			}else {
				pageInfo.fail("Workflow is not in Active state");
				Utils.captureScreen(pageInfo);
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}
	public void checkWorkFlow(ModelPageBean mg){
		try {
		List<WebElement> workFlowExist = driver.findElements(By.xpath("//div[@class='hyperlink-text' or text()='"+mg.workflowName+"']"));
		int count = workFlowExist.size();
		if (count>0){
			WebElement deleteWorkFlow = driver.findElement(By.xpath("//div[text()='"+mg.workflowName+"']//ancestor::div[@role='row']//img[@title='Delete']"));
			clickOnElement(deleteWorkFlow,"Delete the workflow");
			clickOnElement(btnOK,"To delete click on OK button");
			Utils.captureScreen(pageInfo);
		}else {
			pageInfo.info("No workflow present create workflow");
		}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void verifyTriggerPage(ModelPageBean mg){
		try {
			elementIsDisplayed(textVerify);
			WebElement verifyWorkFlowName = driver.findElement(By.xpath("//div[@class='workflowNamediv']/p[text()='"+mg.workflowName+"']"));
			if (elementIsDisplayed(verifyWorkFlowName)){
				pageInfo.info("workflow name verified on Trigger Page");
			}else {
				pageInfo.info("No workflow name present on the Trigger page");
			}
		}catch (Exception e){
			logInfo((e.getMessage()));
		}
	}

	public void clickOnBtnAddTrigger(){
		try {
			if (elementIsDisplayed(btnAddTrigger)){
				clickOnElement(btnAddTrigger,"To click on add trigger button");
				triggerInputForm();
			}else {
				pageInfo.info("No Add Trigger Button is Present");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void triggerInputForm(){
		try {
			if (elementIsDisplayed(inputFormTrigger)){
				pageInfo.info("Input form is opened for Rule");
				elementIsDisplayed(txt_selectTriggerRule);
			}else {
				pageInfo.fail("Input form is not opened");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void ruleInputForm(){
		try {
			if (elementIsDisplayed(inputFormTrigger)){
				pageInfo.info("Input form is opened for Trigger");
				elementIsDisplayed(txt_selectRule);
			}else {
				pageInfo.fail("Input form is not opened");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public String searchKpi(ModelPageBean kp){
		String getKPI = "";
		try {
			JSONObject jsObj = new JSONObject(kp.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("trigger");
			getKPI = kpiObj.getString("kpi_name");
			setText(inputSearchTrigger,getKPI,"To enter input to search KPI");
			clickOnElement(btnSearch,"search button click");
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getKPI;
	}

	public String searchKpiForRule(ModelPageBean kp){
		String getKPI = "";
		try {
			JSONObject jsObj = new JSONObject(kp.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("Rule");
			JSONArray kpiForRule=kpiObj.getJSONArray("kpi");
			List<String> list = new ArrayList<String>();
			for(int i = 0; i < kpiForRule.length(); i++){
				list.add(kpiForRule.getJSONObject(i).getString("kpiforRule"));
			}
			for (int i = 0; i < list.size(); i++) {
				getKPI = list.get(i);
				if(i==0){
					clickOnBtnAddRule();
				}else {
					WebElement addMultipleRule = driver.findElement(By.xpath("//span[text()='Add Rules']"));
					clickOnElement(addMultipleRule,"For second Kpi");
				}
				Utils.captureScreen(pageInfo);
				setText(inputSearchRule, getKPI, "To enter input to search KPI");
				clickOnElement(btnSearch, "search button click");
				selectKpiAndAdd(getKPI);
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getKPI;
	}

	public void selectKpiAndAdd(String getKPIName){
		try {
			checkGeneralTab();
			Utils.putThreadSleep(1000);
			Utils.captureScreen(pageInfo);
			Utils.switchToDefaultContent();
			WebElement kpiToSelect = driver.findElement(By.xpath("//input[@value='"+getKPIName+"' and @type='checkbox']"));
			Utils.putThreadSleep(3000);
			Actions actions = new Actions(driver);
			actions.moveToElement(kpiToSelect).click().build().perform();
			clickOnElement(btnAdd,"To add after selecting Kpi clicking Add Button");
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void checkGeneralTab(){
		try {
			Utils.putThreadSleep(1000);
			Utils.captureScreen(pageInfo);
			if (elementIsDisplayed(btnGeneralTab)){
				clickOnElement(btnGeneralTab,"to select the General Tab");
			}else {
				pageInfo.fail("General tab is not Present");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void triggerInputsAndSave(String getKPIName, ModelPageBean op){
		try {
			WebElement checkKpiName = driver.findElement(By.xpath("//span[text()='"+getKPIName+"']"));
			if (elementIsDisplayed(checkKpiName)){
				pageInfo.info("selected KPI present on the Trigger page");
			}else {
				pageInfo.fail("Selected KPI is not Present on the Trigger Page");
			}
			selectOperator(op);
			valueInput(op);
			valuePeriod(op);
			selectDuration(op);
			selectSave();
			continueToRuleActions();
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void ruleInputsAndSave(String getKPIName, ModelPageBean op){
		try {
			WebElement checkKpiName = driver.findElement(By.xpath("//span[text()='"+getKPIName+"']"));
			if (elementIsDisplayed(checkKpiName)){
				pageInfo.info("selected KPI present on the Trigger page");
			}else {
				pageInfo.fail("Selected KPI is not Present on the Trigger Page");
			}
			selectRuleOperator(op);
			selectRulePeriod(op);
			valueRuleInput(op);
			createGroupForRules(op);
			selectSave();
			continueToRuleActions();
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public String selectRuleOperator(ModelPageBean Ro){
		WebElement ruleOperatorDD;
		String getRuleOperator ="";
		try {
			JSONObject jsObj = new JSONObject(Ro.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("Rule");
			JSONArray kpiForRule=kpiObj.getJSONArray("kpi");
			List<String> list = new ArrayList<String>();
			List<String> ruleOperator = new ArrayList<String>();
			for(int i = 0; i < kpiForRule.length(); i++){
				list.add(kpiForRule.getJSONObject(i).getString("kpiforRule"));
				ruleOperator.add(kpiForRule.getJSONObject(i).getString("Operator"));
			}
			for (int j=0; j<list.size(); j++) {
				ruleOperatorDD = driver.findElement(By.xpath("//select[@name='" + list.get(j) + "' and @id='selectedOperator']"));
				clickOnElement(ruleOperatorDD, "To select operator drop down");
				selectVisibleText(ruleOperatorDD, ruleOperator.get(j), "to select operator");
			}
		}catch (Exception e){
				logInfo(e.getMessage());
		}
		return getRuleOperator;
	}

	public String selectRulePeriod(ModelPageBean Rp){
		WebElement rulePeriodDD;
		String getRulePeriod = "";
		try {
			JSONObject jsObj = new JSONObject(Rp.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("Rule");
			JSONArray kpiForRule=kpiObj.getJSONArray("kpi");
			List<String> list = new ArrayList<String>();
			List<String> rulePeriod = new ArrayList<String>();
			for(int i = 0; i < kpiForRule.length(); i++){
				list.add(kpiForRule.getJSONObject(i).getString("kpiforRule"));
				rulePeriod.add(kpiForRule.getJSONObject(i).getString("period"));
			}
			for (int j=0; j<list.size(); j++) {
				rulePeriodDD = driver.findElement(By.xpath("//select[@name='"+list.get(j)+"']//ancestor::div[contains(@class,'align-items-xs-center')]//input[@id='selectedTimeRange']"));
				setText(rulePeriodDD,rulePeriod.get(j),"to select Period");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getRulePeriod;
	}

	public String valueRuleInput(ModelPageBean RV){
		WebElement ruleValue;
		String getValue = "";
		try {
			JSONObject jsObj = new JSONObject(RV.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("Rule");
			JSONArray kpiForRule=kpiObj.getJSONArray("kpi");
			List<String> list = new ArrayList<String>();
			List<String> ruleValueKpi = new ArrayList<String>();
			for(int i = 0; i < kpiForRule.length(); i++){
				list.add(kpiForRule.getJSONObject(i).getString("kpiforRule"));
				if (!kpiForRule.getJSONObject(i).getString("kpiforRule").equalsIgnoreCase(Constants.KPI_Name_Categorical_KPI)) {
					ruleValueKpi.add(kpiForRule.getJSONObject(i).getString("value"));
				}else {
					pageInfo.info("This KPI is not able to enter value ");
				}
			}
			for (int j=0; j<list.size(); j++) {
				if (!kpiForRule.getJSONObject(j).getString("kpiforRule").equalsIgnoreCase(Constants.KPI_Name_Categorical_KPI)) {
					ruleValue = driver.findElement(By.xpath("//select[@name='" + list.get(j) + "']//ancestor::div[contains(@class,'align-items-xs-center')]//input[@id='selectedValue']"));
					setText(ruleValue, ruleValueKpi.get(j), "to select value");
				}else {
					pageInfo.info("This KPI is not able to enter value ");
				}
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getValue;
	}

	public String selectOperator(ModelPageBean op){
		String getOperator ="";
		try {
			JSONObject jsObj = new JSONObject(op.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("trigger");
			getOperator = kpiObj.getString("Operator");
			clickOnElement(selectOperator,"Open the dropdown");
			WebElement selectOption = driver.findElement(By.xpath("//option[text()='"+getOperator.trim()+"']"));
			clickOnElement(selectOption,"To select Operator");
			clickOnElement(selectOperator,"close dropdown");
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getOperator;
	}

	public String valueInput(ModelPageBean vp){
		String getValue = "";
		try {
			JSONObject jsObj = new JSONObject(vp.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("trigger");
			getValue = kpiObj.getString("value");
			if (elementIsDisplayed(input_Value)){
				setText(input_Value,getValue,"to enter value Input");
			}else {
				pageInfo.fail("Unable to enter value into Input value");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getValue;
	}

	public String valuePeriod(ModelPageBean period){
		String getPeriod = "";
		try {
			JSONObject jsObj = new JSONObject(period.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("trigger");
			getPeriod = kpiObj.getString("period");
			if (elementIsDisplayed(input_Period)){
				setText(input_Period,getPeriod,"to enter Period Input");
				Utils.captureScreen(pageInfo);
			}else {
				pageInfo.info("Period is not available for this KPI");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getPeriod;
	}

	public String selectDuration(ModelPageBean duration){
		String getDuration = "";
		try {
			JSONObject jsObj = new JSONObject(duration.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("trigger");
			getDuration = kpiObj.getString("duration");
			List<WebElement> clickDuration = driver.findElements(By.xpath("//select[@name='duration']"));
			int count = clickDuration.size();
			if (count > 0) {
				clickOnElement(selectDuration, "To select Duration dropdown");
				WebElement dropdownDuration = driver.findElement(By.xpath("//option[text()='"+getDuration.trim()+"']"));
				clickOnElement(dropdownDuration,"Select from dropdown");
			}else {
				pageInfo.info("No duration option is present on this KPI");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
		return getDuration;
	}

	public void selectSave(){
		try {
			if (elementIsClickable(btn_Save)){
				clickOnElement(btn_Save,"On the Save Button");
			}else {
				pageInfo.fail("Save button is not able to click");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void continueToRuleActions(){
		try {
			clickOnElement(btn_ContineToRulesAndActions,"on the button continue to Rules and Actions");
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void verifyRules(ModelPageBean mg){
		try {
			elementIsDisplayed(txtVerifyRules);
			WebElement verifyWorkFlowName = driver.findElement(By.xpath("//div[@class='workflowNamediv']/p[text()='"+mg.workflowName+"']"));
			if (elementIsDisplayed(verifyWorkFlowName)){
				pageInfo.info("workflow name verified on Rule Page");
			}else {
				pageInfo.info("No workflow name present on the Rule page");
			}
		}catch (Exception e){
			logInfo((e.getMessage()));
		}
	}

	public void clickOnBtnAddRule(){
		try {
			if (elementIsDisplayed(btnAddRule)){
				Actions actions = new Actions(driver);
				actions.moveToElement(btnAddRule).click().build().perform();
				ruleInputForm();
			}else {
				pageInfo.info("No Add Rule Button is Present");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void createGroupForRules(ModelPageBean cb){
		// To create Group for the Rules for KPI
		try {
			JSONObject jsObj = new JSONObject(cb.kpiName);
			JSONObject kpiObj = jsObj.getJSONObject("Rule");
			JSONArray kpiForRule=kpiObj.getJSONArray("kpi");
			List<String> list = new ArrayList<String>();
			for(int i = 0; i < kpiForRule.length(); i++){
				list.add(kpiForRule.getJSONObject(i).getString("kpiforRule"));
				WebElement checkBox = driver.findElement(By.xpath("//span[text()='"+list.get(i)+"']//ancestor::div[contains(@class,'MuiGrid-grid-xs-1')]//input/following-sibling::*[local-name()='svg']"));
				Actions actions = new Actions(driver);
				actions.moveToElement(checkBox).click().build().perform();
			}
			if (elementIsDisplayed(txt_CreateGroup)){
				clickOnElement(txt_CreateGroup,"create Group for Rules");
				createGroupInput(cb);
			}else{
				pageInfo.fail("No create group button is there");
			}
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void createGroupInput(ModelPageBean mg){
		//To enter input for group in Rules page
		try {
			setText(input_GroupName,mg.workflowName,"Group Name");
			clickOnElement(btn_Create,"Create button");
			selectAndOrOperator();
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void selectAndOrOperator(){
		// To select Or operator after creating group
		try {
			clickOnElement(slect_AndOr,"dropdown of the Operator");
			selectVisibleText(slect_AndOr,"OR","select Or operator");
		}catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void waitTillDisappearingOfSpinner(){
		// Wait till the disappearing of spinner
		while(elementIsDisplayed(spinner)){
			Utils.putThreadSleep(500);
		}
	}

}