package adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.btssio.slam.slamjs.R;

import java.util.List;

import objects.Offre;

/**
 * Created by BOUGOUFFA on 22/04/2017.
 */
public class OffreAdapter extends ArrayAdapter<Offre> {
    private Activity activity;
    private List<Offre> items;
    private Offre objBean;
    private int row;

    public OffreAdapter(Activity act, int resource, List<Offre> arrayList) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.items = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((items == null) || ((position + 1) > items.size()))
            return view;

        objBean = items.get(position);

        holder.nom = (TextView) view.findViewById(R.id.nom);
        holder.nbRoles = (TextView) view.findViewById(R.id.nbRoles);

        if (holder.nom != null && null != objBean.getNom()
                && objBean.getNom().trim().length() > 0) {
            holder.nom.setText(Html.fromHtml(objBean.getNom()));
        }

        if (holder.nbRoles != null && null != objBean.getNbRoles()
                && objBean.getNbRoles().trim().length() > 0) {
            holder.nbRoles.setText(Html.fromHtml(objBean.getNbRoles()));
        }

        return view;
    }

    public class ViewHolder {
        public TextView nom, nbRoles;
    }
}
