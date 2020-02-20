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

import BasePackage.BaseTest;

public class MyAccount_002_AddMailExtentions extends BaseTest {

	List<String> ColumnDetails = new ArrayList<String>();
	String SheetName = "MyAccountDetails";
	String CurrentTestCase = this.getClass().getName();
	String CurrentPackage = this.getClass().getPackage().getName();

	/*
	 * List<String> TCName = new ArrayList<String>(); static int i=0;
	 */
	@Test(dataProvider = "ARData")
	public void verify_MyAccount_002_AddMailExtentions_Test(String ExtensionName, String ExtensionDesc,
			String NotificationMsg) {
		/*
		 * TCName.add("TC Name"); Object[][] r = (page.testutil.getdata(SheetName,
		 * CurrentTestCase, TCName, CurrentPackage)); page.test =
		 * page.rep.startTest(r[i][0].toString());// Start this test i++;
		 */
		HomePage homepage = page.getInstance(LoginPage.class).doLogin(page.prop.getProperty("username"),
				page.prop.getProperty("password"));
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AdminPage adminPage = homepage.clickOnAdminMenu();
		MyAccountPage myAccountPage = adminPage.clickOnMyAccountLink();
		myAccountPage.AddMailExtensions(ExtensionName, ExtensionDesc);
		Assert.assertTrue(page.testutil.verifyData(NotificationMsg));
		page.testutil.ClickonOKButton();

	}

	@DataProvider(name = "ARData")
	public Object[][] getARTestData() {
		ColumnDetails.add("ExtensionName");
		ColumnDetails.add("ExtensionDesc");
		ColumnDetails.add("NotificationMsg");
		return page.testutil.getdata(SheetName, CurrentTestCase, ColumnDetails,CurrentPackage);
	}
}
