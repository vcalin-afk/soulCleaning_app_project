package ro.calin.SoulCleaner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.SoulCleaner.database.Time;

import java.time.Duration;

@Service
public class TimeService {

    @Autowired
    Time time;

    public long getNumberOfSecondForSoulCleaningSession() {
        return Duration.between(time.getInitialTime(), time.getFinalTime()).getSeconds();
    }
}
