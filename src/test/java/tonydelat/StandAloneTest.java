package tonydelat;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.LandingPage;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {

        //product name
        String productName = "ADIDAS ORIGINAL";

        //initialize browser
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://rahulshettyacademy.com/client/");

        //instanciating LandingPage class
        LandingPage landingPage = new LandingPage(driver);

        //login
        driver.findElement(By.id("userEmail")).sendKeys("tonycol1984@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Pleyades$48");
        driver.findElement(By.id("login")).click();

        //wait for products to load
        //explicit wait, so confirmation message (added to cart) is shown
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(8));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        //product list in shop
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        //this will allow to iterate through each product from the list
        WebElement prod = products.stream().filter(product->
        product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);

        //click on Add To cart button from selected product
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        //explicit wait, so confirmation message (added to cart) is shown
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        //now waiting for the pop-up to vanish so we can check the cart
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        //clicking on cart button on top
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        //fetching all items in cart
        List<WebElement> productsInCart = driver.findElements(By.xpath("//*[@class='cartSection']/h3"));

        //iterate through list of products in cart, then assert if product added is in cart
        Boolean match = productsInCart.stream().anyMatch(productInCart -> productInCart.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);

        //click on checkout button in cart
        driver.findElement(By.cssSelector(".totalRow button")).click();

        //using Actions class
        Actions a = new Actions(driver);

        //entering data on the "select country" dropdown
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "Austr").build().perform();

        //waiting until suggestions dropdwon on field appears
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        //selecting 2nd item
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

        //clicking on place order
        driver.findElement(By.cssSelector(".action__submit")).click();

        //validate thank you text after placing order is displayed
        String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();

        //verify confirmation message upon placing order
        Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        //closing browser
        driver.close();

    }

}
