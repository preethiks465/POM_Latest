package com.mypages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePage extends Page {

	public BasePage(WebDriver driver) {
		super(driver);
		try {
			prop = new Properties();
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/config/config.properties");
			prop.load(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Generic util Wrappers
	public void doClick(By Locator) {
		staticWait(2000);
		driver.findElement(Locator).click();
	}

	public void doSendKeys(By Locator, String text) {
		driver.findElement(Locator).sendKeys(text);
	}

	public String doGetText(By Locator) {
		return driver.findElement(Locator).getText();
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public boolean isDisplayed(By Locator) {
		return driver.findElement(Locator).isDisplayed();
	}

	public String getPageHeader(By Locator) {
		return getElement(Locator).getAttribute("alt");
	}

	public void staticWait(int waitseconds) {
		try {
			Thread.sleep(waitseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WebElement getElement(By Locator) {
		WebElement element = null;
		try {
			waitForElementPresent(Locator);
			element = driver.findElement(Locator);
			return element;
		} catch (Exception e) {
			System.out.println("error occured");
			e.printStackTrace();
		}
		return element;
	}

	public List<WebElement> getElements(By Locator) {
		List<WebElement> element = null;
		try {
			waitForElementPresent(Locator);
			element = driver.findElements(Locator);
			return element;
		} catch (Exception e) {
			System.out.println("error occured");
			e.printStackTrace();
		}
		return element;
	}

	public void waitForElementPresent(By Locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
		} catch (Exception e) {
			System.out.println("Some error occured while waiting for element" + Locator.toString());
			e.printStackTrace();
		}

	}

	public void waitForPageTitle(String title) {
		try {
			wait.until(ExpectedConditions.titleContains(title));
		} catch (Exception e) {
			System.out.println("Some error occured while waiting for getting page title" + title);
			e.printStackTrace();
		}

	}

}
