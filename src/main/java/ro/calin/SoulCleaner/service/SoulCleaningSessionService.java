package ro.calin.SoulCleaner.service;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ro.calin.SoulCleaner.database.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SoulCleaningSessionService {

    @Autowired
    SoulCleaningCount soulCleaningCount;

    @Autowired
    SoulCleaningSessionDAO soulCleaningSessionDAO;

    @Autowired
    SoulCleaningTime soulCleaningTime;

    private Logger LOG = LoggerFactory.getLogger(SoulCleaningSessionService.class);

    public void connect() {

        LOG.info("Ma conectez la Geckodriver.");
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Calin Valentin\\IdeaProjects\\SoulCleaner\\src\\main\\resources\\static/geckodriver.exe");

    }

    public void startCleaningSession(String option, int numberofPages) {

        soulCleaningTime.setInitialTime(LocalTime.now());

        FirefoxDriver firefoxDriver = new FirefoxDriver();

        LOG.info("Ma conectez la site-ul Danbooru.");
        firefoxDriver.get("https://danbooru.donmai.us/");

        WebElement searchInput = firefoxDriver.findElementByCssSelector("#tags");
        searchInput.sendKeys(option);
        WebElement searchBox = firefoxDriver.findElementByCssSelector("#search-box-submit");
        searchBox.click();

        List<WebElement> pagePosts = firefoxDriver.findElementsByCssSelector(".post-preview");
        int countPictures = 0;

        LOG.info("Incep analizarea pozelor pentru " + numberofPages + " pagini");
        for (int j = 1; j <= numberofPages; j++) {
            for (int i = 0; i < pagePosts.size(); i++) {
                WebElement image = pagePosts.get(i);
                try {
                    LOG.info("Analizez poza.");
                    image.click();
                } catch (Exception e) {
                    LOG.error("Poza este ascunsa in pagina web. Sar peste ea...");
                    e.printStackTrace();
                    continue;
                }

                countPictures++;
                firefoxDriver.navigate().back();
                pagePosts.clear();
                pagePosts.addAll(firefoxDriver.findElementsByCssSelector(".post-preview"));
                if (i == pagePosts.size() - 1) {
                    if (j == numberofPages) {
                        firefoxDriver.quit();
                        break;
                    }
                    LOG.info("Trec la pagina urmatoare din site-ul web...");
                    WebElement nextPageArrow = firefoxDriver.findElementByCssSelector("#paginator-next");
                    nextPageArrow.click();
                    pagePosts.clear();
                    pagePosts.addAll(firefoxDriver.findElementsByCssSelector(".post-preview"));
                    break;
                }
            }
        }
        LOG.info("Am terminat de analizat pozele.");

        LOG.info("Setez numarul de poze analizate: " + countPictures);
        soulCleaningCount.setCountPictures(countPictures);

        soulCleaningTime.setFinalTime(LocalTime.now());

    }

    public void saveCleaningSession(String option, int numberOfPages, long numberOfSeconds, int numberOfPictures) {

        SoulCleaningSession soulCleaningSession = new SoulCleaningSession();

        LOG.info("Incep salvarea sesiunii SoulCleaning in baza de date: option = " + option + ", number of pages = " + numberOfPages +
                ", number of seconds = " + numberOfSeconds + ", number of pictures = " + numberOfPictures + ", site = Danbooru");
        if (option.equals("")){
            soulCleaningSession.setTag_name("No Tag");
        } else {
            soulCleaningSession.setTag_name(option);
        }
        soulCleaningSession.setPage_count(numberOfPages);
        soulCleaningSession.setSeconds_count(numberOfSeconds);
        soulCleaningSession.setPicture_count(numberOfPictures);
        soulCleaningSession.setSite("Danbooru");

        LOG.info("Am terminat de salvat sesiunea SoulCleaning in baza de date.");
        soulCleaningSessionDAO.save(soulCleaningSession);

    }

    public List<SoulCleaningSession> getLastCleaningSession() {

        List<SoulCleaningSession> soulCleaningSessions = this.findDesc();

        List<SoulCleaningSession> lastSoulCleaningSessionList = new ArrayList<>();

        LOG.info("Incep sa caut ultima sesiune SoulCleaning...");
        for (SoulCleaningSession soulCleaningSession: soulCleaningSessions) {
            lastSoulCleaningSessionList.add(soulCleaningSession);
            break;
        }

        LOG.info("Intorc ultima sesiune SoulCleaning.");
        return lastSoulCleaningSessionList;

    }

    public void deleteSoulCleaningSession(int soulCleaningSessionId) {

        LOG.info("Sterg sesiunea SoulCleaning cu id-ul " + soulCleaningSessionId);
        soulCleaningSessionDAO.deleteById(soulCleaningSessionId);

    }

    public List<SoulCleaningSession> findDesc() {

        LOG.info("Intorc sesiunile SoulCleaning in ordine descrescatoare.");
        return soulCleaningSessionDAO.findByOrderByIdDesc();

    }

    public Page<SoulCleaningSession> getAllByPage(int pageNumber) {

        Pageable firstPage = PageRequest.of(pageNumber,6);

        LOG.info("Intorc sesiunile SoulCleaning pentru pagina " + pageNumber + ",in ordine crescatoare.");
        return soulCleaningSessionDAO.findAll(firstPage);

    }

    public Page<SoulCleaningSession> getAllByPageDesc(int pageNumber) {

        Pageable firstPage = PageRequest.of(pageNumber,6);

        LOG.info("Intorc sesiunile SoulCleaning pentru pagina " + pageNumber + ",in ordine descrescatoare.");
        return soulCleaningSessionDAO.findByOrderByIdDesc(firstPage);

    }

    public void setSoulCleaningSessionDAO(SoulCleaningSessionDAO soulCleaningSessionDAO) {

        this.soulCleaningSessionDAO = soulCleaningSessionDAO;

    }

    public void setSoulCleaningTime(SoulCleaningTime soulCleaningTime) {
        this.soulCleaningTime = soulCleaningTime;
    }

    public void setSoulCleaningCount(SoulCleaningCount soulCleaningCount) {
        this.soulCleaningCount = soulCleaningCount;
    }




}
