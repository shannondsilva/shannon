package extentReportsP;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;

import javax.swing.JOptionPane;

import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class extentedtest {
	
	ExtentReports extent;
	ExtentTest extenttest;
	WebDriver driver;
	
	@BeforeMethod
	public void initialiseFriver() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Shannon\\Downloads\\sJar\\chromedriver_win32_77\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://stackoverflow.com/questions");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

	}
	
	@Test
	public void test1() throws InterruptedException {
		JOptionPane.showMessageDialog(null, "test1");;
		driver.findElement(By.id("nav-tags")).click();
		String Title1 = driver.getTitle();
		Assert.assertTrue(Title1.equals("Tags - Stack Overflow"));
		Thread.sleep(2000);
	}
	
	@Test
	public void test2() throws InterruptedException {
		JOptionPane.showMessageDialog(null, "test2");;
		driver.findElement(By.id("nav-users")).click();
		String Title1 = driver.getTitle();
		Assert.assertTrue(Title1.equals("Users - Stack Overflow123"));
		Thread.sleep(2000);
	}
	
	@Test(enabled = false)
	public void test3() {
		//Dummy class for skipped scenerio.
	}
	@BeforeClass
	public void ExtenrtSetUp() {
		
		extent = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html",true);
		extenttest = extent.startTest("extentedtest");
		extent.addSystemInfo("Ran From", "Shannon Dsilva");
		extent.addSystemInfo("System name", "Shannon - L450");
	}
	
	public static String getScreenShot(WebDriver driver, String screenshotname) throws IOException {
		
		TakesScreenshot sc = (TakesScreenshot)driver;
		File f = sc.getScreenshotAs(OutputType.FILE);
		org.openqa.selenium.io.FileHandler.copy(f, new File("C:\\Users\\Shannon\\ExtentReportsShannon\\FailureScreenShots\\"+screenshotname));	
		return "C:\\Users\\Shannon\\ExtentReportsShannon\\FailureScreenShots"+screenshotname;
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if(result.getStatus() == ITestResult.FAILURE) {
			extenttest.log(LogStatus.FAIL, "Test has failed for "+result.getName());
			String imagePath = extentedtest.getScreenShot(driver, "shannondsilvascreenshot.png");
			extenttest.log(LogStatus.FAIL, extenttest.addScreenCapture(imagePath));
		}
		if(result.getStatus() == ITestResult.SKIP) {
			extenttest.log(LogStatus.SKIP, "Test has skipped for "+result.getName());
		}
		if(result.getStatus() == ITestResult.SUCCESS) {
			extenttest.log(LogStatus.PASS, "Test has passed for "+result.getName());
		}
		
		extent.endTest(extenttest);
		driver.close();
	}
}
