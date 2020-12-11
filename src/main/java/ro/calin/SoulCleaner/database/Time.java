package ro.calin.SoulCleaner.database;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class Time {

    public LocalTime initialTime;

    public LocalTime finalTime;

    public LocalTime getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(LocalTime initialTime) {
        this.initialTime = initialTime;
    }

    public LocalTime getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(LocalTime finalTime) {
        this.finalTime = finalTime;
    }
}
