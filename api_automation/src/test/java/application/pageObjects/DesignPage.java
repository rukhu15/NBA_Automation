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
import org.openqa.selenium.support.locators.RelativeLocator;
import java.util.Iterator;
import java.util.List;


/**
 * @author Prateek Sethi
 */
public class DesignPage extends PageInit {
    private static ExtentTest pNode;

    @FindBy(xpath = "//span[@class='MuiTab-wrapper']//span[contains(text(),'Audience')]")
    WebElement tab_audience;
    @FindBy(xpath = "//span[@class='MuiTab-wrapper']//span[contains(text(),'Channels')]")
    WebElement tab_channel;
    @FindBy(xpath = "//span[@class='MuiTab-wrapper']//span[contains(text(),'Content')]")
    WebElement tab_content;
    @FindBy(xpath = "//div[@class='audience-button']")
    WebElement btn_create_audience;
    @FindBy(xpath = "//input[@class='addAudienceInput floatLeft']")
    WebElement txt_audience;
    @FindBy(xpath = "//i[@class='fas fa-check floatLeft']")
    WebElement lnk_save_audience_name;
    @FindBy(xpath = "//button[@id='add-filter-btn']")
    WebElement btn_add_filter;
    @FindBy(xpath = "//div[@class='filter-div']")
    WebElement filter_section;
    @FindBy(xpath = "//div[@class='subCategoryCol-checkbox']")
    WebElement chkbox_sub_filter_type;
    @FindBy(xpath = "//span[@class='MuiStepLabel-labelContainer']/span[text()='Design']")
    WebElement lbl_Design;
    @FindBy(xpath = "//span[normalize-space()='Add']")
    WebElement btn_filter_sub_type_add;
    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;
    @FindBy(xpath = "//span[normalize-space()='Save']")
    WebElement btn_save_overallAudience;
    @FindBy(xpath = "//button[@id='channels']")
    WebElement tab_channels;
    @FindBy(xpath = "//i[@class='fas fa-arrow-right']")
    WebElement arrow_channel_select;
    @FindBy(xpath = "//button[@id='content']")
    WebElement contentTab;
    @FindBy(xpath = "//table[contains(@class, 'content-table')]")
    WebElement contentTabMessageList;

    @FindBy(xpath = "//button[contains(text(), 'Edit')]")
    WebElement editButton;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    WebElement saveButton;

    String str_filter_type_selection="//div[contains(text(),'Filter_Type')]";
    String str_sub_filter_type_checkbox="//div[@class='subCategoryCol-checkbox']";
    String str_filter_inputBox="//div[@class='design-card-body card-body']/preceding-sibling::div[@class='design-card-title card-header']/div[text()='String_To_Replace']/../../descendant::input";
    String str_filter_value_dropdown = "//div[@class='design-card-body card-body']/preceding-sibling::div[@class='design-card-title card-header']/div[text()='String_To_Replace']/../../descendant::button[2]";
    String str_channel_category_xpath ="//div[@class='MuiListItemText-root']/span[text()='Channel_Category']";

    public DesignPage(ExtentTest t2) {
        super(t2);
    }

    public static DesignPage init(ExtentTest t2) {
        pNode = t2;
        return new DesignPage(pNode);
    }

    public void createAudience(String audienceName) throws Exception {
        clickOnElement(btn_create_audience,"create audience button");
        setText(txt_audience,audienceName,"audience name text box");
        clickOnElement(lnk_save_audience_name,"save audience");
        try {
            selectAudienceInDesignPage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void selectAudienceInDesignPage() throws Exception {
        clickOnElement(btn_add_filter,"btn_add_filter");
        clickOnElement(driver.
                findElement(By.xpath(str_filter_type_selection.
                        replace("Filter_Type","Customer Type"))),"clicking on filter type");
        Thread.sleep(2000);
        clickOnElement(chkbox_sub_filter_type,"Sub filter checkbox");
        clickOnElement(btn_filter_sub_type_add, "filter value add button");
        setText(driver.findElement(By.xpath(str_filter_inputBox.replace("String_To_Replace","Client Type"))),"HCP",
                "set text");
        WebElement ele = driver.findElement(By.xpath("//input[@value='HCP' and @type='checkbox']"));
        Actions a = new Actions(driver);
        a.moveToElement(ele).
                click().build().perform();
        clickOnElement(lbl_Design,"Click on Design label");
        clickOnElement(btn_save_overallAudience,"Saving the audience");
    }

    public void createChannles(DesignPageBean de) throws Exception {
        clickOnElement(tab_channel,"Clicking on channel tab in audience page");
        JSONObject jsObj = new JSONObject(de.channelJson);
        JSONObject categoryObj=jsObj.getJSONObject("category");
        Iterator<?> keys = categoryObj.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            logInfo("Clicking on channel category type");
            clickOnElement(getWebElementUsingDynamicXpath(str_channel_category_xpath,"Channel_Category",key),
                    "Clicking on Personal channel");
            JSONArray channelArr=categoryObj.getJSONArray(key);
            for(int index =0 ; index<channelArr.length();index++){
                String channel= channelArr.getString(index);
                String xpath="//div[@class='MuiCardHeader-content']/h6[text()='"+channel+"']/../preceding-sibling::div//*[name()='svg']//*[name()='path']";
                WebElement channelCheckBox=driver.findElement(By.xpath("//div[@class='MuiCardHeader-content']/h6[text()='"+channel+"']/../preceding-sibling::div//*[name()='svg']//*[name()='path']"));
                Actions a = new Actions(driver);
                a.moveToElement(channelCheckBox).
                        click().build().perform();

            }
            clickOnElement(arrow_channel_select,"Finally selecting channels");
        }
    }

    public void openContentSection() throws Exception {
        Markup m = MarkupHelper.createLabel("openContentSection", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Move to content section
            clickOnElement(contentTab, "Content tab");
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkMessageList() throws Exception {
        Markup m = MarkupHelper.createLabel("checkMessageList", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            //Check message list is displayed
            verificationOfMessageList();
            //Click on edit button
            clickOnEditButton();
            //Unselect all key topics
            clickOnCheckboxOfKeyTopics("Select All");
            //Select one key topic
            clickOnCheckboxOfKeyTopics("Efficacy");
            //Click on save button
            clickOnSaveButton();
            //Check message is displayed if one topic present in the message is selected by user
            checkMessageIsDisplayed("Treatment End Results");
            //Check if channel is not selected then its message should not be present
            checkMessageNotPresent("Guiding Treatment Criteria", "RT Email");
            //Click on edit button
            clickOnEditButton();
            //Unselect all key topics
            clickOnCheckboxOfKeyTopics("Select All");
            //Click on save button
            clickOnSaveButton();
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void clickOnCheckboxOfKeyTopics(String keyTopicSelected) throws Exception {
        Markup m = MarkupHelper.createLabel("clickOnCheckboxOfKeyTopics", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
           WebElement keyTopic = driver.findElement(By.xpath("//span[text()='" + keyTopicSelected + "']//preceding-sibling::span//*[local-name()='svg']"));
           clickOnElement(keyTopic, keyTopicSelected);
           Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkMessageIsDisplayed(String messageTitle) throws Exception {
        Markup m = MarkupHelper.createLabel("checkMessageIsDisplayed", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            WebElement messageWithTopic = driver.findElement(By.xpath("//table[contains(@class, 'content-table')]//tbody//tr//th[text()='" + messageTitle + "']"));
            Assertion.verifyEqual(elementIsDisplayed(messageWithTopic), true, "CIQ-TC-3887 Validate if a single message has multiple topics selected then even if one of the topics is selected the message will be visible", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkMessageNotPresent(String messageTitle, String channelNotSelected) throws Exception {
        Markup m = MarkupHelper.createLabel("checkMessageNotPresent", ExtentColor.BLUE);
        pageInfo.info(m);
        boolean isChannelNotPresent = true, isMessageNotPresent = true;
        try{
            List<WebElement> channelSelected = driver.findElements(By.xpath("//div[contains(@class, 'channel-div')]//div[@id='panel1d-content']//label//span[contains(@class, 'MuiFormControlLabel-label')]"));
            for(int i=0; i<channelSelected.size(); i++){
                WebElement nameOfChannel = driver.findElement(By.xpath("(//div[contains(@class, 'channel-div')]//div[@id='panel1d-content']//label//span[contains(@class, 'MuiFormControlLabel-label')])[" + (i+1) + "]"));
                if(getText(nameOfChannel).equalsIgnoreCase(channelNotSelected)){
                    isChannelNotPresent = false;
                    break;
                }
                else{
                    isChannelNotPresent = true;
                }
            }
            List<WebElement> messageList = driver.findElements(By.xpath("//table[contains(@class, 'content-table')]//tbody//tr//th"));
            for(int i=0; i<messageList.size(); i++){
                WebElement messageText = driver.findElement(By.xpath("(//table[contains(@class, 'content-table')]//tbody//tr//th[text()='Treatment End Results'])[" + (i+1) + "]"));
                if(getText(messageText).equalsIgnoreCase(messageTitle)){
                    isMessageNotPresent = false;
                    break;
                }
                else{
                    isMessageNotPresent = true;
                }
            }
            Assertion.verifyEqual(isChannelNotPresent && isMessageNotPresent, true, "CIQ-TC-3886 Validate if a channel is not selected under Design>Channel then it's message should not be displayed on content section", pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnSaveButton() throws Exception {
        Markup m = MarkupHelper.createLabel("clickOnSaveButton", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            clickOnElement(saveButton, "Save button");
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickOnEditButton() throws Exception {
        Markup m = MarkupHelper.createLabel("clickOnEditButton", ExtentColor.BLUE);
        pageInfo.info(m);
        try{
            clickOnElement(editButton, "Edit button");
            Utils.captureScreen(pageInfo);
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
    }


    public void verificationOfMessageList() throws Exception {
        Markup m = MarkupHelper.createLabel("verificationOfMessageList", ExtentColor.BLUE);
        pageInfo.info(m);
        boolean isMessageDisplayed = false;
        try{
            List<WebElement> messageList = driver.findElements(By.xpath("//table[contains(@class, 'content-table')]//tbody//tr"));
            int countOfMessages = messageList.size();
            for(int i=0; i<countOfMessages; i++){
                WebElement eachMessage = driver.findElement(By.xpath("//table[contains(@class, 'content-table')]//tbody//tr[" + (i+1) + "]"));
                if(elementIsDisplayed(eachMessage)){
                    isMessageDisplayed = true;
                }
                else {
                    isMessageDisplayed = false;
                    break;
                }
            }
            Assertion.verifyEqual(isMessageDisplayed && elementIsDisplayed(contentTabMessageList), true, "CIQ-TC-3884 validate that the user is able to view Message list on the right side of the content section", pageInfo);
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

}