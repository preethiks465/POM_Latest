/**
 * 
 */
package com.mypages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author PreethikrishnaSankur
 *
 */
public class LoginPage extends BasePage {

	// Page Locators
	private By userName = By.id("UserEmail");
	private By password = By.id("UserPassword");
	private By loginButton = By.id("logincheck");
	private By headerlogo = By.xpath("//img[@src='/images/autorabit-logo.svg']");

	public WebElement getUserName() {
		return getElement(userName);
	}

	public WebElement getPassword() {
		return getElement(password);
	}

	public WebElement getLoginButton() {
		return getElement(loginButton);
	}

	public WebElement getHeaderlogo() {
		return getElement(headerlogo);
	}

	public String getLoginPageTitle() {
		return getPageTitle();
	}

	public String getLoginPageHeader() {
		return getPageHeader(headerlogo);
	}

	public HomePage doLogin(String userName, String password) {
		getUserName().sendKeys(userName);
		getPassword().sendKeys(password);
		getLoginButton().click();
		return getInstance(HomePage.class);
	}

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
