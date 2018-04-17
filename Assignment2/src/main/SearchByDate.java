package main;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class SearchByDate {
	String path = "http://localhost:8080/library-master/member/dashboard.php";
	WebDriver chrome;
	String exePath = "E:\\Programs\\chromedriver.exe";
	public String expected = null;
	public String actual = null;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", exePath);
		chrome = new ChromeDriver();
		chrome.get(path);
		chrome.manage().window().maximize();
		chrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys("jahidd26");
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys("123456");
		chrome.findElement(By.name("submit")).click();
	}

	@BeforeMethod
	public void beforeMethod() {
		chrome.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[6]/a[1]")).click();
	}

	@DataProvider(name = "test1")
	public Object[][] test1() {
		return new Object[][] { { "2014-11-13 03:00:00", "2014-11-13 03:00:00" },
				{ "2014-11-13 03:00:00", "2016-11-13 03:00:00" }, { "2014-11-13", "2016-11-13" },{ "2014-11-13 03:00:00", "2015-06-13 03:00:00" }};
	}

	@DataProvider(name = "test2")
	public Object[][] test2() {
		return new Object[][] { { "2016-11-13 03:00:00", "2014-11-13 03:00:00" }, { "", "" },
				{ "1800-11-13 03:00:00", "1900-11-13 03:00:00" }, { "", "2016-11-13 03:00:00" },
				{ "2016-11-13 03:00:00", "" } };
	}

	@Test(dataProvider = "test1")
	public void foundData(String from, String to) {
		WebDriverWait wait = new WebDriverWait(chrome, 2);
		wait.until(ExpectedConditions.visibilityOf(chrome.findElement(By.name("from"))));
		chrome.findElement(By.name("from")).clear();
		chrome.findElement(By.name("from")).sendKeys(from);
		chrome.findElement(By.name("to")).clear();
		chrome.findElement(By.name("to")).sendKeys(to);
		chrome.findElement(By.xpath("/html[1]/body[1]/div[4]/div[2]/form[1]/div[3]/div[1]/button[1]")).click();
		List<WebElement> DateAdded = chrome.findElements(
				By.xpath("/html[1]/body[1]/div[7]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr/td[10]"));
		for (int i = 0; i < DateAdded.size(); i++) {
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			if (from.matches("\\d+-\\d+-\\d+")) {
				format = new SimpleDateFormat("yyyy-mm-dd");
			}
			try {
				Date date = format.parse(DateAdded.get(i).getText());
				Date fromdate = format.parse(from);
				Date todate = format.parse(to);
				assertTrue(date.compareTo(fromdate) >= 0 && date.compareTo(todate) <= 0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "test2")
	public void noData(String from, String to) {
		WebDriverWait wait = new WebDriverWait(chrome, 2);
		wait.until(ExpectedConditions.visibilityOf(chrome.findElement(By.name("from"))));
		chrome.findElement(By.name("from")).clear();
		chrome.findElement(By.name("from")).sendKeys(from);
		chrome.findElement(By.name("to")).clear();
		chrome.findElement(By.name("to")).sendKeys(to);
		chrome.findElement(By.xpath("/html[1]/body[1]/div[4]/div[2]/form[1]/div[3]/div[1]/button[1]")).click();
		WebElement noData = chrome.findElement(
				By.xpath("/html[1]/body[1]/div[7]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]"));
		assertEquals(noData.getText(), "No data available in table");
	}

	@AfterTest
	public void afterTest() {
		chrome.close();
	}

}
