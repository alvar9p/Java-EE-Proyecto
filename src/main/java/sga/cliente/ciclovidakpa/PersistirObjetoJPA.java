package sga.cliente.ciclovidakpa;

import javax.persistence.*;
import sga.domain.Persona;
import org.apache.logging.log4j.*;

public class PersistirObjetoJPA {
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        // Inicia la transacción
        
        // Paso 1. Crea nuevo objeto
        // Objeto en estado transitivo
        Persona persona1 = new Persona("Pedro", "Luna", "pluma@mail.com", "98765432");
        
        // Paso 2. Inicia la transacción
        tx.begin();
        
        // Paso 3. Ejecuta SQL
        em.persist(persona1);
        
        // Paso 4. commit/rollback
        tx.commit();
        
        // Objeto en estado detached
        log.debug("Objeto persistido - estado detached: " + persona1);
        
        // Cerramos el entity manager
        em.close();
        
    }
}
