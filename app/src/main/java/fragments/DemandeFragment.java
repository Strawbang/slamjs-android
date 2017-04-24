package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.btssio.slam.slamjs.R;
import repository.EventRepository;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public class DemandeFragment extends Fragment implements View.OnClickListener {
    public  String SERVER_URL = null;
    private JSONObject jsonResponse;
    private EventRepository eventRepo;
    private String leOffre;
    private String url;

    TextView tvNomEvent;
    TextView tvType;
    TextView tvDate;
    TextView tvNbJours;
    TextView tvNomRole;
    TextView tvNbRole;

    EditText editText;
    Button button;

    public DemandeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_demande, container, false);

        eventRepo = new EventRepository(getActivity().getApplicationContext());
        url = eventRepo.getUrl().toString();

        tvNomEvent = (TextView) v.findViewById(R.id.nomEvent);
        tvType = (TextView) v.findViewById(R.id.type);
        tvDate = (TextView) v.findViewById(R.id.date);
        tvNbJours = (TextView) v.findViewById(R.id.nbJours);
        tvNomRole = (TextView) v.findViewById(R.id.nomRole);
        tvNbRole = (TextView) v.findViewById(R.id.nbRoles);

        editText = (EditText) v.findViewById(R.id.editMail);
        button = (Button) v.findViewById(R.id.button);

        button.setOnClickListener(this);

        return v;
    }

    public void onStart() {
        super.onStart();
        sendRequest();
    }

    private void sendRequest() {

        // Creation de l'objet Flux lie a l'utilisateur avec passage du contexte
        eventRepo = new EventRepository(getActivity().getApplicationContext());

        // Verification de l'existence d'un flux
        if (eventRepo.isOffreConfigured()) {
            leOffre = eventRepo.getOffre();
        } else {
            Toast.makeText(getActivity(), "Pas trouvé", Toast.LENGTH_SHORT).show();
        }

        SERVER_URL = url + "/android/offre/" + leOffre;

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
            JSONObject jsonObj = jsonResponse.getJSONObject("offres");

            // Récupération à partir d'un JSONObject nommé
            JSONObject event  = jsonObj.getJSONObject("_event");
            JSONObject role  = jsonObj.getJSONObject("_role");

            // Récupération de l'item qui nous intéresse
            tvNbRole.setText(jsonObj.getString("nbRoles"));

            tvNomRole.setText(role.getString("nom"));

            tvNomEvent.setText(event.getString("nom"));
            tvType.setText(event.getString("type"));
            tvDate.setText(event.getString("date"));
            tvNbJours.setText(event.getString("nombreJours"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == button) {
            String email = editText.getText().toString();
            if (email != null) {
                String SERVER_URL = url + "/android/postule/" + leOffre + "/"+email;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getActivity(), " " + response, Toast.LENGTH_SHORT).show();
                                button.setVisibility(View.INVISIBLE);
                                editText.setVisibility(View.INVISIBLE);

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
            }else {
                Toast.makeText(getActivity(), "Ajouté un e-mail !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
