package repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public class EventRepository extends Repository {
    // Constructeur
    public EventRepository(Context context) {
        super(context);
    }

    // Enregistre l event dans les SharedPreferences
    public void setEvent(String event) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.putString("EVENT",event);
        prefsEditor.commit();
    }

    // Enregistre l offre dans les SharedPreferences
    public void setOffre(String offre) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.putString("OFFRE",offre);
        prefsEditor.commit();
    }

    // Enregistre l url dans les SharedPreferences
    public void setUrl(String url) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.putString("URL",url);
        prefsEditor.commit();
    }

    // Supprime l'évènement
    public void unsetEvent() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.remove("EVENT");
        prefsEditor.commit();
    }

    // Supprime l'offre
    public void unsetOffre() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.remove("OFFRE");
        prefsEditor.commit();
    }

    // Indique si un event est configure ou non
    public boolean isEventConfigured() {
        EventRepository eventRepo = new EventRepository(Repository.context);
        String event = eventRepo.getEvent();

        if (event.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    // Indique si une offre est configure ou non
    public boolean isOffreConfigured() {
        EventRepository eventRepo = new EventRepository(Repository.context);
        String offre = eventRepo.getOffre();

        if (offre.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    // Recupere l'event de l'utilisateur
    public String getEvent()	{
        SharedPreferences app = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        return app.getString("EVENT", "");
    }

    // Recupere l'offre de l'utilisateur
    public String getOffre()	{
        SharedPreferences app = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        return app.getString("OFFRE", "");
    }

    // Recupere l'url de l'utilisateur
    public String getUrl()	{
        SharedPreferences app = PreferenceManager.getDefaultSharedPreferences(Repository.context);
        return app.getString("URL", "");
    }
}
