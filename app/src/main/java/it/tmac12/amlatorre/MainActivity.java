package it.tmac12.amlatorre;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
    private static LazyAdapter mLazyAdapter;
    ListView mainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainListView = (ListView) findViewById(R.id.main_listview);
        mLazyAdapter = new LazyAdapter(XmlParser.parse(this, R.raw.data), getLayoutInflater(), this);
        mainListView.setAdapter(mLazyAdapter);
    }
}