package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

    WebDriver driver;

    //class constructor
    public LandingPage(WebDriver driver){
        this.driver = driver;
        //need to add this line if using page Factory:
        PageFactory.initElements(driver, this);
    }

    //declaring elements
//    WebElement userEmail = driver.findElement(By.id("userEmail"));
//    WebElement userPassword = driver.findElement(By.id("userPassword"));
//    WebElement loginButton = driver.findElement(By.id("login"));

    //declaring elements in a nicer way - page factory
    @FindBy(id="userEmail")
    WebElement userEmail;

    @FindBy(id="userPassword")
    WebElement userPassword;

    @FindBy(id="login")
    WebElement loginButton;

    public void loginApplication(String email, String password){
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        loginButton.click();
    }

    public void goTo(){
        driver.get("https://rahulshettyacademy.com/client/");
    }

}
