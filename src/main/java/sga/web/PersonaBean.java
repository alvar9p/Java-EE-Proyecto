package sga.web;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.*;
import org.primefaces.event.RowEditEvent;
import sga.domain.Persona;
import sga.servicio.PersonaService;

@Named("personaBean")
@RequestScoped
public class PersonaBean {
    
    Logger log = LogManager.getRootLogger();
    
    @Inject
    private PersonaService personaService;
    
    private Persona personaSeleccionada;
    
    List<Persona> personas;
    
    public PersonaBean(){
        log.debug("Iniciando el objeto PersonaBean");
    }

    @PostConstruct
    //Inicializa cada elemento de personaBean
    public void inicializar(){
        //Iniciamos las variables
        this.personas = personaService.listarPersonas();
        log.debug("personas recuperadas en ManagedBean: " + this.personas);
        this.personaSeleccionada = new Persona();
    }
    
    //Recuperar cualquer persona que se haya modificado
    public void editListener(RowEditEvent event){
        Persona persona = (Persona) event.getObject();
        personaService.modificarPersona(persona);
    }

    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }
    
    //Todo gira en torno a la personaSeleccionada
    public void agregarPersona(){
        this.personaService.registrarPersona(personaSeleccionada);
        this.personas.add(personaSeleccionada);
        //Reestablecemos el valor
        this.personaSeleccionada = null;
    }
    
    public void eliminarPersona(){
        this.personaService.eliminarPersona(personaSeleccionada);
        this.personas.remove(this.personaSeleccionada);
        this.personaSeleccionada = null;
    }
    
    public void reiniciarPersonaSeleccionada(){
        this.personaSeleccionada = new Persona();
    }
    
    
}
