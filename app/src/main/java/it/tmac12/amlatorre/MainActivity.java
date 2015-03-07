package it.tmac12.amlatorre;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.main_listview);
        LazyAdapter adapter = new LazyAdapter(this, R.layout.row_game, XmlParser.parse(this, R.raw.data));
        listView.setAdapter(adapter);
    }
}