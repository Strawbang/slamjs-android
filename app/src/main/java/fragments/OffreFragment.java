package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.btssio.slam.slamjs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.OffreAdapter;
import objects.Offre;
import repository.EventRepository;

/**
 * Created by BOUGOUFFA on 20/04/2017.
 */
public class OffreFragment extends Fragment implements AdapterView.OnItemClickListener {
    public  String SERVER_URL = null;
    private JSONObject jsonResponse;
    private ArrayList<Offre> items;
    private ListView lv;
    private EventRepository eventRepo;
    private String leEvent;
    private String url;

    public OffreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offres, container, false);

        eventRepo = new EventRepository(getActivity().getApplicationContext());
        url = eventRepo.getUrl().toString();

        lv = (ListView) v.findViewById(R.id.list);
        items = new ArrayList<Offre>();
        lv.setOnItemClickListener(this);
        return v;
    }

    public void onStart() {
        super.onStart();
        // Envoi d'une requete dans la file d'attente
        sendRequest();
    }

    //Méthode pour interoger le serveur par http
    private void sendRequest() {
        // Creation de l'objet Flux lie a l'utilisateur avec passage du contexte
        eventRepo = new EventRepository(getActivity().getApplicationContext());

        // Verification de l'existence d'un flux
        if (eventRepo.isEventConfigured()) {
            leEvent = eventRepo.getEvent();
        } else {
            Toast.makeText(getActivity(), "Error id ListView", Toast.LENGTH_SHORT).show();
        }

        SERVER_URL = url + "/android/event/" + leEvent + "/offre";

        StringRequest stringRequest = new StringRequest(SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("reponse", "" + response);
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), " " + error, Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void parseJSON(String response) {
        try {
            jsonResponse = new JSONObject(response);
            // Création du tableau général à partir d'un JSONObject
            JSONArray jsonArray = jsonResponse.getJSONArray("offres");
            Offre currentOffre = null;

            // Pour chaque élément du tableau
            for (int i = 0; i < jsonArray.length(); i++) {
                currentOffre = new Offre(null, null, null);

                // Création d'un tableau élément à  partir d'un JSONObject
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Récupération à partir d'un JSONObject nommé
                JSONObject fields  = jsonObj.getJSONObject("_role");

                // Récupération de l'item qui nous intéresse
                String id = jsonObj.getString("_id");
                String nom = fields.getString("nom");
                String nbRoles = jsonObj.getString("nbRoles");

                currentOffre.setId(id);
                currentOffre.setNom(nom);
                currentOffre.setNbRoles(nbRoles);

                // Ajout dans l'ArrayList
                items.add(currentOffre);
            }

            ArrayAdapter<Offre> objAdapter = new OffreAdapter(getActivity(), R.layout.row_offre, items);
            lv.setAdapter(objAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        eventRepo = new EventRepository(getActivity().getApplicationContext());
        //Innitialise le repository pour enregistrer au seins de l'application l'id de
        // l'evenement choisis pour le recuperer dans un autre fragment
        String leOffre = items.get(position).getId().toString();
        eventRepo.setOffre(leOffre);

        Fragment fragment = new DemandeFragment();
        //"Controlleur" de fragment qui change le fragment choisis
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
    }
}
