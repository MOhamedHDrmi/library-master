import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LoginValid {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E:\\Programs\\chromedriver.exe");
        WebDriver chrome = new ChromeDriver();
        chrome.get("http://localhost/library-master/member/");
        chrome.manage().window().maximize();
        chrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        chrome.findElement(By.name("username")).clear();
        chrome.findElement(By.name("username")).sendKeys("mokhles08");
        chrome.findElement(By.name("password")).clear();
        chrome.findElement(By.name("password")).sendKeys("12345678");
        chrome.findElement(By.name("submit")).click();
    }
}
