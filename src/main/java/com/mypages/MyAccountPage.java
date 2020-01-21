package com.mypages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	By adminMyAccountText = By.xpath("//h1[contains(text(),'Account Contact Details')]");
	By adminAddExtentionButton = By.xpath("//div[@id='addMailExtn']");
	By adminMailExtentionName = By.xpath("(//input[@name='extnName'])[last()]");
	By adminMailExtentionDescription = By.xpath("(//input[@name='extnDesc'])[last()]");
	By adminSaveExtentionButton = By.xpath("//div[@id='saveMailExtensions']");
	public By adminMailExtenNameTable = By.xpath(".//*[@id='extnTable']/tbody/tr/td[2]/input");
	public By adminMailExtenDescripTable = By.xpath(".//*[@id='extnTable']/tbody/tr/td[3]/input");

	public boolean verifyMyAccountDetails() {
		return isDisplayed(adminMyAccountText);
	}

	public void AddMailExtensions(String ExtnName, String ExtnDesc) {
		doClick(adminAddExtentionButton);
		doSendKeys(adminMailExtentionName, ExtnName);
		doSendKeys(adminMailExtentionDescription, ExtnDesc);
		doClick(adminSaveExtentionButton);

	}
}
