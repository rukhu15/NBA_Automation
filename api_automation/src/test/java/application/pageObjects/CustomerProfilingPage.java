package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import data.beans.DesignPageBean;
import framework.utility.common.Assertion;
import framework.utility.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CustomerProfilingPage extends PageInit{

    private static ExtentTest pNode;

    @FindBy(xpath = "//nav[@id='sideMenu']//button")
    WebElement navBarExpandCollapseButton;

    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;

    @FindBy(xpath = "//select[@id='selectedBrandVal']")
    WebElement drpdwn_Profiling_brand;

    @FindBy(xpath = "//button[@role='tab']//span[text()='HCP']")
    WebElement tab_HCP;

    @FindBy(xpath = "//button[@id='filters-button']")
    WebElement btn_Filters;

    @FindBy(xpath = "//button/span[text()='Apply']")
    WebElement btn_Apply;

    @FindBy(xpath = "//button/span[text()='Save']")
    WebElement btn_Save;

    @FindBy(xpath = "//div[@class='save-div']")
    WebElement btn_SaveDesignFilter;

    @FindBy(xpath = "//div[@class='buttons-wrapper']//span[text()='Save']")
    WebElement btn_SaveLoad;

    @FindBy(xpath = "//div[@class='buttons-wrapper']//span[text()='Cancel']")
    WebElement btn_CancelLoad;

    @FindBy(xpath = "//button/span[text()='Load']")
    WebElement btn_Load;

    @FindBy(xpath = "//div[@role='tooltip']//input[@placeholder='Search']")
    WebElement input_SearchLoad;

    @FindBy(xpath = "//button/span[text()='Delete']")
    WebElement btn_Delete;

    @FindBy(xpath = "//button[text()='Load']")
    WebElement btn_LoadDesign;

    @FindBy(xpath = "//button/span[text()='Confirm']")
    WebElement btn_Confirm;

    @FindBy(xpath = "//div[@class='select-fiter-display']")
    WebElement dropDown_Filter;


    public CustomerProfilingPage(ExtentTest t1) {
        super(t1);
    }

    public static CustomerProfilingPage init(ExtentTest t1) {
        pNode = t1;
        return new CustomerProfilingPage(pNode);
    }

    public JSONArray getCustomerJson(DesignPageBean Json){
        //To get data from Json
        Markup m = MarkupHelper.createLabel("Customer Profiling Page", ExtentColor.BLUE);
        pageInfo.info(m);
        JSONArray customerJson = null;
        try {
            JSONObject jsObj = new JSONObject(Json.Filter);
            customerJson = jsObj.getJSONArray("customer");
            System.out.println(customerJson.getJSONObject(0).getString("filter_header"));
        }catch (Exception e){
            logInfo(e.getMessage());
        }
        return customerJson;
    }

    public void sideNavForProfiling(){
        //To Navigate to Profiling page
        try {
            closeToastMessages();
            openSideNavItem(navBarExpandCollapseButton,true,"Profiling");
            waitTillDisappearingOfSpinner();
            openSideNavItem(navBarExpandCollapseButton, false);
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

    public void selectBrandInProfiling(DesignPageBean brandName) {
        //To select Brand on the profiling page
        try {
            if (brandName.Brand != null && !brandName.Brand.isEmpty()) {
                selectVisibleText(drpdwn_Profiling_brand, brandName.Brand, "Profiling Brand Select box");
            }else {
                pageInfo.info("Profiling Brand is unable to select");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectTabOnProfiling(){
        //To select the HCP tab present and click on the Filters button on the Profiling page
        try {
            if (elementIsDisplayed(tab_HCP)){
                clickOnElement(tab_HCP,"The HCP tab");
            }else {
                pageInfo.info("HCP tab is not clicked");
            }
            clickBtnFilters();
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickBtnFilters(){
        //To click on the Filters button on Profiling page
        try {
            if (elementIsDisplayed(btn_Filters)){
                clickOnElement(btn_Filters,"The Filters");
            }else {
                pageInfo.info("unable to click on filters button");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectFilterDropDown(JSONArray Json){
        //To select the input from the filters
        try {
            for (int i=0;i<Json.length();i++) {
                WebElement filterDropDown = driver.findElement(By.xpath("//div[@class='item-content']//span[text()='" + Json.getJSONObject(i).getString("filter_header")+"']"));
                if (filterDropDown.isDisplayed()) {
                    clickOnElement(filterDropDown, "To select Segment Filter");
                } else {
                    pageInfo.info("Segment is not available");
                }
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void searchFilter(JSONArray Json){
        //To search the filter and select on filters section
        try {
            for (int i=0;i<Json.length();i++) {
                WebElement searchFil = driver.findElement(By.xpath("//div[contains(@class,'autocomplete-input')]/input[@placeholder='Search']"));
                setText(searchFil,Json.getJSONObject(i).getString("filter_name"),"to search element");
                List<WebElement> filter = driver.findElements(By.xpath("//div[@class='segment-selector-item-wrapper']//div[text()='"+Json.getJSONObject(i).getString("filter_name")+"']"));
                if (filter.size()>0){
                    WebElement filterJson = driver.findElement(By.xpath("//div[@class='segment-selector-item-wrapper']//div[text()='"+Json.getJSONObject(i).getString("filter_name")+"']"));
                    clickOnElement(filterJson,"on the filter");
                    clickOnElement(btn_Apply,"the apply button");
                }else {
                    pageInfo.info("No filter is present on the segment");
                }
            }
            verifyFilterCorrect(Json);
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void verifyFilterCorrect(JSONArray Json){
        //To verify applied filter is present
        try {
            for (int i=0;i<Json.length();i++) {
                WebElement appliedFilter = driver.findElement(By.xpath("//div[@class='filter-title' and text()='"+Json.getJSONObject(i).getString("filter_name")+"']"));
                if (appliedFilter.isDisplayed()) {
                    Assertion.verifyEqual(elementIsDisplayed(appliedFilter),true,"The filter already selected",pageInfo);
                } else {
                    pageInfo.info("Filter is not applied");
                }
            }
            filterDropDownAndSelectAll(Json);
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void filterDropDownAndSelectAll(JSONArray Json){
        //To click dropdown and click checkbox select All
        try {
            clickOnElement(dropDown_Filter,"on the filter dropdown");
            for (int i=0;i<Json.length();i++) {
                WebElement checkBox = driver.findElement(By.xpath("//div[text()='"+Json.getJSONObject(i).getString("filter_name")+"']//preceding-sibling::span//*[local-name()='svg']"));
                Actions actions = new Actions(driver);
                actions.moveToElement(checkBox).click().build().perform();
            }
            clickOnElement(btn_Apply,"the apply button");
            waitTillDisappearingOfSpinner();
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void saveFilter(JSONArray Json){
        //To open Save functionality
        try {
            checkLoadPresent(Json);
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void createFilter(JSONArray Json){
        //To create New Filter
        try {
            clickOnElement(btn_Save,"on the Save button To open Save functionality");
            for (int i=0;i<Json.length();i++) {
                WebElement inputSave = driver.findElement(By.xpath("//input[@placeholder='New filter name']"));
                if (inputSave.isDisplayed()) {
                    setText(inputSave, Json.getJSONObject(i).getString("create_load"), "input for save filter");
                    clickOnElement(btn_SaveLoad, "save after enter the input");
                }else {
                    pageInfo.info("Unable to set text on input");
                }
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkLoadPresent(JSONArray Json){
        //To check the Load functionality already filter is present are not
        try {
            createFilter(Json);
            List<WebElement> loadPresent = driver.findElements(By.xpath("//span[text()='Filter Name already exists']"));
            if (loadPresent.size()>0){
                clickOnElement(btn_CancelLoad,"cancel creating load");
                clickOnElement(btn_Load,"on the load button");
                for (int i=0;i<Json.length();i++) {
                    setText(input_SearchLoad, Json.getJSONObject(i).getString("create_load"),"Filter input to delete");
                    List<WebElement> filterCheck = driver.findElements(By.xpath("//div[text()='"+Json.getJSONObject(i).getString("create_load")+"' and @class='text']"));
                    if (filterCheck.size()>0){
                        WebElement deleteFilter = driver.findElement(By.xpath("//div[text()='"+Json.getJSONObject(i).getString("create_load")+"' and @class='text']/ancestor::div[@class='option-flex']//div[@class='filter-delete-div']"));
                        clickOnElement(deleteFilter,"To delete the Filter");
                        clickOnElement(btn_Delete,"confirm delete button");
                        createFilter(Json);
                    }else {
                        pageInfo.info("filter is not present to delete");
                    }
                }
            }else {
                pageInfo.info("No Filter is present we can create new");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void designPageLoadCheck(){
        //To check the load functionality present are not in design page
        try {
            List<WebElement> btnLoad = driver.findElements(By.xpath("//button[text()='Load']"));
            if (btnLoad.size()>0){
                clickOnElement(btn_LoadDesign,"On the Load button");
            }else {
                pageInfo.info("There no Load button present check Audience present");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkLoadFilterPresent(JSONArray Json){
        //To check Filter present in the load or not
        try {
            for (int i=0;i<Json.length();i++) {
                WebElement inputSave = driver.findElement(By.xpath("//div[contains(@class,'autocomplete-input')]/input[@placeholder='Search']"));
                if (inputSave.isDisplayed()) {
                    setText(inputSave, Json.getJSONObject(i).getString("create_load"), "input for save filter");
                    List<WebElement> applyFilter = driver.findElements(By.xpath("//div[@class='item-row']//div[text()='"+Json.getJSONObject(i).getString("create_load")+"']"));
                    if (applyFilter.size()>0){
                        WebElement applyFilterClick = driver.findElement(By.xpath("//div[@class='item-row']//div[text()='"+Json.getJSONObject(i).getString("create_load")+"']"));
                        clickOnElement(applyFilterClick,"To select the Filter");
                    }else {
                        pageInfo.info("Filter is not present please create");
                    }
                }else {
                    pageInfo.info("Unable to set text on input");
                }
            }
            confirmFilter(Json);
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void confirmFilter(JSONArray Json){
        //To check confirmation message
        try {
            for (int i=0;i<Json.length();i++) {
                WebElement inputSave = driver.findElement(By.xpath("//h6[contains(text(),'" + Json.getJSONObject(i).getString("create_load") + "')]"));
                Assertion.verifyEqual(elementIsDisplayed(inputSave),true,"the Load is correct",pageInfo);
                clickOnElement(btn_Confirm,"to confirm the filter");
                clickOnElement(btn_SaveDesignFilter,"to save the load");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }
}
