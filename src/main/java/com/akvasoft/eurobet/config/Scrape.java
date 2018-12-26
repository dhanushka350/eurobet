package com.akvasoft.eurobet.config;

import com.akvasoft.eurobet.dto.Item;
import com.akvasoft.eurobet.modals.*;
import com.akvasoft.eurobet.modals.Match;
import com.akvasoft.eurobet.repo.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Scrape implements InitializingBean {

    static String status = "READY";
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


    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    @ResponseBody
    private ModelAndView scrape() throws InterruptedException {
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/status"}, method = RequestMethod.GET)
    @ResponseBody
    private String status() throws InterruptedException {
        return status;
    }

    @RequestMapping(value = {"/scrape/league"}, method = RequestMethod.POST)
    @ResponseBody
    private void scrape(@RequestBody Item item) throws InterruptedException {
        status = "received league " + item.getLeague() + " and link " + item.getLink() + ",";
        scrapeOld(item.getLeague(), item.getLink());
        status = "DONE!,";
        status = "READY,";
    }

    public FirefoxDriver getDriver() {
        System.setProperty("webdriver.gecko.driver", "/var/lib/tomcat8/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(false);

        FirefoxDriver driver = new FirefoxDriver(options);
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

        return driver;
    }


    public void scrapeOld(String league, String link) throws InterruptedException {
        FirefoxDriver driver = getDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.get(link);
        scrapeMainTable(driver);
//        WebElement basket = driver.findElementByXPath("/html/body/div[5]/div[1]/div/div/div/div[2]/div/div/div/div/div/ul[2]/li[3]/a/h2");
//        jse.executeScript("arguments[0].scrollIntoView(true);", basket);
//        jse.executeScript("arguments[0].click();", basket);
//
//        Thread.sleep(2000);
//
//        WebElement europa = driver.findElementByXPath("/html/body/div[5]/div[1]/div/div/div/div[2]/div/div/div/div/div/ul[2]/li[3]").findElement(By.className("open")).findElement(By.tagName("ul")).findElements(By.xpath("./*")).get(1);
//        for (WebElement ignored : europa.findElements(By.xpath("./*"))) {
//            if (ignored.findElement(By.tagName("a")).findElement(By.tagName("h4")).getAttribute("innerText").equalsIgnoreCase("Europa")) {
//                jse.executeScript("arguments[0].scrollIntoView(true);", ignored.findElement(By.tagName("a")));
//                jse.executeScript("arguments[0].click();", ignored.findElement(By.tagName("a")));
//                Thread.sleep(500);
//
//                for (WebElement element : ignored.findElement(By.className("sidebar-league")).findElements(By.xpath("./*"))) {
//                    jse.executeScript("arguments[0].scrollIntoView(true);", element.findElement(By.tagName("a")));
//                    jse.executeScript("arguments[0].click();", element.findElement(By.tagName("a")));
//                    Thread.sleep(1000);
//                    scrapeMainTable(driver);
//                    break;
//                }
//
//                break;
//            }
//        }


    }

    private void scrapeMainTable(FirefoxDriver driver) throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement centerDiv = driver.findElementByClassName("main-content-wrapper");
        WebElement tables = centerDiv.findElement(By.className("baseAnimation"));
        List<String> matchList = new ArrayList<>();

        for (WebElement table : tables.findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*"))) {
            for (WebElement element : table.findElement(By.className("anti-row")).findElements(By.xpath("./*"))) {
                Thread.sleep(1000);
                WebElement row = element.findElement(By.className("event-row")).findElement(By.className("event-wrapper-info")).findElement(By.className("event-players"));
                System.err.println("collected " + row.findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href"));
                matchList.add(row.findElement(By.tagName("span")).findElement(By.tagName("a")).getAttribute("href"));
            }

        }

        status = "found " + matchList.size() + " matches,";
        for (String s : matchList) {
            driver.get(s);
            scrapeInnerTables(driver);
        }

    }

    private boolean scrapeInnerTables(FirefoxDriver driver) throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
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

        Thread.sleep(1000);
        WebElement match = driver.findElementByXPath("/html/body/div[5]/div[2]/div/div/div/div/div/div[1]/div");
        int length = match.findElement(By.className("breadcrumbs")).getAttribute("innerText").split(">").length;
        String matchTitle = match.findElement(By.className("breadcrumbs")).getAttribute("innerText").split(">")[length - 1];
        String date = match.findElement(By.className("date-time")).getAttribute("innerText").split("Ore")[0].trim();
        String time = match.findElement(By.className("date-time")).getAttribute("innerText").split("Ore")[1].trim();
        String team1 = matchTitle.split("-")[0].trim();
        String team2 = matchTitle.split("-")[1].trim();


        if (matchRepo.findTopByDateEqualsAndTimeEqualsAndOneEqualsAndTwoEquals(date, time, team1, team2) != null) {
            status = "already scraped";
            System.out.println("already scraped.. skipping");
            return true;
        }

        matchModal = new Match();
        matchModal.setDate(date.trim());
        matchModal.setTime(time.trim());
        matchModal.setOne(matchTitle.split("-")[0].trim());
        matchModal.setTwo(matchTitle.split("-")[1].trim());
        matchModal.setStatus("Pre Match");
        matchRepo.save(matchModal);
        status = "scraping Team 1 - " + team1 + " Team 2 - " + team2;
        status = "Date - " + date + " Time - " + time;
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

                            ttMatch = new TTMatch();
                            ttMatch.setMatch(matchModal);
                            ttMatch.setOne(value_1);
                            ttMatch.setTwo(value_2);
                            ttMatchRepo.save(ttMatch);
                        }
                        status = "t/t match completed.,";

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
                            ttHandicap = new TTHandicap();
                            ttHandicap.setMatch(matchModal);
                            ttHandicap.setName(value_1);
                            ttHandicap.setOne(value_2);
                            ttHandicap.setTwo(value_3);
                            ttHandicapRepo.save(ttHandicap);
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
                            pariDispariInclSupplRepo.save(pariDispariInchSuppl);

                        }
                        status = "pari/dispari (incl.suppl.) completed.,";
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

                            ttHandicap1T = new TTHandicap1T();
                            ttHandicap1T.setMatch(matchModal);
                            ttHandicap1T.setName(value_1);
                            ttHandicap1T.setOne(value_2);
                            ttHandicap1T.setTwo(value_2);
                            ttHandicap1TRepo.save(ttHandicap1T);

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
                            pariDispari1TRepo.save(pariDispari1T);
                        }
                        status = "pari/dispari 1T completed.,";
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

                            ttHandicap2T = new TTHandicap2T();
                            ttHandicap2T.setMatch(matchModal);
                            ttHandicap2T.setName(value_1);
                            ttHandicap2T.setOne(value_2);
                            ttHandicap2T.setTwo(value_3);
                            ttHandicap2TRepo.save(ttHandicap2T);
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
                            quartoConPuntPiuAltoRepo.save(quartoConPuntPiuAlto);
                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            quartoConPuntPiuAlto = new QuartoConPuntPiuAlto();
                            quartoConPuntPiuAlto.setMatch(matchModal);
                            quartoConPuntPiuAlto.setName(name);
                            quartoConPuntPiuAlto.setValue(value);
                            quartoConPuntPiuAltoRepo.save(quartoConPuntPiuAlto);
                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            quartoConPuntPiuAlto = new QuartoConPuntPiuAlto();
                            quartoConPuntPiuAlto.setMatch(matchModal);
                            quartoConPuntPiuAlto.setName(name);
                            quartoConPuntPiuAlto.setValue(value);
                            quartoConPuntPiuAltoRepo.save(quartoConPuntPiuAlto);
                        }
                        status = "quarto con punt. piu' alto completed.,";
                    }

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

                            ttHandicap_1Quarto = new TTHandicap_1Quarto();
                            ttHandicap_1Quarto.setMatch(matchModal);
                            ttHandicap_1Quarto.setName(value_1);
                            ttHandicap_1Quarto.setOne(value_2);
                            ttHandicap_1Quarto.setTwo(value_3);
                            ttHandicap_1QuartoRepo.save(ttHandicap_1Quarto);
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
                            pariDispari_1QuartoRepo.save(pariDispari1Quarto);
                        }
                        status = "pari/dispari 1° quarto completed.,";
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

                            ttHandicap2Quarto = new TTHandicap2Quarto();
                            ttHandicap2Quarto.setMatch(matchModal);
                            ttHandicap2Quarto.setName(value_1);
                            ttHandicap2Quarto.setOne(value_2);
                            ttHandicap2Quarto.setTwo(value_3);
                            ttHandicap_2QuartoRepo.save(ttHandicap2Quarto);
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
                            pariDispari2QuartoRepo.save(pariDispari2Quarto);
                        }
                        status = "pari/dispari 2° quarto completed.,";
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

                            ttHandicap3Quarto = new TTHandicap3Quarto();
                            ttHandicap3Quarto.setName(value_1);
                            ttHandicap3Quarto.setOne(value_2);
                            ttHandicap3Quarto.setTwo(value_3);
                            ttHandicap3Quarto.setMatch(matchModal);
                            ttHandicap_3QuartoRepo.save(ttHandicap3Quarto);
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
                            pariDispari3QuartoRepo.save(pariDispari3Quarto);
                        }
                        status = "pari/dispari 3° quarto completed.,";
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

                            ttHandicap4Quarto = new TTHandicap4Quarto();
                            ttHandicap4Quarto.setMatch(matchModal);
                            ttHandicap4Quarto.setName(value_1);
                            ttHandicap4Quarto.setOne(value_2);
                            ttHandicap4Quarto.setTwo(value_3);
                            ttHandicap_4QuartoRepo.save(ttHandicap4Quarto);
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
                            tempoFinale_1Repo.save(tempoFinale_1);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            tempoFinale_1 = new TempoFinale_1();
                            tempoFinale_1.setMatch(matchModal);
                            tempoFinale_1.setName(name);
                            tempoFinale_1.setValue(value);
                            tempoFinale_1Repo.save(tempoFinale_1);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            tempoFinale_1 = new TempoFinale_1();
                            tempoFinale_1.setMatch(matchModal);
                            tempoFinale_1.setName(name);
                            tempoFinale_1.setValue(value);
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
                            comboMatchUltimoPuntoRepo.save(comboMatchUltimoPunto);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            comboMatchUltimoPunto = new ComboMatchUltimoPunto();
                            comboMatchUltimoPunto.setMatch(matchModal);
                            comboMatchUltimoPunto.setName(name);
                            comboMatchUltimoPunto.setValue(value);
                            comboMatchUltimoPuntoRepo.save(comboMatchUltimoPunto);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                            name = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                            value = row.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).findElement(By.tagName("a")).findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            comboMatchUltimoPunto = new ComboMatchUltimoPunto();
                            comboMatchUltimoPunto.setMatch(matchModal);
                            comboMatchUltimoPunto.setName(name);
                            comboMatchUltimoPunto.setValue(value);
                            comboMatchUltimoPuntoRepo.save(comboMatchUltimoPunto);
                            System.err.println("name" + name);
                            System.err.println("value" + value);

                        }
                        status = "combo match + ultimo punto completed.,";
                    }
                }
// break loop when you find TUTTE
                break;
            }
        }
        return true;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        this.scrapeOld();
    }
}
