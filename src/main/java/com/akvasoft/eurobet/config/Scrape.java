package com.akvasoft.eurobet.config;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

public class Scrape {

    public FirefoxDriver getDriver() {
        System.setProperty("webdriver.gecko.driver", "/var/lib/tomcat8/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(false);

        FirefoxDriver driver = new FirefoxDriver(options);
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

        return driver;
    }


    public void scrapeOld() throws InterruptedException {
        FirefoxDriver driver = getDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.get("https://www.eurobet.it/it/scommesse/?fbclid=IwAR27GYB04rdte-s9-WdpYjLAfg19oul1wlMnjWaB15ZLU3rReTXOl_AL_sA#!/");

        WebElement basket = driver.findElementByXPath("/html/body/div[5]/div[1]/div/div/div/div[2]/div/div/div/div/div/ul[2]/li[3]/a/h2");
        jse.executeScript("arguments[0].scrollIntoView(true);", basket);
        jse.executeScript("arguments[0].click();", basket);

        Thread.sleep(2000);

        WebElement europa = driver.findElementByXPath("/html/body/div[5]/div[1]/div/div/div/div[2]/div/div/div/div/div/ul[2]/li[3]").findElement(By.className("open")).findElement(By.tagName("ul")).findElements(By.xpath("./*")).get(1);
        for (WebElement ignored : europa.findElements(By.xpath("./*"))) {
            if (ignored.findElement(By.tagName("a")).findElement(By.tagName("h4")).getAttribute("innerText").equalsIgnoreCase("Europa")) {
                jse.executeScript("arguments[0].scrollIntoView(true);", ignored.findElement(By.tagName("a")));
                jse.executeScript("arguments[0].click();", ignored.findElement(By.tagName("a")));
                Thread.sleep(500);

                for (WebElement element : ignored.findElement(By.className("sidebar-league")).findElements(By.xpath("./*"))) {
                    jse.executeScript("arguments[0].scrollIntoView(true);", element.findElement(By.tagName("a")));
                    jse.executeScript("arguments[0].click();", element.findElement(By.tagName("a")));
                    Thread.sleep(1000);
                    scrapeMainTable(driver);
                    break;
                }

                break;
            }
        }


    }

    private void scrapeMainTable(FirefoxDriver driver) throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement centerDiv = driver.findElementByClassName("main-content-wrapper");
        WebElement tables = centerDiv.findElement(By.className("baseAnimation"));
        for (WebElement table : tables.findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*"))) {
            System.out.println(table.findElement(By.className("box-title")).getAttribute("innerText"));
            for (WebElement element : table.findElement(By.className("anti-row")).findElements(By.xpath("./*"))) {
                WebElement row = element.findElement(By.className("event-row")).findElement(By.className("event-wrapper-info")).findElement(By.className("event-players"));
                jse.executeScript("arguments[0].scrollIntoView(true);", row.findElement(By.tagName("span")).findElement(By.tagName("a")));
                jse.executeScript("arguments[0].click();", row.findElement(By.tagName("span")).findElement(By.tagName("a")));
                Thread.sleep(1000);
                scrapeInnerTables(driver);
                break;
            }

            break;
        }

    }

    private void scrapeInnerTables(FirefoxDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement type = driver.findElementByXPath("/html/body/div[5]/div[2]/div/div")
                .findElement(By.tagName("div")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.className("filtri-sport"));
        for (WebElement ul : type.findElement(By.tagName("ul")).findElements(By.xpath("./*"))) {
            if (ul.getAttribute("innerText").equalsIgnoreCase("TUTTE")) {
                jse.executeScript("arguments[0].scrollIntoView(true);", ul);
                jse.executeScript("arguments[0].click();", ul);
                break;
            }
        }

    }
}
