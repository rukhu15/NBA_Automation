package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import data.beans.ModelPageBean;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelActionsPage extends PageInit{

    private static ExtentTest pNode;

    @FindBy(xpath = "//p[text()='Add your first action']")
    WebElement txtVerifyActions;

    @FindBy(xpath = "//button/span[text()='Add Actions']")
    WebElement btnAddActions;

    @FindBy(xpath = "//div[contains(@class,'content__wrap')]")
    WebElement inputFormActions;

    @FindBy(xpath = "//div[@class='slide-title-div']/p[text()='Select Channels']")
    WebElement txt_selectActions;

    @FindBy(xpath = "//button[@class='add']")
    WebElement btnAdd;

    @FindBy(xpath = "//p[@class='header-title' and text()='Content Recommendation']")
    WebElement txt_formheader;

    @FindBy(xpath = "//span[text()='Message']//ancestor::div[contains(@class,'content-cards')]//span[text()='Continue']")
    WebElement btn_MessageContinue;

    @FindBy(xpath = "//span[text()='Key topic']//ancestor::div[contains(@class,'content-cards')]//span[text()='Continue']")
    WebElement btn_KeyTopicContinue;

    @FindBy(xpath = "//p[@class='header-title' and text()='Content Recommendation - Message']")
    WebElement txt_headerMessage;

    @FindBy(xpath = "//p[@class='header-title' and text()='Content Recommendation - Key Topic']")
    WebElement txt_headerKeyTopic;

    @FindBy(xpath = "(//span[text()='AI/ML Recommended']//ancestor::label[contains(@class,'selected-radio-button')]//div/*[local-name()='svg'])[1]")
    WebElement checkbox_AIML;

    @FindBy(xpath = "(//span[text()='Override AI/ML Algo']//ancestor::label[contains(@class,'selected-radio-button')]//div/*[local-name()='svg'])[1]")
    WebElement checkbox_OverideAIML;

    @FindBy(xpath = "//div[contains(@class,'selectKeyTopic')]")
    WebElement dropDownClick;

    @FindBy(xpath = "//button/span[text()='Save']")
    WebElement btn_Save;

    @FindBy(xpath = "//input[@placeholder='Enter the title name']")
    WebElement txt_inputtitlename;

    @FindBy(xpath = "//div[@aria-label='title-placeholder-control']//div[@aria-label='rdw-dropdown']")
    WebElement dropdown_suggTitle;

    @FindBy(xpath = "//div[@aria-label='rdw-block-control']")
    WebElement dropdown_suggReason;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    WebElement btn_Apply;

    @FindBy(xpath = "//button[contains(text(),'Done')]")
    WebElement btn_Done;


    public ModelActionsPage(ExtentTest t1) {
        super(t1);
    }

    public static ModelActionsPage init(ExtentTest t1) {
        pNode = t1;
        return new ModelActionsPage(pNode);
    }

    public JSONArray getActionsInput(ModelPageBean actions){
        // To get json data from excel
        Markup m = MarkupHelper.createLabel("Actions Page", ExtentColor.BLUE);
        pageInfo.info(m);
        JSONArray actionDetails=null;
        try{
            JSONObject jsObj = new JSONObject(actions.actionsInput);
            actionDetails=jsObj.getJSONArray("Actions");
        }
        catch (Exception e){
            logInfo(e.getMessage());
        }
        return actionDetails;
    }

    public void verifyActionsPage(ModelPageBean mg){
        //To verify it is actions page and workflow present
        try {
            elementIsDisplayed(txtVerifyActions);
            WebElement verifyWorkFlowName = driver.findElement(By.xpath("//div[@class='workflowNamediv']/p[text()='"+mg.workflowName+"']"));
            if (elementIsDisplayed(verifyWorkFlowName)){
                pageInfo.info("workflow name verified on Actions Page");
            }else {
                pageInfo.info("No workflow name present on the Actions page");
            }
        }catch (Exception e){
            logInfo((e.getMessage()));
        }
    }

    public void clickOnBtnAddActions(){
        //To click on the Add Actions button
        try {
            if (elementIsDisplayed(btnAddActions)){
                Actions actions = new Actions(driver);
                actions.moveToElement(btnAddActions).click().build().perform();
                actionsInputForm();
            }else {
                pageInfo.info("No Add Action Button is Present");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void actionsInputForm(){
        //To check input form is opened and text of channels
        try {
            if (elementIsDisplayed(inputFormActions)){
                pageInfo.info("Input form is opened for Trigger");
                elementIsDisplayed(txt_selectActions);
            }else {
                pageInfo.fail("Input form is not opened");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickActionTabs(String tabName){
        //To click on the tabs on the actions input page
        try {
            WebElement clickTab = driver.findElement(By.xpath("//span[text()='"+tabName+"']"));
            clickOnElement(clickTab,"on the tab");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickCheckBoxChannels(JSONArray actionsJson){
        //To select the Channel Inputs and click on Add button
        try {
            for (int i=0;i<actionsJson.length();i++) {
                clickActionTabs(actionsJson.getJSONObject(i).get("Tabname").toString());
                for (int j=0;j<actionsJson.getJSONObject(i).getJSONArray("channel_name").length();j++){
                WebElement verifyCheckBox = driver.findElement(By.xpath("//h6[text()='" + actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j) + "']/parent::div//*[local-name()='svg']"));
                if (elementIsDisplayed(verifyCheckBox)) {
                    WebElement checkBox = driver.findElement(By.xpath("//h6[text()='" + actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j) + "']/parent::div//*[local-name()='svg']"));
                    Actions builder = new Actions(driver);
                    builder.moveToElement(checkBox).click().perform();
                } else {
                    pageInfo.fail("No Channel is Present");
                }
            }}
            clickAddButton();
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void clickAddButton(){
        //To click on the add Button after channels selected
        try {
            if (elementIsDisplayed(btnAdd)) {
                clickOnElement(btnAdd, "To add after selecting channels clicking Add Button");
            }else {
                pageInfo.fail("Unable to click on Add button");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void contentRecommendation(String channel_name){
        //To  select the channel for the content recommendation
        try {
            WebElement clickConfigure = driver.findElement(By.xpath("//span[text()='" + channel_name + "']/ancestor::div[@class='MuiGrid-root MuiGrid-container']/div[@class='MuiGrid-root MuiGrid-grid-xs-9']//div[contains(@class, 'first-configure-override')]"));
            //Scroller to scroll to the element
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);",clickConfigure);
            clickOnElement(clickConfigure, "on the configure button");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void dragPriority(String action, int size) {
        //to check the priority scores for configure inputs
        try {
            List<String> scores = Arrays.asList("20", "40", "40");
            WebElement ele = driver.findElement(By.xpath("//th[text()='" + action + "']/ancestor::tr//div[contains(@class,'slider-component-box')]//span/input[@value]"));
            if (Integer.parseInt(ele.getAttribute("value")) == size) {
                pageInfo.info("Score is 100 continue with save");
            } else {
                ele = driver.findElement(By.xpath("//th[text()='Model Score']/ancestor::tr//div[contains(@class,'slider-component-box')]//span[text()='" + scores.get(0) + "']"));
                clickOnElement(ele, "To make 20 score");
                WebElement ele1 = driver.findElement(By.xpath("//th[text()='Freshness Score']/ancestor::tr//div[contains(@class,'slider-component-box')]//span[text()='" + scores.get(1) + "']"));
                clickOnElement(ele1, "Make 40 for freshness");
                WebElement ele2 = driver.findElement(By.xpath("//th[text()='Random Score']/ancestor::tr//div[contains(@class,'slider-component-box')]//span[text()='" + scores.get(2) + "']"));
                clickOnElement(ele2, "Make 40 for Random");
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void contentConfigure(JSONArray actionsJson,ModelPageBean tn){
        //To configure for the content input for content recommendation
        try {
            for (int i=0;i<actionsJson.length();i++){
                for (int j=0;j<actionsJson.getJSONObject(i).getJSONArray("channel_name").length();j++) {
                    contentRecommendation(actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString());
                    if (elementIsDisplayed(txt_formheader)) {
                        List<WebElement> errorText = driver.findElements(By.xpath("//p[text()='There is no Message mapped to the selected channel.']"));
                        if (errorText.size() == 0) {
                            messageConfigure();
                        } else {
                            keyTopicConfigure(tn);
                        }
                    } else {
                        pageInfo.fail("Content form is not opened");
                    }
                }
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void suggestionConfigure(String channel_name){
        //To select the Suggestion configure
        try {
            WebElement suggestionConfigure = driver.findElement(By.xpath("//span[text()='"+channel_name+"']/ancestor::div[@class='MuiGrid-root MuiGrid-container']//span[text()='Suggestion Title and Reason']/parent::div/following-sibling::div/div[text()='Configure']"));
            clickOnElement(suggestionConfigure, "on the configure button");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void suggestionTitleAndReason(JSONArray actionsJson){
        //To provide Suggestion Title and Reason
        try {
            for (int i=0;i<actionsJson.length();i++) {
                for (int j = 0; j < actionsJson.getJSONObject(i).getJSONArray("channel_name").length(); j++) {
                    suggestionConfigure(actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString());
                    WebElement verifyChannel = driver.findElement(By.xpath("//div[contains(text(),'Configure Suggestion')]//following-sibling::div[text()='"+actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString()+"']"));
                    Utils.putThreadSleep(2000);
                    Assertion.verifyEqual(elementIsDisplayed(verifyChannel),true,"the channel name",pageInfo);
                    suggestionTitle();
                    suggestionDropdown();
                    suggestionReason();
                }
            }
            selectSave();
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void suggestionDropdown(){
        //To select input from the dropdown
        try {
            WebElement dropDownInput = driver.findElement(By.xpath("//div[@class='title-placeholder-dropdown-wrapper']//ul[@id='placeholder-list']//li[@value='{{HCP_Name}}']"));
            clickOnElement(dropDownInput,"to select the dropdown input for suggestion title");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void suggestionTitle(){
        //To enter text on suggestion title
        try {
            setText(txt_inputtitlename,"The person choose","the text message for title");
            clickOnElement(dropdown_suggTitle,"on the dropdown of suggestion title");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void suggestionReason(){
        //To select suggestion reason
        try {
            clickOnElement(dropdown_suggReason,"on the suggestion reason dropdown");
            WebElement dropdownReason = driver.findElement(By.xpath("//div[@class='rich-text-editor']//ul[@id='placeholder-list']//li[@value='{{Brand_Name}}']"));
            clickOnElement(dropdownReason,"on select the suggestion title brand");
            clickOnElement(btn_Apply,"the button Apply");
            saveSuggConfigure();
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void saveSuggConfigure(){
        // To save the final step for suggestion configure
        try {
            clickOnElement(btn_Done,"save the final step for suggestion configure");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void keyTopicConfigure(ModelPageBean tn){
        //To configure for the key topic input for content recommendation
        try {
            clickOnElement(btn_KeyTopicContinue, "continue with Key Topic");
            Assertion.verifyEqual(elementIsDisplayed(txt_headerKeyTopic), true, "Message is Correct", pageInfo);
            Actions builder = new Actions(driver);
            builder.moveToElement(checkbox_OverideAIML).click().perform();
            clickOnElement(dropDownClick, "dropdown to select tag");
            WebElement tagToSelect = driver.findElement(By.xpath("//li[@data-value='"+tn.tagName+"']"));
            clickOnElement(tagToSelect, "select the Tag for automation");
            builder.moveToElement(btn_Save).click().perform();
            WebElement verifyTag = driver.findElement(By.xpath("//span[@class='key-mess-value' and text()='"+tn.tagName+"']"));
            Assertion.verifyEqual(elementIsDisplayed(verifyTag), true, "Verify Tag Present", pageInfo);
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void messageConfigure(){
        //To configure for the Message input for content recommendation
        try {
            clickOnElement(btn_MessageContinue, "continue with message");
            Assertion.verifyEqual(elementIsDisplayed(txt_headerMessage), true, "Message is Correct", pageInfo);
            Actions builder = new Actions(driver);
            builder.moveToElement(checkbox_AIML).click().perform();
            dragPriority("Model Score", 100);
            builder.moveToElement(btn_Save).click().perform();
            WebElement aimLodded = driver.findElement(By.xpath("//span[@class='key-message']/following-sibling::span"));
            Assertion.verifyEqual(elementIsDisplayed(aimLodded), true, "Verify AIML present", pageInfo);
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void selectSave(){
        //To Save the Actions page after providing inputs
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

    public void channelPriority(JSONArray actionsJson) {
        //To drag priority score for channels
        try {
            for (int i = 0; i < actionsJson.length(); i++) {
                for (int j = 0; j < actionsJson.getJSONObject(i).getJSONArray("channel_name").length(); j++) {
                    System.out.println(actionsJson.getJSONObject(i).getJSONArray("channel_name").length());
                    List<WebElement> aimlGenerated = driver.findElements(By.xpath("//span[text()='"+actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString()+"']//ancestor::div[contains(@class,'MuiGrid-container')]//span[text()='AI/ML Generated']"));
                    System.out.println(actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString());
                    if (aimlGenerated.size()>0){
                        pageInfo.info("This '"+actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString()+"' channel is AIML generated no need Priority");
                    }else {
                        WebElement dragPointer = driver.findElement(By.xpath("//span[text()='"+actionsJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString()+"']/ancestor::div[@class='MuiGrid-root MuiGrid-container']//span[@aria-valuetext]"));
                        //To set Priority Score for channels
                        for (int k=0; k<4;k++){
                            dragPointer.sendKeys(Keys.ARROW_RIGHT);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }
}
