package it.tmac12.amlatorre;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GamesFrag extends ListFragment {
    private List<Game> mGames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mGames = null;
    }

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
        final Context context = getActivity();
        if (mGames == null) {
            GamesLoader gamesLoader = new GamesLoader(context);
            gamesLoader.execute();
        } else {
            setGamesToList(context, mGames);
        }
    }

    private void setGamesToList(Context context, List<Game> games) {
        LazyAdapter adapter = new LazyAdapter(context, R.layout.row_game, games);
        getListView().setAdapter(adapter);
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
            mGames = games;
            setGamesToList(mContext, games);
        }
    }
}