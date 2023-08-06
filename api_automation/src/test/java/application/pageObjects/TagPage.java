package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import data.beans.TagPageBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.common.Utils;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class TagPage extends PageInit {
    private static ExtentTest pNode;

    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;

    @FindBy(xpath = "//h3['Add New Tag']")
    WebElement titleAddNewTag;

    @FindBy(xpath = "//button/span[text()='Add New Tag']")
    WebElement btnAddNewTagButton;

    @FindBy(xpath = "//button[@aria-expanded='false']")
    WebElement SideMenuOpen;

    @FindBy(xpath = "//nav[@id='sideMenu']//button")
    WebElement navBarExpandCollapseButton;

    @FindBy(xpath = "//div[@id='Tags']")
    WebElement clickOnSideMenuTags;

    @FindBy(xpath = "//div[@id='Home']")
    WebElement clickOnHomeSideNav;

    @FindBy(xpath = "//button[@aria-expanded='true']")
    WebElement SideMenuCloseClick;

    @FindBy(xpath = "//input[@name='tag_name']")
    WebElement SetTextTagName;

    @FindBy(xpath = "//textarea[@name='tag_desc']")
    WebElement SetTextTagDesc;

    @FindBy(xpath = "//div[@aria-haspopup='listbox']")
    WebElement selectDropDownClick;

    @FindBy(xpath = "//li[@data-value='select all']")
    WebElement selectAllOption;

    @FindBy(xpath = "//button/span[text()='Save and View']")
    WebElement btnSaveAndView;

    @FindBy(xpath = "//button/span[text()='Cancel']")
    WebElement clickOnCancel;

    @FindBy(xpath = "//div[text()='Edit Tag']")
    WebElement btnEditTagOnActions;

    @FindBy(xpath = "//button/span[text()='Delete Tag']")
    WebElement btnDeleteTag;

    @FindBy(xpath = "//button/span[text()='Okay']")
    WebElement btnOkay;

    @FindBy(xpath = "//a[@underline='hover' and text()='Tags']")
    WebElement linkBreadCrumb;

    public TagPage(ExtentTest t2) {
        super(t2);
    }

    public static TagPage init(ExtentTest t2) {
        pNode = t2;
        return new TagPage(pNode);
    }
    public void createTagFlowSideNav() {
        try {
            if (SideMenuOpen.getAttribute("aria-expanded").equalsIgnoreCase("false"))
            {
                clickOnElement(SideMenuOpen, "View All Scenarios Button");
                Utils.captureScreen(pageInfo);
            } else {
                pageInfo.info("sideNav is Opened");
            }
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void sideClickTags(){
        try {
            closeToastMessages();
            openSideNavItem(navBarExpandCollapseButton,true,"Tags");
            clickOnElement(clickOnSideMenuTags,"sideMenuClick on Tags");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void sideMenuClose(){
        try {
            clickOnElement(SideMenuCloseClick,"SideMenuClick to close");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void createNewTag(TagPageBean tg){
        try {
            List<WebElement> listOfTags = driver.findElements(By.xpath("//a[text()='"+tg.tagName+"']"));
            int count = listOfTags.size();
            if(count > 0){
                Utils.captureScreen(pageInfo);
                checkContentAndScenarioUsage(tg);
            } else {
                pageInfo.info("Tag is not available");
                createTag(tg);
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void checkContentAndScenarioUsage(TagPageBean tg){
        try{
            WebElement toCheckContentCount = driver.findElement(By.xpath("//a[text()='"+tg.tagName+"']//ancestor::div[@role='row']//div[text()=' Content Usage:']/span"));
            WebElement toCheckScenarioCount = driver.findElement(By.xpath("//a[text()='"+tg.tagName+"']//ancestor::div[@role='row']//div[text()='Scenario Usage:']/span"));
            if(Integer.parseInt(getText(toCheckContentCount)) == 0 && Integer.parseInt(getText(toCheckScenarioCount)) == 0 )
            {
                pageInfo.info("Deleting Tag from UI");
                tagDeleteFromUi(tg);
                Utils.captureScreen(pageInfo);
                createTag(tg);
            }
            else {
                pageInfo.info("Tag has been used in content or Scenario unable to delete from UI deleting from backend");
                tagDeleteBackend(tg);
                createTag(tg);
            }
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void tagDeleteBackend(TagPageBean tg){
        try {
                DatabaseUtil.deleteExistingTag(ConfigInput.tagSchemaName,tg.tagName);
                DatabaseUtil.closeDBResources();
                createTagFlowSideNav();
                clickOnElement(clickOnHomeSideNav,"To click on SideNavHome");
                sideClickTags();
                sideMenuClose();
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void tagDeleteFromUi(TagPageBean tg){
        try {
            WebElement clickOnEllipsisActions = driver.findElement(By.xpath("//a[text()='"+ tg.tagName +"']//ancestor::div[@role='row']//div[@class='action-cell link-cell']"));
            clickOnElement(clickOnEllipsisActions,"To click on the Tag Actions");
            clickOnElement(btnEditTagOnActions,"to click on Edit on tag");
            WebElement verifyTagToDelete = driver.findElement(By.xpath("//div[@class='tag-title' and text()='"+tg.tagName+"']"));
            elementIsDisplayed(verifyTagToDelete);
            clickOnElement(btnDeleteTag,"To delete Tag");
            clickOnElement(btnOkay,"to click on Okay button");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void createTag(TagPageBean tg){
        try {
            AddNewTagButtonTags();
            InputFormTags(tg);
            SaveAndViewButton(tg);
            Utils.putThreadSleep(5000);
            Utils.captureScreen(pageInfo);
            clickOnElement(linkBreadCrumb,"to click on Breadcrumb");
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }

    public void AddNewTagButtonTags(){
        try{
            if(elementIsDisplayed(btnAddNewTagButton))
            {
                clickOnElement(btnAddNewTagButton," Add New Tag Button");
                Utils.captureScreen(pageInfo);
            }
            else {
                pageInfo.info("Add New Tag is not Present");
            }
            elementIsDisplayed(titleAddNewTag);
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void InputFormTags(TagPageBean tg){
        try{
            setText(SetTextTagName,tg.tagName,"Tag Name");
            setText(SetTextTagDesc, tg.tagDesc, "Tag Description");
            clickOnElement(selectDropDownClick,"To click on the drop down to select all option");
            clickOnElement(selectAllOption,"To click on Select all option");
            Utils.captureScreen(pageInfo);
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    public void SaveAndViewButton(TagPageBean tg){
        try {
            if(elementIsDisplayed(btnSaveAndView))
            {
                clickOnElement(btnSaveAndView, " save and view button");
                waitTillDisappearingOfSpinner();
            }
            else {
                pageInfo.info("save and view is not Present are not able to click");
            }
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