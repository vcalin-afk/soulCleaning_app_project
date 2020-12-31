package ro.calin.SoulCleaner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.SoulCleaner.database.SoulCleaningTime;

import java.time.Duration;

@Service
public class SoulCleaningTimeService {

    @Autowired
    SoulCleaningTime soulCleaningTime;

    private Logger LOG = LoggerFactory.getLogger(SoulCleaningTimeService.class);

    public long getNumberOfSecondForSoulCleaningSession() {

        LOG.info("Intorc timpul petrecut analizand pozele in timpul sesiunii SoulCleaning.");
        return Duration.between(soulCleaningTime.getInitialTime(), soulCleaningTime.getFinalTime()).getSeconds();

    }

    public void setSoulCleaningTime(SoulCleaningTime soulCleaningTime) {
        this.soulCleaningTime = soulCleaningTime;
    }
}
