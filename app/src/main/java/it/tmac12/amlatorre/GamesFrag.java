package it.tmac12.amlatorre;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GamesFrag extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
        emptyView.setText(R.string.empty_games);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GamesLoader gamesLoader = new GamesLoader(getActivity());
        gamesLoader.execute();
    }

    private class GamesLoader extends AsyncTask<Void, Void, List<Game>> {
        private final Context mContext;

        private GamesLoader(Context context) {
            mContext = context;
        }

        @Override
        protected List<Game> doInBackground(Void... params) {
            return XmlParser.parse(mContext, R.raw.data);
        }

        @Override
        protected void onPostExecute(List<Game> games) {
            super.onPostExecute(games);
            ArrayAdapter<Game> adapter = new LazyAdapter(mContext, R.layout.row_game, games);
            getListView().setAdapter(adapter);
        }
    }
}