package main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Signup implements IFunctionsTest{
	private String path;

	public Signup() {
	}

	public Signup(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void testCases(WebDriver chrome) {
		chrome.get(path);
		chrome.manage().window().maximize();
		chrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		chrome.findElement(By.name("firstname")).clear();
		chrome.findElement(By.name("firstname")).sendKeys("Mohamed");
		chrome.findElement(By.name("lastname")).clear();
		chrome.findElement(By.name("lastname")).sendKeys("Hdrmi");
		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys("Hdrmi");
		Select gender = new Select(chrome.findElement(By.name("gender")));
		gender.selectByVisibleText("Male");
		chrome.findElement(By.name("address")).clear();
		chrome.findElement(By.name("address")).sendKeys("Any Address");
		chrome.findElement(By.name("roll")).clear();
		chrome.findElement(By.name("roll")).sendKeys("2184");
		chrome.findElement(By.name("contact")).clear();
		chrome.findElement(By.name("contact")).sendKeys("Any Content");
		Select type = new Select(chrome.findElement(By.name("type")));
		type.selectByVisibleText("Student");
		Select year_level = new Select(chrome.findElement(By.name("year_level")));
		year_level.selectByVisibleText("Second Year");
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys("123456789");
		chrome.findElement(By.name("submit")).click();
	}
}
