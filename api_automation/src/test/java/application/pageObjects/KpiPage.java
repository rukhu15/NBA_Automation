package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import data.beans.KpiPageBean;
import framework.utility.common.Assertion;
import framework.utility.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class KpiPage extends PageInit{

    @FindBy(xpath = "//nav[@id='sideMenu']//button")
    WebElement navBarExpandCollapseButton;

    @FindBy(xpath = "//button[contains(text(), 'New KPI')]")
    WebElement newKpiButton;

    @FindBy(xpath = "//div[@class='sample-formula']//select")
    WebElement formulaDropdown;

    @FindBy(xpath = "//div[@class='formulaViewPart']//h3[text()='A']//following-sibling::div")
    WebElement addComponentDropDownVariableA;

    @FindBy(xpath = "//div[@class='formulaViewPart']//h3[text()='B']//following-sibling::div")
    WebElement addComponentDropDownVariableB;

    @FindBy(xpath = "//div[@class='formulaViewPart']//h3[text()='C']//following-sibling::div")
    WebElement addComponentDropDownVariableC;

    @FindBy(xpath = "//ul[contains(@class, 'selector-list-wrapper')]//li//span[text()='Dataset']")
    WebElement dataSet;

    @FindBy(xpath = "//select[@id='dataSourceDropdown']")
    WebElement dataSourceDropdown;

    @FindBy(xpath = "//select[@name='dataset_name']")
    WebElement fileNameDropdown;

    @FindBy(xpath = "//div[contains(@class, 'column-input')]//input")
    WebElement columnNameInput;

    @FindBy(xpath = "//select[@name='operation']")
    WebElement operationDropdown;

    @FindBy(xpath = "//button//span[contains(text(), 'Apply')]")
    WebElement applyButton;

    @FindBy(xpath = "//div[@class='period-div']//span[text()='Current Period']")
    WebElement currentPeriod;

    @FindBy(xpath = "//div[@class='period-div']//span[text()='Past Period']")
    WebElement pastPeriod;

    @FindBy(xpath = "//div[@class='time-period-block']//input")
    WebElement timePeriodInput;

    @FindBy(xpath = "//div[@class='formula-name']//input")
    WebElement kpiName;

    @FindBy(xpath = "(//div[@class='category']//select[@id='selected'])[1]")
    WebElement kpiCategory;

    @FindBy(xpath = "(//div[@class='category']//select[@id='selected'])[2]")
    WebElement kpiSubCategory;

    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    WebElement saveButton;

    @FindBy(xpath = "//div[@class='plusIcon']//p")
    WebElement kpiFormulaOperatorButton;

    @FindBy(xpath = "//div[@class='operator-dropdown']//ul//li[contains(text(), '-')]")
    WebElement subtractionOperator;

    @FindBy(xpath = "//input[@aria-label='search Scenario']")
    WebElement searchKpiInput;

    @FindBy(xpath = "//button[@aria-label='search']")
    WebElement searchKpiButton;

    @FindBy(xpath = "//button[contains(text(), 'Cancel')]")
    WebElement cancelButtonKpi;

    @FindBy(xpath = "//button[@aria-label='clear']")
    WebElement clearSearch;

    private static ExtentTest pNode;
    public KpiPage(ExtentTest t1) {
        super(t1);
    }

    public static KpiPage init(ExtentTest t1) throws Exception {
        pNode = t1;
        return new KpiPage(pNode);
    }

    public void openKpiBuilderAndClickOnNewKpi(JSONObject kpiDetails){
        Markup m = MarkupHelper.createLabel("openKpiBuilderAndClickOnNewKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            closeToastMessages();

            //Navigate to kpi builder using side navbar
            openSideNavItem(navBarExpandCollapseButton, true, "KPI Builder");

            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);

            //Click on new kpi button
            clickOnElement(newKpiButton, "New Kpi");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void createKpiFormula(JSONObject kpiDetails){
        Markup m = MarkupHelper.createLabel("createKpiFormula", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Create event kpi
            if(kpiDetails.getString("kpi_type").equalsIgnoreCase("event_kpi")){
                createFormulaOfEventKpi(kpiDetails);
            }
            //Create data kpi
            else {
                createFormulaOfDataKpi(kpiDetails);
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void setKpiAvailabilityAndSaveKpi(JSONObject kpiDetails){
        Markup m = MarkupHelper.createLabel("setKpiAvailabilityAndSaveKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Set kpi availability
            setAvailability(kpiDetails.getJSONArray("availability"));

            //Click on save button
            clickOnElement(saveButton, "Save button");
            Utils.captureScreen(pageInfo);
            waitTillDisappearingOfSpinner();

            //Click on cancel button and search for kpi
            clickOnElement(cancelButtonKpi, "Cancel button");

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void searchKpi(JSONObject kpiDetails){
        Markup m = MarkupHelper.createLabel("searchKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Set name of kpi to search
            setText(searchKpiInput, kpiDetails.getString("kpi_name"), "Search kpi input");

            //Click on search button
            clickOnElement(searchKpiButton, "Search kpi");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void activateKpi(JSONObject kpiDetails, boolean activateKpi) {
        Markup m = MarkupHelper.createLabel("activateKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try {

            Utils.putThreadSleep(60000);
            WebElement activateKpiButton, kpiToggleButton;
            Actions builder = new Actions(driver);
            List<WebElement> createdKpi = driver.findElements(By.xpath("//p[text()='" + kpiDetails.getString("kpi_name") + "']//ancestor::tr//input[@type='checkbox']//following-sibling::span"));
            if(createdKpi.size() == 1) {
                //Click on toggle button of kpi to activate
                activateKpiButton = driver.findElement(By.xpath("//p[text()='" + kpiDetails.getString("kpi_name") + "']//ancestor::tr//input[@type='checkbox']//following-sibling::span"));
                kpiToggleButton = driver.findElement(By.xpath("//p[text()='" + kpiDetails.getString("kpi_name") + "']//ancestor::tr//input[@type='checkbox']"));
                if (activateKpi == true) {
                    builder.moveToElement(activateKpiButton).click().perform();
                    //Check kpi is created
                    Assertion.verifyEqual(createdKpi.size() == 1, true, "Kpi is created successfully", pageInfo);
                } else {
                    if (activateKpi == false && kpiToggleButton.isSelected()) {
                        builder.moveToElement(activateKpiButton).click().perform();
                    } else {
                        pageInfo.info("Kpi has been deactivated already");
                    }
                }
            }
            else {
                pageInfo.info("Kpi is not present");
            }

        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void clearSearchKpi(){
        Markup m = MarkupHelper.createLabel("clearSearchKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);
            clickOnElement(clearSearch, "Clear search");

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void setAvailability(JSONArray availability){
        Markup m = MarkupHelper.createLabel("setAvailability", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement availabilityCheckBox=null;
            Actions builder=new Actions(driver);
            for (int i=0; i<availability.length(); i++){
                availabilityCheckBox = driver.findElement(By.xpath("//h3[text()='Availability']//following::label//span[text()='" + availability.get(i).toString() + "']//preceding-sibling::span//input[@type='checkbox']//following-sibling::*[local-name()='svg']"));
                //Scroll to availability checkboxes
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", availabilityCheckBox);
                builder.moveToElement(availabilityCheckBox).click().perform();
            }
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void setKpiNameAndCategory(JSONObject kpiDetails){
        Markup m = MarkupHelper.createLabel("setKpiNameAndCategory", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Set kpi name
            setKpiName(kpiDetails.getString("kpi_name"));
            //Select category
            selectKpiCategory(kpiDetails.getString("category"));
            //Select sub category
            selectKpiSubCategory(kpiDetails.getString("sub_category"));

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectKpiCategory(String category){
        Markup m = MarkupHelper.createLabel("selectKpiCategory", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on category dropdown
            clickOnElement(kpiCategory, "Category dropdown");
            WebElement kpiCategoryOption = driver.findElement(By.xpath("(//div[@class='category']//select[@id='selected'])[1]//option[text()='" + category + "']"));
            clickOnElement(kpiCategoryOption, category);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectKpiSubCategory(String subCategory){
        Markup m = MarkupHelper.createLabel("selectKpiSubCategory", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on sub category dropdown
            clickOnElement(kpiSubCategory, "Sub category dropdown");
            WebElement kpiSubCategoryOption = driver.findElement(By.xpath("(//div[@class='category']//select[@id='selected'])[2]//option[text()='" + subCategory + "']"));
            clickOnElement(kpiSubCategoryOption, subCategory);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void setKpiName(String kpiNameInput){
        Markup m = MarkupHelper.createLabel("setKpiName", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            setText(kpiName, kpiNameInput, "Kpi name");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void waitTillDisappearingOfSpinner(){
        // Wait till the disappearing of spinner
        while(elementIsDisplayed(spinner)){
            Utils.putThreadSleep(100);
        }
    }

    public void createFormulaOfEventKpi(JSONObject kpiDetails){
        Markup m = MarkupHelper.createLabel("createFormulaOfEventKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Select kpi formula
            selectFormula(kpiDetails.getString("formula"));

            //Configure variables of kpi
            configureVariables(kpiDetails);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectTimePeriod(JSONObject kpiJson, String formulaVariable){
        Markup m = MarkupHelper.createLabel("selectTimePeriod", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Select time period
            WebElement periodIcon = driver.findElement(By.xpath("//h3[text()='" + formulaVariable + "']//following-sibling::div//div[@class='periodIconDiv']"));
            clickOnElement(periodIcon, "Period icon");
            Utils.captureScreen(pageInfo);
            if(formulaVariable.equalsIgnoreCase("A")) {
                clickOnElement(currentPeriod, "Current period");
                setText(timePeriodInput, Keys.CONTROL + "a" + Keys.BACK_SPACE, "Clear");
                setText(timePeriodInput, kpiJson.getString("time_period"), "Time period");
            }
            else{
                clickOnElement(pastPeriod, "Past period");
            }
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectFormula(String kpiFormula){
        Markup m = MarkupHelper.createLabel("selectFormula", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on formula dropdown
            clickOnElement(formulaDropdown, "Formula dropdown");

            //Select formula
            selectVisibleText(formulaDropdown, kpiFormula, kpiFormula);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void createFormulaOfDataKpi(JSONObject kpiDetails){
        Markup m = MarkupHelper.createLabel("createFormulaOfDataKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Select kpi formula
            selectFormula(kpiDetails.getString("formula"));

            //Change kpi formula
            clickOnElement(kpiFormulaOperatorButton, "+");
            clickOnElement(kpiFormulaOperatorButton, "+");
            clickOnElement(subtractionOperator, "-");
            Utils.captureScreen(pageInfo);

            //Configure variables of kpi
            configureVariables(kpiDetails);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void configureVariables(JSONObject kpiJson){
        Markup m = MarkupHelper.createLabel("configureVariables", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            if(Integer.parseInt(kpiJson.getString("variables_count")) == 1){
                //Configure variable A
                configureVariableA(kpiJson);
            }
            else {
                //Configure variable A
                configureVariableA(kpiJson);
                //Configure variable B
                configureVariableB(kpiJson);
                //Configure variable C
                configureVariableC(kpiJson);
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void configureVariableA(JSONObject kpiJson){
        Markup m = MarkupHelper.createLabel("configureVariableA", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on add component dropdown
            clickOnElement(addComponentDropDownVariableA, "Add component variable A");
            Utils.captureScreen(pageInfo);

            //Select dataset and operation to perform for the variable A
            selectDataAndOperationToPerformVariableA(kpiJson);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void configureVariableB(JSONObject kpiJson){
        Markup m = MarkupHelper.createLabel("configureVariableB", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on add component dropdown
            clickOnElement(addComponentDropDownVariableB, "Add component variable B");
            Utils.captureScreen(pageInfo);

            //Select dataset and operation to perform for the variable A
            selectDataAndOperationToPerformVariableB(kpiJson);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void configureVariableC(JSONObject kpiJson){
        Markup m = MarkupHelper.createLabel("configureVariableC", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on add component dropdown
            clickOnElement(addComponentDropDownVariableC, "Add component variable C");
            Utils.captureScreen(pageInfo);

            //Select dataset and operation to perform for the variable A
            selectDataAndOperationToPerformVariableC(kpiJson);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectDataAndOperationToPerformVariableA(JSONObject kpiJson){
        Markup m = MarkupHelper.createLabel("selectDataAndOperationToPerformVariableA", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Select dataset option
            clickOnElement(dataSet, "Data set");
            Utils.captureScreen(pageInfo);

            JSONArray formulaDetails = kpiJson.getJSONArray("formula_details");
            for (int i=0; i<formulaDetails.length(); i++) {

                if (formulaDetails.getJSONObject(i).getString("formula_variable").equalsIgnoreCase("A")) {

                    //Select data source
                    selectDataSource(formulaDetails.getJSONObject(i).getString("data_source"));

                    //Select file name
                    selectFileName(formulaDetails.getJSONObject(i).getString("file_name"));

                    //Select column
                    selectColumn(formulaDetails.getJSONObject(i).getString("column_name"));

                    //Select operation
                    selectOperation(formulaDetails.getJSONObject(i).getString("operation"));

                    //Click apply button
                    clickOnElement(applyButton, "Apply button");
                    Utils.captureScreen(pageInfo);

                    //Select time period
                    selectTimePeriod(kpiJson, formulaDetails.getJSONObject(i).getString("formula_variable"));

                    if(kpiJson.getString("applyGroupBy").equalsIgnoreCase("true")){
                        //Select the group by operation
                        selectOperationGroupBy(formulaDetails.getJSONObject(i).getString("formula_variable"), kpiJson.getJSONArray("groupByColumns"));
                    }

                }
                else {
                    logInfo("Formula details for variable not found");
                }

            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectDataAndOperationToPerformVariableB(JSONObject kpiJson){
        Markup m = MarkupHelper.createLabel("selectDataAndOperationToPerformVariableB", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Select dataset option
            clickOnElement(dataSet, "Data set");
            Utils.captureScreen(pageInfo);

            JSONArray formulaDetails = kpiJson.getJSONArray("formula_details");
            for (int i=0; i<formulaDetails.length(); i++) {

                if (formulaDetails.getJSONObject(i).getString("formula_variable").equalsIgnoreCase("B")) {

                    //Select data source
                    selectDataSource(formulaDetails.getJSONObject(i).getString("data_source"));

                    //Select file name
                    selectFileName(formulaDetails.getJSONObject(i).getString("file_name"));

                    //Select column
                    selectColumn(formulaDetails.getJSONObject(i).getString("column_name"));

                    //Select operation
                    selectOperation(formulaDetails.getJSONObject(i).getString("operation"));

                    //Click apply button
                    clickOnElement(applyButton, "Apply button");
                    Utils.captureScreen(pageInfo);

                    //Select time period
                    selectTimePeriod(kpiJson, formulaDetails.getJSONObject(i).getString("formula_variable"));

                    if(kpiJson.getString("applyGroupBy").equalsIgnoreCase("true")){
                        //Select the group by operation
                        selectOperationGroupBy(formulaDetails.getJSONObject(i).getString("formula_variable"), kpiJson.getJSONArray("groupByColumns"));
                    }
                }
                else {
                    logInfo("Formula details for variable not found");
                }

            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectDataAndOperationToPerformVariableC(JSONObject kpiJson){
        Markup m = MarkupHelper.createLabel("selectDataAndOperationToPerformVariableC", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Select dataset option
            clickOnElement(dataSet, "Data set");
            Utils.captureScreen(pageInfo);

            JSONArray formulaDetails = kpiJson.getJSONArray("formula_details");
            for (int i=0; i<formulaDetails.length(); i++) {

                if (formulaDetails.getJSONObject(i).getString("formula_variable").equalsIgnoreCase("C")) {

                    //Select data source
                    selectDataSource(formulaDetails.getJSONObject(i).getString("data_source"));

                    //Select file name
                    selectFileName(formulaDetails.getJSONObject(i).getString("file_name"));

                    //Select column
                    selectColumn(formulaDetails.getJSONObject(i).getString("column_name"));

                    //Select operation
                    selectOperation(formulaDetails.getJSONObject(i).getString("operation"));

                    //Click apply button
                    clickOnElement(applyButton, "Apply button");
                    Utils.captureScreen(pageInfo);

                    //Select time period
                    selectTimePeriod(kpiJson, formulaDetails.getJSONObject(i).getString("formula_variable"));

                    if(kpiJson.getString("applyGroupBy").equalsIgnoreCase("true")){
                        //Select the group by operation
                        selectOperationGroupBy(formulaDetails.getJSONObject(i).getString("formula_variable"), kpiJson.getJSONArray("groupByColumns"));
                    }

                }
                else {
                    logInfo("Formula details for variable not found");
                }

            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectOperationGroupBy(String formulaVariable, JSONArray groupByColumns){
        Markup m = MarkupHelper.createLabel("selectOperationGroupBy", ExtentColor.BLUE);
        pageInfo.info(m);

        try{

            WebElement groupByIcon = driver.findElement(By.xpath("//h3[text()='" + formulaVariable + "']//following-sibling::div//div[@class='searchIconDiv']"));
            clickOnElement(groupByIcon, "Group by icon");

            WebElement groupByColumn;
            //Group by columns to select
            for (int i=0; i<groupByColumns.length(); i++){
                groupByColumn = driver.findElement(By.xpath("//div[@class='search-column-div']//span[text()='" + groupByColumns.get(i).toString() + "']//preceding-sibling::span"));
                clickOnElement(groupByColumn, groupByColumns.get(i).toString());
            }
            Utils.captureScreen(pageInfo);
            clickOnElement(applyButton, "Apply button");

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectOperation(String operation){
        Markup m = MarkupHelper.createLabel("selectOperation", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(operationDropdown, "Operation dropdown");
            WebElement operationOption = driver.findElement(By.xpath("//select[@name='operation']//option[text()='" + operation + "']"));
            clickOnElement(operationOption, operation);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void selectColumn(String columnName){
        Markup m = MarkupHelper.createLabel("selectColumn", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Remove column name input
            setText(columnNameInput, Keys.CONTROL + "a" + Keys.BACK_SPACE, "Clear");
            setText(columnNameInput, columnName, "Column");
            WebElement columnOptionToSelect = driver.findElement(By.xpath("//div[text()='Column']//following::ul//li[text()='" + columnName + "']"));
            clickOnElement(columnOptionToSelect, columnName);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectFileName(String fileName){
        Markup m = MarkupHelper.createLabel("selectFileName", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(fileNameDropdown, "File name dropdown");
            WebElement fileNameOption = driver.findElement(By.xpath("//select[@name='dataset_name']//option[text()='" + fileName + "']"));
            clickOnElement(fileNameOption, fileName);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void selectDataSource(String dataSource){
        Markup m = MarkupHelper.createLabel("selectDataSource", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(dataSourceDropdown, "Data source dropdown");
            WebElement dataSourceOption = driver.findElement(By.xpath("//select[@id='dataSourceDropdown']//option[text()='" + dataSource + "']"));
            clickOnElement(dataSourceOption, dataSource);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public JSONObject getKpiDetails(KpiPageBean kpi, String kpiType){
        Markup m = MarkupHelper.createLabel("getKpiDetails", ExtentColor.BLUE);
        pageInfo.info(m);
        JSONObject detailsOfKpi=null;
        try{

            JSONObject jsObj = new JSONObject(kpi.kpiDetails);
            JSONArray kpiDetails=jsObj.getJSONArray("kpi");
            for(int i=0; i<kpiDetails.length(); i++){
                if(kpiDetails.get(i).toString().contains(kpiType)){
                    detailsOfKpi = kpiDetails.getJSONObject(i).getJSONObject(kpiType);
                    break;
                }
                else {
                    pageInfo.info("Kpi details not found");
                }
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return detailsOfKpi;
    }

}
