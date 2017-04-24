package fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
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

import adapters.ListDemandeAdapter;
import objects.DemandeFiguration;
import repository.EventRepository;

import static fragments.EventsFragment.SERVER_URL;
/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public class ListDemandeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private JSONObject jsonResponse;
    private ArrayList<DemandeFiguration> items;
    private ListView lv;
    private EventRepository eventRepo;
    private String url;

    private EditText editText;
    private Button button;

    public ListDemandeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_demande, container, false);

        button = (Button) v.findViewById(R.id.buttonMail);
        button.setOnClickListener(this);
        editText = (EditText) v.findViewById(R.id.editTextMail);

        eventRepo = new EventRepository(getActivity().getApplicationContext());
        url = eventRepo.getUrl().toString();

        lv = (ListView) v.findViewById(R.id.listPostulation);
        items = new ArrayList<DemandeFiguration>();
        lv.setOnItemClickListener(this);

        return v;
    }

    private void sendRequest(String mail) {
        String SERVER_URL = url + "/android/postu/" + mail;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("reponse", "" + response);
                        if(response == "Aucune postulation trouvé"){
                            Toast.makeText(getActivity(), " " + response, Toast.LENGTH_SHORT).show();
                        }
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void parseJSON(String response) {
        try {
            jsonResponse = new JSONObject(response);
            // Création du tableau général à partir d'un JSONObject
            JSONArray jsonArray = jsonResponse.getJSONArray("postulations");
            DemandeFiguration currentDemande = null;

            // Pour chaque élément du tableau
            for (int i = 0; i < jsonArray.length(); i++) {
                currentDemande = new DemandeFiguration(null, null, null, null, null, null, null, null);

                // Création d'un tableau élément à  partir d'un JSONObject
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Récupération à partir d'un JSONObject nommé
                JSONObject offre  = jsonObj.getJSONObject("_offre");
                JSONObject event  = offre.getJSONObject("_event");
                JSONObject role  = offre.getJSONObject("_role");

                // Récupération de l'item qui nous intéresse
                String id = jsonObj.getString("_id");
                String etat = jsonObj.getString("etat");

                String nbRoles = offre.getString("nbRoles");

                String nomEvent = event.getString("nom");
                String type = event.getString("type");
                String date = event.getString("date");
                String nombreJours = event.getString("nombreJours");

                String nomRole = role.getString("nom");

                currentDemande.setId(id);
                currentDemande.setEtat(etat);
                currentDemande.setNomEvent(nomEvent);
                currentDemande.setTypeEvent(type);
                currentDemande.setDateEvent(date);
                currentDemande.setNombreJoursEvent(nombreJours);
                currentDemande.setNomRole(nomRole);
                currentDemande.setNbRoles(nbRoles);

                // Ajout dans l'ArrayList
                items.add(currentDemande);
            }

            ArrayAdapter<DemandeFiguration> objAdapter = new ListDemandeAdapter(getActivity(), R.layout.row_list_demande, items);
            lv.setAdapter(objAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if(button == v){
            String mail = editText.getText().toString();
            sendRequest(mail);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        //Lorsque l'on clic sur un élément de la list une AlertDialog apparais
        new AlertDialog.Builder(getActivity())
                .setTitle("Supprimer la demande")
                .setMessage("Etes-vous sûr de vouloir supprimer la demande pour " + items.get(i).getNomRole()
                        +" de l'évènement " + items.get(i).getNomEvent() + " ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    //Confirmation de la suppression
                    public void onClick(DialogInterface dialog, int which) {
                        SERVER_URL = url + "/android/postulation/" + items.get(i).getId() + "/delete";

                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, SERVER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("reponse", "" + response);
                                        //Une fois la suppression effectué on supprime l'élément de la collection
                                        items.remove(items.get(i));
                                        //Et on réadapte la listView
                                        ArrayAdapter<DemandeFiguration> objAdapter = new ListDemandeAdapter(getActivity(), R.layout.row_list_demande, items);
                                        lv.setAdapter(objAdapter);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }
}
