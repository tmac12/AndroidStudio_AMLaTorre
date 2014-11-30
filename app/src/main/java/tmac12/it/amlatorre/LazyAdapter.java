package tmac12.it.amlatorre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marco on 30/11/14.
 */
public class LazyAdapter extends BaseAdapter {
    List<XmlParser.Partita> mPartite;
    LayoutInflater mInflater;
    Context mContext;


    public LazyAdapter (List<XmlParser.Partita> eventi, LayoutInflater inflater, Context context)
    {
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
        XmlParser.Partita evento = (XmlParser.Partita) getItem(position);

        //carico l'id della risorsa immagine da una string. http://stackoverflow.com/questions/4313007/setting-android-images-from-string-value
        //int resId = mContext.getResources().getIdentifier(evento.immagine, "drawable", mContext.getPackageName());
        //holder.thumbnailImageView.setImageResource(resId);


        //carico l'immagine
        if (evento.luogo.equals("Crespano del Grappa")){
            holder.thumbnailImageView.setImageResource(R.drawable.home);
        }
        else
        {
            holder.thumbnailImageView.setImageResource(R.drawable.bus);
        }
        holder.avversarioTextView.setText(evento.avversario);
        holder.orarioTextView.setText(evento.data);

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
