package ro.calin.SoulCleaner.service;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ro.calin.SoulCleaner.database.SoulCleaningSession;
import ro.calin.SoulCleaner.database.SoulCleaningSessionDAO;
import ro.calin.SoulCleaner.database.SeleniumConnection;
import ro.calin.SoulCleaner.database.Time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SoulCleaningSessionService {

    @Autowired
    SeleniumConnection seleniumConnection;

    @Autowired
    SoulCleaningSessionDAO soulCleaningSessionDAO;

    @Autowired
    Time time;

    public void connect() {
        System.setProperty("webdriver.gecko.driver", "F:/geckodriver.exe");

    }

    public void startCleaningSession(String option, int numberofPages) {
        time.setInitialTime(LocalTime.now());

        FirefoxDriver firefoxDriver = new FirefoxDriver();

        firefoxDriver.get("https://danbooru.donmai.us/");

        WebElement searchInput = firefoxDriver.findElementByCssSelector("#tags");
        searchInput.sendKeys(option);
        WebElement searchBox = firefoxDriver.findElementByCssSelector("#search-box-submit");
        searchBox.click();

        List<WebElement> pagePosts = firefoxDriver.findElementsByCssSelector(".post-preview");
        int countPictures = 0;

        for (int j = 1; j <= numberofPages; j++) {
            for (int i = 0; i < pagePosts.size(); i++) {
                WebElement image = pagePosts.get(i);
                try {
                    image.click();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
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
                    WebElement nextPageArrow = firefoxDriver.findElementByCssSelector("#paginator-next");
                    nextPageArrow.click();
                    pagePosts.clear();
                    pagePosts.addAll(firefoxDriver.findElementsByCssSelector(".post-preview"));
                    break;
                }
            }
        }
        seleniumConnection.setCountPictures(countPictures);
        time.setFinalTime(LocalTime.now());
    }

    public void saveCleaningSession(String option, int numberOfPages, long numberOfSeconds, int numberOfPictures) {
        SoulCleaningSession soulCleaningSession = new SoulCleaningSession();
        if (option.equals("")){
            soulCleaningSession.setTag_name("No Tag");
        } else {
            soulCleaningSession.setTag_name(option);
        }
        soulCleaningSession.setPage_count(numberOfPages);
        soulCleaningSession.setSeconds_count(numberOfSeconds);
        soulCleaningSession.setPicture_count(numberOfPictures);
        soulCleaningSession.setSite("Danbooru");

        soulCleaningSessionDAO.save(soulCleaningSession);
    }

    public List<SoulCleaningSession> getLastCleaningSession() {
        List<SoulCleaningSession> soulCleaningSessions = this.findDesc();

        List<SoulCleaningSession> lastSoulCleaningSessionList = new ArrayList<>();

        for (SoulCleaningSession soulCleaningSession: soulCleaningSessions) {
            lastSoulCleaningSessionList.add(soulCleaningSession);
            break;
        }
        return lastSoulCleaningSessionList;
    }

    public List<SoulCleaningSession> getAllCleaningSessions() {
        Iterable<SoulCleaningSession> cleaningSessions = soulCleaningSessionDAO.findAll();

        List<SoulCleaningSession> soulCleaningSessionList = new ArrayList<>();

        for (SoulCleaningSession soulCleaningSession : cleaningSessions) {
            soulCleaningSessionList.add(soulCleaningSession);
        }

        return soulCleaningSessionList;
    }

    public void deleteSoulCleaningSession(int soulCleaningSessionId) {
        soulCleaningSessionDAO.deleteById(soulCleaningSessionId);
    }

    public List<SoulCleaningSession> findDesc() {
        return soulCleaningSessionDAO.findByOrderByIdDesc();

    }

    public Page<SoulCleaningSession> getAllByPage(int pageNumber) {
        Pageable firstPage = PageRequest.of(pageNumber,6);

        return soulCleaningSessionDAO.findAll(firstPage);
    }

    public Page<SoulCleaningSession> getAllByPageDesc(int pageNumber) {
        Pageable firstPage = PageRequest.of(pageNumber,6);

        return soulCleaningSessionDAO.findByOrderByIdDesc(firstPage);
    }

    public void setSoulCleaningSessionDAO(SoulCleaningSessionDAO soulCleaningSessionDAO) {
        this.soulCleaningSessionDAO = soulCleaningSessionDAO;
    }
}
