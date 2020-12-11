package ro.calin.SoulCleaner.database;

import javax.persistence.*;

@Entity
@Table(name = "cleaning_session")
public class SoulCleaningSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String site;

    private int page_count;

    private int picture_count;

    private long seconds_count;

    private String tag_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getPicture_count() {
        return picture_count;
    }

    public void setPicture_count(int picture_count) {
        this.picture_count = picture_count;
    }

    public long getSeconds_count() {
        return seconds_count;
    }

    public void setSeconds_count(long seconds_count) {
        this.seconds_count = seconds_count;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
