package org.wso2.cep.email.monitor.ui.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.core.BrowserManager;

import static org.testng.AssertJUnit.assertTrue;

public class QueryTest {

    private WebDriver driver;

    @DataProvider(name = "correctCredentials")
    public Object[][] createData2() {
        return new Object[][] {
                { "admin", "admin"}
        };
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = BrowserManager.getWebDriver();
        driver.get("https://localhost:9443/emailMonitor-1.0.0/");
    }

    @Test(groups = "cep.email.monitor", description = "testing sign in functionality with correct credentials", dataProvider = "correctCredentials")
    public void TestCorrectCredentials(String userName, String password){
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        assertTrue(driver.getCurrentUrl().equals("https://localhost:9443/emailMonitor-1.0.0/"));
    }

    //test with empty input
    @Test(groups = "cep.email.monitor", description = "testing empty email address field validation", dependsOnMethods = { "TestCorrectCredentials" })
    public void TestEmptyQuery() {
        clearQueryForm();
        driver.findElement(By.xpath("//input[@value='Save Query']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check query for correct sysntax and check whether email monitor is configured with correct details!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
    }

    //test with valid input and delete

    @Test(groups = "cep.email.monitor", description = "testing empty email password field validation", dependsOnMethods = { "TestCorrectCredentials" })
    public void TestAddingValidQuery() {
        driver.findElement(By.xpath("//textarea[@name='cepQueries']")).sendKeys("frequency > 5/h add label IMPORTANT");
        driver.findElement(By.xpath("//input[@value='Save Query']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("CEP query added successfully."));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        assertTrue(driver.findElement(By.xpath("//table[@id='storedQueries']/tbody/tr/td/div/p")).getText().equals("Query : frequency > 5/h add label IMPORTANT"));
        clearQueryForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty email password field validation", dependsOnMethods = { "TestAddingValidQuery" })
    public void TestDeletingAddedQuery() {
        driver.findElement(By.xpath("//table[@id='storedQueries']/tbody/tr/td/div/p/button")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().contains("Are you sure you want to delete Query :"));
        driver.findElement(By.xpath("//button[@data-bb-handler='confirm']")).click();

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().contains("Email query removed successfully."));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='storedQueries']/tbody/tr/td/p")));

        assertTrue(driver.findElement(By.xpath("//table[@id='storedQueries']/tbody/tr/td/p")).getText().equals("There are no queries deployed to query the email account"));
        clearQueryForm();
    }

    //test with invalid input
    @Test(groups = "cep.email.monitor", description = "testing empty email password field validation", dependsOnMethods = { "TestCorrectCredentials" })
    public void TestAddingInvalidQuery() {
        driver.findElement(By.xpath("//textarea[@name='cepQueries']")).sendKeys("from : (abc@gmail.com andOR def@wso2.com)\n" +
                "add label IMPORTANT");
        driver.findElement(By.xpath("//input[@value='Save Query']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check query for correct sysntax and check whether email monitor is configured with correct details!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearQueryForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing sign out functionality", dependsOnMethods = { "TestAddingInvalidQuery", "TestDeletingAddedQuery", "TestEmptyQuery" })
    public void TestSignOut(){
        driver.findElement(By.xpath("//li/a[@data-toggle='dropdown']")).click();
        driver.findElement(By.xpath("//li/a[@href='./logout']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        assertTrue(driver.getCurrentUrl().contains(":9443/sso/login?SAMLRequest"));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

    private void clearQueryForm(){
        WebElement toClear;
        toClear = driver.findElement(By.xpath("//textarea[@name='cepQueries']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
    }
}
