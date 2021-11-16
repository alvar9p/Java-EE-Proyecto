package sga.cliente.ciclovidakpa;

import javax.persistence.*;
import sga.domain.Persona;
import org.apache.logging.log4j.*;

public class ActualizarObjetoSesionLarga {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

        // Inicia la transacción
        // Paso 1. Iniciar la transacción
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        // Paso 2. Ejecutamos SQL de tipo select
        Persona persona1 = em.find(Persona.class, 1);
        
        log.debug("Objeto encontrado: " + persona1);
        
        // Paso 3. setValue (nuevoValor)
        persona1.setEmail("jjuarez@mail.com");
        
        persona1.setEmail("j.juarez@mail.com");
        
        // Paso 4. Termina la transacción
        tx.commit();
        
        // Objeto en estado detached
        log.debug("Objeto modificado: " + persona1);

        // Cerramos el entity manager
        em.close();
    }
}
