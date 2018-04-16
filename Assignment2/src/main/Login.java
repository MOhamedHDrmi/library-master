package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Login implements IFunctionsTest{
	private String path;
	private String username;
	private String password;

	public Login() {
	}

	

	public Login(String path, String username, String password) {
		this.path = path;
		this.username = username;
		this.password = password;
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
		chrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		chrome.findElement(By.name("username")).clear();
		chrome.findElement(By.name("username")).sendKeys(username);
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys(password);
		chrome.findElement(By.name("submit")).click();	
	}

}
