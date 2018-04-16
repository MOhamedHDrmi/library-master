package main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver","E:\\Programs\\chromedriver.exe");
		 WebDriver chrome = new ChromeDriver();
		 Signup signup = new Signup("http://localhost:8080/library-master/student_form.php");
		 signup.testCases(chrome);
		 Login login = new Login("http://localhost:8080/library-master/member/");
		 login.testCases(chrome);
	}
}
