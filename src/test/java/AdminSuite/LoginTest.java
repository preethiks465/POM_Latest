package AdminSuite;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mypages.HomePage;
import com.mypages.LoginPage;

import BasePackage.BaseTest;

public class LoginTest extends BaseTest {

	List<String> ColumnDetails = new ArrayList<String>();
	String SheetName = "MyAccountDetails";
	public String CurrentTestCase = this.getClass().getName();
	String CurrentPackage = this.getClass().getPackageName().toString();
	/*
	 * List<String> TCName = new ArrayList<String>(); static int i = 0;
	 */

	@Test(dataProvider = "ARData")
	public void verifyLoginTest(String expLoginPageTitle, String expLoginPageHeader, String expHomePageHeader) {
		/*
		 * TCName.add("TC Name"); Object[][] r = (page.testutil.getdata(SheetName,
		 * CurrentTestCase, TCName, CurrentPackage)); page.test =
		 * page.rep.startTest(r[i][0].toString());// Start this test i++;
		 */
		String LoginPageTitle = page.getInstance(LoginPage.class).getLoginPageTitle();
		Assert.assertEquals(LoginPageTitle, expLoginPageTitle);
		String LoginPageHeader = page.getInstance(LoginPage.class).getLoginPageHeader();

		Assert.assertEquals(LoginPageHeader, expLoginPageHeader);
		HomePage homepage = page.getInstance(LoginPage.class).doLogin(page.prop.getProperty("username"),
				page.prop.getProperty("password"));
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String HomePageHeader = homepage.getHomePageHeader();
		System.out.println(HomePageHeader);
		Assert.assertEquals(HomePageHeader, expHomePageHeader);
	}

	@DataProvider(name = "ARData")
	public Object[][] getARTestData() {
		ColumnDetails.add("LoginPageTitle");
		ColumnDetails.add("LoginPageHeader");
		ColumnDetails.add("HomePageHeader");
		return page.testutil.getdata(SheetName, CurrentTestCase, ColumnDetails, CurrentPackage);
	}
}
