package extentManager;

//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.util.TestUtil;

public class ExtentManager {
	private static ExtentReports extent;
	public static String fName;

	public static ExtentReports getInstance() {
		if (extent == null) {
			Date d = new Date();
			String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
			extent = new ExtentReports(TestUtil.REPORT_PATH + File.separator + fileName, true,
					DisplayOrder.OLDEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir") + "//ReportsConfig.xml"));
			extent.addSystemInfo("Selenium Version", "3.12.0");
			fName = fileName;
		}
		return extent;
	}

	public static String fileName1() {
		return fName;
	}

}
