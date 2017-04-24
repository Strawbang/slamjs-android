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

import objects.Event;

/**
 * Created by BOUGOUFFA on 24/04/2017.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    private Activity activity;
    private List<Event> items;
    private Event objBean;
    private int row;

    public EventAdapter(Activity act, int resource, List<Event> arrayList) {
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
        holder.type = (TextView) view.findViewById(R.id.type);
        holder.date = (TextView) view.findViewById(R.id.date);
        holder.nombreJours = (TextView) view.findViewById(R.id.nombreJours);

        if (holder.nom != null && null != objBean.getNom()
                && objBean.getNom().trim().length() > 0) {
            holder.nom.setText(Html.fromHtml(objBean.getNom()));
        }

        if (holder.type != null && null != objBean.getType()
                && objBean.getType().trim().length() > 0) {
            holder.type.setText(Html.fromHtml(objBean.getType()));
        }

        if (holder.date != null && null != objBean.getDate()
                && objBean.getDate().trim().length() > 0) {
            holder.date.setText(Html.fromHtml(objBean.getDate()));
        }

        if (holder.nombreJours != null && null != objBean.getNombreJours()
                && objBean.getNombreJours().trim().length() > 0) {
            holder.nombreJours.setText(Html.fromHtml(objBean.getNombreJours()));
        }

        return view;
    }

    public class ViewHolder {
        public TextView nom, type, date, nombreJours;
    }
}
