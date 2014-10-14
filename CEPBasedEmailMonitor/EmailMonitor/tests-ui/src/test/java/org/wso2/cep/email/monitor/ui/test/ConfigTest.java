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

public class ConfigTest {

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

    @Test(groups = "cep.email.monitor", description = "testing ability ro move to config tab", dependsOnMethods = { "TestCorrectCredentials" })
    public void TestMovingToConfigTab(){
        driver.findElement(By.xpath("//li/a[@href='#configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Configure']")));

        assertTrue(driver.getPageSource().contains("<div id=\"configure\" class=\"tab-pane fade active in\">"));
    }

    //test cases for each text box empty

    @Test(groups = "cep.email.monitor", description = "testing empty email address field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyEmailAddress() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='emailAddress']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty email password field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyEmailPassword() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='emailPassword']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty esb username field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyESBUserName() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='esbUserName']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty esb password field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyESBPassword() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='esbPassword']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty esb IP field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyESBIP() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='esbIP']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty esb port field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyESBPort() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='esbPort']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty cep username field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyCEPUserName() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='cepUserName']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty cep password field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyCEPPassword() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='cepPassword']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty cep IP field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyCEPIP() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='cepIP']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    @Test(groups = "cep.email.monitor", description = "testing empty cep port field validation", dependsOnMethods = { "TestMovingToConfigTab" })
    public void TestEmptyCEPPort() {
        populateConfigForm();
        WebElement toClear = driver.findElement(By.xpath("//input[@name='cepPort']"));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("Please check values provided for input parameters!"));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }

    //test cases for incorrect input in each text box



    //test cases for correct input in all text boxes

    @Test(groups = "cep.email.monitor", description = "testing empty cep port field validation", dependsOnMethods = { "TestEmptyEmailAddress", "TestEmptyEmailPassword",
            "TestEmptyESBUserName", "TestEmptyESBPassword", "TestEmptyESBIP", "TestEmptyESBPort", "TestEmptyCEPUserName", "TestEmptyCEPPassword", "TestEmptyCEPIP", "TestEmptyCEPPort"})
    public void TestCorrectConfigStoring() {
        populateConfigForm();

        driver.findElement(By.xpath("//input[@value='Configure']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bootbox-body']")));

        assertTrue(driver.findElement(By.xpath("//div[@class='bootbox-body']")).getText().equals("CEP and ESB configured successfully."));
        driver.findElement(By.xpath("//button[@data-bb-handler='ok']")).click();
        clearConfigForm();
    }



    @Test(groups = "cep.email.monitor", description = "testing sign out functionality", dependsOnMethods = { "TestCorrectConfigStoring" })
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

    private void populateConfigForm(){
        driver.findElement(By.xpath("//input[@name='emailAddress']")).sendKeys("synapse.demo.1@gmail.com");
        driver.findElement(By.xpath("//input[@name='emailPassword']")).sendKeys("mailpassword1");
        driver.findElement(By.xpath("//input[@name='esbUserName']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='esbPassword']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='esbIP']")).sendKeys("127.0.0.1");
        driver.findElement(By.xpath("//input[@name='esbPort']")).sendKeys("9444");
        driver.findElement(By.xpath("//input[@name='cepUserName']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='cepPassword']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='cepIP']")).sendKeys("127.0.0.1");
        driver.findElement(By.xpath("//input[@name='cepPort']")).sendKeys("7711");
    }

    private void clearConfigForm(){
        String[] textBoxes = {"emailAddress", "emailPassword", "esbUserName", "esbPassword", "esbIP", "esbPort", "cepUserName", "cepPassword", "cepIP", "cepPort"};
        WebElement toClear;
        for(String name : textBoxes){
            toClear = driver.findElement(By.xpath("//input[@name='" + name + "']"));
            toClear.sendKeys(Keys.CONTROL + "a");
            toClear.sendKeys(Keys.DELETE);
        }
    }
}
