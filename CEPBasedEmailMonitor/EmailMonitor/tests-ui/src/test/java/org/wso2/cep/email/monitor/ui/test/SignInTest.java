package org.wso2.cep.email.monitor.ui.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.core.BrowserManager;

import static org.testng.AssertJUnit.assertTrue;

public class SignInTest {

    private WebDriver driver;

    @DataProvider(name = "wrongCredentials")
     public Object[][] createData1() {
        return new Object[][] {
                { "Cedric Anne", "wrong password"}
        };
    }

    @DataProvider(name = "correctCredentials")
     public Object[][] createData2() {
        return new Object[][] {
                { "admin", "admin"}
        };
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = BrowserManager.getWebDriver();
    }

    @Test(groups = "cep.email.monitor", description = "testing redirection to log in UI")
    public void TestRedirection(){
        driver.get("https://localhost:9443/emailMonitor-1.0.0/");
        assertTrue(driver.getCurrentUrl().contains(":9443/sso/login?SAMLRequest"));
    }

    @Test(groups = "cep.email.monitor", description = "testing sign in functionality with wrong credentials", dataProvider = "wrongCredentials", dependsOnMethods = { "TestRedirection" })
    public void TestWrongCredentials(String userName, String password){
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        assertTrue(driver.findElement(By.xpath("//div[@class='alert alert-error']")).getText().equals("Username or password is invalid"));
    }

    @Test(groups = "cep.email.monitor", description = "testing sign in functionality with correct credentials", dataProvider = "correctCredentials", dependsOnMethods = { "TestWrongCredentials" })
    public void TestCorrectCredentials(String userName, String password){
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        assertTrue(driver.getCurrentUrl().equals("https://localhost:9443/emailMonitor-1.0.0/"));
    }

    @Test(groups = "cep.email.monitor", description = "testing sign out functionality", dependsOnMethods = { "TestCorrectCredentials" })
    public void TestSignOut(){
        driver.findElement(By.xpath("//li/a[@data-toggle='dropdown']")).click();
        driver.findElement(By.xpath("//li/a[@href='./logout']")).click();

        assertTrue(driver.getCurrentUrl().contains(":9443/sso/login?SAMLRequest"));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
