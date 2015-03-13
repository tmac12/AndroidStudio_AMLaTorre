package it.tmac12.amlatorre;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

// TODO why lazy? :)
public class LazyAdapter extends ArrayAdapter<Game> {
    private final Context mContext;
    private final LayoutInflater mInflater;

    public LazyAdapter(Context context, int layoutResId, List<Game> games) {
        super(context, layoutResId, games);
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.row_game, parent, false);
            ImageView iconView = (ImageView) view.findViewById(R.id.id_icon);
            TextView opponentView = (TextView) view.findViewById(R.id.id_opponent);
            TextView datetimeView = (TextView) view.findViewById(R.id.id_datetime);
            TextView locationView = (TextView) view.findViewById(R.id.id_location);
            viewHolder = new ViewHolder(iconView, opponentView, datetimeView, locationView);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Game game = getItem(position);
        boolean isHomeGame = game.isHomeGame();
        viewHolder.mIconView.setImageResource(isHomeGame ? R.drawable.ic_home : R.drawable.ic_away);
        String contentDescr = mContext.getString(isHomeGame ? R.string.content_descr_home_icon : R.string.content_descr_away_icon);
        viewHolder.mIconView.setContentDescription(contentDescr);
        viewHolder.mOpponentView.setText(game.mOpponent);
        if (!TextUtils.isEmpty(game.mTime)) {
            viewHolder.mDatetimeView.setText(mContext.getString(R.string.game_datetime, game.mDate, game.mTime));
        } else {
            viewHolder.mDatetimeView.setText(game.mDate);
        }
        if (!TextUtils.isEmpty(game.mLocation)) {
            viewHolder.mLocationView.setText(game.mLocation);
            viewHolder.mLocationView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mLocationView.setVisibility(View.GONE);
        }
        return view;
    }

    private static class ViewHolder {
        private final ImageView mIconView;
        private final TextView mOpponentView;
        private final TextView mDatetimeView;
        private final TextView mLocationView;

        public ViewHolder(ImageView iconView, TextView opponentView, TextView datetimeView, TextView locationView) {
            mIconView = iconView;
            mOpponentView = opponentView;
            mDatetimeView = datetimeView;
            mLocationView = locationView;
        }
    }
}