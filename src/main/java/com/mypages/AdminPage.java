package com.mypages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminPage extends BasePage {
	public AdminPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	By adminMyAccountLink = By.xpath("//label[text()='My Account']");

	public MyAccountPage clickOnMyAccountLink() {
		doClick(adminMyAccountLink);
		return getInstance(MyAccountPage.class);
	}

}
