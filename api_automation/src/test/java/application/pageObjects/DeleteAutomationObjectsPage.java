package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import data.beans.DeleteObjectPageBean;
import framework.utility.common.Assertion;
import framework.utility.common.DatabaseUtil;
import framework.utility.common.Utils;
import framework.utility.globalConst.ConfigInput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DeleteAutomationObjectsPage extends PageInit{

    @FindBy(xpath = "//nav[@id='sideMenu']//button")
    WebElement navBarExpandCollapseButton;

    @FindBy(xpath = "//input[@aria-label='search Scenario']")
    WebElement searchKpiInput;

    @FindBy(xpath = "//button[@aria-label='search']")
    WebElement searchKpiButton;

    @FindBy(xpath = "//button[@aria-label='clear']")
    WebElement clearSearch;

    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;

    @FindBy(xpath = " //div[@class='dropdown1']//ul//li[text()='Delete']")
    WebElement deleteKpiOption;

    @FindBy(xpath = "//button//span[text()='OK']")
    WebElement okButton;

    @FindBy(xpath = "//button[@id='browse']//span[contains(text(), 'Browse')]")
    WebElement browseAllTab;

    @FindBy(xpath = "//input[@aria-label='Search Customer']")
    WebElement inputSearchSegment;

    @FindBy(xpath = "//button[@aria-label='search']//*[local-name()='svg']")
    WebElement searchButton;

    @FindBy(xpath = "(//input[@aria-label='Switch demo'])[1]")
    WebElement useAsFilterSegment;

    @FindBy(xpath = "//input[@aria-label='search Scenario']")
    WebElement inputSearchScenario;

    @FindBy(xpath = "(//input[@aria-label='Switch demo'])[1]//following-sibling::span[contains(@class, 'MuiSwitch-thumb')]")
    WebElement useAsFilterSegmentToggle;

    @FindBy(xpath = "//button//span[contains(text(), 'Confirm')]")
    WebElement confirmButton;

    @FindBy(xpath = "//button[@aria-label='search']//span//*[local-name()='svg']")
    WebElement searchButtonScenario;

    @FindBy(xpath = "//div[@class='aggrid-eliipsis-popup']//li[text()='Delete']")
    WebElement deleteScenario;

    @FindBy(xpath = "//div[@class='aggrid-eliipsis-popup']//li[text()='Deactivate Scenario']")
    WebElement deactivateScenario;

    @FindBy(xpath = "//button//span[text()='OK']")
    WebElement okButtonDelete;
    @FindBy(xpath = "(//div[@class='seg-delete-icon'])[1]//*[local-name()='svg']")
    WebElement deleteSegmentIcon;

    @FindBy(xpath = "//div[@role='tablist']//button//span[text()='Active']")
    WebElement activeTab;

    @FindBy(xpath = "//div[@role='tablist']//button//span[text()='inactive']")
    WebElement inActiveTab;

    @FindBy(xpath = "//button//span[text()='Load']")
    WebElement loadButton;

    @FindBy(xpath = "//div[@role='combobox']//input[@type='text']")
    WebElement searchLoadTextBox;

    @FindBy(xpath = "//button//span[text()='Delete']")
    WebElement deleteButton;

    @FindBy(xpath = "//div[text()='Edit Tag']")
    WebElement btnEditTagOnActions;

    @FindBy(xpath = "//button/span[text()='Delete Tag']")
    WebElement btnDeleteTag;

    @FindBy(xpath = "//button/span[text()='Okay']")
    WebElement btnOkay;

    @FindBy(xpath = "//button[@role='button']")
    WebElement btn_sidebarHamburgerButton;

    @FindBy(xpath = "//div[contains(text(),'Next Best Engagement')]/ancestor::div[@id='Next Best Engagement']")
    WebElement lnk_NBE;

    @FindBy(xpath = "//button[contains(text(),'Create Scenario')]")
    WebElement btn_createScenario;

    private static ExtentTest pNode;
    public DeleteAutomationObjectsPage(ExtentTest t1) {
        super(t1);
    }

    public static DeleteAutomationObjectsPage init(ExtentTest t1) throws Exception {
        pNode = t1;
        return new DeleteAutomationObjectsPage(pNode);
    }

    public void deleteKpi(DeleteObjectPageBean objectToBeDeleted){
        Markup m = MarkupHelper.createLabel("deleteKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            JSONObject jsObj = new JSONObject(objectToBeDeleted.objectsToDelete);
            JSONArray kpiNames = jsObj.getJSONArray("kpi_names");

            for(int i=0; i<kpiNames.length(); i++) {

                //Navigate to kpi builder using side navbar
                openSideNavItem(navBarExpandCollapseButton, true, "KPI Builder");

                //Close the navbar
                openSideNavItem(navBarExpandCollapseButton, false);

                //Check if kpi is present and delete it
                checkIfKpiIsPresentThenDelete(kpiNames.get(i).toString());

            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteTag(DeleteObjectPageBean objectToBeDeleted){
        Markup m = MarkupHelper.createLabel("deleteTag", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            JSONObject jsObj = new JSONObject(objectToBeDeleted.objectsToDelete);
            String tagName = jsObj.get("tag_name").toString();

            //Navigate to kpi builder using side navbar
            openSideNavItem(navBarExpandCollapseButton, true, "Tags");

            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);

            //Check if tag can be deleted on ui or backend
            WebElement contentCount = driver.findElement(By.xpath("//a[text()='"+ tagName +"']//ancestor::div[@role='row']//div[text()=' Content Usage:']/span"));
            WebElement scenarioCount = driver.findElement(By.xpath("//a[text()='"+ tagName +"']//ancestor::div[@role='row']//div[text()='Scenario Usage:']/span"));

            if(Integer.parseInt(getText(contentCount)) == 0 && Integer.parseInt(getText(scenarioCount)) == 0 ) {
                //Delete tag on ui
                tagDeleteFromUi(tagName);
            }
            else {
                pageInfo.info("Tag has been used in content or Scenario unable to delete from UI deleting from backend");
                //Delete tag from backend
                tagDeleteBackend(tagName);
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void tagDeleteBackend(String tagName){
        try {
            DatabaseUtil.deleteExistingTag(ConfigInput.tagSchemaName,tagName);
            DatabaseUtil.closeDBResources();
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void tagDeleteFromUi(String tagName){
        try {

            //Click on actions
            WebElement clickOnEllipsisActions = driver.findElement(By.xpath("//a[text()='"+ tagName +"']//ancestor::div[@role='row']//div[@class='action-cell link-cell']"));
            clickOnElement(clickOnEllipsisActions,"Actions");
            Utils.captureScreen(pageInfo);

            //Click on edit
            clickOnElement(btnEditTagOnActions,"Edit tag");
            Utils.captureScreen(pageInfo);

            //Click on delete
            clickOnElement(btnDeleteTag,"Delete Tag");
            clickOnElement(btnOkay,"Okay");
            Utils.captureScreen(pageInfo);

        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteSegment(DeleteObjectPageBean objectToBeDeleted){
        Markup m = MarkupHelper.createLabel("deleteSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            JSONObject jsObj = new JSONObject(objectToBeDeleted.objectsToDelete);
            String segmentName = jsObj.get("segment_name").toString();

            //Navigate to segmentation using side navbar
            openSideNavItem(navBarExpandCollapseButton, true, "Segmentation");

            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);

            //Delete segment
            deleteSegmentCreated(segmentName);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteScenario(DeleteObjectPageBean objectToBeDeleted){
        Markup m = MarkupHelper.createLabel("deleteScenario", ExtentColor.BLUE);
        pageInfo.info(m);
        String scenarioStatus = "";
        try{

            JSONObject jsObj = new JSONObject(objectToBeDeleted.objectsToDelete);
            String scenarioName = jsObj.get("scenario_name").toString();

            //Navigate to scenario
            navigateToScenarioPage();

            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);

            //Check scenario status then delete
            scenarioStatus = checkScenarioStatus(scenarioName);

            if(scenarioStatus.equalsIgnoreCase("IN PROGRESS")){
                waitTillScenariosStatusChangesThenDelete(scenarioName);
            }
            else if(scenarioStatus.equalsIgnoreCase("Success") || scenarioStatus.equalsIgnoreCase("Failed")){
                deactivateScenario(scenarioName);
                openInActiveTab();
                deleteScenarioFromUi(scenarioName);
            }
            else {
                if(checkIfScenarioPresentInactiveTab(scenarioName)){
                    deleteScenarioFromUi(scenarioName);
                }
                else{
                    pageInfo.info("Scenario is not present inactive tab");
                }

            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void navigateToScenarioPage() {
        try {
            closeToastMessages();
            clickOnElement(btn_sidebarHamburgerButton, "sidebar hamburger menu button");
            clickOnElement(lnk_NBE, "NBE Link on sidebar");
            if (elementIsDisplayed(btn_createScenario))
                pNode.pass("TestUser navigated back to scenario list page");
            else
                pNode.fail("TestUser could not be navigated back to scenario list page");
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void deleteFilter(DeleteObjectPageBean objectToBeDeleted){
        Markup m = MarkupHelper.createLabel("deleteFilter", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            JSONObject jsObj = new JSONObject(objectToBeDeleted.objectsToDelete);
            String filterName = jsObj.get("filter_name").toString();

            //Navigate to segmentation using side navbar
            openSideNavItem(navBarExpandCollapseButton, true, "Profiling");

            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);

            //Click on load button
            clickOnElement(loadButton, "Load");
            Utils.captureScreen(pageInfo);

            //Search load
            setText(searchLoadTextBox, filterName, "Search load");

            List<WebElement> searchResult = driver.findElements(By.xpath("//div[@role='tooltip']//ul//li//div[text()='" + filterName + "']//parent::div[@class='item-row']//following-sibling::div[@class='filter-delete-div']//*[local-name()='svg']"));

            if(searchResult.size() == 1) {
                //Delete load filter
                WebElement deleteLoadFilter = driver.findElement(By.xpath("//div[@role='tooltip']//ul//li//div[text()='" + filterName + "']//parent::div[@class='item-row']//following-sibling::div[@class='filter-delete-div']//*[local-name()='svg']"));
                clickOnElement(deleteLoadFilter, "Delete load filter");
                clickOnElement(deleteButton, "Delete");
                waitTillDisappearingOfSpinner();
            }
            else {
                pageInfo.info("Filter is not present");
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void waitTillScenariosStatusChangesThenDelete(String scenarioName){
        Markup m = MarkupHelper.createLabel("waitTillScenariosStatusChangesThenDelete", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Deactivate scenario after it changes status
            WebElement scenarioStatusPresent = driver.findElement(By.xpath("//div[@col-id='scenario_name']//div[text()='" + scenarioName + "']//ancestor::div[@role='row']//div[@col-id='status_desc']//span[contains(@class,'MuiChip-label')]"));
            while (getText(scenarioStatusPresent).equalsIgnoreCase("IN PROGRESS")) {
                scenarioStatusPresent = driver.findElement(By.xpath("//div[@col-id='scenario_name']//div[text()='" + scenarioName + "']//ancestor::div[@role='row']//div[@col-id='status_desc']//span[contains(@class,'MuiChip-label')]"));
                Utils.putThreadSleep(500);
                if(!getText(scenarioStatusPresent).equalsIgnoreCase("IN PROGRESS")){
                    break;
                }
            }
            deactivateScenario(scenarioName);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public boolean checkIfScenarioPresentInactiveTab(String scenarioName){
        Markup m = MarkupHelper.createLabel("checkIfScenarioPresentInactiveTab", ExtentColor.BLUE);
        pageInfo.info(m);
        boolean isScenarioPresent = false;
        try{

            //Open inactive tab
            openInActiveTab();

            //Search scenario
            searchScenario(scenarioName);

            List<WebElement> scenario = driver.findElements(By.xpath("//div[@col-id='scenario_name']//div[text()='" + scenarioName + "']"));
            if(scenario.size() == 1){
                isScenarioPresent = true;
            }
            else {
                isScenarioPresent = false;
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return  isScenarioPresent;
    }

    public void deactivateScenario(String scenarioName){
        Markup m = MarkupHelper.createLabel("deactivateScenario", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement actionsButtonScenario = driver.findElement(By.xpath("//div[@col-id='scenario_name']//div[text()='" + scenarioName + "']//ancestor::div[@role='row']//p[@class='ellipsis']//*[local-name()='svg']"));
            clickOnElement(actionsButtonScenario, "Actions");
            clickOnElement(deactivateScenario, "Deactivate scenario");
            waitTillDisappearingOfSpinner();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteScenarioFromUi(String scenarioName){
        Markup m = MarkupHelper.createLabel("deleteScenarioFromUi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement actionsButtonScenario = driver.findElement(By.xpath("//div[@col-id='scenario_name']//div[text()='" + scenarioName + "']//ancestor::div[@role='row']//p[@class='ellipsis']//*[local-name()='svg']"));
            clickOnElement(actionsButtonScenario, "Actions");
            clickOnElement(deleteScenario, "Delete");
            clickOnElement(okButtonDelete, "Ok");
            waitTillDisappearingOfSpinner();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public String checkScenarioStatus(String scenarioName){
        Markup m = MarkupHelper.createLabel("checkScenarioStatus", ExtentColor.BLUE);
        pageInfo.info(m);
        String scenarioStatus = "";
        try{

            //check scenario status if present in active tab
            openActiveTab();
            searchScenario(scenarioName);
            if(checkScenarioIsPresentActiveTab(scenarioName)){
                scenarioStatus = getScenarioStatus(scenarioName);
            }
            else {
                pageInfo.info("Check inactive tab scenario");
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return scenarioStatus;
    }

    public String getScenarioStatus(String scenarioName){
        Markup m = MarkupHelper.createLabel("getScenarioStatus", ExtentColor.BLUE);
        pageInfo.info(m);
        String scenarioStatus = "";
        try{

            WebElement scenarioStatusPresent = driver.findElement(By.xpath("//div[@col-id='scenario_name']//div[text()='" + scenarioName + "']//ancestor::div[@role='row']//div[@col-id='status_desc']//span[contains(@class,'MuiChip-label')]"));
            scenarioStatus = getText(scenarioStatusPresent);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return scenarioStatus;
    }

    public boolean checkScenarioIsPresentActiveTab(String scenarioName){
        Markup m = MarkupHelper.createLabel("checkScenarioIsPresentActiveTab", ExtentColor.BLUE);
        pageInfo.info(m);
        boolean isScenarioPresent = false;
        try{

            List<WebElement> scenario = driver.findElements(By.xpath("//div[@col-id='scenario_name']//div[text()='" + scenarioName + "']"));
            if(scenario.size() == 1){
                isScenarioPresent = true;
            }
            else {
                isScenarioPresent = false;
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return isScenarioPresent;
    }

    public void openActiveTab(){
        Markup m = MarkupHelper.createLabel("openActiveTab", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(activeTab, "Active tab");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void openInActiveTab(){
        Markup m = MarkupHelper.createLabel("openInActiveTab", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(inActiveTab, "Inactive tab");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void searchScenario(String scenarioName){
        Markup m = MarkupHelper.createLabel("searchScenario", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            setText(inputSearchScenario, scenarioName, "Search scenario");
            clickOnElement(searchButtonScenario, "Search button");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteSegmentCreated(String segmentName){
        Markup m = MarkupHelper.createLabel("deleteSegmentCreated", ExtentColor.BLUE);
        pageInfo.info(m);
        String segment = "";
        try{

            //Open browse all tab
            clickOnBrowseAllTabSegmentation();

            //Search segment
            searchSegment(segmentName);

            //Delete segment
            deleteSegmentThatUsedKpi(segmentName);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteSegmentThatUsedKpi(String segment){
        Markup m = MarkupHelper.createLabel("deleteSegmentThatUsedKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            Actions builder = new Actions(driver);
            List<WebElement> segmentPresent = driver.findElements(By.xpath("//div[contains(@class,'segment-name') and text()='" + segment + "']"));
            if(segmentPresent.size() > 0) {
                //Check use as filter is on
                if(useAsFilterSegment.isSelected()){
                    builder.moveToElement(useAsFilterSegmentToggle).click().perform();
                    clickOnElement(confirmButton, "Confirm");
                    waitTillDisappearingOfSpinner();
                    //Click on delete segment icon
                    clickOnDeleteSegment();
                    deleteSegmentFromDb(segment);
                }
                else{
                    clickOnDeleteSegment();
                    deleteSegmentFromDb(segment);
                }
            }
            else {
                pageInfo.info("Segment is not present/has been deleted");
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void deleteSegmentFromDb(String segment){
        Markup m = MarkupHelper.createLabel("deleteSegmentFromDb", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Delete segment from database
            int schemaId = DatabaseUtil.getSegmentNameForDeletion(ConfigInput.segmentationSchemaName, segment);
            DatabaseUtil.deleteRecordsBasedOnSchemaId(ConfigInput.segmentationSchemaName, String.valueOf(schemaId), segment);
            DatabaseUtil.closeDBResources();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnDeleteSegment(){
        Markup m = MarkupHelper.createLabel("clickOnDeleteSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(deleteSegmentIcon, "Delete");
            clickOnElement(confirmButton, "Confirm");
            Utils.captureScreen(pageInfo);
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void searchSegment(String segment){
        Markup m = MarkupHelper.createLabel("searchSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Search segment
            setText(inputSearchSegment, segment, "Segment");

            //Click on search button
            clickOnElement(searchButton, "Search");
            waitTillDisappearingOfSpinner();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void clickOnBrowseAllTabSegmentation(){
        Markup m = MarkupHelper.createLabel("clickOnBrowseAllTabSegmentation", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on browse all tab
            clickOnElement(browseAllTab, "Browse all");
            Utils.captureScreen(pageInfo);
            waitTillDisappearingOfSpinner();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void checkIfKpiIsPresentThenDelete(String kpiName) {
        Markup m = MarkupHelper.createLabel("checkIfKpiIsPresentThenDelete", ExtentColor.BLUE);
        pageInfo.info(m);
        try {

            //Search kpi
            searchKpi(kpiName);

            //Check kpi usage and delete segment where it is used
            checkKpiUsage(kpiName);

            //To delete deactivate kpi
            activateKpi(kpiName, false);

            //Clear search
            clearSearchKpi();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void searchKpi(String kpiName){
        Markup m = MarkupHelper.createLabel("searchKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Set name of kpi to search
            setText(searchKpiInput, kpiName, "Search kpi input");

            //Click on search button
            clickOnElement(searchKpiButton, "Search kpi");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
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

    public void waitTillDisappearingOfSpinner(){
        // Wait till the disappearing of spinner
        while(elementIsDisplayed(spinner)){
            Utils.putThreadSleep(100);
        }
    }

    public void activateKpi(String kpiName, boolean activateKpi) {
        Markup m = MarkupHelper.createLabel("activateKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try {

            WebElement activateKpiButton, kpiToggleButton;
            Actions builder = new Actions(driver);
            List<WebElement> createdKpi = driver.findElements(By.xpath("//p[text()='" + kpiName + "']//ancestor::tr//input[@type='checkbox']//following-sibling::span"));
            if(createdKpi.size() == 1) {
                //Click on toggle button of kpi to activate
                activateKpiButton = driver.findElement(By.xpath("//p[text()='" + kpiName + "']//ancestor::tr//input[@type='checkbox']//following-sibling::span"));
                kpiToggleButton = driver.findElement(By.xpath("//p[text()='" + kpiName + "']//ancestor::tr//input[@type='checkbox']"));
                if (activateKpi == true) {
                    builder.moveToElement(activateKpiButton).click().perform();
                    //Check kpi is created
                    Assertion.verifyEqual(createdKpi.size() == 1, true, "Kpi is created successfully", pageInfo);
                } else {
                    if (activateKpi == false && kpiToggleButton.isSelected()) {
                        builder.moveToElement(activateKpiButton).click().perform();
                        //Click on delete option to delete kpi
                        clickOnDeleteKpi(kpiName);
                    } else {
                        pageInfo.info("Kpi has been deactivated already");
                        //Click on delete option to delete kpi
                        clickOnDeleteKpi(kpiName);
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

    public void clickOnDeleteKpi(String kpiName){
        Markup m = MarkupHelper.createLabel("clickOnDeleteKpi", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement kpiActionsOptionButton = driver.findElement(By.xpath("(//p[text()='" + kpiName + "']//ancestor::tr//p)[3]//*[local-name()='svg']"));

            //Click on actions
            clickOnElement(kpiActionsOptionButton, "Actions");
            Utils.captureScreen(pageInfo);

            //Click on delete option
            if(elementIsEnabled(deleteKpiOption)) {
                clickOnElement(deleteKpiOption, "Delete");
            }
            else {
                pageInfo.fail("Delete option is disabled");
            }

            //Click on ok button
            clickOnElement(okButton, "Ok");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkKpiUsage(String kpiName){
        Markup m = MarkupHelper.createLabel("checkKpiUsage", ExtentColor.BLUE);
        pageInfo.info(m);
        int segmentUsageCount = 0;
        try{

            List<WebElement> kpiSearchResult = driver.findElements(By.xpath("//p[text()='" + kpiName + "']//ancestor::tr//input[@type='checkbox']//following-sibling::span"));

            if(kpiSearchResult.size() == 1) {

                //Check if kpi is used in segment
                segmentUsageCount = checkKpiUsageInSegment(kpiName);
                if (segmentUsageCount > 0) {
                    pageInfo.fail("Segment is not deleted");
                } else {
                    clearSearchKpi();
                    pageInfo.info("Kpi is not used in segment");
                }

            }
            else {
                pageInfo.info("Kpi is not present");
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public int checkKpiUsageInSegment(String kpiName){
        Markup m = MarkupHelper.createLabel("checkKpiUsageInSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        int countSegmentUsage = 0;
        try{

            WebElement countUsageInSegment = driver.findElement(By.xpath("//p[text()='" + kpiName + "']//ancestor::tr//div[contains(text(),'Segment Usage')]//following-sibling::div[@class='kpi-usage-chip']//div"));
            countSegmentUsage = Integer.parseInt(getText(countUsageInSegment));
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return  countSegmentUsage;
    }

}
