package BasePackage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.mypages.BasePage;
import com.mypages.Page;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;
import com.util.WebEventListener;

import extentManager.CopyFile;
import extentManager.ExtentManager;
//import extentManager.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public Page page;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public ChromeDriverService services;
	public String CurrentTestCase = this.getClass().getName();
	String CurrentPackage = this.getClass().getPackageName().toString();

	List<String> RunMode = new ArrayList<String>();
	String SheetName = "MyAccountDetails";

	List<String> TCName = new ArrayList<String>();
	static int i = 0;

	@BeforeClass
	public void ReportStart() {
		services = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("D:\\Selenium\\drivers\\version79\\chromedriver.exe"))
				.usingAnyFreePort().build();
		try {
			services.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver = new RemoteWebDriver(services.getUrl(), new ChromeOptions());
		page = new BasePage(driver);
		driver.close();

	}

	@BeforeMethod
	@Parameters(value = { "browser" })
	public void setup(String browser) {

		TCName.add("TC Name");
		Object[][] r = (page.testutil.getdata(SheetName, CurrentTestCase, TCName, CurrentPackage));
		if (r.length <= 1) {
			page.test = page.rep.startTest(r[0][0].toString());// Start this test i++;
		} else {
			page.test = page.rep.startTest(r[i][0].toString());
			i++;
		}

		RunMode.add("RunMode");
		Object[][] r2 = (page.testutil.getdata(SheetName, CurrentTestCase, RunMode, CurrentPackage));
		String RunStatus = r2[0][0].toString();
		if (RunStatus.equals("Y")) {
			if (browser.equals("chrome")) {

				driver = new RemoteWebDriver(services.getUrl(), new ChromeOptions());

			} else if (browser.equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else
				System.out.println("No browser setup in xml");
			// page = new BasePage(driver);
			e_driver = new EventFiringWebDriver(driver);
			eventListener = new WebEventListener(driver);
			e_driver.register(eventListener);
			driver = e_driver;
			page = new BasePage(driver);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
			System.out.println(page.prop.getProperty("url"));
			driver.get(page.prop.getProperty("url"));
		} else {
			page.test.log(LogStatus.SKIP, "The test method " + CurrentTestCase + " is skipped");
			throw new SkipException("Skipping the testcase " + CurrentTestCase);

		}
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	@AfterTest
	public void copyFile() {
		if (page.rep != null) {
			page.rep.endTest(page.test);
			page.rep.flush();
			// The above two lines are compulsory else reports will not be generated
		}
		CopyFile.copyFileFromReports();
		services.stop();
	}

}
