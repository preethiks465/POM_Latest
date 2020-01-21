package com.mypages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	// Home Page Locators
	By UserNameLabel = By.xpath("//div[@id='Index_ProfileName' and text()='Sreekanth Bandi']");
	By adminMenu = By.xpath("//div[@data-name='Admin']");

	public String getHomePageHeader() {
		return doGetText(UserNameLabel);
	}

	public AdminPage clickOnAdminMenu() {
		doClick(adminMenu);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getInstance(AdminPage.class);
	}

}
