package uiTestAutomation;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.util.Properties;

public class buyTShirts {

	public static WebDriver driver;

	public static void initWebDriver(String URL) throws InterruptedException {

		// Setting up Chrome driver path.
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		// Launching Chrome browser.
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(URL);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void main(String[] args) throws InterruptedException, java.io.IOException {

		initWebDriver("https://www.saucedemo.com/");

		String title = driver.getTitle();
		Assert.assertEquals(title, "Swag Labs");

		swagLabsLogin();

		List<WebElement> itemsList = driver.findElements(By.className("inventory_item_name"));

		// Adding items that are T-Shirts
		int counter = 1;
		for (WebElement items : itemsList) {
			if (items.getText().contains("T-Shirt")) {
				driver.findElement(By.xpath("(//div[@class='pricebar']/button)[" + counter + "]")).click();
			}
			counter++;
		}

		checkout();

		endSession();
	}

	public static void swagLabsLogin() {
		// This method handles Login
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.className("btn_action")).click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void checkout() {
		Properties config = new Properties();
		// This method handles checkout
		driver.findElement(By.className("shopping_cart_link")).click();

		List<WebElement> cartItems = driver.findElements(By.className("cart_item"));

		// Making sure if the cart has items
		if (cartItems.size() > 0) {
			driver.findElement(By.className("btn_action")).click();
		}

		// Checking for validation
		driver.findElement(By.className("btn_primary")).click();
		driver.findElement(By.className("error-button")).isDisplayed();

		driver.findElement(By.id("first-name")).sendKeys("Arya");
		driver.findElement(By.id("last-name")).sendKeys("Stark");
		driver.findElement(By.id("postal-code")).sendKeys("10018");
		driver.findElement(By.className("btn_primary")).click();

		WebElement checkoutOverviewPageSubHeader = driver.findElement(By.className("subheader"));
		Assert.assertEquals(checkoutOverviewPageSubHeader.getText(), "Checkout: Overview");

		driver.findElement(By.className("btn_action")).click();

		WebElement checkoutFinishPageSubHeader = driver.findElement(By.className("complete-header"));
		Assert.assertEquals(checkoutFinishPageSubHeader.getText(), "THANK YOU FOR YOUR ORDER");

	}

	public static void endSession() {
		driver.close();
		driver.quit();
	}

}
