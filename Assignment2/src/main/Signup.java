package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Signup{
	private String path = "http://localhost:8080/library-master/student_form.php";
	private String exepath = "E:\\Programs\\chromedriver.exe";
	private WebDriver chrome;

	public Signup() {
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
	
	@DataProvider(name="signup")
	public Object[][] cases(){
		return new Object[][] {{"Mohamed","","Hdrmi","Male","Giza","01234848465","Contact","Student","First Year","123456789"},
			{"Mohamed","Alhdrmi","Hdrmi","Male","Giza","01234848465","Contact","Student","First Year","123456789"},
			{"Mohamed","Alhdrmi","Hdrmi","Male","Giza","01234848465","Contact"," ","First Year","123456789"},
			{"Mohamed","Alhdrmi","Hdrmi","Male","Giza","01234848465","Contact","Student","First Year",""},
			{"Mohamed","Alhdrmi","Hdrmi","Male","Giza","01234848465","Contact","Student","First Year","123456789"}};
	}

	@Test(dataProvider="signup")
	public void testCases(String[] Case) {
		chrome.manage().window().maximize();
		chrome.findElement(By.name("firstname")).clear();
		chrome.findElement(By.name("firstname")).sendKeys(Case[0]);
		chrome.findElement(By.name("lastname")).clear();
		chrome.findElement(By.name("lastname")).sendKeys(Case[1]);
		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys(Case[2]);
		Select gender = new Select(chrome.findElement(By.name("gender")));
		gender.selectByVisibleText(Case[3]);
		chrome.findElement(By.name("address")).clear();
		chrome.findElement(By.name("address")).sendKeys(Case[4]);
		chrome.findElement(By.name("roll")).clear();
		chrome.findElement(By.name("roll")).sendKeys(Case[5]);
		chrome.findElement(By.name("contact")).clear();
		chrome.findElement(By.name("contact")).sendKeys(Case[6]);
		Select type = new Select(chrome.findElement(By.name("type")));
		type.selectByVisibleText(Case[7]);
		Select year_level = new Select(chrome.findElement(By.name("year_level")));
		year_level.selectByVisibleText(Case[8]);
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys(Case[9]);
		chrome.findElement(By.name("submit")).click();
		chrome.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/center[1]/h2[1]")).getText().equals("You are Successfully Registered. You can now login your account.");
	}
	
	@AfterTest
	public void terminateBrowser() {
		chrome.close();
	}
}
