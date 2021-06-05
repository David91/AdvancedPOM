package staff.am;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;


public class SearchJobsPage extends BasePage {
    private WebDriverWait wait;
    private By productNumber = By.cssSelector("[data-key]");
    private By checkBoxCheck = By.xpath("//*[@id='jobsfilter-category']//input[@checked='checked']");
    private String jobCategoryLocator = "//*[@id='jobsfilter-category']//*[text()='%s']";
    private String jobCategoryInput = "//*[@id='jobsfilter-category']//*[text()='%s']/preceding::input[1]";
    private String jobCategorySpan = "//*[@id='jobsfilter-category']//*[text()='%s']/following::span";
    String urlTemplate1 = "/en/jobs/categories/index?JobsFilter%5Bkey_word%5D=&JobsFilter%5Bcategory%5D%5B0%5D=";
    String urlTemplate2 = "%s#search_list_block";

    public SearchJobsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 20);
    }

    //Open method which does not give the direct access to the page = DRAFT
    public SearchJobsPage open(String jobCategoryName) {
        By value = By.xpath(String.format(jobCategoryLocator, jobCategoryName));
        String valueElem = driver.findElement(value).getAttribute("value");
        String url = String.format(urlTemplate2, valueElem);
        driver.get(BASE_URL + urlTemplate1 + urlTemplate2);
        return this;
    }

    public String checkedBox(String jobCategoryName) {
        By checkedBox = By.xpath(String.format(jobCategoryInput, jobCategoryName));
        WebElement checkedBoxElement = driver.findElement(checkedBox);
        try {
            return checkedBoxElement.getAttribute("checked");
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public int checkIfCheckBoxByJobCategory(String jobCategoryName) {
        By checkedBox = By.xpath(String.format(jobCategoryInput, jobCategoryName));
        WebElement checkedBoxElement = driver.findElement(checkedBox);
        return Integer.parseInt(checkedBoxElement.getAttribute("value"));
    }

    public int checkIfCheckBoxByValue() {
        WebElement findCheckBoxElement = driver.findElement(checkBoxCheck);
        return Integer.parseInt(findCheckBoxElement.getAttribute("value"));
    }

    public int findActualJobsCount() {
        List<WebElement> count = driver.findElements(productNumber);
        return count.size();
    }

    public int findExpectedJobsCount(String jobCategoryName) {
        By jobNumber = By.xpath(String.format(jobCategorySpan, jobCategoryName));
        WebElement jobsExpectedElement = driver.findElement(jobNumber);
        String jobsExpectedNum = jobsExpectedElement.getText().replace("(", "").replace(")", "");
        return Integer.parseInt(jobsExpectedNum);
    }

    public String checkJobTitle() {
        List<WebElement> count = driver.findElements(productNumber);
        WebElement jobElement = getRandomElement(count);
        String jobRandom = jobElement.getText();
        String jobRandomTitle = jobRandom.substring(0, jobRandom.indexOf("\n"));
        jobElement.click();
        return jobRandomTitle;
    }

    private WebElement getRandomElement(List<WebElement> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public void waitPageLoad() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxCheck));
    }
}
