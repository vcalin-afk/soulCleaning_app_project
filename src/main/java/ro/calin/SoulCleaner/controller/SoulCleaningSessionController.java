package ro.calin.SoulCleaner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ro.calin.SoulCleaner.database.SoulCleaningSession;
import ro.calin.SoulCleaner.database.SoulCleaningSessionDAO;
import ro.calin.SoulCleaner.database.SeleniumConnection;
import ro.calin.SoulCleaner.service.SoulCleaningSessionService;
import ro.calin.SoulCleaner.service.TimeService;

import java.util.List;

@Controller
public class SoulCleaningSessionController {

    @Autowired
    SeleniumConnection seleniumConnection;

    @Autowired
    SoulCleaningSessionService soulCleaningSessionService;

    @Autowired
    SoulCleaningSessionDAO soulCleaningSessionDAO;

    @Autowired
    TimeService timeService;

    @GetMapping("/activate-session")
    public ModelAndView activateSession(@RequestParam("options") String option,
                                          @RequestParam("numberOfPages") int numberofPages) {

        soulCleaningSessionService.connect();

        ModelAndView modelAndView = new ModelAndView("index");

        if (option.equals("disabledOption")) {
            modelAndView.addObject("invalidOption", "Choose one tag from the dropdown menu");
            return modelAndView;
        }

        soulCleaningSessionService.startCleaningSession(option, numberofPages);

        soulCleaningSessionService.saveCleaningSession(option, numberofPages, timeService.getNumberOfSecondForSoulCleaningSession(), seleniumConnection.getCountPictures());

        modelAndView.addObject("messageSuccessful", "<i class=\"fas fa-check-circle mr-2\"></i>SoulCleaning is successful!");
        modelAndView.addObject("soulCleaningSessionList", soulCleaningSessionService.getLastCleaningSession());
        modelAndView.addObject("messageFinishProcess", "Congratulations! You have saved <span class=\"text_color_cyan_lighten\">" +
                timeService.getNumberOfSecondForSoulCleaningSession() + " seconds</span> from your life. You are now one step closer to reaching your goal. " +
                "I'm looking forward to our next session!");
        if (option.equals("")){
            modelAndView.addObject("tagChosen", "No Tag");
        } else {
            modelAndView.addObject("tagChosen", option);
        }

        return modelAndView;

    }

    @GetMapping("myCleaningSessions")
    public ModelAndView showMyCleaningSessionsPage(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber) {

        ModelAndView modelAndView = new ModelAndView("my-cleaning-sessions");
        modelAndView.addObject("allCleaningSessions", soulCleaningSessionService.getAllByPage(pageNumber));
        modelAndView.addObject("prevPage", "http://localhost:8080/myCleaningSessions?pageNumber=" + (pageNumber - 1));
        modelAndView.addObject("nextPage", "http://localhost:8080/myCleaningSessions?pageNumber=" + (pageNumber + 1));

        return modelAndView;
    }

    @GetMapping("dashboard")
    public ModelAndView showIndexPage() {
        return new ModelAndView("index");
    }

    @PostMapping("myCleaningSessions/delete-soulcleaningsession")
    @ResponseBody
    public String deleteSoulCleaningSession(@RequestParam("id") int soulCleaningSessionId) {
        soulCleaningSessionService.deleteSoulCleaningSession(soulCleaningSessionId);

        return "ok";
    }

    @GetMapping("getSessionsForAjax")
    @ResponseBody
    public List<SoulCleaningSession> getSessionsForAjax() {
        return soulCleaningSessionService.getAllCleaningSessions();
    }

    @GetMapping("test")
    @ResponseBody
    public void test() {

    }
}
