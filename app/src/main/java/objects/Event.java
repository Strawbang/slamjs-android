package objects;

/**
 * Created by BOUGOUFFA on 20/04/2017.
 */
public class Event {
    private String id;
    private String nom;
    private String type;
    private String date;
    private String nombreJours;


    public Event(String id, String nom, String type, String date, String nombreJours){
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.date = date;
        this.nombreJours = nombreJours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNombreJours() {
        return nombreJours;
    }

    public void setNombreJours(String nombreJours) {
        this.nombreJours = nombreJours;
    }
}
