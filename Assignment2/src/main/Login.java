package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Login implements IFunctionsTest{
	private String path;

	public Login() {
	}

	public Login(String path) {
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
		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys("Hdrmi");
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys("123456789");
		chrome.findElement(By.name("submit")).click();		
	}

}
