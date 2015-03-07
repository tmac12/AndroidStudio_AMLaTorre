package it.tmac12.amlatorre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LazyAdapter extends BaseAdapter {
    List<Game> mPartite;
    LayoutInflater mInflater;
    Context mContext;

    public LazyAdapter(List<Game> eventi, LayoutInflater inflater, Context context) {
        mPartite = eventi;
        mInflater = inflater;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mPartite.size();
    }

    @Override
    public Object getItem(int position) {
        return mPartite.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {
            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.row_partita, null);
            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.avversarioTextView = (TextView) convertView.findViewById(R.id.text_avversario);
            holder.orarioTextView = (TextView) convertView.findViewById(R.id.text_orario);
            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {
            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }
        // More code after this
        //XmlParser.Categoria categoria = (XmlParser.Categoria) getItem(position);
        Game evento = (Game) getItem(position);
        //carico l'id della risorsa immagine da una string. http://stackoverflow.com/questions/4313007/setting-android-images-from-string-value
        //int resId = mContext.getResources().getIdentifier(evento.immagine, "drawable", mContext.getPackageName());
        //holder.thumbnailImageView.setImageResource(resId);
        //carico l'immagine
        if (evento.mLocation.equals("Crespano del Grappa")) {
            holder.thumbnailImageView.setImageResource(R.drawable.ic_home);
        } else {
            holder.thumbnailImageView.setImageResource(R.drawable.ic_away);
        }
        holder.avversarioTextView.setText(evento.mOpponent);
        holder.orarioTextView.setText(evento.mDate);
        return convertView;
    }

    // this is used so you only ever have to do
// inflation and finding by ID once ever per View
    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView avversarioTextView;
        public TextView orarioTextView;
    }
}
