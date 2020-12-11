package ro.calin.SoulCleaner.database;

import org.springframework.stereotype.Component;

@Component
public class SeleniumConnection {

    private int countPictures;

    public int getCountPictures() {
        return countPictures;
    }

    public void setCountPictures(int countPictures) {
        this.countPictures = countPictures;
    }
}
