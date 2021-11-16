package sga.cliente.cascade;

import javax.persistence.*;
import sga.domain.Persona;
import sga.domain.Usuario;
import org.apache.logging.log4j.*;

public class PersistenciaCascadaJPA {
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        // Paso 1. Crear nuevo objeto
        // Objeto en estado transitivo
        Persona persona1 = new Persona("Hugo", "Hernandez", "hhernandez@mail.com", "65894217");
        
        // Crear objeto usuario (tiene dependencia con el objeto persona)
        Usuario usuario1 = new Usuario("hhernandez", "123", persona1);
        
        // Paso 3. Persistimos el obj usuario
        em.persist(usuario1);
        
        // Paso 4. Commit transacción
        tx.commit();
        
        // Objetos en estado detached
        log.debug("Objeto persistido: " + persona1);
        log.debug("Objeto persistido: " + usuario1);
        
        em.close();
    }
    
}
