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

    private void scrapeInnerTables(FirefoxDriver driver) throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement type = driver.findElementByXPath("/html/body/div[5]/div[2]/div/div")
                .findElement(By.tagName("div")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.className("filtri-sport"));
        for (WebElement ul : type.findElement(By.tagName("ul")).findElements(By.xpath("./*"))) {
            if (ul.getAttribute("innerText").equalsIgnoreCase("TUTTE")) {
                jse.executeScript("arguments[0].scrollIntoView(true);", ul);
                jse.executeScript("arguments[0].click();", ul);
                Thread.sleep(1000);

                for (WebElement table : driver.findElementByXPath("/html/body/div[5]/div[2]/div/div/div/div/div").findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*"))) {


                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t match")) {
                        WebElement valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElement(By.tagName("div"));
                        System.out.println("================ t/t match");
                        for (WebElement row : valueSet.findElements(By.xpath("./*"))) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                        }


                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElement(By.tagName("div")).findElements(By.xpath("./*"));

                        System.out.println("================ t/t handicap");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 (5.5 pti)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElement(By.tagName("div")).findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 (5.5 pti)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 tempi regolam.")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElement(By.tagName("div")).findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 tempi regolam.");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("supplementari?")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElement(By.tagName("div")).findElements(By.xpath("./*"));

                        System.out.println("================ supplementari?");
                        for (WebElement row : valueSet) {
                            String sl = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String no = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(sl);
                            System.err.println(no);

                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("T/T + U/O ( incl. suppl.)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElement(By.tagName("div")).findElements(By.xpath("./*"));

                        System.out.println("================ T/T + U/O ( incl. suppl.)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String team_1_under = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String team_1_over = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            String team_2_under = row.findElements(By.xpath("./*")).get(3).findElement(By.tagName("a")).getAttribute("innerText");
                            String team_2_over = row.findElements(By.xpath("./*")).get(4).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(team_1_under);
                            System.err.println(team_1_over);
                            System.err.println(team_2_under);
                            System.err.println(team_2_over);


                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o (incl.suppl.)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o (incl.suppl.)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String under = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String over = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");

                            System.err.println(value_1);
                            System.err.println(under);
                            System.err.println(over);

                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("U/O totale punti")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ U/O totale punti");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String under = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String over = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            String ESATTAMENTE = row.findElements(By.xpath("./*")).get(3).findElement(By.tagName("a")).getAttribute("innerText");

                            System.err.println(value_1);
                            System.err.println(under);
                            System.err.println(over);
                            System.err.println(ESATTAMENTE);

                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("pari/dispari (incl.suppl.)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ pari/dispari (incl.suppl.)");
                        for (WebElement row : valueSet) {
                            String PARI = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String DISPARI = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(PARI);
                            System.err.println(DISPARI);

                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 1T")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ t/t handicap 1T");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);

                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 1T (senza margine)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 1T (senza margine)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);

                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o 1T")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o 1T");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String under = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String over = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(under);
                            System.err.println(over);

                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("pari/dispari 1T")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ pari/dispari 1T");
                        for (WebElement row : valueSet) {
                            String pari = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String dispari = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(pari);
                            System.err.println(dispari);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 2T")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ t/t handicap 2T");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 2T (senza margine)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 2T (senza margine)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o 2T")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o 2T");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String under = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String over = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(under);
                            System.err.println(over);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("pari/dispari 2T")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ pari/dispari 2T");
                        for (WebElement row : valueSet) {
                            String PARI = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String DISPARI = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(PARI);
                            System.err.println(DISPARI);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o 1° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o 1° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o 2° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o 2° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o 3° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o 3° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o 4° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o 4° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

//                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("quarto con punt. piu' alto")) {
//                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
//                                .findElement(By.tagName("div"))
//                                .findElements(By.xpath("./*"));
//
//                        System.out.println("================ quarto con punt. piu' alto");
//                        for (WebElement row : valueSet) {
//                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
//                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
//                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
//                            System.err.println(value_1);
//                            System.err.println(value_2);
//                            System.err.println(value_3);
//                        }
//
//                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 1° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ t/t handicap 1° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 1° quarto (senza margini)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 1° quarto (senza margini)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("pari/dispari 1° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ pari/dispari 1° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 2° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ t/t handicap 2° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 2° quarto (senza margini)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 2° quarto (senza margini) ");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("pari/dispari 2° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ pari/dispari 2° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                        }

                    }


                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 3° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ t/t handicap 3° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 3° quarto (senza margini)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 3° quarto (senza margini)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("pari/dispari 3° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ pari/dispari 3° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 4° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ t/t handicap 4° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1X2 4° quarto (senza margini)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1X2 4° quarto (senza margini)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("pari/dispari 4° quarto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ pari/dispari 4° quarto");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("U/O casa (incl.suppl.)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ U/O casa (incl.suppl.)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("U/O ospite (incl.suppl.)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ U/O ospite (incl.suppl.)");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }
                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1° tempo/finale")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1° tempo/finale");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);
                            System.err.println(value_3);
                        }

                    }
                }

                break;
            }
        }

    }
}
