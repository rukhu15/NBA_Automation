package application.pageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ContentPage extends PageInit {
	private static ExtentTest pNode;
	SoftAssert art= new SoftAssert();
	
	 @FindBy(xpath = "//nav[@id='sideMenu']//button")
	    WebElement navBarExpandCollapseButton;
	 
	  @FindBy(xpath = "//div[@id='Content']")
	    WebElement sideMenuContent;
	  
	  @FindBy(xpath = "//li//p[text()='Content']")
	    WebElement contentBreadcrumb;
	  
	  @FindBy(xpath = "//div[@class='content-title']")
	    WebElement contentTitle;
	  
	  @FindBy(xpath = "//div[text()='Content']/following-sibling::div")
	    WebElement tooptipIcon;
	  
	  @FindBy(xpath = "//div[@role='tooltip']/div")
	    WebElement contentHelpToolTip;
	
	  @FindBy(xpath = "//select[@id='selectedBrandVal']")
	    WebElement brandDropdown;
	  
	  @FindBy(xpath = "//select/parent::div")
	    WebElement expandDropdown;
	  
	  @FindBy(xpath = "//div[contains(@class,'highlighted')]/div[@id='Content']")
	    WebElement highlitedContentOption;
	  
	  @FindBy(xpath = "//button[@role='button']")
	    WebElement expandSideBarButton;
	  
	  @FindBy(xpath = "//table//th//input[@type='checkbox']")
	    WebElement checkboxColumn;
	  
	  String parameterizedColumnNameSpanTagname = "//table//th//span[text()='%s']";
	  
	  String parameterizedColumnName = "//table//th[text()='%s']";
	
	  String parameterizedSortableColumnName = "//th//span[contains(@class, 'Sort') and text()='%s']"; 
	  
	public ContentPage(ExtentTest t2) {
		super(t2);
	}
	
	public static ContentPage init(ExtentTest t2) throws Exception {
		pNode = t2;
		return new ContentPage(pNode);
	}
	
    public void sideClickContent(){
        try {
            closeToastMessages();
           // openSideNavItem(navBarExpandCollapseButton,true,"Content");
            clickOnElement(sideMenuContent,"sideMenuClick on Content");
            
        }catch (Exception e){
            logInfo(e.getMessage());
        }
    }
    
    public void verifyContentBreadcrumbIsPresent() {
    	Assert.assertTrue(elementIsDisplayed(contentBreadcrumb));
    	if(elementIsDisplayed(contentBreadcrumb)) {
    		pNode.pass("Breadcrumb is displayed on Content landing page");
    	}
    }
    
    public void verifyContentTitle() {
    	String expectedTitle = "Content";
    	Assert.assertEquals(expectedTitle, contentTitle.getText());
    	if(elementIsDisplayed(contentTitle)) {
    		pNode.pass("Content Title is displayed on Content landing page");
    	}
    }
   
    public void mouseHoverOnHelpTooltip() {
    	actions.moveToElement(tooptipIcon).build().perform();
    }
    
    public void validateHelpTooltipBesidesContentTitle() {
    	String expectedTooltip = "Content management provides the capability to manage content metadata so that they can be leveraged for content personalization in NBE.";
    	Assert.assertEquals(expectedTooltip, contentHelpToolTip.getText());
    	if(elementIsDisplayed(contentHelpToolTip)) {
    		pNode.pass("help tooltip displayed besides content title on Content landing page");
    	}
    }
    
    public void validateBrandDropdownIsPresent() {
    	Assert.assertTrue(elementIsDisplayed(brandDropdown));
    	if(elementIsDisplayed(brandDropdown)) {
    		pNode.pass("Brand dropdown is present at the right top corner of the Content landing page.");
    	}
    }
    
   public List<String> getListOfBrandsFromDropdown() {
	   clickOnElement(expandDropdown, "click on dropdown element");
	   List<String> listOfOptions =getOptionValue(brandDropdown);
	   System.out.println("Number of brands present in drowpdown are "+listOfOptions.size());
	   return listOfOptions;
   }
    
    public void validatesIfHavingOneOptionByDefaultItsSelectedInBranddropdown(){
    	 List<String> listOfOptions=getListOfBrandsFromDropdown();
    	if( listOfOptions.size()==1) {
    		String OptionExp=listOfOptions.get(0);
    		String OptionAct=getText(brandDropdown);
    		Assert.assertEquals(OptionExp, OptionAct);
    		pNode.pass("When there is only one brand in the workspace it is selected by default in Brand dropdown on Content landing page");
    	}else {
    		System.out.println("Multiple options are present in Brand dropdown on Content landing page");
    	}
    }
    
    public void selectBrandFromDropdowm() {
    	 List<String> listOfOptions=getListOfBrandsFromDropdown();
    	 //Selection last brand from options
    	 selectIndex(brandDropdown,listOfOptions.size()-1, "b dropdwon" );
    }
    
   public void getTextOfAllBrandOptions() {
	  String name= brandDropdown.getText();
	  System.out.println(name);
   }
   
   public void validateContentTabOnSidebarisHighlited() {
	   Assert.assertTrue(highlitedContentOption.isDisplayed());
	   if(highlitedContentOption.isDisplayed()) 
		   pNode.pass("Content option in sidebar is highlited");
   }
   
   public void expandOrCollapseSideBar() throws IOException{
	   clickOnElement(expandSideBarButton, "expand side bar button");
   }
   
   public void verifyContenTabIsVisible() {
	   Assert.assertTrue(sideMenuContent.isDisplayed(),"Content is displayed in side menu bar");
	   if(sideMenuContent.isDisplayed()) 
		   pNode.pass("Content is displayed in side menu bar");
   }
   
   public void verifyColumnsDisplayedOnContentPage() {
	   String [] expectedColumn = {"Name", "Content Type", "Channels", "Import Date", "Key Topics", "Actions"};
	   
	   art.assertTrue(checkboxColumn.isDisplayed(),"checkbox column is not displayed");
	   
	  for(int index=0; index<expectedColumn.length-3; index++) {
		  String columnName= String.format(parameterizedColumnNameSpanTagname, expectedColumn[index]);
		  WebElement ele=driver.findElement(By.xpath(columnName));
		  art.assertTrue(ele.isDisplayed(), expectedColumn[index] +" column is not displayed");
	  }
	  
	  for(int index=expectedColumn.length-2; index<expectedColumn.length-1; index++) {
		  String columnName= String.format(parameterizedColumnName, expectedColumn[index]);
		  WebElement ele=driver.findElement(By.xpath(columnName));
		  art.assertTrue(ele.isDisplayed(), expectedColumn[index] +" column is not displayed");
	  }
   }
   
   public void verifyColumnsAreSortable(String ColumnName) {
	   String columnName= String.format(parameterizedSortableColumnName, ColumnName);
	   WebElement ele=driver.findElement(By.xpath(columnName));
		  art.assertTrue(ele.isDisplayed()," column is not sortable");
	  }
}
