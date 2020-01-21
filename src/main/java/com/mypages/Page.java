package com.mypages;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import extentManager.ExtentManager;

public abstract class Page {
	public static WebDriver driver;
	public WebDriverWait wait;
	public Properties prop;
	public ExtentReports rep = ExtentManager.getInstance();// Making it public as it can be accessible in every package
	public static ExtentTest test;
	public com.util.TestUtil testutil;

	public Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, 10);
	}

	public <TPage extends BasePage> TPage getInstance(Class<TPage> pageclass) {
		try {
			return pageclass.getDeclaredConstructor(WebDriver.class).newInstance(this.driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
