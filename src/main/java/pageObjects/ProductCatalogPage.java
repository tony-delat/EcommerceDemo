package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogPage {

    WebDriver driver;

    public ProductCatalogPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Web Elements declaration
    @FindBy(css=".mb-3")
    List<WebElement> products;




}
