package com.akvasoft.eurobet.config;

import com.akvasoft.eurobet.dto.Item;
import com.akvasoft.eurobet.modals.*;
import com.akvasoft.eurobet.modals.Match;
import com.akvasoft.eurobet.repo.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Scrape implements InitializingBean {

    static String status = "READY";
    static String pre_matchTime = "";
    static String live_matchTime = "";
    String liveScourTeam1 = "0";
    String liveScourTeam2 = "0";
    @Autowired
    private com.akvasoft.eurobet.repo.Match matchRepo;
    @Autowired
    private TTMatchRepo ttMatchRepo;
    @Autowired
    private TTHandicapRepo ttHandicapRepo;
    @Autowired
    private TPTI55Repo tpti55Repo;
    @Autowired
    private TempiRegolamRepo tempiRegolamRepo;
    @Autowired
    private SupplementariRepo supplementariRepo;
    @Autowired
    private TTUOInclSupplRepo ttuoInclSupplRepo;
    @Autowired
    private UOInclSupplRepo uoInclSupplRepo;
    @Autowired
    private UOTotalPuntiRepo uoTotalPuntiRepo;
    @Autowired
    private PariDispariInclSupplRepo pariDispariInclSupplRepo;
    @Autowired
    private TTHandicap1TRepo ttHandicap1TRepo;
    @Autowired
    private SenzaMargine_12_1TRepo senzaMargine_12_1TRepo;
    @Autowired
    private UO1TRepo uo1TRepo;
    @Autowired
    private PariDispari1TRepo pariDispari1TRepo;
    @Autowired
    private TTHandicap2TRepo ttHandicap2TRepo;
    @Autowired
    private SenzaMargine_12_2TRepo senzaMargine_12_2TRepo;
    @Autowired
    private UO2TRepo uo2TRepo;
    @Autowired
    private PariDispari2TRepo pariDispari2TRepo;
    @Autowired
    private UOQuarto_1Repo uoQuarto_1Repo;
    @Autowired
    private UOQuarto_2Repo uoQuarto_2Repo;
    @Autowired
    private UOQuarto_3Repo uoQuarto_3Repo;
    @Autowired
    private UOQuarto_4Repo uoQuarto_4Repo;
    @Autowired
    private TTHandicap_1QuartoRepo ttHandicap_1QuartoRepo;
    @Autowired
    private SenzaMargine_12_1QuartoRepo senzaMargine_12_1QuartoRepo;
    @Autowired
    private PariDispari_1QuartoRepo pariDispari_1QuartoRepo;
    @Autowired
    private TTHandicap_2QuartoRepo ttHandicap_2QuartoRepo;
    @Autowired
    private SenzaMargine_12_2QuartoRepo senzaMargine_12_2QuartoRepo;
    @Autowired
    private PariDispari2QuartoRepo pariDispari2QuartoRepo;
    @Autowired
    private TTHandicap_3QuartoRepo ttHandicap_3QuartoRepo;
    @Autowired
    private SenzaMargine_12_3QuartoRepo senzaMargine_12_3QuartoRepo;
    @Autowired
    private PariDispari3QuartoRepo pariDispari3QuartoRepo;
    @Autowired
    private TTHandicap_4QuartoRepo ttHandicap_4QuartoRepo;
    @Autowired
    private SenzaMargine_12_4QuartoRepo senzaMargine_12_4QuartoRepo;
    @Autowired
    private PariDispari4QuartoRepo pariDispari4QuartoRepo;
    @Autowired
    private UOCaseInclSupplRepo uoCaseInclSupplRepo;
    @Autowired
    private UOOspiteInclSupplRepo uoOspiteInclSupplRepo;
    @Autowired
    private TempoFinale_1Repo tempoFinale_1Repo;
    @Autowired
    private QuartoConPuntPiuAltoRepo quartoConPuntPiuAltoRepo;
    @Autowired
    private PrimaSquadraSegnareRepo primaSquadraSegnareRepo;
    @Autowired
    private UltimaSquadraSegnareRepo ultimaSquadraSegnareRepo;
    @Autowired
    private ComboMatchUltimoPuntoRepo comboMatchUltimoPuntoRepo;
    @Autowired
    private ScrapeLinksRepo scrapeLinksRepo;
    @Autowired
    private ScoureRepo scoureRepo;
    @Autowired
    private ScrapeRepo scrapeRepo;
    @Autowired
    private ScrapeMatchRepo scrapeMatchRepo;


    @RequestMapping(value = {"/scrape/league"}, method = RequestMethod.POST)
    @ResponseBody
    private void scrape(@RequestBody List<Item> item) throws InterruptedException {
        scrapeList(item);
    }

    public FirefoxDriver getDriver() {
        System.setProperty("webdriver.gecko.driver", "/var/lib/tomcat8/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
//        FirefoxProfile p = new FirefoxProfile();
//        p.setPreference("javascript.enabled", false);
        options.setHeadless(true);
//        options.setProfile(p);


        FirefoxDriver driver = new FirefoxDriver(options);
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

        return driver;
    }

    public void scrapeList(List<Item> items) throws InterruptedException {
        scrapeLinksRepo.deleteAll();
        ScrapeLinks links = null;
        for (Item item : items) {
            links = new ScrapeLinks();
            links.setName(item.getLeague());
            links.setValue(item.getLink());
            scrapeLinksRepo.save(links);
        }

    }

    public void scrapeLive() {

        new Thread(() -> {
            List<ScrapeLinks> all = null;
            FirefoxDriver driver = getDriver();
            while (true) {
                all = scrapeLinksRepo.findAll();
                System.err.println("LIVE THREAD STARTED");
                SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                live_matchTime = time_formatter.format(System.currentTimeMillis());
                driver.navigate().refresh();
                try {

                    for (ScrapeLinks links : all) {
                        try {
                            this.goToLive(driver, links.getName());
                        } catch (WebDriverException r) {
                            try {
                                driver.close();
                            } catch (Exception e) {
                            }
                            Thread.sleep(10000);
                            driver = getDriver();
                        } catch (Exception r) {
                            r.printStackTrace();
                        }
                    }

                    Thread.sleep(200000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void scrapePreMatch() {
        new Thread(() -> {
            List<ScrapeLinks> all = null;
            FirefoxDriver driver = getDriver();
            while (true) {
                all = scrapeLinksRepo.findAll();
                System.err.println("PRE MATCH THREAD STARTED");
                driver.navigate().refresh();
                SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                pre_matchTime = time_formatter.format(System.currentTimeMillis());
                try {

                    for (ScrapeLinks links : all) {
                        try {
                            this.scrapeOld(links.getName(), links.getValue(), driver);
                        } catch (WebDriverException w) {
                            try {
                                driver.close();
                            } catch (Exception p) {
                            }
                            Thread.sleep(10000);
                            driver = getDriver();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Thread.sleep(300000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }


    public void scrapeOld(String league, String link, FirefoxDriver driver) throws Exception {
        try {
//            FirefoxDriver driver = getDriver();
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            System.err.println(league + "=====================================================================================");
            driver.get(link);
            Thread.sleep(2000);
            scrapeMainTable(driver, "PRE MATCH");
//            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToLive(FirefoxDriver driver, String league) throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.get("https://www.eurobet.it");
        Thread.sleep(5000);
        WebElement live = driver.findElementByXPath("/html/body/header/div/nav/ul/li[2]/a");
        jse.executeScript("arguments[0].click();", live);
        Thread.sleep(10000);
        boolean found = false;

        for (Match match : matchRepo.findAll()) {
            if (match.getStatus().equalsIgnoreCase("PRE MATCH ENDED AND LIVE") || match.getStatus().equalsIgnoreCase("LIVE")) {
                match.setStatus("LIVE ENDED");
                matchRepo.save(match);
            }
        }


        for (WebElement sport : driver.findElementByXPath("/html/body/div[4]/div[1]/div/div/div/div[2]/div/div/div/div/div/ul/div/ul").findElements(By.xpath("./*"))) {
            System.out.println(sport.findElement(By.tagName("a")).findElement(By.tagName("h2")).getAttribute("innerText") + "**");
            if (sport.findElement(By.tagName("a")).findElement(By.tagName("h2")).getAttribute("innerText").equalsIgnoreCase("Basket")) {
                jse.executeScript("arguments[0].click();", sport.findElement(By.tagName("a")));
                for (WebElement element : sport.findElements(By.xpath("./*")).get(1).findElements(By.xpath("./*"))) {
                    System.err.println(element.findElement(By.tagName("li")).findElement(By.tagName("a")).findElement(By.tagName("h4")).getAttribute("innerText") + "=========================+++" + league);
                    if (element.findElement(By.tagName("li")).findElement(By.tagName("a")).findElement(By.tagName("h4")).getAttribute("innerText").equalsIgnoreCase(league)) {
                        jse.executeScript("arguments[0].click();", element.findElement(By.tagName("li")).findElement(By.tagName("a")));
                        Thread.sleep(5000);

                        for (WebElement li : element.findElement(By.tagName("ul")).findElements(By.xpath("./*"))) {
                            jse.executeScript("arguments[0].click();", li.findElement(By.className("match-row")).findElement(By.className("match")).findElement(By.tagName("a")));
                            Thread.sleep(1000);
                            try {
                                liveScourTeam1 = li.findElement(By.className("match-row")).findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                liveScourTeam2 = li.findElement(By.className("match-row")).findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                                scrapeInnerTables(driver, "LIVE");
                                liveScourTeam1 = "";
                                liveScourTeam2 = "";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + live);
//                            break;
                        }
                    }
                }
            }
        }
    }

    private void scrapeMainTable(FirefoxDriver driver, String type) throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement centerDiv = driver.findElementByClassName("main-content-wrapper");
        WebElement tables = null;
        try {
            tables = centerDiv.findElement(By.className("baseAnimation"));
        } catch (NoSuchElementException r) {
            return;
        }

        List<String> matchList = new ArrayList<>();

        for (WebElement table : tables.findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*"))) {
            for (WebElement element : table.findElement(By.className("anti-row")).findElements(By.xpath("./*"))) {
                Thread.sleep(2000);
                try {
                    WebElement row = element.findElement(By.className("event-row")).findElement(By.className("event-wrapper-info")).findElement(By.className("event-players"));
                    jse.executeScript("arguments[0].scrollIntoView(true);", row);
                    System.err.println("collected " + row.findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href"));
                    matchList.add(row.findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href"));
                } catch (NoSuchElementException e) {
                    System.err.println(element.getAttribute("innerHTML"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        status = "found " + matchList.size() + " matches,";
        for (String s : matchList) {
            try {
                driver.get(s);
                Thread.sleep(1000);
                scrapeInnerTables(driver, type);
            } catch (Exception r) {
                r.printStackTrace();
            }
        }
    }

    private boolean scrapeInnerTables(FirefoxDriver driver, String scrape_type) throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Match matchModal = null;
        TTMatch ttMatch = null;
        TTHandicap ttHandicap = null;
        TPTI55 tpti55 = null;
        TempiRegolam tempiRegolam = null;
        Supplementari supplementari = null;
        TTUOInclSuppl ttuoInclSuppl = null;
        UOInclSuppl uoInclSuppl = null;
        UOTotalPunti uoTotalPunti = null;
        PariDispariInchSuppl pariDispariInchSuppl = null;
        TTHandicap1T ttHandicap1T = null;
        SenzaMargine_12_1T senzaMargine_12_1T = null;
        UO1T uo1T = null;
        PariDispari1T pariDispari1T = null;
        TTHandicap2T ttHandicap2T = null;
        SenzaMargine_12_2T senzaMargine_12_2T = null;
        UO2T uo2T = null;
        PariDispari2T pariDispari2T = null;
        UOQuarto_1 uoQuarto_1 = null;
        UOQuarto_2 uoQuarto_2 = null;
        UOQuarto_3 uoQuarto_3 = null;
        UOQuarto_4 uoQuarto_4 = null;
        TTHandicap_1Quarto ttHandicap_1Quarto = null;
        SenzaMargine_12_1Quarto senzaMargine_12_1Quarto = null;
        PariDispari1Quarto pariDispari1Quarto = null;
        TTHandicap2Quarto ttHandicap2Quarto = null;
        SenzaMargine_12_2Quarto senzaMargine_12_2Quarto = null;
        PariDispari2Quarto pariDispari2Quarto = null;
        TTHandicap3Quarto ttHandicap3Quarto = null;
        SenzaMargine_12_3Quarto senzaMargine_12_3Quarto = null;
        PariDispari3Quarto pariDispari3Quarto = null;
        TTHandicap4Quarto ttHandicap4Quarto = null;
        SenzaMargine_12_4Quarto senzaMargine_12_4Quarto = null;
        PariDispari4Quarto pariDispari4Quarto = null;
        UOCaseInclSuppl uoCaseInclSuppl = null;
        UOOspiteInclSuppl uoOspiteInclSuppl = null;
        TempoFinale_1 tempoFinale_1 = null;
        QuartoConPuntPiuAlto quartoConPuntPiuAlto = null;
        PrimaSquadraSegnare primaSquadraSegnare = null;
        UltimaSquadraSegnare ultimaSquadraSegnare = null;
        ComboMatchUltimoPunto comboMatchUltimoPunto = null;


        ScrapeMatch scrapeMatch = new ScrapeMatch();
        com.akvasoft.eurobet.modals.Scrape scrape = new com.akvasoft.eurobet.modals.Scrape();
        scrape.setTime(dtf.format(now));
        scrape = scrapeRepo.save(scrape);

        Thread.sleep(2000);
        WebElement match = null;
        try {
            match = driver.findElementByXPath("/html/body/div[5]/div[2]/div/div/div/div/div/div[1]/div");
        } catch (NoSuchElementException e) {
            match = driver.findElementByXPath("/html/body/div[4]/div[2]/div/div[1]/div/div/div/div[1]/div");
        }
        int length = match.findElement(By.className("breadcrumbs")).getAttribute("innerText").split(">").length;
        String matchTitle = match.findElement(By.className("breadcrumbs")).getAttribute("innerText").split(">")[length - 1];
        String date = match.findElement(By.className("date-time")).getAttribute("innerText").split("Ore")[0].trim();
        String time = match.findElement(By.className("date-time")).getAttribute("innerText").split("Ore")[1].trim();
        String team1 = matchTitle.split("-")[0].trim();
        String team2 = matchTitle.split("-")[1].trim();

        matchModal = matchRepo.findTopByDateEqualsAndTimeEqualsAndOneEqualsAndTwoEquals(date, time, team1, team2);

        if (matchModal != null) {
            if (scrape_type.equalsIgnoreCase("LIVE") && matchModal.getStatus().equalsIgnoreCase("PRE MATCH")) {
                matchModal.setStatus("PRE MATCH ENDED AND LIVE");
                matchRepo.save(matchModal);

                scrapeMatch.setMatch(matchModal);
                scrapeMatch.setScrape(scrape);
                scrapeMatchRepo.save(scrapeMatch);

            } else if (scrape_type.equalsIgnoreCase("LIVE") && matchModal.getStatus().equalsIgnoreCase("LIVE ENDED")) {
                matchModal.setStatus("LIVE");
                matchRepo.save(matchModal);

                scrapeMatch.setMatch(matchModal);
                scrapeMatch.setScrape(scrape);
                scrapeMatchRepo.save(scrapeMatch);
            }

        } else {

            matchModal = new Match();
            matchModal.setDate(date.trim());
            matchModal.setTime(time.trim());
            matchModal.setOne(matchTitle.split("-")[0].trim());
            matchModal.setTwo(matchTitle.split("-")[1].trim());
            matchModal.setStatus(scrape_type);
            matchModal = matchRepo.save(matchModal);

            scrapeMatch.setMatch(matchModal);
            scrapeMatch.setScrape(scrape);
            scrapeMatchRepo.save(scrapeMatch);
        }

        status = "scraping Team 1 - " + team1 + " Team 2 - " + team2;
        status = "Date - " + date + " Time - " + time;
        WebElement type = null;
        try {
            type = driver.findElementByXPath("/html/body/div[5]/div[2]/div/div")
                    .findElement(By.tagName("div")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.className("filtri-sport"));
        } catch (NoSuchElementException r) {
            type = driver.findElementByXPath("/html/body/div[4]/div[2]/div/div[1]/div/div/div/div[2]/div/section/div/div[1]");
        }

        for (WebElement ul : type.findElement(By.tagName("ul")).findElements(By.xpath("./*"))) {
            if (ul.getAttribute("innerText").equalsIgnoreCase("TUTTE")) {
                jse.executeScript("arguments[0].scrollIntoView(true);", ul);
                jse.executeScript("arguments[0].click();", ul);
                Thread.sleep(5000);
                List<WebElement> elements = null;
                try {
                    elements = driver.findElementByXPath("/html/body/div[5]/div[2]/div/div/div/div/div").findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*"));
                } catch (NoSuchElementException v) {
                    elements = driver.findElementByXPath("/html/body/div[4]/div[2]/div/div[1]/div/div/div").findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*"));
                }

                if (scrape_type.equalsIgnoreCase("LIVE")) {
                    try {
                        Scoure teamScoures = new Scoure();
                        teamScoures.setMatch(matchModal);
                        teamScoures.setOne(liveScourTeam1);
                        teamScoures.setTwo(liveScourTeam2);
                        teamScoures.setScrape(scrape);
                        scoureRepo.save(teamScoures);
                    } catch (Exception t) {
                        t.printStackTrace();
                    }
                }

                for (WebElement table : elements) {

                    if (!table.getAttribute("class").equalsIgnoreCase("box-container")) {
                        continue;
                    }


                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t match")) {

                        WebElement valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElement(By.tagName("div"));
                        System.out.println("================ t/t match");
                        for (WebElement row : valueSet.findElements(By.xpath("./*"))) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);

                            ttMatch = new TTMatch();
                            ttMatch.setMatch(matchModal);
                            ttMatch.setOne(value_1);
                            ttMatch.setTwo(value_2);
                            ttMatch.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                ttMatch.setScrtime(live_matchTime);
                            } else {
                                ttMatch.setScrtime(pre_matchTime);
                            }

                            ttMatchRepo.save(ttMatch);
                        }
                        status = "t/t match completed.,";

                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap")) {
                        System.err.println("================ t/t handicap");
                        List<WebElement> valueSet = null;

                        if (scrape_type.equalsIgnoreCase("LIVE")) {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap = new TTHandicap();
                                ttHandicap.setMatch(matchModal);
                                ttHandicap.setName(value_1);
                                ttHandicap.setOne(value_2);
                                ttHandicap.setTwo(value_3);
                                ttHandicap.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap.setScrtime(pre_matchTime);
                                }

                                ttHandicapRepo.save(ttHandicap);
                            }
                        } else {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                    .findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap = new TTHandicap();
                                ttHandicap.setMatch(matchModal);
                                ttHandicap.setName(value_1);
                                ttHandicap.setOne(value_2);
                                ttHandicap.setTwo(value_3);
                                ttHandicap.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap.setScrtime(pre_matchTime);
                                }

                                ttHandicapRepo.save(ttHandicap);
                            }
                        }
                        status = "t/t handicap completed.,";
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

                            tpti55 = new TPTI55();
                            tpti55.setMatch(matchModal);
                            tpti55.setOne(value_1);
                            tpti55.setMultiple(value_2);
                            tpti55.setTwo(value_3);
                            tpti55.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                tpti55.setScrtime(live_matchTime);
                            } else {
                                tpti55.setScrtime(pre_matchTime);
                            }

                            tpti55Repo.save(tpti55);
                        }
                        status = "1X2 (5.5 pti) completed.,";
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

                            tempiRegolam = new TempiRegolam();
                            tempiRegolam.setMatch(matchModal);
                            tempiRegolam.setOne(value_1);
                            tempiRegolam.setMultiple(value_2);
                            tempiRegolam.setTwo(value_3);
                            tempiRegolam.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                tempiRegolam.setScrtime(live_matchTime);
                            } else {
                                tempiRegolam.setScrtime(pre_matchTime);
                            }

                            tempiRegolamRepo.save(tempiRegolam);
                        }
                        status = "1X2 tempi regolam completed.,";
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

                            supplementari = new Supplementari();
                            supplementari.setMatch(matchModal);
                            supplementari.setSl(sl);
                            supplementari.setNo(no);
                            supplementari.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                supplementari.setScrtime(live_matchTime);
                            } else {
                                supplementari.setScrtime(pre_matchTime);
                            }

                            supplementariRepo.save(supplementari);

                        }
                        status = "supplementari? completed.,";
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

                            ttuoInclSuppl = new TTUOInclSuppl();
                            ttuoInclSuppl.setMatch(matchModal);
                            ttuoInclSuppl.setName(value_1);
                            ttuoInclSuppl.setTeamOneUnder(team_1_under);
                            ttuoInclSuppl.setTeamOneOver(team_1_over);
                            ttuoInclSuppl.setTeamTwoUnder(team_2_under);
                            ttuoInclSuppl.setTeamTwoOver(team_2_over);
                            ttuoInclSuppl.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                ttuoInclSuppl.setScrtime(live_matchTime);
                            } else {
                                ttuoInclSuppl.setScrtime(pre_matchTime);
                            }

                            ttuoInclSupplRepo.save(ttuoInclSuppl);

                        }
                        status = "T/T + U/O ( incl. suppl.) completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("u/o (incl.suppl.)")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ u/o (incl.suppl.). \n");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String under = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            String over = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");

                            System.err.println(value_1);
                            System.err.println(under);
                            System.err.println(over);

                            uoInclSuppl = new UOInclSuppl();
                            uoInclSuppl.setMatch(matchModal);
                            uoInclSuppl.setName(value_1);
                            uoInclSuppl.setUnder(under);
                            uoInclSuppl.setOver(over);
                            uoInclSuppl.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoInclSuppl.setScrtime(live_matchTime);
                            } else {
                                uoInclSuppl.setScrtime(pre_matchTime);
                            }

                            uoInclSupplRepo.save(uoInclSuppl);

                        }
                        status = "u/o (incl.suppl.) completed.,";
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

                            uoTotalPunti = new UOTotalPunti();
                            uoTotalPunti.setMatch(matchModal);
                            uoTotalPunti.setName(value_1);
                            uoTotalPunti.setUnder(under);
                            uoTotalPunti.setOver(over);
                            uoTotalPunti.setEsattamente(ESATTAMENTE);
                            uoTotalPunti.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoTotalPunti.setScrtime(live_matchTime);
                            } else {
                                uoTotalPunti.setScrtime(pre_matchTime);
                            }

                            uoTotalPuntiRepo.save(uoTotalPunti);

                        }
                        status = "U/O totale punti completed.,";
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

                            pariDispariInchSuppl = new PariDispariInchSuppl();
                            pariDispariInchSuppl.setMatch(matchModal);
                            pariDispariInchSuppl.setPari(PARI);
                            pariDispariInchSuppl.setDispari(DISPARI);
                            pariDispariInchSuppl.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                pariDispariInchSuppl.setScrtime(live_matchTime);
                            } else {
                                pariDispariInchSuppl.setScrtime(pre_matchTime);
                            }

                            pariDispariInclSupplRepo.save(pariDispariInchSuppl);

                        }
                        status = "pari/dispari (incl.suppl.) completed.,";
                    }


                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 1T")) {
                        System.err.println("================ t/t 1T handicap");
                        List<WebElement> valueSet = null;
                        if (scrape_type.equalsIgnoreCase("LIVE")) {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap1T = new TTHandicap1T();
                                ttHandicap1T.setMatch(matchModal);
                                ttHandicap1T.setName(value_1);
                                ttHandicap1T.setOne(value_2);
                                ttHandicap1T.setTwo(value_2);
                                ttHandicap1T.setScrape(scrape);
                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap1T.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap1T.setScrtime(pre_matchTime);
                                }

                                ttHandicap1TRepo.save(ttHandicap1T);
                            }
                        } else {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                    .findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap1T = new TTHandicap1T();
                                ttHandicap1T.setMatch(matchModal);
                                ttHandicap1T.setName(value_1);
                                ttHandicap1T.setOne(value_2);
                                ttHandicap1T.setTwo(value_2);
                                ttHandicap1T.setScrape(scrape);
                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap1T.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap1T.setScrtime(pre_matchTime);
                                }

                                ttHandicap1TRepo.save(ttHandicap1T);
                            }
                        }

                        status = "t/t handicap 1T completed.,";
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

                            senzaMargine_12_1T = new SenzaMargine_12_1T();
                            senzaMargine_12_1T.setMatch(matchModal);
                            senzaMargine_12_1T.setOne(value_1);
                            senzaMargine_12_1T.setMulti(value_2);
                            senzaMargine_12_1T.setTwo(value_3);
                            senzaMargine_12_1T.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                senzaMargine_12_1T.setScrtime(live_matchTime);
                            } else {
                                senzaMargine_12_1T.setScrtime(pre_matchTime);
                            }

                            senzaMargine_12_1TRepo.save(senzaMargine_12_1T);

                        }
                        status = "1X2 1T (senza margine) completed.,";
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

                            uo1T = new UO1T();
                            uo1T.setMatch(matchModal);
                            uo1T.setName(value_1);
                            uo1T.setUnder(under);
                            uo1T.setOver(over);
                            uo1T.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uo1T.setScrtime(live_matchTime);
                            } else {
                                uo1T.setScrtime(pre_matchTime);
                            }

                            uo1TRepo.save(uo1T);

                        }
                        status = "u/o 1T completed.,";
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

                            pariDispari1T = new PariDispari1T();
                            pariDispari1T.setMatch(matchModal);
                            pariDispari1T.setPari(pari);
                            pariDispari1T.setDispari(dispari);
                            pariDispari1T.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                pariDispari1T.setScrtime(live_matchTime);
                            } else {
                                pariDispari1T.setScrtime(pre_matchTime);
                            }

                            pariDispari1TRepo.save(pariDispari1T);
                        }
                        status = "pari/dispari 1T completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 2T")) {
                        System.err.println("================ t/t handicap 2 T");
                        List<WebElement> valueSet = null;
                        if (scrape_type.equalsIgnoreCase("LIVE")) {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap2T = new TTHandicap2T();
                                ttHandicap2T.setMatch(matchModal);
                                ttHandicap2T.setName(value_1);
                                ttHandicap2T.setOne(value_2);
                                ttHandicap2T.setTwo(value_3);
                                ttHandicap2T.setScrape(scrape);
                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap2T.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap2T.setScrtime(pre_matchTime);
                                }

                                ttHandicap2TRepo.save(ttHandicap2T);
                            }
                        } else {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                    .findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap2T = new TTHandicap2T();
                                ttHandicap2T.setMatch(matchModal);
                                ttHandicap2T.setName(value_1);
                                ttHandicap2T.setOne(value_2);
                                ttHandicap2T.setTwo(value_3);
                                ttHandicap2T.setScrape(scrape);
                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap2T.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap2T.setScrtime(pre_matchTime);
                                }

                                ttHandicap2TRepo.save(ttHandicap2T);
                            }
                        }
                        status = "t/t handicap 2T completed.,";
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

                            senzaMargine_12_2T = new SenzaMargine_12_2T();
                            senzaMargine_12_2T.setMatch(matchModal);
                            senzaMargine_12_2T.setOne(value_1);
                            senzaMargine_12_2T.setMulti(value_2);
                            senzaMargine_12_2T.setTwo(value_3);
                            senzaMargine_12_2T.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                senzaMargine_12_2T.setScrtime(live_matchTime);
                            } else {
                                senzaMargine_12_2T.setScrtime(pre_matchTime);
                            }

                            senzaMargine_12_2TRepo.save(senzaMargine_12_2T);
                        }
                        status = "1X2 2T (senza margine) completed.,";
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

                            uo2T = new UO2T();
                            uo2T.setMatch(matchModal);
                            uo2T.setName(value_1);
                            uo2T.setOver(over);
                            uo2T.setUnder(under);
                            uo2T.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uo2T.setScrtime(live_matchTime);
                            } else {
                                uo2T.setScrtime(pre_matchTime);
                            }

                            uo2TRepo.save(uo2T);
                        }
                        status = "u/o 2T completed.,";
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

                            pariDispari2T = new PariDispari2T();
                            pariDispari2T.setMatch(matchModal);
                            pariDispari2T.setPari(PARI);
                            pariDispari2T.setDispari(DISPARI);
                            pariDispari2T.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                pariDispari2T.setScrtime(live_matchTime);
                            } else {
                                pariDispari2T.setScrtime(pre_matchTime);
                            }


                            pariDispari2TRepo.save(pariDispari2T);
                        }
                        status = "pari/dispari 2T completed.,";
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

                            uoQuarto_1 = new UOQuarto_1();
                            uoQuarto_1.setMatch(matchModal);
                            uoQuarto_1.setName(value_1);
                            uoQuarto_1.setUnder(value_2);
                            uoQuarto_1.setOver(value_3);
                            uoQuarto_1.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoQuarto_1.setScrtime(live_matchTime);
                            } else {
                                uoQuarto_1.setScrtime(pre_matchTime);
                            }


                            uoQuarto_1Repo.save(uoQuarto_1);
                        }
                        status = "u/o 1° quarto completed.,";
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

                            uoQuarto_2 = new UOQuarto_2();
                            uoQuarto_2.setMatch(matchModal);
                            uoQuarto_2.setName(value_1);
                            uoQuarto_2.setUnder(value_2);
                            uoQuarto_2.setOver(value_3);
                            uoQuarto_2.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoQuarto_2.setScrtime(live_matchTime);
                            } else {
                                uoQuarto_2.setScrtime(pre_matchTime);
                            }

                            uoQuarto_2Repo.save(uoQuarto_2);
                        }
                        status = "u/o 2° quarto completed.,";
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

                            uoQuarto_3 = new UOQuarto_3();
                            uoQuarto_3.setMatch(matchModal);
                            uoQuarto_3.setName(value_1);
                            uoQuarto_3.setUnder(value_2);
                            uoQuarto_3.setOver(value_3);
                            uoQuarto_3.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoQuarto_3.setScrtime(live_matchTime);
                            } else {
                                uoQuarto_3.setScrtime(pre_matchTime);
                            }

                            uoQuarto_3Repo.save(uoQuarto_3);
                        }
                        status = "u/o 3° quarto completed.,";
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

                            uoQuarto_4 = new UOQuarto_4();
                            uoQuarto_4.setMatch(matchModal);
                            uoQuarto_4.setName(value_1);
                            uoQuarto_4.setUnder(value_2);
                            uoQuarto_4.setOver(value_3);
                            uoQuarto_4.setScrape(scrape);
                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoQuarto_4.setScrtime(live_matchTime);
                            } else {
                                uoQuarto_4.setScrtime(pre_matchTime);
                            }

                            uoQuarto_4Repo.save(uoQuarto_4);
                        }
                        status = "u/o 4° quarto completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("quarto con punt. piu' alto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ quarto con punt. piu' alto");
                        for (WebElement row : valueSet) {
                            String name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            quartoConPuntPiuAlto = new QuartoConPuntPiuAlto();
                            quartoConPuntPiuAlto.setMatch(matchModal);
                            quartoConPuntPiuAlto.setName(name);
                            quartoConPuntPiuAlto.setValue(value);
                            quartoConPuntPiuAlto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                quartoConPuntPiuAlto.setScrtime(live_matchTime);
                            } else {
                                quartoConPuntPiuAlto.setScrtime(pre_matchTime);
                            }

                            quartoConPuntPiuAltoRepo.save(quartoConPuntPiuAlto);
                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            quartoConPuntPiuAlto = new QuartoConPuntPiuAlto();
                            quartoConPuntPiuAlto.setMatch(matchModal);
                            quartoConPuntPiuAlto.setName(name);
                            quartoConPuntPiuAlto.setValue(value);
                            quartoConPuntPiuAlto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                quartoConPuntPiuAlto.setScrtime(live_matchTime);
                            } else {
                                quartoConPuntPiuAlto.setScrtime(pre_matchTime);
                            }
                            quartoConPuntPiuAltoRepo.save(quartoConPuntPiuAlto);
                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            quartoConPuntPiuAlto = new QuartoConPuntPiuAlto();
                            quartoConPuntPiuAlto.setMatch(matchModal);
                            quartoConPuntPiuAlto.setName(name);
                            quartoConPuntPiuAlto.setValue(value);
                            quartoConPuntPiuAlto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                quartoConPuntPiuAlto.setScrtime(live_matchTime);
                            } else {
                                quartoConPuntPiuAlto.setScrtime(pre_matchTime);
                            }
                            quartoConPuntPiuAltoRepo.save(quartoConPuntPiuAlto);
                        }
                        status = "quarto con punt. piu' alto completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 1° quarto")) {

                        System.err.println("================ t/t handicap 1° quarto");
                        List<WebElement> valueSet = null;

                        if (scrape_type.equalsIgnoreCase("LIVE")) {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap_1Quarto = new TTHandicap_1Quarto();
                                ttHandicap_1Quarto.setMatch(matchModal);
                                ttHandicap_1Quarto.setName(value_1);
                                ttHandicap_1Quarto.setOne(value_2);
                                ttHandicap_1Quarto.setTwo(value_3);
                                ttHandicap_1Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap_1Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap_1Quarto.setScrtime(pre_matchTime);
                                }

                                ttHandicap_1QuartoRepo.save(ttHandicap_1Quarto);
                            }
                        } else {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                    .findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap_1Quarto = new TTHandicap_1Quarto();
                                ttHandicap_1Quarto.setMatch(matchModal);
                                ttHandicap_1Quarto.setName(value_1);
                                ttHandicap_1Quarto.setOne(value_2);
                                ttHandicap_1Quarto.setTwo(value_3);
                                ttHandicap_1Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap_1Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap_1Quarto.setScrtime(pre_matchTime);
                                }

                                ttHandicap_1QuartoRepo.save(ttHandicap_1Quarto);
                            }
                        }
                        status = "t/t handicap 1° quarto completed.,";
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

                            senzaMargine_12_1Quarto = new SenzaMargine_12_1Quarto();
                            senzaMargine_12_1Quarto.setMatch(matchModal);
                            senzaMargine_12_1Quarto.setOne(value_1);
                            senzaMargine_12_1Quarto.setMulti(value_2);
                            senzaMargine_12_1Quarto.setTwo(value_3);
                            senzaMargine_12_1Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                senzaMargine_12_1Quarto.setScrtime(live_matchTime);
                            } else {
                                senzaMargine_12_1Quarto.setScrtime(pre_matchTime);
                            }

                            senzaMargine_12_1QuartoRepo.save(senzaMargine_12_1Quarto);
                        }
                        status = "1X2 1° quarto (senza margini) completed.,";
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

                            pariDispari1Quarto = new PariDispari1Quarto();
                            pariDispari1Quarto.setMatch(matchModal);
                            pariDispari1Quarto.setPari(value_1);
                            pariDispari1Quarto.setDispari(value_2);
                            pariDispari1Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                pariDispari1Quarto.setScrtime(live_matchTime);
                            } else {
                                pariDispari1Quarto.setScrtime(pre_matchTime);
                            }

                            pariDispari_1QuartoRepo.save(pariDispari1Quarto);
                        }
                        status = "pari/dispari 1° quarto completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 2° quarto")) {

                        System.err.println("================ t/t handicap 2° quarto");
                        List<WebElement> valueSet = null;

                        if (scrape_type.equalsIgnoreCase("LIVE")) {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap2Quarto = new TTHandicap2Quarto();
                                ttHandicap2Quarto.setMatch(matchModal);
                                ttHandicap2Quarto.setName(value_1);
                                ttHandicap2Quarto.setOne(value_2);
                                ttHandicap2Quarto.setTwo(value_3);
                                ttHandicap2Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap2Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap2Quarto.setScrtime(pre_matchTime);
                                }

                                ttHandicap_2QuartoRepo.save(ttHandicap2Quarto);
                            }
                        } else {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                    .findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap2Quarto = new TTHandicap2Quarto();
                                ttHandicap2Quarto.setMatch(matchModal);
                                ttHandicap2Quarto.setName(value_1);
                                ttHandicap2Quarto.setOne(value_2);
                                ttHandicap2Quarto.setTwo(value_3);
                                ttHandicap2Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap2Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap2Quarto.setScrtime(pre_matchTime);
                                }

                                ttHandicap_2QuartoRepo.save(ttHandicap2Quarto);
                            }
                        }

                        status = "t/t handicap 2° quarto completed.,";
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

                            senzaMargine_12_2Quarto = new SenzaMargine_12_2Quarto();
                            senzaMargine_12_2Quarto.setMatch(matchModal);
                            senzaMargine_12_2Quarto.setOne(value_1);
                            senzaMargine_12_2Quarto.setMulti(value_2);
                            senzaMargine_12_2Quarto.setTwo(value_3);
                            senzaMargine_12_2Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                senzaMargine_12_2Quarto.setScrtime(live_matchTime);
                            } else {
                                senzaMargine_12_2Quarto.setScrtime(pre_matchTime);
                            }

                            senzaMargine_12_2QuartoRepo.save(senzaMargine_12_2Quarto);
                        }
                        status = "1X2 2° quarto (senza margini) completed.,";
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

                            pariDispari2Quarto = new PariDispari2Quarto();
                            pariDispari2Quarto.setMatch(matchModal);
                            pariDispari2Quarto.setPari(value_1);
                            pariDispari2Quarto.setDispari(value_2);
                            pariDispari2Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                pariDispari2Quarto.setScrtime(live_matchTime);
                            } else {
                                pariDispari2Quarto.setScrtime(pre_matchTime);
                            }

                            pariDispari2QuartoRepo.save(pariDispari2Quarto);
                        }
                        status = "pari/dispari 2° quarto completed.,";
                    }


                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 3° quarto")) {

                        System.err.println("================ t/t handicap 2° quarto");
                        List<WebElement> valueSet = null;

                        if (scrape_type.equalsIgnoreCase("LIVE")) {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap3Quarto = new TTHandicap3Quarto();
                                ttHandicap3Quarto.setName(value_1);
                                ttHandicap3Quarto.setOne(value_2);
                                ttHandicap3Quarto.setTwo(value_3);
                                ttHandicap3Quarto.setMatch(matchModal);
                                ttHandicap3Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap3Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap3Quarto.setScrtime(pre_matchTime);
                                }


                                ttHandicap_3QuartoRepo.save(ttHandicap3Quarto);
                            }
                        } else {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                    .findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap3Quarto = new TTHandicap3Quarto();
                                ttHandicap3Quarto.setName(value_1);
                                ttHandicap3Quarto.setOne(value_2);
                                ttHandicap3Quarto.setTwo(value_3);
                                ttHandicap3Quarto.setMatch(matchModal);
                                ttHandicap3Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap3Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap3Quarto.setScrtime(pre_matchTime);
                                }

                                ttHandicap_3QuartoRepo.save(ttHandicap3Quarto);
                            }
                        }
                        status = "t/t handicap 3° quarto completed.,";
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

                            senzaMargine_12_3Quarto = new SenzaMargine_12_3Quarto();
                            senzaMargine_12_3Quarto.setMatch(matchModal);
                            senzaMargine_12_3Quarto.setOne(value_1);
                            senzaMargine_12_3Quarto.setMulti(value_2);
                            senzaMargine_12_3Quarto.setTwo(value_3);
                            senzaMargine_12_3Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                senzaMargine_12_3Quarto.setScrtime(live_matchTime);
                            } else {
                                senzaMargine_12_3Quarto.setScrtime(pre_matchTime);
                            }

                            senzaMargine_12_3QuartoRepo.save(senzaMargine_12_3Quarto);
                        }
                        status = "1X2 3° quarto (senza margini) completed.,";
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

                            pariDispari3Quarto = new PariDispari3Quarto();
                            pariDispari3Quarto.setMatch(matchModal);
                            pariDispari3Quarto.setPari(value_1);
                            pariDispari3Quarto.setDispari(value_2);
                            pariDispari3Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                pariDispari3Quarto.setScrtime(live_matchTime);
                            } else {
                                pariDispari3Quarto.setScrtime(pre_matchTime);
                            }

                            pariDispari3QuartoRepo.save(pariDispari3Quarto);
                        }
                        status = "pari/dispari 3° quarto completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("t/t handicap 4° quarto")) {

                        System.err.println("================ t/t handicap 2° quarto");
                        List<WebElement> valueSet = null;

                        if (scrape_type.equalsIgnoreCase("LIVE")) {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap4Quarto = new TTHandicap4Quarto();
                                ttHandicap4Quarto.setMatch(matchModal);
                                ttHandicap4Quarto.setName(value_1);
                                ttHandicap4Quarto.setOne(value_2);
                                ttHandicap4Quarto.setTwo(value_3);
                                ttHandicap4Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap4Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap4Quarto.setScrtime(pre_matchTime);
                                }

                                ttHandicap_4QuartoRepo.save(ttHandicap4Quarto);
                            }
                        } else {
                            valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport")).findElement(By.tagName("div"))
                                    .findElement(By.tagName("div")).findElements(By.xpath("./*"));
                            for (WebElement row : valueSet) {
                                String value_1 = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                                String value_3 = row.findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).getAttribute("innerText");
                                System.err.println(value_1);
                                System.err.println(value_2);
                                System.err.println(value_3);
                                ttHandicap4Quarto = new TTHandicap4Quarto();
                                ttHandicap4Quarto.setMatch(matchModal);
                                ttHandicap4Quarto.setName(value_1);
                                ttHandicap4Quarto.setOne(value_2);
                                ttHandicap4Quarto.setTwo(value_3);
                                ttHandicap4Quarto.setScrape(scrape);

                                if (scrape_type.equalsIgnoreCase("LIVE")) {
                                    ttHandicap4Quarto.setScrtime(live_matchTime);
                                } else {
                                    ttHandicap4Quarto.setScrtime(pre_matchTime);
                                }

                                ttHandicap_4QuartoRepo.save(ttHandicap4Quarto);
                            }
                        }
                        status = "t/t handicap 4° quarto completed.,";
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

                            senzaMargine_12_4Quarto = new SenzaMargine_12_4Quarto();
                            senzaMargine_12_4Quarto.setMatch(matchModal);
                            senzaMargine_12_4Quarto.setOne(value_1);
                            senzaMargine_12_4Quarto.setMulti(value_2);
                            senzaMargine_12_4Quarto.setTwo(value_3);
                            senzaMargine_12_4Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                senzaMargine_12_4Quarto.setScrtime(live_matchTime);
                            } else {
                                senzaMargine_12_4Quarto.setScrtime(pre_matchTime);
                            }

                            senzaMargine_12_4QuartoRepo.save(senzaMargine_12_4Quarto);
                        }
                        status = "1X2 4° quarto (senza margini) completed.,";
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

                            pariDispari4Quarto = new PariDispari4Quarto();
                            pariDispari4Quarto.setMatch(matchModal);
                            pariDispari4Quarto.setPari(value_1);
                            pariDispari4Quarto.setDispari(value_2);
                            pariDispari4Quarto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                pariDispari4Quarto.setScrtime(live_matchTime);
                            } else {
                                pariDispari4Quarto.setScrtime(pre_matchTime);
                            }

                            pariDispari4QuartoRepo.save(pariDispari4Quarto);
                        }
                        status = "pari/dispari 4° quarto completed.,";
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

                            uoCaseInclSuppl = new UOCaseInclSuppl();
                            uoCaseInclSuppl.setMatch(matchModal);
                            uoCaseInclSuppl.setName(value_1);
                            uoCaseInclSuppl.setUnder(value_2);
                            uoCaseInclSuppl.setOver(value_3);
                            uoCaseInclSuppl.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoCaseInclSuppl.setScrtime(live_matchTime);
                            } else {
                                uoCaseInclSuppl.setScrtime(pre_matchTime);
                            }


                            uoCaseInclSupplRepo.save(uoCaseInclSuppl);
                        }
                        status = "U/O casa (incl.suppl.) completed.,";
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

                            uoOspiteInclSuppl = new UOOspiteInclSuppl();
                            uoOspiteInclSuppl.setMatch(matchModal);
                            uoOspiteInclSuppl.setName(value_1);
                            uoOspiteInclSuppl.setUnder(value_2);
                            uoOspiteInclSuppl.setOver(value_3);
                            uoOspiteInclSuppl.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                uoOspiteInclSuppl.setScrtime(live_matchTime);
                            } else {
                                uoOspiteInclSuppl.setScrtime(pre_matchTime);
                            }

                            uoOspiteInclSupplRepo.save(uoOspiteInclSuppl);
                        }
                        status = "U/O ospite (incl.suppl.) completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("1° tempo/finale")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ 1° tempo/finale");
                        for (WebElement row : valueSet) {

                            String name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            tempoFinale_1 = new TempoFinale_1();
                            tempoFinale_1.setMatch(matchModal);
                            tempoFinale_1.setName(name);
                            tempoFinale_1.setValue(value);
                            tempoFinale_1.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                tempoFinale_1.setScrtime(live_matchTime);
                            } else {
                                tempoFinale_1.setScrtime(pre_matchTime);
                            }
                            tempoFinale_1Repo.save(tempoFinale_1);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            tempoFinale_1 = new TempoFinale_1();
                            tempoFinale_1.setMatch(matchModal);
                            tempoFinale_1.setName(name);
                            tempoFinale_1.setValue(value);
                            tempoFinale_1.setScrape(scrape);


                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                tempoFinale_1.setScrtime(live_matchTime);
                            } else {
                                tempoFinale_1.setScrtime(pre_matchTime);
                            }
                            tempoFinale_1Repo.save(tempoFinale_1);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            tempoFinale_1 = new TempoFinale_1();
                            tempoFinale_1.setMatch(matchModal);
                            tempoFinale_1.setName(name);
                            tempoFinale_1.setValue(value);
                            tempoFinale_1.setScrape(scrape);


                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                tempoFinale_1.setScrtime(live_matchTime);
                            } else {
                                tempoFinale_1.setScrtime(pre_matchTime);
                            }
                            tempoFinale_1Repo.save(tempoFinale_1);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                        }
                        status = "1° tempo/finale completed.,";
                    }


                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("prima squadra a segnare")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ prima squadra a segnare");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);

                            primaSquadraSegnare = new PrimaSquadraSegnare();
                            primaSquadraSegnare.setMatch(matchModal);
                            primaSquadraSegnare.setTeam1(value_1);
                            primaSquadraSegnare.setTeam2(value_2);
                            primaSquadraSegnare.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                primaSquadraSegnare.setScrtime(live_matchTime);
                            } else {
                                primaSquadraSegnare.setScrtime(pre_matchTime);
                            }
                            primaSquadraSegnareRepo.save(primaSquadraSegnare);
                        }
                        status = "prima squadra a segnare completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("ultima squadra a segnare")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ ultima squadra a segnare");
                        for (WebElement row : valueSet) {
                            String value_1 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).getAttribute("innerText");
                            String value_2 = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).getAttribute("innerText");
                            System.err.println(value_1);
                            System.err.println(value_2);

                            ultimaSquadraSegnare = new UltimaSquadraSegnare();
                            ultimaSquadraSegnare.setMatch(matchModal);
                            ultimaSquadraSegnare.setTeam1(value_1);
                            ultimaSquadraSegnare.setTeam2(value_2);
                            ultimaSquadraSegnare.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                ultimaSquadraSegnare.setScrtime(live_matchTime);
                            } else {
                                ultimaSquadraSegnare.setScrtime(pre_matchTime);
                            }

                            ultimaSquadraSegnareRepo.save(ultimaSquadraSegnare);
                        }
                        status = "ultima squadra a segnare completed.,";
                    }

                    if (table.findElement(By.className("box-title")).getAttribute("innerText").equalsIgnoreCase("combo match + ultimo punto")) {
                        List<WebElement> valueSet = table.findElements(By.xpath("./*")).get(1).findElement(By.className("box-sport"))
                                .findElement(By.tagName("div"))
                                .findElements(By.xpath("./*"));

                        System.out.println("================ combo match + ultimo punto");
                        for (WebElement row : valueSet) {

                            String name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            String value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            comboMatchUltimoPunto = new ComboMatchUltimoPunto();
                            comboMatchUltimoPunto.setMatch(matchModal);
                            comboMatchUltimoPunto.setName(name);
                            comboMatchUltimoPunto.setValue(value);
                            comboMatchUltimoPunto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                comboMatchUltimoPunto.setScrtime(live_matchTime);
                            } else {
                                comboMatchUltimoPunto.setScrtime(pre_matchTime);
                            }

                            comboMatchUltimoPuntoRepo.save(comboMatchUltimoPunto);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            comboMatchUltimoPunto = new ComboMatchUltimoPunto();
                            comboMatchUltimoPunto.setMatch(matchModal);
                            comboMatchUltimoPunto.setName(name);
                            comboMatchUltimoPunto.setValue(value);
                            comboMatchUltimoPunto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                comboMatchUltimoPunto.setScrtime(live_matchTime);
                            } else {
                                comboMatchUltimoPunto.setScrtime(pre_matchTime);
                            }

                            comboMatchUltimoPuntoRepo.save(comboMatchUltimoPunto);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            comboMatchUltimoPunto = new ComboMatchUltimoPunto();
                            comboMatchUltimoPunto.setMatch(matchModal);
                            comboMatchUltimoPunto.setName(name);
                            comboMatchUltimoPunto.setValue(value);
                            comboMatchUltimoPunto.setScrape(scrape);

                            if (scrape_type.equalsIgnoreCase("LIVE")) {
                                comboMatchUltimoPunto.setScrtime(live_matchTime);
                            } else {
                                comboMatchUltimoPunto.setScrtime(pre_matchTime);
                            }

                            comboMatchUltimoPuntoRepo.save(comboMatchUltimoPunto);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                        }
                        status = "combo match + ultimo punto completed.,";
                    }
                }

                break;
            }
        }
        return true;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.scrapeLive();
        this.scrapePreMatch();
    }
}
