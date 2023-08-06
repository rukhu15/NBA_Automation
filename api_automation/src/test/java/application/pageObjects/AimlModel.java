package application.pageObjects;

import java.util.Arrays;
import java.util.List;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import data.beans.AimlModelPageBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.common.Utils;
import framework.utility.globalConst.ConfigInput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;

import framework.utility.common.Assertion;

public class AimlModel extends PageInit{

    @FindBy(xpath = "//button[@id='modules']//span[text()='AI Modules']")
    WebElement aiModulesTab;

    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;

    @FindBy(xpath = "//button//span[text()='Model Building']")
    WebElement modelBuilding;

    @FindBy(xpath = "//button[contains(text(),'New Model')]")
    WebElement newModelButton;

    @FindBy(xpath = "//div[contains(text(), 'Model Name')]//following::input[@type='text']")
    WebElement modelName;

    @FindBy(xpath = "//div[text()='Description']//following::textarea[@name='description']")
    WebElement modelDescription;

    @FindBy(xpath = "//div[@role='button' and contains(@id, 'base_model_selected')]")
    WebElement baseModelDesignDropdown;

    @FindBy(xpath = "//div[@role='button' and contains(@id, 'brand_selected')]")
    WebElement brandModelDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Create')]")
    WebElement createButton;

    @FindBy(xpath = "//div[@class='header-close-icon']")
    WebElement closeNewModelConfigurationWindow;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    WebElement nextButton;

    @FindBy(xpath = "//h2[text()='CHANNELS']//following-sibling::div//button")
    WebElement channelsDropdown;

    @FindBy(xpath = "//h2[text()='CONTROL VARIABLES']//following-sibling::div//button")
    WebElement controlVariablesDropdown;

    @FindBy(xpath = "//div[@class='multiselectDropdown']//div[text()='Select All']")
    WebElement checkBoxSelectAll;

    @FindBy(xpath = "//button[contains(text(), 'Run Model')]")
    WebElement runModelButton;

    @FindBy(xpath = "//button//span[contains(text(), 'Continue')]")
    WebElement continueButton;

    @FindBy(xpath = "//button//span[contains(text(), 'Cancel')]")
    WebElement cancelButton;

    @FindBy(xpath = "//div[@role='button' and contains(@id, 'choose-best-scenario')]")
    WebElement scenarioDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    WebElement saveButton;

    @FindBy(xpath = "//nav[@id='sideMenu']//button")
    WebElement navBarExpandCollapseButton;

    @FindBy(xpath = "//button[contains(text(), 'Back')]")
    WebElement backButton;

    @FindBy(xpath = "//div[contains(@class, 'scenario-selection-wrapper')]//div[contains(@class, 'MuiAlert-message')]")
    WebElement textScenarioSelectionScreen;

    @FindBy(xpath = "//div[@class='title-text']")
    WebElement pageTitle;

    @FindBy(xpath = "//span[text()='Model Building']//ancestor::button[contains(@class, 'tab__item')]")
    WebElement tabModelBuilding;

    @FindBy(xpath = "//div[@class='aiml-datasets-tables-div']//div[@role='grid']")
    WebElement scoringDatasetTable;

    @FindBy(xpath = "//div[@class='dataset-info']")
    WebElement noteDatasetsScreen;

    @FindBy(xpath = "//div[@class='table-header']//*[name()='svg']")
    WebElement scoringDatasetHelpText;

    @FindBy(xpath = "//button[contains(text(), 'Exit')]")
    WebElement exitButton;

    @FindBy(xpath = "//div[contains(@class, 'scenarioSelectionGrid')]//div[@role='grid']")
    WebElement allRunScenariosTable;

    @FindBy(xpath = "//div[@class='modelparam-algo-container']//div[@role='grid']")
    WebElement modelParametersTable;

    @FindBy(xpath = "(//div[@class='ai-savedmodelstab-section']//div[@class='header-text'])[1]")
    WebElement inUseModelInstancesTableLabel;

    @FindBy(xpath = "(//div[@class='ai-savedmodelstab-section']//div[@class='header-text'])[2]")
    WebElement readyToUseModelInstancesTableLabel;

    @FindBy(xpath = "//div[@class='ai-model-actions-ellipsis']//ul//li[text()='Use Model']")
    WebElement useModelButton;

    @FindBy(xpath = "//div[contains(@class, 'brand-financials-div')]")
    WebElement brandFinancialSection;

    @FindBy(xpath = "//div[contains(@class, 'promotional-cost-div')]")
    WebElement promotionalCostSection;

    private static ExtentTest pNode;
    public AimlModel(ExtentTest t1) {
        super(t1);
    }

    public static AimlModel init(ExtentTest t1) throws Exception {
        pNode = t1;
        return new AimlModel(pNode);
    }

    public JSONObject getModelDetails(AimlModelPageBean aiml, String modelType){
        Markup m = MarkupHelper.createLabel("getModelDetails", ExtentColor.BLUE);
        pageInfo.info(m);
        JSONObject detailsOfModel=null;
        try{

            JSONObject jsObj = new JSONObject(aiml.modelDetails);
            JSONArray modelDetails=jsObj.getJSONArray("model");
            for(int i=0; i<modelDetails.length(); i++){
                if(modelDetails.get(i).toString().contains(modelType)){
                    detailsOfModel = modelDetails.getJSONObject(i).getJSONObject(modelType);
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
        return detailsOfModel;
    }

    public void openAiModulesTabAndSelectModule(String aimlModuleToBeUsd){
        Markup m = MarkupHelper.createLabel("openAiModulesTabAndSelectModule", ExtentColor.BLUE);
        pageInfo.info(m);

        try{

            closeToastMessages();

            //Navigate to aiml models through side navbar
            openSideNavItem(navBarExpandCollapseButton, true, "Models");

            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);

            //Open ai modules tab
            clickOnElement(aiModulesTab, "Ai Modules");
            Utils.captureScreen(pageInfo);

            //Click on the module to open to be used to create model
            WebElement aimlModuleToCreateModel = driver.findElement(By.xpath("//div[@class='module-card-box']//div[text()='" + aimlModuleToBeUsd + "']"));
            clickOnElement(aimlModuleToCreateModel, aimlModuleToBeUsd);

            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

            //Navigate to model building
            clickOnElement(modelBuilding, "Model building");

            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void openNewModelFormAndCreate(JSONObject modelDetails){
        Markup m = MarkupHelper.createLabel("openNewModelFormAndCreate", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Click on new button
            clickOnNewButton();
            //Click on radio button of how to create new model
            clickOnRadioButtonModelConfiguration(modelDetails.getString("option_for_creating_new_model"));
            //Set model name
            setModelName(modelDetails.getString("model_name"));

            //Check if model name is duplicate by checking if warning text is displayed
            checkIfModelExistsAndDelete(modelDetails);

            //Set model description
            setModelDescription(modelDetails.getString("model_description"));
            //Select base model design option
            selectBaseModelDesignOption(modelDetails.getString("base_model_design"));
            //Select brand model option
            selectBrandModelOption(modelDetails.getString("brand"));
            //Click on create button
            clickOnCreateButton();
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkIfModelExistsAndDelete(JSONObject modelDetails){
        Markup m = MarkupHelper.createLabel("checkIfModelExistsAndDelete", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            List<WebElement> modelNameExists = driver.findElements(By.xpath("//div[@class='error-div']"));
            int count = modelNameExists.size();
            Assertion.verifyEqual(count == 1, true, "CIQ-TC-3368 To verify Model names cannot be duplicated with in a workspaces", pageInfo);

            if(count >= 1){
                deleteModelFromDataBase(modelDetails.getString("model_name"));
                //Close new model configuration window
                closeModelConfigurationWindow();
                //Click on new button
                clickOnNewButton();
                //Click on radio button of how to create new model
                clickOnRadioButtonModelConfiguration(modelDetails.getString("option_for_creating_new_model"));
                //Set model name
                setModelName(modelDetails.getString("model_name"));
            }
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnNewButton(){
        Markup m = MarkupHelper.createLabel("clickOnNewButton", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Click on new button
            clickOnElement(newModelButton, "New model");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void closeModelConfigurationWindow(){
        Markup m = MarkupHelper.createLabel("closeModelConfigurationWindow", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            clickOnElement(closeNewModelConfigurationWindow, "Close");
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnCreateButton(){
        Markup m = MarkupHelper.createLabel("clickOnCreateButton", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            clickOnElement(createButton, "Create");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectBaseModelDesignOption(String baseModelDesign){
        Markup m = MarkupHelper.createLabel("selectBaseModelDesignOption", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            clickOnElement(baseModelDesignDropdown, "Base model design dropdown");
            WebElement baseModelDesignOption = driver.findElement(By.xpath("//div[@id='menu-base_model_selected']//ul//li[text()='" + baseModelDesign + "']"));
            clickOnElement(baseModelDesignOption, baseModelDesign);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectBrandModelOption(String brand){
        Markup m = MarkupHelper.createLabel("selectBrandModelOption", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            clickOnElement(brandModelDropdown, "Brand model dropdown");
            WebElement brandModelOption = driver.findElement(By.xpath("//div[@id='menu-brand_selected']//ul//li[text()='" + ConfigInput.api_brandName + "']"));
            clickOnElement(brandModelOption, brand);
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnRadioButtonModelConfiguration(String optionForCreatingNewModel){
        Markup m = MarkupHelper.createLabel("clickOnRadioButtonModelConfiguration", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            WebElement radioButton = driver.findElement(By.xpath("//div[@class='radio-item-text' and contains(text(), '" + optionForCreatingNewModel + "')]//preceding-sibling::span"));
            clickOnElement(radioButton, optionForCreatingNewModel);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void setModelName(String modelNameAiml){
        Markup m = MarkupHelper.createLabel("setModelName", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            setText(modelName, modelNameAiml, "Model name");
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void setModelDescription(String modelDescriptionAiml){
        Markup m = MarkupHelper.createLabel("setModelDescription", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            setText(modelDescription, modelDescriptionAiml, "Model description");
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void navigateToVariablesScreen(){
        Markup m = MarkupHelper.createLabel("navigateToVariablesScreen", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            //Click on next button
            clickOnElement(nextButton, "Next");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

            //Verify user is navigated to variables screen
            WebElement variablesScreenStepper = driver.findElement(By.xpath("//div[contains(text(), 'Variables')]//ancestor::button"));
            Assertion.verifyEqual(elementIsDisplayed(controlVariablesDropdown) && variablesScreenStepper.getAttribute("data-progress").equalsIgnoreCase("true"), true, "CIQ-TC-3296 To verify user is navigated to Variables section when user clicks on Next button on dataset screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectChannels(){
        Markup m = MarkupHelper.createLabel("selectChannels", ExtentColor.BLUE);
        pageInfo.info(m);

        try{

            //Open channels dropdown
            clickOnElement(channelsDropdown, "Channels dropdown opened");
            Utils.captureScreen(pageInfo);

            //Uncheck all channels
            clickOnElement(checkBoxSelectAll, "Select all");
            Utils.captureScreen(pageInfo);

            //Select channel at the top
            WebElement channelSelected = driver.findElement(By.xpath("(//div[@class='multiselectDropdown']//div//input[@type='checkbox']//parent::div)[2]"));
            clickOnElement(channelSelected, "Channel selected");
            Utils.captureScreen(pageInfo);

            //Close channels dropdown
            clickOnElement(channelsDropdown, "Channels dropdown closed");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectControlVariables(){
        Markup m = MarkupHelper.createLabel("selectControlVariables", ExtentColor.BLUE);
        pageInfo.info(m);

        try{

            //Count control variables selected by default
            List<WebElement> controlVariablesSelectedByDefault = driver.findElements(By.xpath("//h2[text()='CONTROL VARIABLES']//following-sibling::div//div[@role='button']"));
            int countOfControlVariablesByDefault = controlVariablesSelectedByDefault.size();

            //Open control variables dropdown
            clickOnElement(controlVariablesDropdown, "Control variables dropdown opened");
            Utils.captureScreen(pageInfo);

            //Uncheck all control variables
            clickOnElement(checkBoxSelectAll, "Select all");
            Utils.captureScreen(pageInfo);

            //Select control variable at the top
            WebElement controlVariableSelected = driver.findElement(By.xpath("(//div[@class='multiselectDropdown']//div//input[@type='checkbox']//parent::div)[2]"));
            clickOnElement(controlVariableSelected, "Control variable selected");
            Utils.captureScreen(pageInfo);

            //Close control variables dropdown
            clickOnElement(controlVariablesDropdown, "Control variables dropdown closed");
            Utils.captureScreen(pageInfo);

            //Count control variables selected after removing and adding control variables
            List<WebElement> controlVariablesSelectedByUser = driver.findElements(By.xpath("//h2[text()='CONTROL VARIABLES']//following-sibling::div//div[@role='button']"));
            int countOfControlVariablesSelectByUser = controlVariablesSelectedByUser.size();

            Assertion.verifyEqual(countOfControlVariablesByDefault != countOfControlVariablesSelectByUser, true, "CIQ-TC-3328 To verify user is able to create a Model i.e. Model1, and able to drop and add  the Control variables on Variable screen", pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnExitButton(){
        Markup m = MarkupHelper.createLabel("clickOnExitButton", ExtentColor.BLUE);
        pageInfo.info(m);

        try{

            //Click on exit button
            clickOnElement(exitButton, "Exit button");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

            //Click on continue button
            clickOnElement(continueButton, "Continue");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectAlgorithm(String baseAlgorithm){
        Markup m = MarkupHelper.createLabel("selectAlgorithm", ExtentColor.BLUE);
        pageInfo.info(m);

        try{

//            //Select the type of algorithm
//            WebElement algorithmAiml = driver.findElement(By.xpath("//span[text()='" + baseAlgorithm + "']//preceding-sibling::span//input"));
//            clickOnElement(algorithmAiml, baseAlgorithm);
//            Utils.captureScreen(pageInfo);

//            if(baseAlgorithm.equals("XG Boost Decision Tree")) {
//                //Check count of columns in xg boost decision tree table and loop through each column to verify its uneditable
//                List<WebElement> xgBoostDecisionTreeTableColumns = driver.findElements(By.xpath("//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText']"))
//                int countOfColumns = xgBoostDecisionTreeTableColumns.size()
//
//                for(int i=0; i<countOfColumns; i++) {
//                    WebElement eachColumn = driver.findElement(By.xpath("(//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText'])[" + (i+1) + "]"))
//                    //CIQ_TC_3347 To verify the  columns displayed when user clicks on radio button “XG Boost Decision Tree
//                    //CIQ_TC_3349 To verify none of the columns are editable for XG Boost Decision Tree Algorithm
//                    WebUI.verifyEqual(eachColumn.isDisplayed() && eachColumn.getAttribute("unselectable").equalsIgnoreCase("on"), true)
//                }
//
//                algorithmAiml = driver.findElement(By.xpath("//span[text()='Random Forest Predictor']//preceding-sibling::span//input"))
//                algorithmAiml.click()
//
//                'Check count of columns in random forest predictor table and loop through each column to verify its uneditable'
//                List<WebElement> randomForestPredictorTableColumns = driver.findElements(By.xpath("//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText']"))
//                countOfColumns = randomForestPredictorTableColumns.size()
//
//                for(int i=0; i<countOfColumns; i++) {
//                    WebElement eachColumn = driver.findElement(By.xpath("(//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText'])[" + (i+1) + "]"))
//                    'CIQ_TC_3346 To verify the  columns displayed when user clicks on radio button "Random Forest Predictor"'
//                    'CIQ_TC_3348 To verify none of the columns are editable for Random Forest Algorithm'
//                    WebUI.verifyEqual(eachColumn.isDisplayed() && eachColumn.getAttribute("unselectable").equalsIgnoreCase("on"), true)
//                }
//            }
//            else {
//                'Check count of columns in random forest predictor table and loop through each column to verify its uneditable'
//                List<WebElement> randomForestPredictorTableColumns = driver.findElements(By.xpath("//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText']"))
//                int countOfColumns = randomForestPredictorTableColumns.size()
//
//                for(int i=0; i<countOfColumns; i++) {
//                    WebElement eachColumn = driver.findElement(By.xpath("(//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText'])[" + (i+1) + "]"))
//                    'CIQ_TC_3346 To verify the  columns displayed when user clicks on radio button "Random Forest Predictor"'
//                    'CIQ_TC_3348 To verify none of the columns are editable for Random Forest Algorithm'
//                    WebUI.verifyEqual(eachColumn.isDisplayed() && eachColumn.getAttribute("unselectable").equalsIgnoreCase("on"), true)
//                }
//
//                algorithmAiml = driver.findElement(By.xpath("//span[text()='XG Boost Decision Tree']//preceding-sibling::span//input"))
//                algorithmAiml.click()
//
//                'Check count of columns in xg boost decision tree table and loop through each column to verify its uneditable'
//                List<WebElement> xgBoostDecisionTreeTableColumns = driver.findElements(By.xpath("//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText']"))
//                countOfColumns = xgBoostDecisionTreeTableColumns.size()
//
//                for(int i=0; i<countOfColumns; i++) {
//                    WebElement eachColumn = driver.findElement(By.xpath("(//div[@class='aiml-modelparam-step']//div[@role='columnheader']//span[@ref='eText'])[" + (i+1) + "]"))
//                    'CIQ_TC_3347 To verify the  columns displayed when user clicks on radio button “XG Boost Decision Tree“'
//                    'CIQ_TC_3349 To verify none of the columns are editable for XG Boost Decision Tree Algorithm'
//                    WebUI.verifyEqual(eachColumn.isDisplayed() && eachColumn.getAttribute("unselectable").equalsIgnoreCase("on"), true)
//                }
//            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnRunModel(){
        Markup m = MarkupHelper.createLabel("clickOnRunModel", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            //Click on run model button
            clickOnElement(runModelButton, "Run model");
            waitTillDisappearingOfSpinner();

            //Check progress %
            WebElement progressBarPercentage = driver.findElement(By.xpath("//div[@class='run-model-popper-content']//p"));

            //Get % progress
            int progressPercent = Integer.parseInt(progressBarPercentage.getText().replaceAll("[^0-9]", ""));

            int i=0;

            //Wait until progress is 100% to move to scenario selection'
            while (progressPercent != 100) {
                progressPercent = Integer.parseInt(progressBarPercentage.getText().replaceAll("[^0-9]", ""));
                Utils.putThreadSleep(6000);
                i++;

                //Run is failed if it is not completed before 10 minutes'
                if(i>=100) {
                    pageInfo.fail("Run is failed");
                    break;
                }
            }
            Assertion.verifyEqual(elementIsClickable(continueButton), true, "CIQ-TC-3360 To verify user is able to click on Continue button of pop up displayed on Model parameter screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnContinueButton(){
        Markup m = MarkupHelper.createLabel("clickOnContinueButton", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            //Click on continue button
            clickOnElement(continueButton, "Continue");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void navigateToModelParametersScreen(){
        Markup m = MarkupHelper.createLabel("navigateToModelParametersScreen", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            //Click on next button
            if(elementIsClickable(nextButton)) {
                clickOnElement(nextButton, "Next");
                waitTillDisappearingOfSpinner();
                Assertion.verifyEqual(elementIsDisplayed(modelParametersTable), true, "CIQ-TC-3340 To verify when user is on the Variable screen , user can navigated back to Model Parameters screen only by clicking on Next button present  on Variable screen", pageInfo);
            }
            else {
                pageInfo.fail("Next button is not clickable");
            }
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectScenario(){
        Markup m = MarkupHelper.createLabel("selectScenario", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            //Click on scenario dropdown
            clickOnElement(scenarioDropdown, "Scenario dropdown");
            Utils.captureScreen(pageInfo);

            //Choose scenario option
            WebElement scenarioOption = driver.findElement(By.xpath("(//ul[contains(@aria-labelledby, 'choose-best-scenario')]//li)[2]"));
            clickOnElement(scenarioOption, "Scenario option");
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnSaveButtonOnRoiInputsScreen(){
        Markup m = MarkupHelper.createLabel("clickOnSaveButtonOnRoiInputsScreen", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            int i=0;
            while(!elementIsClickable(saveButton) || i <= 20){
                Utils.putThreadSleep(500);
                i++;
            }

            //Click on save button
            clickOnElement(saveButton, "Save button");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

            Assertion.verifyEqual(elementIsClickable(continueButton) && elementIsClickable(cancelButton), true, "CIQ-TC-3710 To verify user is able to click on Continue/Cancel button of pop up displayed on ROI input screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnBack(){
        Markup m = MarkupHelper.createLabel("clickOnBack", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            Utils.captureScreen(pageInfo);
            //Click on back button
            clickOnElement(backButton, "Back");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkBackIsDisabled(){
        Markup m = MarkupHelper.createLabel("checkBackIsDisabled", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            Assertion.verifyEqual(elementIsEnabled(backButton), false, "CIQ-TC-3444 CIQ-TC-3448 To verify  back button is disabled and user cannot click on it,  on ROI Input screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyModelDetailsModelBuildingPage(String screenType, JSONObject modelDetails){
        Markup m = MarkupHelper.createLabel("verifyModelDetailsModelBuildingPage", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            //Verify user is directed to model building of channel recommendation page
            Assertion.verifyEqual(getText(pageTitle).equalsIgnoreCase(modelDetails.getString("module")) && tabModelBuilding.getAttribute("aria-selected").equalsIgnoreCase("true"), true, "CIQ-TC-3298 To verify user is able to click on Back button and user is directed to Channel Recommendation module → Model Building  tab section", pageInfo);

            //Check status of model
            WebElement statusModel = driver.findElement(By.xpath("//div[text()='" + modelDetails.getString("model_name") + "']//ancestor::div[@role='row']//div[@col-id='status_name']//span"));
            if(screenType.equalsIgnoreCase("Datasets") || screenType.equalsIgnoreCase("Variables") || screenType.equalsIgnoreCase("Variables") || screenType.equalsIgnoreCase("Scenario Selection")) {
                Assertion.verifyEqual(getText(statusModel).equalsIgnoreCase("EDITING"), true, "CIQ-TC-3299 CIQ-TC-3329 CIQ-TC-3355 CIQ-TC-3419 To verify the Status of Model when  click on Back button and is on Recommendation module →Model Building table", pageInfo);
            }
            else {
                pageInfo.info("Roi inputs");
            }
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnModelNameLink(String tabName, String modelName){
        Markup m = MarkupHelper.createLabel("clickOnModelNameLink", ExtentColor.BLUE);
        pageInfo.info(m);

        try{
            //Click on model name link
            WebElement modelToBeOpened = driver.findElement(By.xpath("//div[text()='" + modelName + "'and @class='hyperlink-text']"));
            clickOnElement(modelToBeOpened, modelName);
            waitTillDisappearingOfSpinner();

            //Verify user is navigated to specific screen
            WebElement activeTab = driver.findElement(By.xpath("//div[contains(text(), '" + tabName + "')]//ancestor::button"));
            if(tabName.equalsIgnoreCase("Datasets")) {
                Assertion.verifyEqual(activeTab.getAttribute("data-progress").equalsIgnoreCase("true") && elementIsDisplayed(scoringDatasetTable), true, "CIQ-TC-3300 To verify user is navigated to Dataset screen when user clicks on Model name/View Icon, Only when user is on step1 i.e. Dataset screen of Model configuration", pageInfo);
            }
            else if(tabName.equalsIgnoreCase("Variables")){
                Assertion.verifyEqual(activeTab.getAttribute("data-progress").equalsIgnoreCase("true") && elementIsDisplayed(controlVariablesDropdown), true, "CIQ-TC-3319 To verify user is navigated to Variable screen when user clicks on Model name/View Icon, Only when user is on step2 i.e. Variable screen of Model configuration", pageInfo);
            }
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyUiElementsOfDatasetsScreen(){
        Markup m = MarkupHelper.createLabel("verifyUiElementsOfDatasetsScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Verify steps show on datasets screen
            verifyStepperShowsOnDatasetsScreen();

            //Verify note displayed on datasets screen
            verifyNoteDisplayedOnDatasetsScreen();

            //Verify user is able to see scoring dataset table
            verifyScoringDatasetTableDisplayedOnDatasetsScreen();

            //Check for columns of training dataset table'
            verifyColumnsInTrainingDatasetTable();

            //Hover on help text icon of scoring dataset and extract text
            verifyHelpTextOfScoringDataset();
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyUiElementsOfScenarioSelectionScreen(String tabName){
        Markup m = MarkupHelper.createLabel("verifyUiElementsOfScenarioSelectionScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Check text displayed on datasets screen
            checkTextDisplayedOnScenarioSelectionScreen();

            //Check exit and back are disabled in scenario selection screen
            checkExitAndBackAreDisabledOnScenarioSelection();

            //Check user is able to see the dropdown of scenario
            checkDropOfScenarioPresent();

            //Check user is navigated to scenario selection screen
            checkUserIsOnScenarioSelectionScreen(tabName);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyUiElementsOfRoiInputsScreen(){
        Markup m = MarkupHelper.createLabel("verifyUiElementsOfRoiInputsScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Check back is disabled on roi inputs screen
            checkBackIsDisabled();

            //Check brand financial and promotional cost sections are present on ROI inputs screen
            checkSectionsOnRoiInputsScreen();
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkSectionsOnRoiInputsScreen(){
        Markup m = MarkupHelper.createLabel("checkSectionsOnRoiInputsScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            Assertion.verifyEqual(elementIsDisplayed(brandFinancialSection) && elementIsDisplayed(promotionalCostSection), true, "CIQ-TC-3614 To verify two sections Brand Financial. and Promotional Cost are displayed on ROI input screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyUiElementsOfModelParametersScreen(String tabName){
        Markup m = MarkupHelper.createLabel("verifyUiElementsOfModelParametersScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Check user is navigated to model parameters screen
            checkUserIsOnModelParametersScreen(tabName);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void checkDropOfScenarioPresent(){
        Markup m = MarkupHelper.createLabel("checkDropOfScenarioPresent", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            Assertion.verifyEqual(elementIsDisplayed(scenarioDropdown), true, "CIQ-TC-3414 To verify the  user is able to see the Scenario Number drop down in the Choose the best model section on Scenario selection screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkUserIsOnScenarioSelectionScreen(String tabName){
        Markup m = MarkupHelper.createLabel("checkUserIsOnScenarioSelectionScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            WebElement activeTab = driver.findElement(By.xpath("//div[contains(text(), '" + tabName + "')]//ancestor::button"));
            Assertion.verifyEqual(elementIsDisplayed(allRunScenariosTable) && activeTab.getAttribute("data-progress").equalsIgnoreCase("true"), true, "CIQ-TC-3367 To verify user is navigated to Scenario selection screen when user clicks on Model name/View Icon, Only when user is on step4 i.e. Scenario selection screen of Model configuration", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkUserIsOnModelParametersScreen(String tabName){
        Markup m = MarkupHelper.createLabel("checkUserIsOnModelParametersScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            WebElement activeTab = driver.findElement(By.xpath("//div[contains(text(), '" + tabName + "')]//ancestor::button"));
            Assertion.verifyEqual(elementIsDisplayed(modelParametersTable) && activeTab.getAttribute("data-progress").equalsIgnoreCase("true"), true, "CIQ-TC-3341 To verify user is navigated to Model parameter screen when user clicks on Model name/View Icon, Only when user is on step3 i.e. Model parameter screen of Model configuration", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyUiElementsOfSavedModels(JSONObject modelDetails){
        Markup m = MarkupHelper.createLabel("verifyUiElementsOfSavedModels", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Check in use model instances brands cant be duplicated
            checkSavedModelsBrandsInUseModelInstances();

            //Check in ready to use model instances brands can be duplicated
            checkSavedModelsBrandsInReadyToUseModelInstances();

            //Check table labels in saved models
            checkTableLabel();

            }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnUseModel(JSONObject modelDetails){
        Markup m = MarkupHelper.createLabel("clickOnUseModel", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on actions option
            WebElement actionsOptionPresentModel = driver.findElement(By.xpath("//div[text()='" + modelDetails.getString("model_name") + "']//ancestor::div[@role='row']//div[@class='seg-vellipsis-icon']//*[local-name()='svg']"));
            clickOnElement(actionsOptionPresentModel, "Actions");

            //Click on use model button
            Assertion.verifyEqual(elementIsClickable(useModelButton), true, "CIQ-TC-3213 To verify user is able to click on Use Model option present in the ellipses of Ready to Use Model", pageInfo);
            clickOnElement(useModelButton, "Use model");
            Utils.captureScreen(pageInfo);

            //Click on continue button
            clickOnContinueButton();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkSavedModelsBrandsInUseModelInstances(){
        Markup m = MarkupHelper.createLabel("checkSavedModelsBrandsInUseModelInstances", ExtentColor.BLUE);
        pageInfo.info(m);
        List<String> brandsOfModelsInUseModelInstances = Arrays.asList("");
        boolean duplicateBrandNameInUseModelInstances = true;
        try{
            List<WebElement> inUseModelInstances = driver.findElements(By.xpath("(//div[contains(@class,'other-models-table')]//div[@role='grid'])[1]//div[@name='center']//div[@col-id='brand_name']"));
            for (int i=0; i<inUseModelInstances.size(); i++){
                WebElement brandsOfModel = driver.findElement(By.xpath("((//div[contains(@class,'other-models-table')]//div[@role='grid'])[1]//div[@name='center']//div[@col-id='brand_name'])[" + (i+1) + "]"));
                brandsOfModelsInUseModelInstances.add(getText(brandsOfModel));
                if(brandsOfModelsInUseModelInstances.contains(getText(brandsOfModel))){
                    duplicateBrandNameInUseModelInstances = true;
                    break;
                }
                else {
                    duplicateBrandNameInUseModelInstances = false;
                }
            }
            for (int i=0; i<brandsOfModelsInUseModelInstances.size(); i++){
                System.out.println(brandsOfModelsInUseModelInstances.get(i));
            }
            Assertion.verifyEqual(duplicateBrandNameInUseModelInstances, false, "CIQ-TC-3247 To verify two Model cannot have same brand in In Use table on Saved Draft screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkTableLabel(){
        Markup m = MarkupHelper.createLabel("checkTableLabels", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Check in use model instance table label
            Assertion.verifyEqual(getText(inUseModelInstancesTableLabel).trim().equalsIgnoreCase("In Use Model Instances"), true, "CIQ-TC-3396 Testcase to validate in saved models: Use Models label should change to In Use Model Instance", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkSavedModelsBrandsInReadyToUseModelInstances(){
        Markup m = MarkupHelper.createLabel("checkSavedModelsBrandsInReadyToUseModelInstances", ExtentColor.BLUE);
        pageInfo.info(m);
        List<String> brandsOfModelsInReadyToUseModelInstances = Arrays.asList("");
        boolean duplicateBrandNameInReadyToUseModelInstances = false;
        try{
            List<WebElement> readyToUseModelInstances = driver.findElements(By.xpath("(//div[contains(@class,'other-models-table')]//div[@role='grid'])[2]//div[@name='center']//div[@col-id='brand_name']"));
            for (int i=0; i<readyToUseModelInstances.size(); i++){
                WebElement brandsOfModel = driver.findElement(By.xpath("((//div[contains(@class,'other-models-table')]//div[@role='grid'])[2]//div[@name='center']//div[@col-id='brand_name'])[" + (i+1) + "]"));
                brandsOfModelsInReadyToUseModelInstances.add(getText(brandsOfModel));
                if(brandsOfModelsInReadyToUseModelInstances.contains(getText(brandsOfModel))){
                    duplicateBrandNameInReadyToUseModelInstances = true;
                    break;
                }
                else {
                    duplicateBrandNameInReadyToUseModelInstances = false;
                }
            }
            for (int i=0; i<brandsOfModelsInReadyToUseModelInstances.size(); i++){
                System.out.println(brandsOfModelsInReadyToUseModelInstances.get(i));
            }
            Assertion.verifyEqual(duplicateBrandNameInReadyToUseModelInstances, true, "CIQ-TC-3248 To verify two Model with same brand can be present in Ready to use Models table", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyHelpTextOfScoringDataset(){
        Markup m = MarkupHelper.createLabel("verifyHelpTextOfScoringDataset", ExtentColor.BLUE);
        pageInfo.info(m);
        String helpTextScoringDataset = "";
        try{
            Actions builder=new Actions(driver);
            builder.moveToElement(scoringDatasetHelpText).perform();
            helpTextScoringDataset = scoringDatasetTable.getAttribute("aria-label");
            Assertion.verifyEqual(helpTextScoringDataset.equalsIgnoreCase("Normalized dataset that will be used for further score generation"), true, "CIQ-TC-3305 To verify the help text of Scoring and Training tables on Dataset screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyColumnsInTrainingDatasetTable(){
        Markup m = MarkupHelper.createLabel("verifyColumnsInTrainingDatasetTable", ExtentColor.BLUE);
        pageInfo.info(m);
        boolean isScoringDatasetTableColumnPresent = false;
        try{
            //Expected columns in scoring dataset table
            List<String> expectedColumnsScoringDatasetTable = Arrays.asList("DATA SET NAME", "DESCRIPTION", "GRANULARITY", "FREQUENCY", "LAST UPDATED");
            //Check for columns of training dataset table'
            List<WebElement> actualScoringDatasetTableColumns = driver.findElements(By.xpath("//div[@class='table-header']//following-sibling::div//span[@ref='eText']"));
            int numberActualOfColumns = actualScoringDatasetTableColumns.size();
            for(int i=0; i<numberActualOfColumns; i++) {
                WebElement eachColumn = driver.findElement(By.xpath("(//div[@class='table-header']//following-sibling::div//span[@ref='eText'])[" + (i+1) + "]"));
                if(elementIsDisplayed(eachColumn) && expectedColumnsScoringDatasetTable.contains(getText(eachColumn))){
                    isScoringDatasetTableColumnPresent = true;
                }
                else {
                    isScoringDatasetTableColumnPresent = false;
                    break;
                }
                pageInfo.info("Scoring dataset table column name " + getText(eachColumn));
            }
            //Verify scoring dataset table columns
            Assertion.verifyEqual(isScoringDatasetTableColumnPresent, true, "CIQ-TC-3307 To verify the columns displayed in Training Dataset table when user is on dataset screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyNoteDisplayedOnDatasetsScreen(){
        Markup m = MarkupHelper.createLabel("verifyNoteDisplayedOnDatasetsScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            Assertion.verifyEqual(elementIsDisplayed(noteDatasetsScreen) && !getText(noteDatasetsScreen).isEmpty(), true, "CIQ-TC-3303 To verify a note is always displayed on Dataset screen and user cannot close it", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkTextDisplayedOnScenarioSelectionScreen(){
        Markup m = MarkupHelper.createLabel("checkTextDisplayedOnScenarioSelectionScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            Assertion.verifyEqual(elementIsDisplayed(textScenarioSelectionScreen) && !getText(textScenarioSelectionScreen).isEmpty(), true, "CIQ-TC-3363 To verify the text displayed when user is on Scenario selection screen and user cannot close it", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkExitAndBackAreDisabledOnScenarioSelection(){
        Markup m = MarkupHelper.createLabel("checkExitAndBackAreDisabledOnScenarioSelection", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            Assertion.verifyEqual(!elementIsEnabled(backButton) && !elementIsEnabled(exitButton), true, "CIQ-TC-3412 CIQ-TC-3526 To verify user is not able to navigate to previous screen of the Model created during Model run or Model run is successful", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyScoringDatasetTableDisplayedOnDatasetsScreen(){
        Markup m = MarkupHelper.createLabel("verifyScoringDatasetTableDisplayedOnDatasetsScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            Assertion.verifyEqual(elementIsDisplayed(scoringDatasetTable), true, "CIQ-TC-3304 To verify user is able to see 2 tables when user is on Dataset screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyStepperShowsOnDatasetsScreen(){
        Markup m = MarkupHelper.createLabel("verifyStepperShowsOnDatasetsScreen", ExtentColor.BLUE);
        pageInfo.info(m);
        boolean stepsPresentOnDatasets = false;
        try{
            List<WebElement> modelFlowSteps = driver.findElements(By.xpath("//div[@class='aimlmodelstepper']//button[contains(@class, 'mantine-Stepper-step')]"));
            int countSteps = modelFlowSteps.size();
            for(int i=0; i<countSteps; i++) {
                WebElement eachStep = driver.findElement(By.xpath("//div[@class='aimlmodelstepper']//button[contains(@class, 'mantine-Stepper-step')][" + (i+1) + "]"));
                if(elementIsDisplayed(eachStep)){
                    stepsPresentOnDatasets = true;
                }
                else {
                    stepsPresentOnDatasets = false;
                    break;
                }
            }
            Assertion.verifyEqual(stepsPresentOnDatasets, true, "CIQ-TC-3295 To verify the Steps displayed when user  clicks on Create button and is on Dataset screen", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteModelFromDataBase(String modelName){
        int modelID = DatabaseUtil.getModelIdForDeletion(ConfigInput.aimlSchemaName, modelName);
        DatabaseUtil.deleteRecordsBasedOnModelId(ConfigInput.aimlSchemaName, String.valueOf(modelID));
        DatabaseUtil.closeDBResources();
    }

    public void waitTillDisappearingOfSpinner(){
        // Wait till the disappearing of spinner
        while(elementIsDisplayed(spinner)){
            Utils.putThreadSleep(100);
        }
    }
}
