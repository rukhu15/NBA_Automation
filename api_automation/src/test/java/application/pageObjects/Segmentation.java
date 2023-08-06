package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import data.beans.SegmentationPageBean;
import framework.utility.common.Assertion;
import framework.utility.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


public class Segmentation extends PageInit{

    @FindBy(xpath = "//nav[@id='sideMenu']//button")
    WebElement navBarExpandCollapseButton;

    @FindBy(xpath = "//button[@id='browse']//span[contains(text(), 'Browse')]")
    WebElement browseAllTab;
    @FindBy(xpath = "//input[@aria-label='Search Customer']")
    WebElement inputSearchSegment;

    @FindBy(xpath = "//button[@aria-label='search']//*[local-name()='svg']")
    WebElement searchButton;

    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;

    @FindBy(xpath = "//button[contains(text(), 'Create Segment')]")
    WebElement createSegmentButton;

    @FindBy(xpath = "//button//span[text()='Done']")
    WebElement doneButton;

    @FindBy(xpath = "//input[@id='fullWidth']")
    WebElement ruleBasedSegmentationScheme;

    @FindBy(xpath = "//textarea[@id='fullWidth']")
    WebElement ruleBasedSegmentationDescription;

    @FindBy(xpath = "(//select[@class='rule-segment-category-lists'])[1]")
    WebElement ruleBasedSegmentCategoryDropdown;

    @FindBy(xpath = "(//select[@class='rule-segment-category-lists'])[2]")
    WebElement ruleBasedSegmentBrandDropdown;

    @FindBy(xpath = "//button//span[text()=' Create ']")
    WebElement createButton;

    @FindBy(xpath = "//i[@class='fa fa-plus plus-image']")
    WebElement addSegmentButton;

    @FindBy(xpath = "//div[@id='rule-wizard-parent']//input")
    WebElement segmentInput;

    @FindBy(xpath = "//button//span[contains(text(), 'Apply')]")
    WebElement applyButtonSegmentConfiguration;

    @FindBy(xpath = "//button//span[contains(text(), 'Save Rule(s)')]")
    WebElement saveRuleButton;

    @FindBy(xpath = "//select[@id='demo-customized-select-native']")
    WebElement ruleOperatorDropDown;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    WebElement saveButton;

    @FindBy(xpath = "//span[text()='Add Rules']")
    WebElement addRulesButton;

    @FindBy(xpath = "//button//span[contains(text(), 'Confirm')]")
    WebElement confirmButton;

    @FindBy(xpath = "//ol[@class='MuiBreadcrumbs-ol']//li//a[text()='Segmentation']")
    WebElement segmentationBreadcrumb;

    @FindBy(xpath = "(//div[@class='seg-vellipsis-icon'])[1]//*[local-name()='svg']")
    WebElement actionsButton;

    @FindBy(xpath = "//ul//li[text()='Activate']")
    WebElement activateButton;

    @FindBy(xpath = "(//input[@aria-label='Switch demo'])[1]//following-sibling::span[contains(@class, 'MuiSwitch-thumb')]")
    WebElement useAsFilterSegmentToggle;

    @FindBy(xpath = "(//div[@class='squircle-outer-div'])[1]//span[contains(@class,'MuiChip-label')]")
    WebElement segmentStatus;

    @FindBy(xpath = "//input[@id='segment-name']")
    WebElement segmentSchemeRuleBasedSegment;

    @FindBy(xpath = "//input[@id='segment-description']")
    WebElement segmentDescriptionRuleBasedSegment;

    @FindBy(xpath = "//button[@class='segment-refresh-button']//*[local-name()='svg']")
    WebElement refreshButton;

    private static ExtentTest pNode;
    public Segmentation(ExtentTest t1) {
        super(t1);
    }

    public static Segmentation init(ExtentTest t1) throws Exception {
        pNode = t1;
        return new Segmentation(pNode);
    }

    public void openSegmentationTabBrowse(JSONObject segmentDetails){
        Markup m = MarkupHelper.createLabel("openSegmentationTabBrowse", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            closeToastMessages();

            //Navigate to segmentation using side navbar
            openSideNavItem(navBarExpandCollapseButton, true, "Segmentation");

            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);

            //Open browse all tab
            clickOnBrowseAllTabSegmentation();

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

    public void createSegment(JSONObject segmentDetails){
        Markup m = MarkupHelper.createLabel("createSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on create segment
            clickOnCreateSegmentButton();

            System.out.println(segmentDetails.getString("segment_type"));

            //Select segment type
            selectSegmentType(segmentDetails.getString("segment_type"));

            if(segmentDetails.getString("segment_type").equalsIgnoreCase("rule")){
                //Complete segment details
                completeSegmentationDetails(segmentDetails);
                //Add segment
                addSegmentRuleBasedSegment(segmentDetails);
                //Save segment
                clickOnSaveButton();
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void activateSegment(JSONObject segmentDetails){
        Markup m = MarkupHelper.createLabel("activateSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(segmentationBreadcrumb, "Segmentation");
            //Open browse all tab
            clickOnBrowseAllTabSegmentation();
            //Search segment
            searchSegment(segmentDetails.getString("name_of_segment"));

            //Check if segment is created
            checkIfSegmentIsCreated(segmentDetails.getString("name_of_segment"));

            //Activate segment
            clickOnElement(actionsButton, "Actions");
            clickOnElement(actionsButton, "Actions");
            clickOnElement(activateButton, "Activate");
            waitTillDisappearingOfSpinner();

            //Navigate to segmentation using side navbar
            openSideNavItem(navBarExpandCollapseButton, true, "Segmentation");
            //Close the navbar
            openSideNavItem(navBarExpandCollapseButton, false);
            //Open browse all tab
            clickOnBrowseAllTabSegmentation();

            //Search segment
            searchSegment(segmentDetails.getString("name_of_segment"));

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkIfSegmentIsCreated(String segmentName){
        Markup m = MarkupHelper.createLabel("checkIfSegmentIsCreated", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement segmentCreated = driver.findElement(By.xpath("//div[@class='segment-name-new-div-parent']//div[text()='" + segmentName + "']"));
            Assertion.verifyEqual(elementIsDisplayed(segmentCreated), true, "Segment is created", pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void useAsFilterSegment(){
        Markup m = MarkupHelper.createLabel("useAsFilterSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Wait till segment is in state active
            while(!getText(segmentStatus).equalsIgnoreCase("ACTIVE")){
                clickOnElement(refreshButton, "Refresh");
                Utils.putThreadSleep(1000);
            }
            clickOnElement(useAsFilterSegmentToggle, "Use as filter");
            waitTillDisappearingOfSpinner();
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void clickOnSaveButton(){
        Markup m = MarkupHelper.createLabel("clickOnSaveButton", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(saveButton, "Save");
            Utils.captureScreen(pageInfo);
            clickOnElement(confirmButton, "Confirm");
            waitTillDisappearingOfSpinner();

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
            Utils.captureScreen(pageInfo);
            waitTillDisappearingOfSpinner();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void completeSegmentationDetails(JSONObject segmentDetails){
        Markup m = MarkupHelper.createLabel("completeSegmentationDetails", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Set segment schema
            setText(ruleBasedSegmentationScheme, segmentDetails.getString("name_of_segment"), "Segment scheme");

            //Set description
            setText(ruleBasedSegmentationDescription, segmentDetails.getString("description"), "Description");

            //Select category
            clickOnElement(ruleBasedSegmentCategoryDropdown, "Category dropdown");
            WebElement categoryOption = driver.findElement(By.xpath("(//select[@class='rule-segment-category-lists'])[1]//option[text()='" + segmentDetails.getString("category") + "']"));
            clickOnElement(categoryOption, segmentDetails.getString("category"));

            //Select brand
            clickOnElement(ruleBasedSegmentBrandDropdown, "Brand dropdown");
            WebElement brandOption = driver.findElement(By.xpath("(//select[@class='rule-segment-category-lists'])[2]//option[text()='" + segmentDetails.getString("brand") + "']"));
            clickOnElement(brandOption, segmentDetails.getString("brand"));
            Utils.captureScreen(pageInfo);

            //Click on create button
            clickOnCreateButton();

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnCreateSegmentButton(){
        Markup m = MarkupHelper.createLabel("clickOnCreateSegmentButton", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            clickOnElement(createSegmentButton, "Create segment");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectSegmentType(String type){
        Markup m = MarkupHelper.createLabel("selectSegmentType", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            Actions builder = new Actions(driver);

            WebElement segmentType = driver.findElement(By.xpath("//div[contains(@class, 'radio-button-label') and text()='" + type + "']//preceding-sibling::span//*[local-name()='svg' and @data-testid='RadioButtonUncheckedIcon']"));
            builder.moveToElement(segmentType).click().perform();
            Utils.captureScreen(pageInfo);

            //Click on done button
            clickOnElement(doneButton, "Done");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void addSegmentRuleBasedSegment(JSONObject segmentDetails){
        Markup m = MarkupHelper.createLabel("addSegmentRuleBasedSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Click on add segment button
            clickOnElement(addSegmentButton, "Add segment");
            Utils.captureScreen(pageInfo);

            //Configure segment
            configureRuleBasedSegment(segmentDetails);

            //Set segment scheme
            setText(segmentSchemeRuleBasedSegment, segmentDetails.getString("name_of_segment"), "Segment scheme");

            //Set segment description
            setText(segmentDescriptionRuleBasedSegment, segmentDetails.getString("description"), "Segment description");
            Utils.captureScreen(pageInfo);

            //Click on save rule
            clickOnElement(saveRuleButton, "Save rule");
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void configureRuleBasedSegment(JSONObject segmentDetails){
        Markup m = MarkupHelper.createLabel("configureRuleBasedSegment", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            //Select rule operator
            clickOnElement(ruleOperatorDropDown, "Rule operator dropdown");
            WebElement ruleOperatorOption = driver.findElement(By.xpath("//select[@id='demo-customized-select-native']//option[text()='" + segmentDetails.getString("rule_operator") + "']"));
            clickOnElement(ruleOperatorOption, segmentDetails.getString("rule_operator"));
            Utils.captureScreen(pageInfo);

            for(int i=0; i<segmentDetails.getJSONArray("segment").length(); i++){
                //Click on segment dropdown
                WebElement segmentDropDown = driver.findElement(By.xpath("(//div[@class='dropdown-icon'])[" + (i+1) + "]//*[local-name()='svg']"));
                clickOnElement(segmentDropDown, "Segment dropdown");
                //Select category
                selectCategoryOfSegmentConfigurationPage(segmentDetails.getJSONArray("segment").getJSONObject(i).getString("category"), segmentDetails.getJSONArray("segment").getJSONObject(i).getString("sub_category"));
                //Select attribute
                selectAttributeOfSegmentConfigurationPage(segmentDetails.getJSONArray("segment").getJSONObject(i).getString("attribute"));
                //Select attribute
                selectOperatorOfSegmentConfigurationPage(segmentDetails.getJSONArray("segment").getJSONObject(i).getString("operator"));
                //Set input
                setInputOfSegmentConfigurationPage(segmentDetails.getJSONArray("segment").getJSONObject(i).getString("input_filter"));
                if(i < segmentDetails.getJSONArray("segment").length() - 1){
                    //Click on add rule
                    clickOnElement(addRulesButton, "Add rules");
                }
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void setInputOfSegmentConfigurationPage(String filter){
        Markup m = MarkupHelper.createLabel("setInputOfSegmentConfigurationPage", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            setText(segmentInput, filter, filter);
            //Select input option to be used
            if(segmentInput.getAttribute("type").equalsIgnoreCase("text")){
                WebElement inputOption = driver.findElement(By.xpath("//div[@class='categorical-block' and @id='" + filter + "']"));
                clickOnElement(inputOption, filter);
            }
            Utils.captureScreen(pageInfo);
            clickOnElement(applyButtonSegmentConfiguration, "Apply");

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void selectOperatorOfSegmentConfigurationPage(String operator){
        Markup m = MarkupHelper.createLabel("selectOperatorOfSegmentConfigurationPage", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement segmentOperator = driver.findElement(By.xpath("//ul[@class='operator-parent']//li[text()='" + operator + "']"));
            clickOnElement(segmentOperator, operator);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectAttributeOfSegmentConfigurationPage(String attribute){
        Markup m = MarkupHelper.createLabel("selectAttributeOfSegmentConfigurationPage", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement segmentAttribute = driver.findElement(By.xpath("//ul[@class='attribute-list-parent']//li[contains(text(), '" + attribute + "')]"));
            clickOnElement(segmentAttribute, attribute);
            Utils.captureScreen(pageInfo);

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectCategoryOfSegmentConfigurationPage(String category, String subCategory){
        Markup m = MarkupHelper.createLabel("selectCategoryOfSegmentConfigurationPage", ExtentColor.BLUE);
        pageInfo.info(m);
        try{

            WebElement segmentSubCategory = driver.findElement(By.xpath("//p[@id='" + subCategory + "']"));
            Utils.scrollToAnElement(segmentSubCategory);
            clickOnElement(segmentSubCategory, subCategory);
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

    public JSONObject getSegmentDetails(SegmentationPageBean segment, String segmentType){
        Markup m = MarkupHelper.createLabel("getSegmentDetails", ExtentColor.BLUE);
        pageInfo.info(m);
        JSONObject detailsOfSegment=null;
        try{

            JSONObject jsObj = new JSONObject(segment.detailsOfSegment);
            JSONArray segmentDetails=jsObj.getJSONArray("segment");
            for(int i=0; i<segmentDetails.length(); i++){
                if(segmentDetails.get(i).toString().contains(segmentType)){
                    detailsOfSegment = segmentDetails.getJSONObject(i).getJSONObject(segmentType);
                    break;
                }
                else {
                    pageInfo.info("Segment details not found");
                }
            }

        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return detailsOfSegment;
    }


}
