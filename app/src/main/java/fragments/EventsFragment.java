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

import adapters.EventAdapter;
import objects.Event;
import repository.EventRepository;

/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public class EventsFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static String SERVER_URL ;
    private JSONObject jsonResponse;
    private ArrayList<Event> items;
    private ListView lv;

    private EventRepository eventRepo;
    private String leEvent;
    private String url;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_events, container, false);

        eventRepo = new EventRepository(getActivity().getApplicationContext());
        url = eventRepo.getUrl().toString();

        lv = (ListView) v.findViewById(R.id.list);
        items = new ArrayList<Event>();
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
        SERVER_URL = url + "/android/events";

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
                        Log.e("Error : ", ""+error);
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
            JSONArray jsonArray = jsonResponse.getJSONArray("events");
            Event currentEvent = null;

            // Pour chaque élément du tableau
            for (int i = 0; i < jsonArray.length(); i++) {
                currentEvent = new Event(null, null, null, null, null);

                // Création d'un tableau élément à  partir d'un JSONObject
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Récupération de l'item qui nous intéresse
                String id = jsonObj.getString("_id");
                String nom = jsonObj.getString("nom");
                String type = jsonObj.getString("type");
                String date = jsonObj.getString("date");
                String nombreJours = jsonObj.getString("nombreJours");

                currentEvent.setId(id);
                currentEvent.setNom(nom);
                currentEvent.setType(type);
                currentEvent.setDate(date);
                currentEvent.setNombreJours(nombreJours);

                // Ajout dans l'ArrayList
                items.add(currentEvent);
            }

            ArrayAdapter<Event> objAdapter = new EventAdapter(getActivity(), R.layout.row, items);
            lv.setAdapter(objAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        //Innitialise le repository pour enregistrer au seins de l'application l'id de
        // l'evenement choisis pour le recuperer dans un autre fragment
        leEvent = items.get(position).getId().toString();
        eventRepo.setEvent(leEvent);

        //"Controlleur" de fragment qui change le fragment choisis
        Fragment fragment = new OffreFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
    }
}
