package main;

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
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ViewBorrowedBooks_TransactionTest {
	WebDriver chrome;
	@BeforeTest
	private void beforeTransactionTest() {
		System.setProperty("webdriver.chrome.driver", "E:\\Programs\\chromedriver.exe");
		chrome = new ChromeDriver();
		chrome.manage().window().maximize();
		chrome.get("http://localhost:8080/library-master/member/");

		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys("jahidd26");
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys("123456");
		chrome.findElement(By.name("submit")).click();
		chrome.findElement(By.xpath("/html/body/div[2]/div/div/div/ul/li[3]/a"))
				.click();
		chrome.findElement(By.xpath(
				"/html/body/div[2]/div/div/div/ul/li[3]/ul/li[2]/a"))
		.click();
	}

	

	@Test(enabled = true)
	public void showTeacherBooks() {
		chrome.findElement(By.cssSelector("body > div.container > div > div > div > ul > li:nth-child(2) > a")).click();
	}


	@Test(enabled = true)
	public void SortByTitle() {
		chrome.findElement(By.cssSelector("#example > thead > tr > th.sorting_asc")).click();
	}
	@Test(enabled = true)
	public void showStudentBooks() {
		chrome.findElement(By.cssSelector("body > div.container > div > div > div > ul > li:nth-child(1) > a")).click();
	}
	
	
	
	
	
	@DataProvider(name = "ExistingData")
	public static Object[] getSearchBooks_ExistingData() {
		String[] s = { "Assembly Language Programming", "jahid mahmud", "Fourth Year", "jahidd26@gmail.com" };
		return s;
	}

	@Test(dataProvider = "ExistingData")
	public void Search_ExistingData(String input) {
		chrome.findElement(By.cssSelector("#example_filter > label > input[type=\"text\"]")).clear();
		chrome.findElement(By.cssSelector("#example_filter > label > input[type=\"text\"]")).sendKeys(input);
		WebElement table = chrome.findElement(By.xpath("//*[@id=\"example\"]/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		boolean isFound = false;
		for (int i = 0; i < rows.size(); i++) {
			isFound = false;
			List<WebElement> col = rows.get(i).findElements(By.tagName("td"));
			for (WebElement cell : col) {
				if (cell.getText().toLowerCase().contains(input.toLowerCase())) {
					isFound = true;
					break;
				}
			}
			if (!isFound)// not found in any row
				break;
		}
		Assert.assertTrue(isFound);
	}

	@DataProvider(name = "NotExistingData")
	public static Object[] getSearchBooks_NotExistingData() {
		String[] s = { "blabalbal" };
		return s;
	}

	@Test(dataProvider = "NotExistingData")
	public void Search_NotExistingData(String input) {
		chrome.findElement(By.cssSelector("#example_filter > label > input[type=\"text\"]")).clear();
		chrome.findElement(By.cssSelector("#example_filter > label > input[type=\"text\"]")).sendKeys(input);
		WebElement out = chrome.findElement(By.xpath("//*[@id=\"example\"]/tbody/tr/td"));

		Assert.assertEquals("No matching records found", out.getText());
	}
	
	
	
	@DataProvider(name = "ExistingData_date")
	public Object[][] test1() {
		return new Object[][] { { "2014-12-14 02:39:23", "2016-12-14 02:39:23" }};
	}

	@DataProvider(name = "notExistingData_date")
	public Object[][] test2() {
		return new Object[][] { { "2016-11-13 03:00:00", "2014-11-13 03:00:00" }};
	}

	@Test(dataProvider = "ExistingData_date")
	public void foundData(String from, String to) {
		chrome.findElement(By.xpath("/html/body/div[7]/div/div/div/ul/li[3]/a")).click();
		WebDriverWait wait = new WebDriverWait(chrome,2);
		wait.until(ExpectedConditions.visibilityOf(chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputEmail']"))));
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputEmail']")).clear();
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputEmail']")).sendKeys(from);
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputPassword']")).clear();
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputPassword']")).sendKeys(to);
		chrome.findElement(By.xpath("//*[@id=\"myModal3\"]/div[2]/form/div[3]/div/button")).click();
		List<WebElement> DateAdded = chrome.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[6]"));
		for (int i = 0; i < DateAdded.size(); i++) {
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			if (from.matches("\\d+-\\d+-\\d+")) {
				format = new SimpleDateFormat("yyyy-mm-dd");
			}
			try {
				Date date = format.parse(DateAdded.get(i).getText());
				Date fromdate = format.parse(from);
				Date todate = format.parse(to);
				System.out.println(DateAdded.get(i).getText());
				assertTrue(date.compareTo(fromdate) >= 0 && date.compareTo(todate) <= 0);
			} catch (ParseException e) {
				System.out.println("7aga 8lt");
				e.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "notExistingData_date")
	public void noData(String from, String to) {

		chrome.findElement(By.xpath("/html/body/div[7]/div/div/div/ul/li[3]/a")).click();
		WebDriverWait wait = new WebDriverWait(chrome, 2);
		wait.until(ExpectedConditions.visibilityOf(chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputPassword']"))));
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputEmail']")).clear();
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputEmail']")).sendKeys(from);
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputPassword']")).clear();
		chrome.findElement(By.xpath("//div[@id='myModal3']//div[@class='modal-body']//form[@class='form-horizontal']//div[@class='control-group']//div[@class='controls']//input[@id='inputPassword']")).sendKeys(to);
		chrome.findElement(By.xpath("//*[@id=\"myModal3\"]/div[2]/form/div[3]/div/button")).click();
		WebElement noData = chrome.findElement(
				By.xpath("//*[@id=\"example\"]/tbody/tr/td"));
		Assert.assertEquals(noData.getText(), "No data available in table");

	}
	@AfterTest
	public void afterTest() {
		chrome.close();
	}


}
