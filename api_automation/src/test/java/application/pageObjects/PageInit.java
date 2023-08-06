package application.pageObjects;

import com.aventstack.extentreports.ExtentTest;
import framework.utility.common.Assertion;
import framework.utility.common.DriverFactory;
import framework.utility.common.Utils;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.Constants;
import framework.utility.propertiesManager.MessageReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageInit {
	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static WebDriverWait smallWait;
	protected static WebDriverWait longWait;
	protected static ExtentTest pageInfo;
	protected static Actions actions;
	protected static JavascriptExecutor js;

	public PageInit(ExtentTest t1) {
		pageInfo = t1;
		driver = DriverFactory.getDriver();
		smallWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_SMALL_WAIT_TIME));
		wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
		longWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_LONG_WAIT_TIME));
		actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	public PageInit(WebDriver driver, ExtentTest t1) {
		pageInfo = t1;
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
		longWait = new WebDriverWait(driver, Duration.ofSeconds(25));
		actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Get Text
	 *
	 * @param element [WebElement]
	 * @return
	 */
	protected String getText(WebElement element) {
		highlightElement(element);
		return wait.until(ExpectedConditions.elementToBeClickable(element)).getText();
	}

	protected static void logInfo(String info) {
		System.out.println(info); // todo add logger for log.js
		pageInfo.info(info); // log for Extent Report
	}

	/**
	 * Use to set text of an INPUT having a fixed Label provided that the Label is a
	 * SPAN
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param value         [String] - Value to be set
	 * @param fieldName     [String] - Field name e.g.
	 *                      setTextInInputFieldWhereReferenceIsSpan(areaFilter,
	 *                      "config.filter.value", "AUT_TEAM HYBRID", "Team
	 *                      Instance");
	 * @return
	 */
	protected static WebElement setTextInInputFieldWhereReferenceIsSpan(WebElement parentElement, String key,
			String value, String fieldName) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		}

		setText(elem, value, fieldName);
		return elem;
	}

	protected static String getTextInInputFieldWhereReferenceIsSpan(WebElement parentElement, String key) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		}

		// return elem.getText();
		return elem.getAttribute("value");
	}

	/**
	 * Use to set text of an TEXTAREA having a fixed Label provided that the Label
	 * is a SPAN
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param value         [String] - Value to be set
	 * @param fieldName     [String] - Field name e.g.
	 *                      setTextInTextAreaReferenceIsSpan(actionBody,
	 *                      "scenario.pg1.scenario.desc", "Test Desc", "Scenario
	 *                      Description");
	 * @return
	 */
	protected static WebElement setTextInTextAreaReferenceIsSpan(WebElement parentElement, String key, String value,
			String fieldName) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;

		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//textarea[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//textarea[1]"))));
		}

		setText(elem, value, fieldName);
		return elem;
	}

	/**
	 * Use to set text of an TEXTAREA having a fixed Label provided that the Label
	 * is a Input
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param value         [String] - Value to be set
	 * @param fieldName     [String] - Field name e.g.
	 *                      setTextInTextAreaReferenceIsSpan(actionBody,
	 *                      "scenario.pg1.scenario.desc", "Test Desc", "Scenario
	 *                      Description");
	 * @return
	 */
	protected static WebElement setTextInTextAreaReferenceIsInput(WebElement parentElement, String key, String value,
			String fieldName) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;

		try {
			elem = wait.until(ExpectedConditions
					.elementToBeClickable(parentElement.findElement(By.xpath("//input[@id='bootstrap-input']"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			elem = wait.until(ExpectedConditions
					.elementToBeClickable(parentElement.findElement(By.xpath("//input[@id='bootstrap-input']"))));
		}

		setText(elem, value, fieldName);
		return elem;
	}

	/**
	 * Use to set text of an TEXTAREA having a fixed Label provided that the Label
	 * is a Body
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param value         [String] - Value to be set
	 * @param fieldName     [String] - Field name e.g.
	 *                      setTextInTextAreaReferenceIsSpan(actionBody,
	 *                      "scenario.pg1.scenario.desc", "Test Desc", "Scenario
	 *                      Description");
	 * @return
	 */
	protected static WebElement setTextInTextAreaReferenceIsBody(WebElement parentElement, String key, String value,
			String fieldName) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;

		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(By.xpath(
					"//body/div[3]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/div[1]/div[3]/div[1]/form[1]/div[1]/div[2]/div[1]/div[1]/input[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(By.xpath(
					"//body/div[3]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/div[1]/div[3]/div[1]/form[1]/div[1]/div[2]/div[1]/div[1]/input[1]"))));
		}

		setText(elem, value, fieldName);
		return elem;
	}

	/**
	 * Use to click on a WebElement having a fixed Label provided that the Label is
	 * a SPAN
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param fieldName     [String] - Field name e.g.
	 *                      clickOnElementWhereReferenceIsSpan(actionBody,
	 *                      "team.pg1.isMirror", "Is Mirror");
	 * @return
	 */
	protected static void clickOnElementWhereReferenceIsSpan(WebElement parentElement, String key, String fieldName)
			throws Exception {
		String labelName = MessageReader.getLabel(key, null);

		// get the Id from the Label
		String elemId = null;

		try {
			elemId = wait
					.until(ExpectedConditions.elementToBeClickable(parentElement
							.findElement(By.xpath(".//span[contains(text(), '" + labelName + "')]/parent::*"))))
					.getAttribute("for");
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			Thread.sleep(Constants.MAX_WAIT_TIME);
			elemId = wait
					.until(ExpectedConditions.elementToBeClickable(parentElement
							.findElement(By.xpath(".//span[contains(text(), '" + labelName + "')]/parent::*"))))
					.getAttribute("for");
		}

		// set Text
		clickOnElementUsingJs(wait.until(ExpectedConditions.elementToBeClickable(By.id(elemId))), fieldName);
	}

	protected static boolean getCheckBoxStatusWhereReferenceIsSpan(WebElement parentElement, String key) {
		String labelName = MessageReader.getLabel(key, null);

		WebElement checkBox = null;

		try {
			checkBox = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
				checkBox = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
						By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
			} catch (InterruptedException ex) {
				Assertion.markAsFailure("Couldn't find the Checkbox with label: " + labelName);
			}
		}

		if (checkBox != null) {
			Utils.scrollToAnElement(checkBox);
			return checkBox.isSelected();
		}

		Assertion.markAsFailure("Couldn't, find the Checkbox having label: " + labelName);
		return false;
	}

	/**
	 * Use to Check an INPUT CHECKBOX having a fixed Label provided that the Label
	 * is a SPAN
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param fieldName     [String] - Field name e.g.
	 *                      checkInputCheckboxWhereReferenceIsSpan(modalArea,
	 *                      "enable.account.sharing", "Enable Account Sharing");
	 * @return
	 */
	protected static boolean checkInputCheckboxWhereReferenceIsSpan(WebElement parentElement, String key,
			String fieldName) throws IOException {
		String labelName = MessageReader.getLabel(key, null);

		WebElement checkBox = null;

		try {
			checkBox = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
			}
			checkBox = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		}

		Utils.scrollToAnElement(checkBox);
		if (!checkBox.isSelected()) {
			clickOnElement(checkBox, "Check " + fieldName);
			Utils.captureScreen(pageInfo);
			return true;
		} else {
			pageInfo.info("Checkbox " + labelName + " is already Checked");
			Utils.captureScreen(pageInfo);
			return false;
		}
	}

	/**
	 * Use to Un-Check an INPUT CHECKBOX having a fixed Label provided that the
	 * Label is a SPAN
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param fieldName     [String] - Field name e.g.
	 *                      unCheckInputCheckboxWhereReferenceIsSpan(modalArea,
	 *                      "enable.zip.sharing", "Enable Zip Sharing");
	 * @return [boolean] true if any changes were made
	 */
	protected static boolean unCheckInputCheckboxWhereReferenceIsSpan(WebElement parentElement, String key,
			String fieldName) throws IOException {
		String labelName = MessageReader.getLabel(key, null);

		WebElement checkBox = null;
		try {
			checkBox = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
			}
			checkBox = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		}

		Utils.scrollToAnElement(checkBox);
		if (checkBox.isSelected()) {
			clickOnElement(checkBox, "Un Check " + fieldName);
			Utils.captureScreen(pageInfo);
			return true;
		} else {
			pageInfo.info("Checkbox " + labelName + " is already Un Checked");
			Utils.captureScreen(pageInfo);
			return false;
		}
	}

	/**
	 * Set Date
	 *
	 * @param parentElement [WebElement] - Parent Div, must be unique
	 * @param key           [String] - Label Name for the UI date Object
	 * @param value         [String] - value to be set
	 * @param fieldName     [String] - Target field name e.g. setDate(positionForm,
	 *                      "position.pg1.start.date", "2020/01/01", "Start Date");
	 */
	protected static void setDate(WebElement parentElement, String key, String value, String fieldName) {
		String labelName = MessageReader.getLabel(key, null);

		// get the Id from the Label
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]//input[1]"))));
		}

		// set Text
		setText(elem, value, fieldName);
	}

	protected static WebElement getElementWhereReferenceIsLabelInClassicMode(String key) {
		String labelName = MessageReader.getLabel(key, null);

		// get the Id from the Label
		String elemId = null;

		try {
			elemId = wait
					.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//label[contains(text(), '" + labelName + "')]")))
					.getAttribute("for");
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
			}
			elemId = wait
					.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//label[contains(text(), '" + labelName + "')]")))
					.getAttribute("for");
		}

		// set Text
		return wait.until(ExpectedConditions.elementToBeClickable(By.id(elemId)));
	}

	/**
	 * select from a combo box, target Element is a div and reference element is the
	 * label
	 *
	 * @param parentElement
	 * @param key           - Label key mentioned in label.bundles
	 * @param value         - value to be selected
	 * @return
	 */
	protected static WebElement selectFromLightningComboWhereReferenceIsLabel(WebElement parentElement, String key,
			String value, String fieldName) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(By.xpath(
				".//label[contains(text(), '" + labelName + "')]/ancestor::div[1]/lightning-combobox[1]//input"))));

		elem.sendKeys(value); // set the combo box text as the value
		Utils.scrollToBottomOfPage(); // Scroll to the bottom of page, the comb box list may be large
		WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(value)));
		clickOnElement(link, value);
		return elem;
	}

	/**
	 * Use to select from an INPUT SELECT having a fixed Label provided that the
	 * Label is a SPAN
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param value         [String] - Value to be selected
	 * @param fieldName     [String] - Field name e.g.
	 *                      selectFromInputWhereReferenceIsSpan(lblScenarioStage,
	 *                      "scenario.pg1.status", "Active", "Scenario Status");
	 * @return
	 */
	protected static WebElement selectFromInputWhereReferenceIsSpan(WebElement parentElement, String key, String value,
			String fieldName) {
		String labelName = MessageReader.getLabel(key, null);

		WebElement elem = null;

		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement
					.findElement(By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement
					.findElement(By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]"))));
		}

		Utils.scrollToAnElement(elem);
		clickOnElement(elem, "Div-" + fieldName); // click on div, so that select options are shown
		WebElement link = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title='" + value + "']"))); // get the
																											// Link
																											// Object as
																											// Select
																											// Option
		clickOnElement(link, value);
		return elem;
	}

	/**
	 * Use to select from an SELECT having a fixed Label provided that the Label is
	 * a SPAN
	 *
	 * @param parentElement [WebElement], parent element, form or div in which the
	 *                      target element is present, must be unique
	 * @param key           [String] - label key
	 * @param value         [String] - Value to be selected
	 * @param fieldName     [String] - Field name e.g.
	 *                      selectFromSelectBoxWhereReferenceIsSpan(actionBody,
	 *                      "scenario.pg1.team", "AUT_TEAM_01", "Team Name");
	 * @return
	 */
	protected static WebElement selectFromSelectBoxWhereReferenceIsSpan(WebElement parentElement, String key,
			String value, String fieldName) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]/select[1]"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(
					By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]/select[1]"))));
		}

		selectVisibleText(elem, value, fieldName);

		return elem;
	}

	/**
	 *
	 * @param parentElement
	 * @param key
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	protected static String getCRDetailsOnChangeRequestPage(WebElement parentElement, String key) throws Exception {
		String value = null;
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement
					.findElement(By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[2]"))));
		} catch (Exception e) {
			try {
				Thread.sleep(Constants.MAX_LONG_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			Utils.scrollToTopOfPage();
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement
					.findElement(By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[2]"))));
		}

		value = elem.getText();
		if (value.contains("\n")) { // sometimes ui text contains newline character
			value = value.split("\n")[1];
		}
		return value;
	}

	/**
	 * used for the new Salesforce UI components
	 * 
	 * @param parentElement
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected static String getCRDetailsOnChangeRequestPageLWC(WebElement parentElement, String key) throws Exception {
		String value = null;
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = null;
		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(By.xpath(
					".//span[contains(text(), '" + labelName + "')]/ancestor::div[2]//slot[@slot='outputField']"))));
		} catch (Exception e) {
			try {
				Thread.sleep(Constants.MAX_LONG_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			Utils.scrollToTopOfPage();
			elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement.findElement(By.xpath(
					".//span[contains(text(), '" + labelName + "')]/ancestor::div[2]//slot[@slot='outputField']"))));
		}

		value = elem.getText();
		if (value.contains("\n")) { // sometimes ui text contains newline character
			value = value.split("\n")[1];
		}
		return value;
	}

	protected static WebElement selectFromSelectBoxUsingPartialTextWhereReferenceIsSpan(WebElement parentElement,
			String key, String value, String fieldName) {
		String labelName = MessageReader.getLabel(key, null);
		WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(parentElement
				.findElement(By.xpath(".//span[contains(text(), '" + labelName + "')]/ancestor::div[1]/select[1]"))));

		selectPartialVisibleText(elem, value, fieldName);
		return elem;
	}

	/**
	 * Set Text on a WebElement
	 *
	 * @param elem        [WebElement] target element UI
	 * @param text        [String] Value to be set
	 * @param elementName [String] UI Element reference name e.g.
	 *                    setText(txtUserName, "Dr. Who", "UserName");
	 */
	protected static void setText(WebElement elem, String text, String elementName) {
		try {

			wait.until(ExpectedConditions.visibilityOf(elem)).clear();
			// elem.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			Thread.sleep(ConfigInput.additionalWaitTime);
			highlightElement(elem);
			elem.sendKeys(text);
			logInfo("set " + elementName + "'s text as '" + text + "'");

		} catch (Exception e) {
			pageInfo.info("Retry Set Text: " + elementName);
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			wait.until(ExpectedConditions.elementToBeClickable(elem)).clear();
			elem.sendKeys(text);
			logInfo("set " + elementName + "'s text as '" + text + "'");
			try {
				Thread.sleep(ConfigInput.additionalWaitTime);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Select Visible Text, conventional Select Box
	 *
	 * @param elem
	 * @param text
	 * @param elementName
	 */
	protected static void selectVisibleText(WebElement elem, String text, String elementName) {
		Select sel = null;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='3px solid red'",elem);
			sel = new Select(elem);
			sel.selectByVisibleText(text);
			Thread.sleep(ConfigInput.additionalWaitTime);
			logInfo("select from " + elementName + ". Selected text '" + text + "'");
		} catch (Exception e) {
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			// pageInfo.warning("Exception Occurred: " + e.getMessage());
			pageInfo.info("Retry Selecting By Visible Text: " + elementName);
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			sel = new Select(elem);
			sel.selectByVisibleText(text);
			logInfo("select from " + elementName + ". Selected text '" + text + "'");
			try {
				Thread.sleep(ConfigInput.additionalWaitTime);
			} catch (InterruptedException ex) {
			}
		}
	}

	protected static void selectPartialVisibleText(WebElement elem, String text, String elementName) {
		Select sel = new Select(elem);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='3px solid red'",elem);
			List<WebElement> optionsInnerText = sel.getOptions();
			for (WebElement optionText : optionsInnerText) {
				String textContent = optionText.getAttribute("textContent");
				if (textContent.toLowerCase().contains(text.toLowerCase())) {
					logInfo("select from " + elementName + ". Selected text '" + text + "'");
					optionText.click();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logInfo("select from " + elementName + ". Selected text '" + text + "'");
		}
	}

	protected static void clickOnElement(WebElement elem, String elementName) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			Thread.sleep(ConfigInput.additionalWaitTime);
			highlightElement(elem);
			elem.click();
			Thread.sleep(ConfigInput.additionalWaitTime);
			logInfo("Click on " + elementName);
		} catch (Exception e) {
			// pageInfo.warning("Exception Occurred: " + e.getMessage());
			pageInfo.info("Retry Clicking " + elementName);
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			elem.click();
			try {
				Thread.sleep(ConfigInput.additionalWaitTime);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			logInfo("Click on " + elementName);
		}
	}

	protected static List<String> getOptionValue(WebElement elem) {
		Select sel = new Select(elem);
		List<WebElement> we = sel.getOptions();
		List<String> ls = new ArrayList<String>();
		for (WebElement a : we) {
			if (!a.getAttribute("value").equals("Select")) {
				ls.add(a.getAttribute("value").trim());
			}
		}
		return ls;
	}

	protected void setTextUsingJs(WebElement elem, String text, String elementName) {
		try {
			js = (JavascriptExecutor) DriverFactory.getDriver();
			wait.until(ExpectedConditions.elementToBeClickable(elem)).clear();
			js.executeScript("arguments[0].value='" + text + "';", elem);
			Thread.sleep(ConfigInput.additionalWaitTime);
			logInfo("set " + elementName + "'s text as '" + text + "'");
		} catch (Exception e) {
			pageInfo.fail("Failed to set " + text + " for " + elementName);
			pageInfo.error(e.toString());
			e.printStackTrace();
		}
	}

	protected void selectIndex(WebElement elem, int index, String elementName) {
		Select sel = null;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			sel = new Select(elem);
			Thread.sleep(ConfigInput.additionalWaitTime);
			sel.selectByIndex(index);
			logInfo("Select from " + elementName + ". Selected index: '" + index + "'");
		} catch (Exception e) {
			pageInfo.warning("Exception Occurred: " + e.getMessage());
			pageInfo.info("Retry Selecting By Index: " + elementName);
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			sel = new Select(elem);
			sel.selectByIndex(index);
			logInfo("Select from " + elementName + ". Selected index: '" + index + "'");
		}
	}

	protected static void clickOnElementUsingJs(WebElement elem, String elementName) throws Exception {
		js = (JavascriptExecutor) DriverFactory.getDriver();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
		} catch (Exception e) {
			Thread.sleep(Constants.MAX_WAIT_TIME);
			wait.until(ExpectedConditions.elementToBeClickable(elem));
		}

		js.executeScript("arguments[0].click();", elem);
		Thread.sleep(ConfigInput.additionalWaitTime);
		logInfo("Click on " + elementName + " using Js");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	/**
	 * Get web table data under a header use when data is present immediately under
	 * the header column
	 *
	 * @param table
	 * @param headerName
	 * @return
	 * @throws IOException
	 */
	protected String getTableDataUsingHeaderName(WebElement table, String headerName) throws IOException {
		int headerIndex = getHeaderIndex(table, headerName);
		String text = null;
		if (headerIndex == 0) {
			pageInfo.fail("Failed to find the header in the webtable");
			Utils.captureScreen(pageInfo);
			return null;
		}

		if (table.findElements(By.tagName("th")).size() > 0) {
			text = table.findElement(By.xpath(
					".//tr/th[contains(text(), '" + headerName + "')]/parent::*/following::td[" + headerIndex + "]"))
					.getText();
		} else {
			text = table.findElement(By.xpath(
					".//tr/td[contains(text(), '" + headerName + "')]/parent::*/following::td[" + headerIndex + "]"))
					.getText();
		}
		return text;
	}

	/**
	 * Get Table Data - this method is very useful to get a simple webtable data in
	 * a useful format
	 *
	 * @param table         [webelement] - table
	 * @param primaryKey    [int] column index that has to referred as primary key
	 * @param excludeRow    [int] list of rows that need to be excluded
	 * @param excludeColumn [int] list of columns that need to be excluded
	 * @return [HashMap<String, HashMap < String, String>>] table data ex.
	 *         List<Integer> excludeRow = new ArrayList<>(), excludeColumn = new
	 *         ArrayList<>(); excludeRow.add(0); // exclude first Row
	 *         excludeRow.add(-1); // exclude last row excludeColumn.add(0); //
	 *         exclude first column // usage HashMap<String, HashMap<String,
	 *         String>> data = getTableData(this.tableBarUser, 2, excludeRow,
	 *         excludeColumn);
	 */
	protected HashMap<String, HashMap<String, String>> getTableData(WebElement table, int primaryKey,
			List<Integer> excludeRow, List<Integer> excludeColumn) {
		HashMap<String, HashMap<String, String>> data = new HashMap<>();

		// get header list
		List<String> headerList = getHeaderList(table);

		// get number of rows
		int tableRow = table.findElements(By.tagName("tr")).size();

		// iterate for each row and get corresponding values for the headers
		// exclude rows - excludeRow
		// exclude columns - excludeColumn
		for (int j = 1; j < tableRow; j++) {// excluding first row assuming it as header
			int bottonRowIndex = j - tableRow;
			if (excludeRow.contains(j) || excludeRow.contains(bottonRowIndex)) {
				continue;
			}

			int row = j + 1;
			String key = table.findElement(By.xpath(".//tr[" + row + "]/td[" + primaryKey + "]")).getText().trim();
			HashMap<String, String> rowData = new HashMap<>();
			for (int i = 0; i < headerList.size(); i++) {
				int column = i + 1;

				if (excludeColumn.contains(i)) {
					continue;
				}

				// start forming the hash map
				String header = headerList.get(i);
				String cellVal = table.findElement(By.xpath(".//tr[" + row + "]/td[" + column + "]")).getText().trim()
						.toString();
				rowData.put(header, cellVal);
			}
			data.put(key, rowData);
		}
		return data;
	}

	/**
	 * Get header index
	 *
	 * @param table
	 * @param headerName
	 * @return
	 */
	protected int getHeaderIndex(WebElement table, String headerName) {
		List<WebElement> headerList;

		if (table.findElements(By.tagName("th")).size() > 0) {
			headerList = table.findElement(By.xpath(".//tr/th[contains(text(), '" + headerName + "')]/parent::tr[1]"))
					.findElements(By.tagName("th"));
		} else {
			headerList = table.findElement(By.xpath(".//tr/td[contains(text(), '" + headerName + "')]/parent::tr[1]"))
					.findElements(By.tagName("td"));
		}

		int i = 1;
		for (WebElement elem : headerList) {
			if (elem.getText().trim().equals(headerName)) {
				return i;
			}
			i++;
		}
		return 0;
	}

	private List<String> getHeaderList(WebElement table) {
		List<WebElement> headerElements;
		List<String> headerList = new ArrayList<>();
		highlightElement(table);
		if (table.findElements(By.tagName("th")).size() > 0) {
			headerElements = table.findElements(By.tagName("th"));
		} else {
			headerElements = table.findElements(By.className("xyz")); // todo, need to make generic
		}

		for (WebElement header : headerElements) {
			headerList.add(header.getText());
		}
		return headerList;
	}

	protected boolean elementIsDisplayed(WebElement elem) {
		try {
			wait.until(ExpectedConditions.visibilityOf(elem));
			highlightElement(elem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean elementIsEnabled(WebElement elem) {
		try {
			wait.until(ExpectedConditions.visibilityOf(elem));
			highlightElement(elem);
			return elem.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean elementIsClickable(WebElement elem) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elem));
			highlightElement(elem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean elementIsChecked(WebElement elem) {
		try {
			String checked = wait.until(ExpectedConditions.visibilityOf(elem)).getAttribute("checked");
			highlightElement(elem);
			if (checked == null)
				return false;
			else if (checked.equalsIgnoreCase("true"))
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("some error!");
			return false;
		}
	}

	protected void selectCheckboxUsingLabelNameClassicView(String key) {
		String label = MessageReader.getLabel(key, null);
		String elemId = null;
		try {
			elemId = wait
					.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//label[contains(text(), '" + label + "')]")))
					.getAttribute("for");
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			elemId = wait
					.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//label[contains(text(), '" + label + "')]")))
					.getAttribute("for");
		}
		WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(By.id(elemId)));

		if (!elem.isSelected())
			clickOnElement(elem, "Select " + label);
		else
			pageInfo.info("Checkbox " + label + " is already selected");
	}

	protected static WebElement getWebElementToPerformActionOnScenarioOptions(String scenarioName) {
		WebElement elem = null;

		try {
			elem = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
					By.xpath("//*[text()='" + scenarioName + "']/parent::td/following-sibling::td[3]//i"))));
		} catch (Exception e) {
			pageInfo.info(e.getMessage());
			try {
				Thread.sleep(Constants.MAX_WAIT_TIME);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			elem = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
					By.xpath("//*[text()='" + scenarioName + "']/parent::td/following-sibling::td[3]//i"))));
		}

		return elem;
	}

	public static void highlightElement(WebElement element) {
		for (int i = 0; i <2; i++) {
			try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 3px solid red;");
			Thread.sleep(100);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected static void openSideNavItem(WebElement navBarExpandCollapseButton, Boolean openNavBar, String ...sideNavItemName) throws IOException {
		try{
			WebElement navItem=null;
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			if(!(sideNavItemName.length == 0)) {
				navItem = driver.findElement(By.xpath("//div[contains(@class, 'nav-item')]//div[text()='" + sideNavItemName[0] + "']"));
				System.out.println(sideNavItemName[0]);
			}
			//Open or close navbar depending on requirement
			if(navBarExpandCollapseButton.getAttribute("aria-expanded").equalsIgnoreCase("true") && openNavBar == true){
				pageInfo.info("Navbar is opened");
				executor.executeScript("arguments[0].scrollIntoView(true);", navItem);
				clickOnElement(navItem, sideNavItemName[0]);
			}
			else if (navBarExpandCollapseButton.getAttribute("aria-expanded").equalsIgnoreCase("true") && openNavBar == false) {
				clickOnElement(navBarExpandCollapseButton, "Close navbar");
			}
			else if(navBarExpandCollapseButton.getAttribute("aria-expanded").equalsIgnoreCase("false") && openNavBar == true){
				clickOnElement(navBarExpandCollapseButton, "Open navbar");
				executor.executeScript("arguments[0].scrollIntoView(true);", navItem);
				clickOnElement(navItem, sideNavItemName[0]);
			}
			else{
				pageInfo.info("Navbar is closed");
			}

			Utils.captureScreen(pageInfo);
		}
		catch(Exception e){
			logInfo(e.getMessage());
		}
	}

	protected static void closeToastMessages() throws Exception {
		try{

			Utils.captureScreen(pageInfo);
			List<WebElement> toastMessages = driver.findElements(By.xpath("//div[contains(@class, 'Toastify__toast Toastify__toast-theme--colored')]"));
			System.out.println(toastMessages.size());
			if(toastMessages.size() >=1) {
				for (int i = 0; i < toastMessages.size(); i++) {
					WebElement closeToastMessage = driver.findElement(By.xpath("(//div[contains(@class, 'Toastify__toast Toastify__toast-theme--colored')])//button[@aria-label='close']//*[local-name()='svg']"));
					clickOnElement(closeToastMessage, "Close toast message");
					Utils.captureScreen(pageInfo);
				}
			}
			else {
				pageInfo.info("No toast message generated");
			}

		}
		catch(Exception e){
			logInfo(e.getMessage());
		}
	}

	protected WebElement getWebElementUsingDynamicXpath(String xpathStr, String originalStr, String newStr){
		return driver.findElement(By.xpath(xpathStr.replace(originalStr,newStr)));
	}

}