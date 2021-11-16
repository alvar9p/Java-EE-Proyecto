package sga.cliente.asociasiones;

import java.util.List;
import javax.persistence.*;
import sga.domain.Persona;
import sga.domain.Usuario;
import org.apache.logging.log4j.*;

public class ClienteAsociasionesJPA {
    
    static Logger log = LogManager.getRootLogger();
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        
        List<Persona> personas = em.createNamedQuery("Persona.findAll").getResultList();
        
        // Cerramos la conexión
        em.close();
        
        // Imprimir los objetos del tipo persona
        for(Persona persona: personas){
            log.debug("Persona: " + personas);
            // Recuperamos los usuarios de cada persona
            for(Usuario usuario: persona.getUsuarioList()){
                log.debug("Usuario: " + usuario);
            }
        }
        
        
    }
}
