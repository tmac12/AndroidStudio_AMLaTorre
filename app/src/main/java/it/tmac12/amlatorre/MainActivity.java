package it.tmac12.amlatorre;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private static LazyAdapter mLazyAdapter;
    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();
    List<XmlParser.Partita> _partite = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            CaricaDatiDaXML();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainListView = (ListView) findViewById(R.id.main_listview);

        mLazyAdapter = new LazyAdapter(_partite, getLayoutInflater(), this);

        mainListView.setAdapter(mLazyAdapter);
    }

    private void CaricaDatiDaXML() throws XmlPullParserException, IOException {
        //List<XmlParser.Categoria> categorie = null;
        XmlParser xmlParser = new XmlParser();
        InputStream inputData = this.getResources().openRawResource(R.raw.data);
        try {
            _partite = xmlParser.parse(inputData);
        } finally {
            if (inputData != null) {
                inputData.close();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
