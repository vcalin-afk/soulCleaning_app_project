package ro.calin.SoulCleaner.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SoulCleaningSessionDAO extends CrudRepository<SoulCleaningSession, Integer>, PagingAndSortingRepository<SoulCleaningSession, Integer> {

    List<SoulCleaningSession> findByOrderByIdDesc();

}
