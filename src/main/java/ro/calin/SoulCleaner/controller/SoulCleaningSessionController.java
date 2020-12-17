package ro.calin.SoulCleaner.controller;

import org.openqa.selenium.NoSuchWindowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.calin.SoulCleaner.database.SoulCleaningCount;
import ro.calin.SoulCleaner.service.SoulCleaningSessionService;
import ro.calin.SoulCleaner.service.SoulCleaningTimeService;

@Controller
public class SoulCleaningSessionController {

    @Autowired
    SoulCleaningCount soulCleaningCount;

    @Autowired
    SoulCleaningSessionService soulCleaningSessionService;

    @Autowired
    SoulCleaningTimeService soulCleaningTimeService;

    private Logger LOG = LoggerFactory.getLogger(SoulCleaningSessionController.class);

    @GetMapping("/activate-session")
    public ModelAndView activateSession(@RequestParam("options") String option,
                                          @RequestParam("numberOfPages") int numberofPages) {

        soulCleaningSessionService.connect();

        ModelAndView modelAndView = new ModelAndView("index");

        if (option.equals("disabledOption")) {
            LOG.info("Optiunea invalida a fost aleasa: " + option + ". Redirectionez inapoi la pagina de index.");
            modelAndView.addObject("invalidOption", "Choose one tag from the dropdown menu");
            return modelAndView;
        }

        try {
            soulCleaningSessionService.startCleaningSession(option, numberofPages);
        } catch (NoSuchWindowException noSuchWindowException) {
            LOG.error("Sesiunea SoulCleaning a fost intrerupta. Redirectionez inapoi la pagina de index.");
            noSuchWindowException.printStackTrace();

            modelAndView.addObject("invalidOption", "SoulCleaning Session has been interrupted! Please try again.");
            return modelAndView;
        }

        soulCleaningSessionService.saveCleaningSession(option, numberofPages, soulCleaningTimeService.getNumberOfSecondForSoulCleaningSession(), soulCleaningCount.getCountPictures());

        modelAndView.addObject("messageSuccessful", "<i class=\"fas fa-check-circle mr-2\"></i>SoulCleaning is successful!");
        modelAndView.addObject("soulCleaningSessionList", soulCleaningSessionService.getLastCleaningSession());
        modelAndView.addObject("messageFinishProcess", "Congratulations! You have saved <span class=\"text_color_cyan_lighten\">" +
                soulCleaningTimeService.getNumberOfSecondForSoulCleaningSession() + " seconds</span> from your life. You are now one step closer to reaching your goal. " +
                "I'm looking forward to our next session!");
        if (option.equals("")){
            modelAndView.addObject("tagChosen", "No Tag");
        } else {
            modelAndView.addObject("tagChosen", option);
        }

        LOG.info("Afisez ultima sesiune SoulCleaning in pagina de index.");
        return modelAndView;

    }

    @GetMapping("myCleaningSessions")
    public ModelAndView showMyCleaningSessionsPage(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                   @RequestParam(value = "option", defaultValue = "sortSessionFirstToLast") String option) {

        ModelAndView modelAndView = new ModelAndView("my-cleaning-sessions");

        if (option.equals("sortSessionFirstToLast")) {
            modelAndView.addObject("allCleaningSessions", soulCleaningSessionService.getAllByPage(pageNumber));
            modelAndView.addObject("prevPage", "http://localhost:8080/myCleaningSessions?option=" + option + "&pageNumber=" + (pageNumber - 1));
            modelAndView.addObject("nextPage", "http://localhost:8080/myCleaningSessions?option=" + option + "&pageNumber=" + (pageNumber + 1));
        }
        if (option.equals("sortSessionLastToFirst")) {
            modelAndView.addObject("allCleaningSessions", soulCleaningSessionService.getAllByPageDesc(pageNumber));
            modelAndView.addObject("prevPage", "http://localhost:8080/myCleaningSessions?option=" + option + "&pageNumber=" + (pageNumber - 1));
            modelAndView.addObject("nextPage", "http://localhost:8080/myCleaningSessions?option=" + option + "&pageNumber=" + (pageNumber + 1));
        }

        LOG.info("Afisez pagina web my-cleaning-sessions.");
        return modelAndView;

    }

    @GetMapping("dashboard")
    public ModelAndView showIndexPage() {

        LOG.info("Afisez pagina de index.");
        return new ModelAndView("index");

    }

    @DeleteMapping("myCleaningSessions/delete-soulcleaningsession")
    @ResponseBody
    public String deleteSoulCleaningSession(@RequestParam("id") int soulCleaningSessionId) {

        soulCleaningSessionService.deleteSoulCleaningSession(soulCleaningSessionId);

        return "ok";

    }
}
