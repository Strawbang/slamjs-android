package objects;

/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public class Offre {
    private String id;
    private String nom;
    private String nbRoles;


    public Offre(String id, String nom, String nbRoles){
        this.id = id;
        this.nom = nom;
        this.nbRoles = nbRoles;
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

    public String getNbRoles() {
        return nbRoles;
    }

    public void setNbRoles(String nbRoles) {
        this.nbRoles = nbRoles;
    }
}
