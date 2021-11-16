package sga.cliente.ciclovidakpa;

import javax.persistence.*;
import sga.domain.Persona;
import org.apache.logging.log4j.*;

public class ActualizarObjetoJPA {
        static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

        // Inicia la transacción
        // Paso 1. Iniciar la transacción
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        // Paso 2. Ejecutar SQL del tipo select
        // El ID debe existir en la BBDD
        Persona persona1 = em.find(Persona.class, 1);
        
        // Paso 3. Termina la transacción 1
        tx.commit();
        
        // Objeto en estado detached
        log.debug("Objeto recuperado:" + persona1);
        
        // Paso 4. setValue (nuevoValor)
        persona1.setApellido("Juarez");
        
        // Paso 5. Inicia transacción 2
        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        
        // Paso 6. Modificamos el objeto
        em.merge(persona1);
        
        // Paso 7. Termina la transacción 2
        tx2.commit();
        
        // Objeto en estado detached ya modificado
        log.debug("Objeto recuperado: " + persona1);

        // Cerramos el entity manager
        em.close();

    }
}
