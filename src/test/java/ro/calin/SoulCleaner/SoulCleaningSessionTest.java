package ro.calin.SoulCleaner;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import ro.calin.SoulCleaner.database.SoulCleaningSession;
import ro.calin.SoulCleaner.database.SoulCleaningSessionDAO;
import ro.calin.SoulCleaner.service.SoulCleaningSessionService;

import java.util.List;

public class SoulCleaningSessionTest {

    @Test
    void testPageableDatabase() {
        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();
        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        Page<SoulCleaningSession> firstPage = soulCleaningSessionService.getAllByPage(0);
    }

    @Test
    void testingFindDesc() {
        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();
        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        List<SoulCleaningSession> soulCleaningSessions = soulCleaningSessionService.findDesc();
        for (SoulCleaningSession soulCleaningSession: soulCleaningSessions) {
            System.out.println(soulCleaningSession.getId());
        }

    }
}
