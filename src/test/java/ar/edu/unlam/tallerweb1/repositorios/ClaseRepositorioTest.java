package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.modelo.Actividad;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.Periodo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;

public class ClaseRepositorioTest extends SpringTest {

    @Autowired
    private ClaseRepositorio claseRepositorio;

    Periodo periodoDeLaACtividad = new Periodo(LocalDate.now(), LocalDate.now().plusDays(5));

    public ClaseRepositorioTest() throws Exception {
    }

    @Test
    @Rollback @Transactional
    public void getClasesDelMesActualTraeLasClasesDeEseMes() {
        Clase claseDiaLunes = new Clase();
    }

    private Actividad crearActividadConInicioYFin() {
        return new Actividad(
                "Entrenamiento",
                Tipo.CROSSFIT,
                4000F,
                Frecuencia.CON_INICIO_Y_FIN,
                periodoDeLaACtividad
        );
    }
}