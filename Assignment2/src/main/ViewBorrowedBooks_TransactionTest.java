package main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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
		chrome.findElement(By.cssSelector("body > div:nth-child(11) > div > div > div > ul > li.divider-vertical.open > a"))
				.click();
		chrome.findElement(By.cssSelector("body > div:nth-child(11) > div > div > div > ul > li.divider-vertical.open > ul > li:nth-child(2) > a"))
		.click();
	}

	@Test(enabled=true)
	public void showStudentBooks() {
		chrome.findElement(By.cssSelector("body > div.container > div > div > div > ul > li:nth-child(1) > a")).click();
	}

	@Test(enabled = true)
	public void showTeacherBooks() {
		chrome.findElement(By.cssSelector("body > div.container > div > div > div > ul > li:nth-child(2) > a")).click();
	}

	@Test(enabled = true)
	public void showOldBooks() {
		chrome.findElement(By.cssSelector("body > div.container > div > div > div > ul > li:nth-child(3) > a")).click();
	}

	@Test(enabled = true)
	public void sortById() {
		chrome.findElement(By.cssSelector("#example > thead > tr > th:nth-child(1)")).click();
	}

	@Test(enabled = true)
	public void SortByTitle() {
		chrome.findElement(By.cssSelector("#example > thead > tr > th.sorting_asc")).click();
	}

	@DataProvider(name = "ExistingData")
	public static Object[] getSearchBooks_ExistingData() {
		String[] s = { "Computer Graphics", "Programming", "Third Edition", "New" };
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

		String message = "succes";
		Assert.assertTrue(isFound, message);
		System.out.println(message);
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

}
