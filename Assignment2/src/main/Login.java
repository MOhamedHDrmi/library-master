package main;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Login {
	private String path = "http://localhost:8080/library-master/member/";
	private String exepath = "E:\\Programs\\chromedriver.exe";
	private WebDriver chrome;

	public Login() {
	}

	@BeforeTest
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", exepath);
		chrome = new ChromeDriver();
	}

	@BeforeMethod
	public void openPage() {
		chrome.get(path);
	}
	
	@DataProvider(name = "valid")
	public Object[][] casesvalid() {
		return new Object[][] { { "keya07", "123456" } };
	}

	@DataProvider(name = "invalid")
	public Object[][] casesinvalid() {
		return new Object[][] { { "Mohamed", "password' OR 'x'='x" }, { "Mohamed", "123456" }, { "jahidd26", "000000" }, { "Mohamed", "000000" },
				{ "", "" } };
	}
	
	@Test(dataProvider = "valid")
	public void testCasesvlaid(String[] Case) {
		chrome.manage().window().maximize();
		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys(Case[0]);
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys(Case[1]);
		chrome.findElement(By.name("submit")).click();
		assertTrue(chrome.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]")).getText().equals("Welcome: User"));
	}
	
	@Test(dataProvider = "invalid")
	public void testCasesinvalid(String[] Case) {
		chrome.manage().window().maximize();
		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys(Case[0]);
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys(Case[1]);
		chrome.findElement(By.name("submit")).click();
		assertTrue(chrome.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/p[1]/strong[1]")).getText().equals("Please Put Your Username & Password"));
	}
	
	@AfterTest
	public void terminateBrowser() {
		chrome.close();
	}

}
