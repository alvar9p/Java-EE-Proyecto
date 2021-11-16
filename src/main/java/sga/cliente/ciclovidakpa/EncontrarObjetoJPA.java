package sga.cliente.ciclovidakpa;

import javax.persistence.*;
import sga.domain.Persona;
import org.apache.logging.log4j.*;

public class EncontrarObjetoJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

        // Inicia la transacción
        // Paso 1. Iniciar la transacción
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        // Paso 2. Ejecutar SQL de tipo select
        // Clase que esté definida en persistence, luego el PK (ID) que queremos encontrar
        Persona persona1 = em.find(Persona.class, 1);
  
        // Paso 3. Termina la transacción
        tx.commit();
        
        // Objeto en estado detached
        log.debug("Objeto recuperado: " + persona1);

        // Cerramos el entity manager
        em.close();

    }
}
