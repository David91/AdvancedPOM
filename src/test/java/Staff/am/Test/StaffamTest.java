package Staff.am.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import staff.am.HomePage;
import staff.am.RandomJobDetailsPage;
import staff.am.SearchJobsPage;


public class StaffamTest {
    private WebDriver driver;
    private HomePage homePage;
    private SearchJobsPage searchJobsPage;
    private RandomJobDetailsPage randomJobDetailsPage;
    private String jobCategoryOption = "Software development";
    private int currentPageNum = 100;
    private String languageOption = "РУС";

    @BeforeSuite
    public void chromeSetup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeClass
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver).open();
        homePage.waitForPageLoad();
        homePage.searchJobCategory(jobCategoryOption);
    }

    @Test
    public void testStaffAM() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();

        searchJobsPage = new SearchJobsPage(driver).open(jobCategoryOption);
        searchJobsPage.waitPageLoad();

        softAssert.assertNotNull(searchJobsPage.checkedBox(jobCategoryOption), "This selected job catigory does not have checked box");

        softAssert.assertEquals(searchJobsPage.checkIfCheckBoxByJobCategory(jobCategoryOption), searchJobsPage.checkIfCheckBoxByValue(), "The checked checkbox does not matched to job category: " + jobCategoryOption);

        if (searchJobsPage.findExpectedJobsCount(jobCategoryOption) < currentPageNum) {
            softAssert.assertEquals(searchJobsPage.findActualJobsCount(), searchJobsPage.findExpectedJobsCount(jobCategoryOption), "The actual number of products: " + searchJobsPage.findActualJobsCount() + " does not match expected number: " + searchJobsPage.findExpectedJobsCount(jobCategoryOption));
        } else {
            softAssert.assertEquals(searchJobsPage.findActualJobsCount(), currentPageNum, "The actual number of products: " + searchJobsPage.findActualJobsCount() + " does not match expected total number that should be on the current page: " + currentPageNum);
        }

        String jobNameTitle = searchJobsPage.checkJobTitle();
        randomJobDetailsPage = new RandomJobDetailsPage(driver);
        randomJobDetailsPage.waitPageLoad();
        String jobNameDetails = randomJobDetailsPage.checkJobTitleDetails();

        softAssert.assertEquals(jobNameDetails, jobNameDetails, "The title of random chosen job: " + jobNameTitle + " does not match the job title on job Details page: " + jobNameDetails);

        String jobNameDetailsAfterLanguageChangeRussian = randomJobDetailsPage.checkJobTitleAfterLanguageChangeRussian(languageOption);
        softAssert.assertEquals(jobNameDetails, jobNameDetailsAfterLanguageChangeRussian, "The Job Title has been changed after translation to Russian.");

        softAssert.assertAll();
    }

    @AfterClass
    public void quitTest() {

        driver.quit();
    }
}
