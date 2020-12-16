package ro.calin.SoulCleaner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.SoulCleaner.database.SoulCleaningTime;

import java.time.Duration;

@Service
public class SoulCleaningTimeService {

    @Autowired
    SoulCleaningTime soulCleaningTime;

    public long getNumberOfSecondForSoulCleaningSession() {

        return Duration.between(soulCleaningTime.getInitialTime(), soulCleaningTime.getFinalTime()).getSeconds();
    }

}
