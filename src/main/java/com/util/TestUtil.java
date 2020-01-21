package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mypages.BasePage;
import com.mypages.Page;
import com.relevantcodes.extentreports.LogStatus;

public class TestUtil extends Page {
	public TestUtil(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 10;
	// public static String TestSheetPath = System.getProperty("user.dir") +
	// "/src/main/java/com/testdata/TestData.xlsx";
	public static final String REPORT_PATH = System.getProperty("user.dir") + File.separator + "target" + File.separator
			+ "Reports";

	public static Object[][] getdata(String SheetName, String CurrentTestCase, List<String> ColumnDetails,
			String CurrentPackage) {
		String TestSheetPath = System.getProperty("user.dir") + "/src/main/java/com/testdata/" + CurrentPackage
				+ ".xlsx";
		Object[][] data = null;
		// int i = 0;
		int rowcount = -1;
		int colcount;
		try {
			FileInputStream fs = new FileInputStream(TestSheetPath);
			XSSFWorkbook wbook = new XSSFWorkbook(fs);
			XSSFSheet wsheet = wbook.getSheet(SheetName);
			List<Integer> rc = findRow(wsheet, CurrentTestCase);
			List<Integer> cc = findColumns(wsheet, CurrentTestCase, ColumnDetails);

			data = new Object[rc.size()][ColumnDetails.size()];
			// Iterator<Row> rowiterator = wsheet.rowIterator();
			for (int i = 0; i < rc.size(); i++) {
				Iterator<Row> rowiterator = wsheet.rowIterator();
				while (rowiterator.hasNext()) {
					Row row = rowiterator.next();
					if (row.getRowNum() == rc.get(i)) {
						rowcount++;
						colcount = -1;
						for (int j = 0; j < cc.size(); j++) {
							Iterator<Cell> celliterator = row.cellIterator();
							while (celliterator.hasNext()) {
								Cell cell = celliterator.next();
								if (cell.getColumnIndex() == cc.get(j)) {
									colcount++;
									switch (cell.getCellType()) {
									case Cell.CELL_TYPE_STRING:
										data[rowcount][colcount] = cell.getStringCellValue();
										break;
									case Cell.CELL_TYPE_NUMERIC:
										data[rowcount][colcount] = cell.getNumericCellValue();
										break;
									}
								}
							}
						}

					} else {
						continue;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	private static List<Integer> findRow(XSSFSheet sheet, String cellContent) {
		/*
		 * This is the method to find the row number
		 */

		List<Integer> rowNum = new ArrayList<Integer>();

		Iterator<Row> rowiterator = sheet.rowIterator();
		while (rowiterator.hasNext()) {
			Row row = rowiterator.next();
			Iterator<Cell> celliterator = row.cellIterator();
			while (celliterator.hasNext()) {
				Cell cell = celliterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if (cell.getStringCellValue().contains(cellContent)) {
						rowNum.add(row.getRowNum());
					}
				}
			}
		}
		return rowNum;
	}

	private static List<Integer> findColumns(XSSFSheet sheet, String cellContent, List<String> ColumnDetails) {
		/*
		 * This is the method to find the row number
		 */

		List<Integer> ColsNum = new ArrayList<Integer>();

		Iterator<Row> rowiterator = sheet.rowIterator();
		while (rowiterator.hasNext()) {
			Row row = rowiterator.next();
			if (row.getRowNum() == 0) {
				for (int i = 0; i < ColumnDetails.size(); i++) {
					Iterator<Cell> celliterator = row.cellIterator();
					while (celliterator.hasNext()) {
						Cell cell = celliterator.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							if (cell.getStringCellValue().contains(ColumnDetails.get(i))) {
								ColsNum.add(cell.getColumnIndex());
							}
						}
					}
				}
			}
		}
		return ColsNum;
	}

	public static boolean verifyData(String data) {
		WebElement e = null;
		String actualText = "";
		String Object = ".//*[contains(text(),'" + data + "')]";
		// test.log(LogStatus.INFO, "Verifying the provided data value with actual
		// value");
		try {
			e = driver.findElement(By.xpath(Object));
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(e));
			actualText = e.getText();
		} catch (Exception t) {
			// test.log(LogStatus.FAIL, "Following Web Element Not found :" + Object);

		}
		String actualText1 = actualText.toLowerCase();
		String data1 = data.toLowerCase();
		if (actualText1.contains(data1)) {
			return true;
		} else {
			return false;
		}
	}

	public static void ClickonOKButton() {
		driver.findElement(By.xpath("//*[contains(text(),'OK')]")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void takeScreenshotAtEndOfTest() {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + "\\screenshots\\" + System.currentTimeMillis() + ".png";
		try {
			FileUtils.copyFile(src, new File(dest));
			test.log(LogStatus.FAIL, test.addScreenCapture(dest));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean compareTableRowValues(By Locator1,By Locator2, String data) {
		//String object1[] = object.split("-");
		String data1[] = data.split("-");
		boolean flag;
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		test.log(LogStatus.INFO, "Compare different row values in the table");

		List<WebElement> e = driver.findElements(Locator1);
		List<WebElement> e1 = driver.findElements(Locator2);

		int count = 0;
		for (int i = 0; i < e.size(); i++) {
			String actual = (String) jse.executeScript("return arguments[0].value;", e.get(i));

			if (actual.equalsIgnoreCase(data1[0])) {
				String expected = (String) jse.executeScript("return arguments[0].value;", e1.get(i));
				if (expected.equalsIgnoreCase(data1[1]))
					count++;
				break;
			}
		}
		if (count != 0)
			flag = true;
		else
			flag = false;
		return flag;
	}
}