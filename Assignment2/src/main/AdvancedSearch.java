package main;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AdvancedSearch {
	String path = "http://localhost:8080/library-master/member/dashboard.php";
	WebDriver chrome;
	String exePath = "E:\\Programs\\chromedriver.exe";
	public String expected = null;
	public String actual = null;

	@BeforeTest
	public void launchBrowser() {
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
	public void BeforeAdvancedSearchTest()
	{
		chrome.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[5]/a[1]")).click();
	}

	@DataProvider(name="valid")
	public static Object[][] AdvancedSearchDataProvidervalid()
	{
		return new Object[][] {{"computer graphics","donald hearn & pauline baker"},{"computer","donald hearn"},{"computer",""},{"","donald hearn & pauline baker"},{"",""}};
	}
	@DataProvider(name="invalid")
	public static Object[][] AdvancedSearchDataProviderInvalid()
	{
		return new Object[][] {{"ahmed","amr"},{"ahmed","donald hearn & pauline baker"},{"computer graphics","ahmed"}};
	}
	@Test(dataProvider="valid")
	public void AdvancedSearchTestValid(String title,String author) {
		WebDriverWait wait = new WebDriverWait(chrome, 2);
		wait.until(ExpectedConditions.visibilityOf(chrome.findElement(By.name("title"))));
		chrome.findElement(By.name("title")).clear();
		chrome.findElement(By.name("title")).sendKeys(title);
		chrome.findElement(By.name("author")).clear();
		chrome.findElement(By.name("author")).sendKeys(author);
		chrome.findElement(By.xpath("/html[1]/body[1]/div[3]/div[2]/form[1]/div[3]/div[1]/button[1]")).click();
		List<WebElement> titles=chrome.findElements(By.xpath("/html[1]/body[1]/div[7]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr/td[2]"));
		List<WebElement> authors=chrome.findElements(By.xpath("/html[1]/body[1]/div[7]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr/td[4]"));
		for(int i=0;i<titles.size();i++)
		{
			assertTrue(titles.get(i).getText().toLowerCase().contains(title) && authors.get(i).getText().toLowerCase().contains(author));
		}
	}
	
	@Test(dataProvider="invalid")
	public void AdvancedSearchTestInvalid(String title,String author) {
		WebDriverWait wait = new WebDriverWait(chrome, 2);
		wait.until(ExpectedConditions.visibilityOf(chrome.findElement(By.name("title"))));
		chrome.findElement(By.name("title")).clear();
		chrome.findElement(By.name("title")).sendKeys(title);
		chrome.findElement(By.name("author")).clear();
		chrome.findElement(By.name("author")).sendKeys(author);
		chrome.findElement(By.xpath("/html[1]/body[1]/div[3]/div[2]/form[1]/div[3]/div[1]/button[1]")).click();
		String xString=chrome.findElement(By.xpath("/html[1]/body[1]/div[7]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]")).getText();
		assertEquals(xString, "No data available in table");
	}
	@AfterTest
	public void AfterAdvancedSearchTest() {
		chrome.close();
	}
}
