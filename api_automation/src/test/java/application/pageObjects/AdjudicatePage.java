package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import data.beans.AdjudicatePageBean;
import data.beans.ModelPageBean;
import framework.utility.common.Assertion;
import framework.utility.common.Utils;
import framework.utility.globalConst.Constants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjudicatePage extends PageInit {

    private static ExtentTest pNode;

    public AdjudicatePage(ExtentTest t1) {
        super(t1);
    }

    public static AdjudicatePage init(ExtentTest t1) {
        pNode = t1;
        return new AdjudicatePage(pNode);
    }

    @FindBy(xpath = "//span[contains(text(),'Adjudicate')]")
    WebElement stepperAdjudicateTab;

    @FindBy(xpath = "//p[text()='Add your first adjudicate rule for channel']")
    WebElement verifyTextChannel;

    @FindBy(xpath = "//p[text()='Add your first adjudicate rule for Customer']")
    WebElement verifyTextCustomer;

    @FindBy(xpath = "//p[text()='Add your first adjudicate rule for Customer channel']")
    WebElement verifyTextCustomerChannel;

    @FindBy(xpath = "//button/span[text()='Add Rules']")
    WebElement btnAddRules;

    @FindBy(xpath = "//button[@class='add']")
    WebElement btnAdd;

    @FindBy(xpath = "//button/span[text()='Save']")
    WebElement btn_Save;

    @FindBy(xpath = "//span[text()='Customer']")
    WebElement btn_TabCustomer;

    @FindBy(xpath = "//span[text()='Customer-CHANNEL']")
    WebElement btn_TabCustomerChannel;

    public JSONArray getAdjudicateInput(AdjudicatePageBean channels) {
        // To get json data from excel
        Markup m = MarkupHelper.createLabel("Actions Page", ExtentColor.BLUE);
        pageInfo.info(m);
        JSONArray adjudicateJson = null;
        try {
            JSONObject jsObj = new JSONObject(channels.channelName);
            adjudicateJson = jsObj.getJSONArray("Actions");
            System.out.println(adjudicateJson);
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
        return adjudicateJson;
    }

    public void toNavigateAdjudicate() {
        //To Navigate to Adjudicate through stepper
        try {
            Utils.captureScreen(pageInfo);
            clickOnElement(stepperAdjudicateTab, "To click on Adjudicate Stepper");
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void verifyAdjudicateChannelPage() {
        //To verify we are in channel Tab and click on Add Rules
        try {
            Assertion.verifyEqual(elementIsDisplayed(verifyTextChannel), true, "To verify we are in channel Tab", pageInfo);
            btnAddRules();
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void btnAddRules() {
        //To click on the Add Rules button
        try {
            if (elementIsDisplayed(btnAddRules)) {
                Actions actions = new Actions(driver);
                actions.moveToElement(btnAddRules).click().build().perform();
            } else {
                pageInfo.info("No Add Rule Button is Present");
            }
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void clickTabName(String tabName) {
        //To click On the channels Present on Form
        try {
            WebElement clickTab = driver.findElement(By.xpath("//span[text()='" + tabName + "']"));
            clickOnElement(clickTab, "on the tab");
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void clickAddButton() {
        //To click on the add Button after channels selected
        try {
            if (elementIsDisplayed(btnAdd)) {
                clickOnElement(btnAdd, "To add after selecting channels clicking Add Button");
            } else {
                pageInfo.fail("Unable to click on Add button");
            }
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void clickCheckBoxChannels(JSONArray adjudicateJson) {
        //To select the Channel Inputs and click on Add button
        try {
            for (int i = 0; i < adjudicateJson.length(); i++) {
                clickTabName(adjudicateJson.getJSONObject(i).get("Tabname").toString());
                for (int j = 0; j < adjudicateJson.getJSONObject(i).getJSONArray("channel_name").length(); j++) {
                    WebElement verifyCheckBox = driver.findElement(By.xpath("//h6[text()='" + adjudicateJson.getJSONObject(i).getJSONArray("channel_name").get(j) + "']/parent::div//*[local-name()='svg']"));
                    if (elementIsDisplayed(verifyCheckBox)) {
                        WebElement checkBox = driver.findElement(By.xpath("//h6[text()='" + adjudicateJson.getJSONObject(i).getJSONArray("channel_name").get(j) + "']/parent::div//*[local-name()='svg']"));
                        Actions builder = new Actions(driver);
                        builder.moveToElement(checkBox).click().perform();
                    } else {
                        pageInfo.fail("No Channel is Present");
                    }
                }
            }
            clickAddButton();
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void suggestionAndInterval(JSONArray adjudicateJson, AdjudicatePageBean AC) {
        //To select the checkboxes Inputs on customer and click on Add button
        try {
            Actions builder = new Actions(driver);
            for (int i = 0; i < adjudicateJson.length(); i++) {
                WebElement verifyCheckBox = driver.findElement(By.xpath("//h6[text()='" + adjudicateJson.getJSONObject(i).getString("checkbox") + "']/parent::div//*[local-name()='svg']"));
                if (elementIsDisplayed(verifyCheckBox)) {
                    Utils.putThreadSleep(3000);
                    builder.moveToElement(verifyCheckBox).click().perform();
                } else {
                    pageInfo.fail("No checkbox is Present");
                }
            }
            clickAddButton();
            inputCountCustomer(AC);
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void suggestionAndIntervalCC(JSONArray adjudicateJson, AdjudicatePageBean AC) {
        //To select the checkboxes Inputs on Customer-channel and click on Add button
        try {
            Actions builder = new Actions(driver);
            for (int i = 0; i < adjudicateJson.length(); i++) {
                WebElement verifyCheckBox = driver.findElement(By.xpath("//h6[text()='" + adjudicateJson.getJSONObject(i).getString("checkbox") + "']/parent::div//*[local-name()='svg']"));
                if (elementIsDisplayed(verifyCheckBox)) {
                    Utils.putThreadSleep(3000);
                    builder.moveToElement(verifyCheckBox).click().perform();
                } else {
                    pageInfo.fail("No checkbox is Present");
                }
            }
            clickAddButton();
            inputCountCustomerChannel(AC);
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void channelSelectForInput(String channel, AdjudicatePageBean AC) {
        //To enter channel Input
        try {
            JSONObject jsObj = new JSONObject(AC.channelName);
            JSONArray countObj = jsObj.getJSONArray("Actions");
            WebElement inputCount = driver.findElement(By.xpath("//span[text()='" + channel + "']//ancestor::div[contains(@class,'channel-tab')]//input[@name='suggestion_count']"));
            setText(inputCount, countObj.getJSONObject(1).getString("channelScore"), "Input for Interval and channel");
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void channelsInput(JSONArray adjudicateJson, AdjudicatePageBean AC) {
        //To enter all channel Input using for loop
        try {
            for (int i = 0; i < adjudicateJson.length(); i++) {
                for (int j = 0; j < adjudicateJson.getJSONObject(i).getJSONArray("channel_name").length(); j++) {
                    channelSelectForInput(adjudicateJson.getJSONObject(i).getJSONArray("channel_name").get(j).toString(), AC);
                }
            }
            selectSave();
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void selectSave() {
        //To Save the channel rules page after providing inputs
        try {
            if (elementIsClickable(btn_Save)) {
                clickOnElement(btn_Save, "On the Save Button");
            } else {
                pageInfo.fail("Save button is not able to click");
            }
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void verifyAdjudicateCustomer() {
        //To verify that we are in customer Page and click on the add rules
        try {
            Assertion.verifyEqual(elementIsDisplayed(verifyTextCustomer), true, "To verify we are in Customer Tab", pageInfo);
            btnAddRules();
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void verifyAdjudicateCustomerChannel() {
        //To verify that we are in customer Page and click on the add rules
        try {
            Assertion.verifyEqual(elementIsDisplayed(verifyTextCustomerChannel), true, "To verify we are in Customer channel Tab", pageInfo);
            btnAddRules();
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void customerTab() {
        //To click on the customerTab after completion of channel part
        try {
            clickOnElement(btn_TabCustomer, "on the tab customer");
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void customerChannelTab() {
        //To click on the customerTab after completion of channel part
        try {
            clickOnElement(btn_TabCustomerChannel, "on the tab customer-channel");
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void inputCountCustomerChannel(AdjudicatePageBean Json) {
        //To Input the Both Suggestion and Interval for Customer-channel and dropdown channel
        try {
            JSONObject jsObj = new JSONObject(Json.channelName);
            JSONArray countObj = jsObj.getJSONArray("Actions");
            for (int i = 0; i < countObj.length(); i++) {
                WebElement input = driver.findElement(By.xpath("//span[text()='" + countObj.getJSONObject(i).getString("checkbox") + "']//ancestor::div[contains(@class,'item-wrapper')]//input"));
                setText(input, countObj.getJSONObject(i).getString("value"), "Input for Interval and Suggestion count");
                WebElement dropdown = driver.findElement(By.xpath("//span[text()='" + countObj.getJSONObject(i).getString("checkbox") + "']//ancestor::div[contains(@class,'item-wrapper')]//select"));
                clickOnElement(dropdown, "On the dropdown");
                selectVisibleText(dropdown,  countObj.getJSONObject(0).getJSONArray("channel_name").get(0).toString() , "Select channel");
            }
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public List<String> inputCountCustomer(AdjudicatePageBean Json) {
        //To Input the Both Suggestion and Interval for customer
        List<String> JsonInput = new ArrayList<>();
        try {
            JSONObject jsObj = new JSONObject(Json.channelName);
            JSONArray countObj = jsObj.getJSONArray("Actions");
            for (int i = 0; i < countObj.length(); i++) {
                String getValue = "";
                String getCheckbox = "";
                WebElement input = driver.findElement(By.xpath("//span[text()='" + countObj.getJSONObject(i).getString("checkbox") + "']//ancestor::div[contains(@class,'hcp-not-disabled')]//input"));
                setText(input, countObj.getJSONObject(i).getString("value"), "Input for Interval and Suggestion count");
                getValue = countObj.getJSONObject(i).getString("value");
                getCheckbox = countObj.getJSONObject(i).getString("checkbox");
                JsonInput.add(getValue);
                JsonInput.add(getCheckbox);
            }
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
        return JsonInput;
    }
}
