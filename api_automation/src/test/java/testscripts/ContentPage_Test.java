package testscripts;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import application.pageObjects.ContentPage;
import application.pageObjects.HomePage;
import framework.utility.common.Assertion;
import scripts.TestInit;

public class ContentPage_Test extends TestInit{
	
	   @Test(priority = 1, groups = {"SANITY"}, enabled = true)
	    public void ContentPage_TC_1123() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1123", "To verify in primary navigation side bar Content is displayed in expanded state");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	
	        	//Expand Side Menu Bar
	        	contentPage.expandOrCollapseSideBar();
	        	contentPage.verifyContenTabIsVisible();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   
	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1123", enabled = true)
	    public void ContentPage_TC_1125() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1125", "To verify user lands on Content landing page after clicking on Content Icon and in primary navigation it should be highlighted in expanded mode");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.sideClickContent();
	        	contentPage.validateContentTabOnSidebarisHighlited();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	
	   @Test(priority = 2, groups = {"SANITY"},  dependsOnMethods = "ContentPage_TC_1125", enabled = true)
	    public void ContentPage_TC_1124() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1124", "To verify in primary navigation side bar Content is displayed  in collapsed state");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	//Collapse Side Menu Bar
	        	contentPage.expandOrCollapseSideBar();
	        	contentPage.verifyContenTabIsVisible();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   
	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1125", enabled = true)
	    public void ContentPage_TC_1126() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1126", "To verify user lands on Content landing page after clicking on Content Icon and in primary navigation it should be highlighted  in collapsed mode");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.validateContentTabOnSidebarisHighlited();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	
	   @Test(priority = 2, groups = {"SANITY"},dependsOnMethods="ContentPage_TC_1125", enabled = true)
	    public void ContentPage_TC_1127() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1127", "Validate the Breadcrumb on Content landing page");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	//contentPage.sideClickContent();
	        	contentPage.verifyContentBreadcrumbIsPresent();
	        	
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1127", enabled = true)
	    public void ContentPage_TC_1128() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1128", "Validate the Page Title on Content landing page");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.verifyContentTitle();
	       
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   
	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1127", enabled = true)
	    public void ContentPage_TC_1129() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1129", "Validate the help text present beside Page Title");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.mouseHoverOnHelpTooltip();
	        	contentPage.validateHelpTooltipBesidesContentTitle();
	       
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	    
	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1127", enabled = true)
	    public void ContentPage_TC_1130() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1130", "Validate Brand dropdown is present at the right top corner of the Content landing page.");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.validateBrandDropdownIsPresent();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   
	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1127", enabled = true)
	    public void ContentPage_TC_1131() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1131", "Validate when there is only one brand in the workspace it is selected by default in Brand dropdown on Content landing page");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.validatesIfHavingOneOptionByDefaultItsSelectedInBranddropdown();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   
	   @Test(priority = 3, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1127", enabled = true)
	    public void ContentPage_TC_1132() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1132", "Validate user is able to select brand from the drop down when there are multiple brands available");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.getTextOfAllBrandOptions();
	        	contentPage.selectBrandFromDropdowm();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   

	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1127", enabled = true)
	   public void ContentPage_TC_1136() {
	        ExtentTest t2 = pNode.createNode("ContentPage_TC_1136", "To verify the columns displayed on Content landing page");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.verifyColumnsDisplayedOnContentPage();
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }

	   
	   @Test(priority = 2, groups = {"SANITY"}, dependsOnMethods = "ContentPage_TC_1127", enabled = true)
	   public void VerifyColumnIsSortable() {
	        ExtentTest t2 = pNode.createNode("ContentPage: TC_1138, TC_1139, TC_1141", "To verify the columns sortable Content landing page");
	        try {
	        	ContentPage contentPage=ContentPage.init(t2);
	        	contentPage.verifyColumnsAreSortable("Name");
	        	contentPage.verifyColumnsAreSortable("Content Type");
	        	contentPage.verifyColumnsAreSortable("Channels");
	        	contentPage.verifyColumnsAreSortable("Import Date");
	        } catch (Exception e) {
	            markTestAsFailure(e, t2);
	        } finally {
	            Assertion.finalizeSoftAsserts();
	        }
	    }
	   

}
