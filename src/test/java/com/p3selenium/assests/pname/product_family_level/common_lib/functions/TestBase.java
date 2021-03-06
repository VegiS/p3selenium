package com.p3selenium.assests.pname.product_family_level.common_lib.functions;

/**
 * @author ABHISHEK
 *
 */
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.p3selenium.assests.pname.product_family_level.common_lib.data_source.LoadProperty;

public class TestBase extends Bean {
	private WebDriver driver = null;

	private DesiredCapabilities capability = null;
	String test_name = null;
	String username = System.getProperty("user.name");
	String project_path = System.getProperty("user.dir");

	// private String project_root = System.getProperty("user.dir");
	// private LoadProperty property = null;

	@Parameters({ "browser" })
	@BeforeClass
	public WebDriver init(String browser) throws MalformedURLException {
		String url = LoadProperty.getVar("url", "data");
		System.out.println("URL: '" + url + "'");
		test_name = this.getClass().getSimpleName();
		System.out.println("Starting Test: '" + test_name + "'");
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.driver",
					"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName(browser);
			capability.setPlatform(org.openqa.selenium.Platform.ANY);
			setDriver(new FirefoxDriver(capability));
		}

		else if (browser.equalsIgnoreCase("iexplore")) {
			System.out.println("iexplore");
			capability = DesiredCapabilities.internetExplorer();
			System.setProperty("webdriver.ie.driver", project_path
					+ "\\src\\test\\resources\\driver\\IEDriverServer.exe");
			capability.setBrowserName(browser);
			capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			setDriver(new InternetExplorerDriver(capability));
		}

		else if (browser.equalsIgnoreCase("chrome")) {
			capability = DesiredCapabilities.chrome();
			/*
			 * System.setProperty("webdriver.chrome.driver",
			 * "C:\\chromedriver2.8.exe");
			 */
			System.setProperty("webdriver.chrome.driver", project_path
					+ "\\src\\test\\resources\\driver\\chromedriver_v32.exe");

			capability.setBrowserName(browser);
			capability.setPlatform(org.openqa.selenium.Platform.ANY);
			setDriver(new ChromeDriver(capability));

		}

		getDriver().manage().window().maximize();
		return getDriver();
	}

	@AfterMethod
	@AfterClass
	public void tearDown() {
		System.out.println("Ending Test Name: '" + test_name + "'");
		System.out.println("Shuting down driver of Test Name: '" + test_name
				+ "'");
		driver.quit();
		System.out.println("Test: '" + test_name + "' :executed successfully");
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	@AfterSuite(alwaysRun = true)
	public void tearDownAfterSuit() {
		if (driver != null) {
			System.out
					.println("Driver of Test name: "
							+ test_name
							+ " is closing at the end of suit, means this script did not worked properly");
			driver.quit();
		}
	}

}