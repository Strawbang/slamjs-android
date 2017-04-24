package objects;

/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public class DemandeFiguration {
    private String id;
    private String nomEvent;
    private String typeEvent;
    private String dateEvent;
    private String nombreJoursEvent;
    private String nomRole;
    private String nbRoles;
    private String etat;


    public DemandeFiguration(String id, String nomEvent, String typeEvent, String dateEvent, String nombreJoursEvent, String nomRole, String nbRoles, String etat){
        this.id = id;
        this.nomEvent = nomEvent;
        this.typeEvent = typeEvent;
        this.dateEvent = dateEvent;
        this.nombreJoursEvent = nombreJoursEvent;
        this.nomRole = nomRole;
        this.nbRoles = nbRoles;
        this.etat = etat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getNombreJoursEvent() {
        return nombreJoursEvent;
    }

    public void setNombreJoursEvent(String nombreJoursEvent) {
        this.nombreJoursEvent = nombreJoursEvent;
    }

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    public String getNbRoles() {
        return nbRoles;
    }

    public void setNbRoles(String nbRoles) {
        this.nbRoles = nbRoles;
    }
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
