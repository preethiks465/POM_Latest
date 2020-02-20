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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
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

public class BaseTest implements IHookable {

	public static WebDriver driver;
	public static Page page;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static ChromeDriverService services;
	public String CurrentTestCase = this.getClass().getName();
	String CurrentPackage = this.getClass().getPackage().getName();
	public static String RunStatus = "";
	List<String> RunMode = new ArrayList<String>();
	String SheetName = "MyAccountDetails";

	List<String> TCName = new ArrayList<String>();
	static int i = 0;

	@BeforeTest
	@Parameters(value = { "browser" })
	public void ServiceStart(String browser) {
		if (browser.equals("chrome")) {
			services = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File("D:\\Selenium\\drivers\\version79\\chromedriver.exe"))
					.usingAnyFreePort().build();
			try {
				services.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			driver = new RemoteWebDriver(services.getUrl(), new ChromeOptions());
		}
		page = new BasePage(driver);
		driver.close();

	}

	@BeforeMethod
	@Parameters(value = { "browser" })
	public void setup(String browser) {
		TCName.add("TC Name");
		RunMode.add("RunMode");
		Object[][] TestCaseDetails = (page.testutil.getdata(SheetName, CurrentTestCase, TCName, CurrentPackage));
		Object[][] RunModeDetails = (page.testutil.getdata(SheetName, CurrentTestCase, RunMode, CurrentPackage));
		if (TestCaseDetails.length <= 1) {
			page.test = page.rep.startTest(TestCaseDetails[0][0].toString());// Start this test
			RunStatus = RunModeDetails[0][0].toString();
		} else {
			page.test = page.rep.startTest(TestCaseDetails[i][0].toString());
			RunStatus = RunModeDetails[i][0].toString();
			i++;
		}
		if (RunStatus.contentEquals("Y"))
			OpenBrowser(browser);

	}

	@Parameters(value = { "browser" })
	public static void OpenBrowser(String browser) {
		if (RunStatus.contentEquals("Y")) {
			if (browser.equals("chrome")) {
				driver = new RemoteWebDriver(services.getUrl(), new ChromeOptions());
			} else if (browser.equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser.equals("Edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			} else
				System.out.println("No browser setup in xml");
		}
	}

	public static void browserconfig() {

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

	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		if (RunStatus.contentEquals("Y")) {
			BaseTest.browserconfig();
			callBack.runTestMethod(testResult);
			if (testResult.getThrowable() != null) {
				page.test.log(LogStatus.FAIL, testResult.getThrowable().getCause());
				page.testutil.takeScreenshotAtEndOfTest();
			}
		} else {
			page.test.log(LogStatus.SKIP, "The test method " + CurrentTestCase + " is skipped");
			testResult.setStatus(ITestResult.SKIP);

		}
	}

}
