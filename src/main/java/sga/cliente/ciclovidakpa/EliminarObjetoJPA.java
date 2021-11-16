package sga.cliente.ciclovidakpa;

import javax.persistence.*;
import sga.domain.Persona;
import org.apache.logging.log4j.*;

public class EliminarObjetoJPA {
        static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

        // Inicia la transacción
        // Paso 1. Iniciar la transacción
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        // Paso 2. Ejecutamos SQL del tipo select
        Persona persona1 = em.find(Persona.class, 16);
        
        // Si estamos en la misma transacción
        // em.remove(16); v 
        
        // Paso 3. Termina la transacción 1
        tx.commit();
        
        // Objeto en estado detached
        log.debug("Objeto encontrado: " + persona1);
        
        // Paso 4. Inicia transacción
        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        
        // Paso 5. Ejecuta SQL que es un delete
        // Primero uptade, luego delete
        em.remove(em.merge(persona1));
        
        // Paso 6. Termina transacción 2
        tx2.commit();

        // Objeto en estado detached ya eliminado de la BD
        log.debug("Objeto eliminado: " + persona1);
        
        // Cerramos el entity manager
        em.close();
    }
}
