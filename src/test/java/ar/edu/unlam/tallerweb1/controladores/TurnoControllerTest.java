package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

public class TurnoControllerTest {

    ClaseService claseService = mock(ClaseService.class);
    TurnoController turnoController = new TurnoController(claseService);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    void test() {



    }
}