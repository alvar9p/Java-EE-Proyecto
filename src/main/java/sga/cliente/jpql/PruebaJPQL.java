package sga.cliente.jpql;

import java.util.Iterator;
import java.util.List;
import javax.persistence.*;
import org.apache.logging.log4j.*;
import sga.domain.Persona;
import sga.domain.Usuario;

public class PruebaJPQL {
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        String jpql = null;
        Query q = null;
        List<Persona> personas = null;
        Persona persona = null;
        Iterator iter = null;
        Object[] tupla = null;
        List<String> nombres = null;
        List<Usuario> usuarios = null;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        
        //1. Consulta de todos los obj del tipo Persona
        log.debug("\n1. Consulta de todas las Personas");
        jpql = "select p from Persona p";
        personas = em.createQuery(jpql).getResultList();
        // mostrarPersonas(personas);
        
        
        //2. Consulta de la Persona con id = 1
        log.debug("\n2. Consulta de la persona con id = 1");
        jpql = "select p from Persona p where p.idPersona = 1";
        persona = (Persona) em.createQuery(jpql).getSingleResult();
        //log.debug(persona);
        
        
        //3. Consulta de la Persona por nombre
        jpql = "select p from Persona p where p.nombre = 'Karla'";
        personas = em.createQuery(jpql).getResultList();
        //mostrarPersonas(personas);
        
        
        //4. Consulta de datos individuales, se crea un arreglo (tupla) del tipo Object de 3 columnas
        // Solo para ver ciertos parámetros de la BBDD
        log.debug("\n4. Consulta de datos individuales, se crea un array del tipo obj de 3 columnas");
        jpql = "select p.nombre as Nombre, p.apellido as Apellido, p.email as Email from Persona p";
        iter = em.createQuery(jpql).getResultList().iterator();
        while(iter.hasNext()){
            tupla = (Object[]) iter.next();
            String nombre = (String) tupla[0];
            String apellido = (String) tupla[1];
            String email = (String) tupla[2];
            //log.debug("nombre: " + nombre + ", apellido: " + apellido + ", email: " + email);
        }
        
        
        //5. Obtiene el objeto Persona y el id, se crea un arreglo del tipo Object con 2 columnas
        log.debug("\n Obtiene el objeto Persona y el id, se crea un arreglo del tipo Object con 2 columnas");
        jpql = "select p, p.idPersona from Persona p";
        iter = em.createQuery(jpql).getResultList().iterator();
        while(iter.hasNext()){
            tupla = (Object[]) iter.next();
            persona = (Persona)tupla[0];
            int idPersona = (int) tupla[1];
            //log.debug("Objeto persona: " + persona);
            //log.debug("Id persona: " + idPersona);
        }
        
        
        //6. Consulta de todas las personas
        System.out.println("\n6. Consulta de todas las personas");
        jpql = "select new sga.domain.Persona( p.idPersona ) from Persona p";
        personas = em.createQuery(jpql).getResultList();
        //mostrarPersonas(personas);
        
        
        //7. Regresa el valor minimo y máximo del idPersona (scaler result)
        // Para ver el rango de id existentes
        System.out.println("\n7. Regresa el valor minimo y máximo del idPersona (scaler result)");
        jpql = "select min(p.idPersona) as MinId, max(p.idPersona) as MaxId, count(p.idPersona) as Contador from Persona p";
        iter = em.createQuery(jpql).getResultList().iterator();
        while(iter.hasNext()){
            tupla = (Object[]) iter.next();
            Integer idMin = (Integer) tupla[0];
            Integer idMax = (Integer) tupla[1];
            Long count = (Long) tupla[2];
            //log.debug("idMin: " + idMin + ", idMax: " + idMax + ", count: " + count);
        }
        
        
        //8. Cuenta los nombres de las personas que son distintos
        log.debug("\n8. Cuenta los nombres de las personas que son distintos");
        jpql = "select count(distinct p.nombre) from Persona p ";
        Long contador = (Long) em.createQuery(jpql).getSingleResult();
        //log.debug("no. de personas con nombre distinto: " + contador);
        
        
        //9. Concatena y convierte a mayúsculas el nombre y el apellido
        log.debug("\n 9. Concatena y convierte a mayúsculas el nombre y el apellido");
        jpql = "select CONCAT(p.nombre, ' ', p.apellido) as Nombre from Persona p";
        nombres = em.createQuery(jpql).getResultList();
        for(String nombreCompleto: nombres){
            //log.debug(nombreCompleto);
        }
        
        
        //10. Obtiene el objeto persona con id igual al parámetro proporcionado
        log.debug("\n 10. Obtiene el objeto persona con id igual al parámetro proporcionado");
        int idPersona = 15;
        jpql = "select p from Persona p where p.idPersona = :id";
        q = em.createQuery(jpql);
        // "id" debe ser igual al :id, luego el valor (idPersona)
        q.setParameter("id", idPersona);
        persona = (Persona) q.getSingleResult();
        //log.debug(persona);
        
        
        //11. Obtiene las personas que contengan una letra a en el nombre, sin importar si es mayúscula o minúscula
        log.debug("\n11. Obtiene las personas que contengan una letra a en el nombre, sin importar si es mayúscula o minúscula");
        jpql = "select p from Persona p where upper(p.nombre) like upper(:parametro)";
        // Entre %%, debido al uso de 'like' en la consulta
        String parametroString = "%a%";
        q = em.createQuery(jpql);
        // el nombre de la variable -> :parametro, luego el parametro de la variable %a%
        q.setParameter("parametro", parametroString);
        personas = q.getResultList();
        //mostrarPersonas(personas);
        
        
        //12. Uso de between
        log.debug("\n 12. Uso de between");
        jpql = "select p from Persona p where p.idPersona between 1 and 2";
        personas = em.createQuery(jpql).getResultList();
        //mostrarPersonas(personas);
        
        
        //13. Uso del ordenamiento
        // Id mayor a 3 en este caso
        log.debug("\n13. Uso del ordenamiento");
        jpql = "select p from Persona p where p.idPersona > 3 order by p.nombre desc, p.apellido desc";
        personas = em.createQuery(jpql).getResultList();
        //mostrarPersonas(personas);
        
        
        //14. Uso de subquery
        // No todas las BD soportan el uso de subqueris
        log.debug("\n14. Uso de subquery");
        jpql = "select p from Persona p where p.idPersona in (select min(p1.idPersona) from Persona p1)";
        personas = em.createQuery(jpql).getResultList();
        // Es un valor único, pero lo vamos a procesar como lista
        //mostrarPersonas(personas);
        
        
        //15. Uso de join con lazy loding
        log.debug("\n15. Uso de join con lazy loding");
        jpql = "select u from Usuario u join u.persona p";
        usuarios = em.createQuery(jpql).getResultList();
        //mostrarUsuarios(usuarios);
        
        
        //16. Uso de left join con eager loading
        log.debug("\n16. Uso de left join con eager loading");
        // fetch aplica el concepto de eager, recupera todos los datos
        jpql = "select u from Usuario u left join fetch u.persona";
        usuarios = em.createQuery(jpql).getResultList();
        mostrarUsuarios(usuarios);
    }

    private static void mostrarPersonas(List<Persona> personas) {
        for(Persona p: personas){
            log.debug(p);
        }
    }

    private static void mostrarUsuarios(List<Usuario> usuarios) {
        for(Usuario u: usuarios){
            log.debug(u);
        }
    }
}
