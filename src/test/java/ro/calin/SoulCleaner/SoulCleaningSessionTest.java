package ro.calin.SoulCleaner;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelExtensionsKt;
import ro.calin.SoulCleaner.database.SoulCleaningCount;
import ro.calin.SoulCleaner.database.SoulCleaningSession;
import ro.calin.SoulCleaner.database.SoulCleaningSessionDAO;
import ro.calin.SoulCleaner.database.SoulCleaningTime;
import ro.calin.SoulCleaner.service.SoulCleaningSessionService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

class SoulCleaningSessionTest {

    @Test
    void testConnectionToGeckodriver() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();
        soulCleaningSessionService.connect();

    }

    @Test
    void testParsingWhenStartCleaningSessionCalledWithOptionIshtar() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningCount soulCleaningCount = Mockito.mock(SoulCleaningCount.class);
        SoulCleaningTime soulCleaningTime = Mockito.mock(SoulCleaningTime.class);

        soulCleaningSessionService.setSoulCleaningCount(soulCleaningCount);
        soulCleaningSessionService.setSoulCleaningTime(soulCleaningTime);
        soulCleaningSessionService.connect();

        soulCleaningSessionService.startCleaningSession("ishtar_(fate)_(all)", 2);

    }

    @Test
    void testParsingWhenStartCleaningSessionCalledWithBlankOption() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningCount soulCleaningCount = Mockito.mock(SoulCleaningCount.class);
        SoulCleaningTime soulCleaningTime = Mockito.mock(SoulCleaningTime.class);

        soulCleaningSessionService.setSoulCleaningCount(soulCleaningCount);
        soulCleaningSessionService.setSoulCleaningTime(soulCleaningTime);
        soulCleaningSessionService.connect();

        soulCleaningSessionService.startCleaningSession("", 1);

    }

    @Test
    void testPicturesCountWhenStartCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningCount soulCleaningCount = Mockito.mock(SoulCleaningCount.class);
        SoulCleaningTime soulCleaningTime = Mockito.mock(SoulCleaningTime.class);

        soulCleaningSessionService.setSoulCleaningCount(soulCleaningCount);
        soulCleaningSessionService.setSoulCleaningTime(soulCleaningTime);
        soulCleaningSessionService.connect();

        soulCleaningSessionService.startCleaningSession("ishtar_(fate)_(all)", 1);

        Mockito.verify(soulCleaningCount).setCountPictures(19);

    }

    @Test
    void testSetCountPicturesCalledAtLeastOnceWhenStartCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningCount soulCleaningCount = Mockito.mock(SoulCleaningCount.class);
        SoulCleaningTime soulCleaningTime = Mockito.mock(SoulCleaningTime.class);

        soulCleaningSessionService.setSoulCleaningCount(soulCleaningCount);
        soulCleaningSessionService.setSoulCleaningTime(soulCleaningTime);
        soulCleaningSessionService.connect();

        soulCleaningSessionService.startCleaningSession("ishtar_(fate)_(all)", 1);

        Mockito.verify(soulCleaningCount, Mockito.atLeast(1)).setCountPictures(19);

    }

    @Test
    void testSetInitialTimeCalledAtLeastOnceWhenStartCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningCount soulCleaningCount = Mockito.mock(SoulCleaningCount.class);
        SoulCleaningTime soulCleaningTime = Mockito.mock(SoulCleaningTime.class);

        soulCleaningSessionService.setSoulCleaningCount(soulCleaningCount);
        soulCleaningSessionService.setSoulCleaningTime(soulCleaningTime);
        soulCleaningSessionService.connect();

        soulCleaningSessionService.startCleaningSession("ishtar_(fate)_(all)", 1);

        Mockito.verify(soulCleaningTime, Mockito.atLeast(1)).setInitialTime(LocalTime.now());

    }

    @Test
    void testSetFinalTimeCalledAtLeastOnceWhenStartCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningCount soulCleaningCount = Mockito.mock(SoulCleaningCount.class);
        SoulCleaningTime soulCleaningTime = Mockito.mock(SoulCleaningTime.class);

        soulCleaningSessionService.setSoulCleaningCount(soulCleaningCount);
        soulCleaningSessionService.setSoulCleaningTime(soulCleaningTime);
        soulCleaningSessionService.connect();

        soulCleaningSessionService.startCleaningSession("ishtar_(fate)_(all)", 1);

        Mockito.verify(soulCleaningTime, Mockito.atLeast(1)).setFinalTime(LocalTime.now());

    }

    @Test
    void testParsingWhenSaveCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        soulCleaningSessionService.saveCleaningSession("ishtar_(fate)_(all)", 1, 39, 19);

    }

    @Test
    void testParsingWhenSaveCleaningSessionCalledWithBlankOption() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        soulCleaningSessionService.saveCleaningSession("", 1, 39, 19);

    }

    @Test
    void testSavingInDatabaseAtLeastOnceWhenSaveCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSession soulCleaningSession = new SoulCleaningSession();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        soulCleaningSessionService.saveCleaningSession("", 1, 39, 19);

        Mockito.verify(soulCleaningSessionDAO, Mockito.atLeast(1)).save(Mockito.any(SoulCleaningSession.class));

    }

    @Test
    void testParsingWhenGetLastCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        List<SoulCleaningSession> soulCleaningSessions = new ArrayList<>();
        SoulCleaningSession soulCleaningSession = new SoulCleaningSession();
        soulCleaningSession.setId(81);
        soulCleaningSessions.add(soulCleaningSession);
        SoulCleaningSession soulCleaningSession1 = new SoulCleaningSession();
        soulCleaningSession1.setId(80);
        soulCleaningSessions.add(soulCleaningSession1);

        Mockito.when(soulCleaningSessionDAO.findByOrderByIdDesc()).thenReturn(soulCleaningSessions);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        List<SoulCleaningSession> lastCleaningSession = soulCleaningSessionService.getLastCleaningSession();

        assertEquals(lastCleaningSession.get(0).getId(), 81);

    }

    @Test
    void testReturnsListWithOnlyOneElementWhenGetLastCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        List<SoulCleaningSession> soulCleaningSessions = new ArrayList<>();
        SoulCleaningSession soulCleaningSession = new SoulCleaningSession();
        soulCleaningSession.setId(81);
        soulCleaningSessions.add(soulCleaningSession);
        SoulCleaningSession soulCleaningSession1 = new SoulCleaningSession();
        soulCleaningSession1.setId(80);
        soulCleaningSessions.add(soulCleaningSession1);

        Mockito.when(soulCleaningSessionDAO.findByOrderByIdDesc()).thenReturn(soulCleaningSessions);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        List<SoulCleaningSession> lastCleaningSession = soulCleaningSessionService.getLastCleaningSession();

        assertEquals(lastCleaningSession.size(), 1);

    }

    @Test
    void testInteractionAtLeastOnceWhenDeleteSoulCleaningSessionCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        soulCleaningSessionService.deleteSoulCleaningSession(1);

        Mockito.verify(soulCleaningSessionDAO, Mockito.atLeast(1)).deleteById(1);

    }

    @Test
    void testInteractionAtLeastOnceWhenFindDescCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        soulCleaningSessionService.findDesc();

        Mockito.verify(soulCleaningSessionDAO, Mockito.atLeast(1)).findByOrderByIdDesc();

    }

    @Test
    void testSizeOfPageWhenGetAllByPageCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        Page<SoulCleaningSession> testPageForDAO = new Page<SoulCleaningSession>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 6;
            }

            @Override
            public <U> Page<U> map(Function<? super SoulCleaningSession, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<SoulCleaningSession> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<SoulCleaningSession> iterator() {
                return null;
            }
        };


        Mockito.when(soulCleaningSessionDAO.findAll(PageRequest.of(1, 6))).thenReturn(testPageForDAO);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        Page<SoulCleaningSession> testPage2 = soulCleaningSessionService.getAllByPage(1);

        assertEquals(testPage2.getTotalElements(), 6);

    }

    @Test
    void testInteractionAtLeastOnceWhenGetAllByPageCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        soulCleaningSessionService.getAllByPage(1);

        Mockito.verify(soulCleaningSessionDAO, Mockito.atLeast(1)).findAll(PageRequest.of(1, 6));

    }

    @Test
    void testSizeOfPageWhenGetAllByPageDescCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        Page<SoulCleaningSession> testPageForDAO = new Page<SoulCleaningSession>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 6;
            }

            @Override
            public <U> Page<U> map(Function<? super SoulCleaningSession, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<SoulCleaningSession> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<SoulCleaningSession> iterator() {
                return null;
            }
        };


        Mockito.when(soulCleaningSessionDAO.findByOrderByIdDesc(PageRequest.of(1, 6))).thenReturn(testPageForDAO);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        Page<SoulCleaningSession> testPage2 = soulCleaningSessionService.getAllByPageDesc(1);

        assertEquals(testPage2.getTotalElements(), 6);

    }

    @Test
    void testInteractionAtLeastOnceWhenGetAllByPageDescCalled() {

        SoulCleaningSessionService soulCleaningSessionService = new SoulCleaningSessionService();

        SoulCleaningSessionDAO soulCleaningSessionDAO = Mockito.mock(SoulCleaningSessionDAO.class);

        soulCleaningSessionService.setSoulCleaningSessionDAO(soulCleaningSessionDAO);

        soulCleaningSessionService.getAllByPageDesc(1);

        Mockito.verify(soulCleaningSessionDAO, Mockito.atLeast(1)).findByOrderByIdDesc(PageRequest.of(1, 6));

    }


}
