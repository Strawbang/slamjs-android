package repository;

import android.content.Context;

/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public abstract class Repository {
    protected static Context context = null;

    // Constructeur avec en paramètre le contexte de l'application
    public Repository(Context context) {
        if (Repository.context == null) {
            Repository.context = context;
        }
    }
}