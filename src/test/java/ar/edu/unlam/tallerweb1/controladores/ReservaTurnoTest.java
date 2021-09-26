package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;

public class ReservaTurnoTest {

    ClaseService claseService = mock(ClaseService.class);
    TurnoController turnoController = new TurnoController(claseService);
    private Clase clase = new Clase();


    @Test
    public void testQueLaClaseExista(){
        clase.setId(1L);
        giveQueLaClaseExiste(clase);
        Clase claseBuscada= whenBuscoLaClase(clase);
        theEncuentroClase(claseBuscada);
    }

    private void giveQueLaClaseExiste(Clase clase) {
    }

    private Clase whenBuscoLaClase(Clase clase) {
        return null;
    }

    private void theEncuentroClase(Clase clase) {

    }
}
