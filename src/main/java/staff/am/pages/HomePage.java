package staff.am.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private String url = "/";

    @FindBy(xpath = "//select[@id='jobsfilter-category']")
    private WebElement jobCategoryInput;

    @FindBy(xpath = "//button[@data-url='/en/site/search']")
    private WebElement search;

    private WebElement getJobCategoryOption(String categoryOption) {
        String actualOption = String.format("//select[@id='jobsfilter-category']//option[@value='%s']", categoryOption);
        return driver.findElement(By.xpath(actualOption));
    }

    private By pageLoad = By.cssSelector(".staff_body");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void searchJobCategory(String category) {
        String jobCategorySearch = getJobCategoryOption(category).getText();
        jobCategoryInput.sendKeys(jobCategorySearch);
        jobCategoryInput.sendKeys(jobCategorySearch);
        search.click();
    }

    public HomePage open() {
        driver.get(BASE_URL + url);
        return this;
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageLoad));
    }
}