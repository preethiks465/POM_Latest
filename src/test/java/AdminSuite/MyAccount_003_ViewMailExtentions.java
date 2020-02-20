package AdminSuite;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mypages.AdminPage;
import com.mypages.HomePage;
import com.mypages.LoginPage;
import com.mypages.MyAccountPage;
import com.relevantcodes.extentreports.LogStatus;

import BasePackage.BaseTest;

public class MyAccount_003_ViewMailExtentions extends BaseTest {

	List<String> ColumnDetails = new ArrayList<String>();
	String SheetName = "MyAccountDetails";
	String CurrentTestCase = this.getClass().getName();
	String CurrentPackage = this.getClass().getPackage().getName();

	@Test(dataProvider = "ARData")
	public void verify_MyAccount_003_ViewMailExtentions_Test(String MailDetails) {
		HomePage homepage = page.getInstance(LoginPage.class).doLogin(page.prop.getProperty("username"),
				page.prop.getProperty("password"));
		homepage.staticWait(10000);
		AdminPage adminPage = homepage.clickOnAdminMenu();
		MyAccountPage myAccountPage = adminPage.clickOnMyAccountLink();
		myAccountPage.staticWait(5000);

		Assert.assertTrue(page.testutil.compareTableRowValues(myAccountPage.adminMailExtenNameTable,
				myAccountPage.adminMailExtenDescripTable, MailDetails));
		page.test.log(LogStatus.PASS, "Actual is same as Expected");

	}

	@DataProvider(name = "ARData")
	public Object[][] getARTestData() {
		ColumnDetails.add("MailDetails");
		return page.testutil.getdata(SheetName, CurrentTestCase, ColumnDetails, CurrentPackage);
	}
}
