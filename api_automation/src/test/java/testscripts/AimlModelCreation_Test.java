package testscripts;

import application.pageObjects.AimlModel;
import data.beans.AimlModelPageBean;
import dataProvidersPackage.UIDP;
import org.json.JSONObject;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import framework.utility.common.Assertion;
import scripts.TestInit;

public class AimlModelCreation_Test extends TestInit{

    @Test(priority = 1, groups = {"SANITY"}, enabled = true, dataProvider = "createModel", dataProviderClass = UIDP.class)
    public void Aiml_Model_Creation(AimlModelPageBean aimlBean) {
        ExtentTest t1 = pNode.createNode("Aiml_Model_Creation_New_Model_Configuration", "Create end to end aiml model starting from navigating to model building screen and completing basic setup of new model");
        JSONObject modelDetails=null;
        try {

            AimlModel aiModelPage = AimlModel.init(t1);

            //Get the details of kpi to create
            modelDetails = aiModelPage.getModelDetails(aimlBean,"ootb_reconfigured");

            //Open ai modules tab and select Channel Recommendation (Sales Lift)
            aiModelPage.openAiModulesTabAndSelectModule(modelDetails.getString("module"));

            //Create new model
            aiModelPage.openNewModelFormAndCreate(modelDetails);

        } catch (Exception e) {
            markTestAsFailure(e, t1);
        }

        ExtentTest t2 = pNode.createNode("Aiml_Model_Creation_Datasets_Screen", "Open datasets screen");
        try {

            AimlModel aiModelPage = AimlModel.init(t2);

            //Get the details of model to create
            modelDetails = aiModelPage.getModelDetails(aimlBean,"ootb_reconfigured");

            //Verify ui elements of datasets screen
            aiModelPage.verifyUiElementsOfDatasetsScreen();

            //Click on back button
            aiModelPage.clickOnBack();

            //Verify details of model on the model building screen
            aiModelPage.verifyModelDetailsModelBuildingPage("Datasets", modelDetails);

            //Click on model name link on model building screen to navigate to specific tab
            aiModelPage.clickOnModelNameLink("Datasets", modelDetails.getString("model_name"));

            //On datasets screen click on next button in order to navigate to variables screen
            aiModelPage.navigateToVariablesScreen();

        } catch (Exception e) {
            markTestAsFailure(e, t2);
        }

        ExtentTest t3 = pNode.createNode("Aiml_Model_Creation_Variables_Screen", "Open the variables screen");
        try {

            AimlModel aiModelPage = AimlModel.init(t3);

            //Get the details of kpi to create
            modelDetails = aiModelPage.getModelDetails(aimlBean,"ootb_reconfigured");

            //Select channels
            aiModelPage.selectChannels();

            //Select control variables
            aiModelPage.selectControlVariables();

            //Click on exit button and check model status model building page
            aiModelPage.clickOnExitButton();

            //Verify details of model on the model building screen
            aiModelPage.verifyModelDetailsModelBuildingPage("Variables", modelDetails);

            //Click on model name link on model building screen to navigate to specific tab
            aiModelPage.clickOnModelNameLink("Variables", modelDetails.getString("model_name"));

            //On variables screen click on next button in order to navigate to model parameters screen
            aiModelPage.navigateToModelParametersScreen();

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

        ExtentTest t4 = pNode.createNode("Aiml_Model_Creation_Model_Parameters_Screen", "Open the model parameters screen");
        try {

            AimlModel aiModelPage = AimlModel.init(t4);

            //Get the details of kpi to create
            modelDetails = aiModelPage.getModelDetails(aimlBean,"ootb_reconfigured");

            //Click on exit button and check model status model building page
            aiModelPage.clickOnExitButton();

            //Verify details of model on the model building screen
            aiModelPage.verifyModelDetailsModelBuildingPage("Model Parameters", modelDetails);

            //Click on model name link on model building screen to navigate to specific tab
            aiModelPage.clickOnModelNameLink("Model Parameters", modelDetails.getString("model_name"));

            //Verify ui elements of model parameters screen
            aiModelPage.verifyUiElementsOfModelParametersScreen("Model Parameters");

            //Select algorithm
            aiModelPage.selectAlgorithm(modelDetails.getString("base_algorithm"));

            //Click on run model
            aiModelPage.clickOnRunModel();

            //Click on continue button
            aiModelPage.clickOnContinueButton();

        } catch (Exception e) {
            markTestAsFailure(e, t4);
        }

        ExtentTest t5 = pNode.createNode("Aiml_Model_Creation_Scenario_Selection_Screen", "Open the scenario selection screen");
        try {

            AimlModel aiModelPage = AimlModel.init(t5);

            //Get the details of kpi to create
            modelDetails = aiModelPage.getModelDetails(aimlBean,"ootb_reconfigured");

            //Select scenario
            aiModelPage.selectScenario();

            //Verify ui elements of scenario selection screen
            aiModelPage.verifyUiElementsOfScenarioSelectionScreen("Scenario Selection");

            //Click on exit button and check model status model building page
            aiModelPage.clickOnExitButton();

            //Verify details of model on the model building screen
            aiModelPage.verifyModelDetailsModelBuildingPage("Scenario Selection", modelDetails);

            //Click on model name link on model building screen to navigate to specific tab
            aiModelPage.clickOnModelNameLink("Scenario Selection", modelDetails.getString("model_name"));

        } catch (Exception e) {
            markTestAsFailure(e, t5);
        }

        ExtentTest t6 = pNode.createNode("Aiml_Model_Creation_Roi_Inputs_Screen", "Open the roi inputs screen");
        try {

            AimlModel aiModelPage = AimlModel.init(t6);

            //Verify ui elements of roi inputs screen
            aiModelPage.verifyUiElementsOfRoiInputsScreen();

            //Wait until save button is enabled and click
            aiModelPage.clickOnSaveButtonOnRoiInputsScreen();

            //Click on continue button
            aiModelPage.clickOnContinueButton();

        } catch (Exception e) {
            markTestAsFailure(e, t6);
        }

        ExtentTest t7 = pNode.createNode("Aiml_Model_Saved_Models", "Open the saved models tab and click on use model");
        try {

            AimlModel aiModelPage = AimlModel.init(t7);

            //Get the details of kpi to create
            modelDetails = aiModelPage.getModelDetails(aimlBean,"ootb_reconfigured");

            //On saved models tab check brand of models present
            aiModelPage.verifyUiElementsOfSavedModels(modelDetails);

            //Click on use model
            aiModelPage.clickOnUseModel(modelDetails);

        } catch (Exception e) {
            markTestAsFailure(e, t7);
        }
        finally {
            Assertion.finalizeSoftAsserts();
        }
    }


}
