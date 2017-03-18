package usc.com.uscmaps.example1.shubham.bluetoothenabled;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    Button connectButton;
    ListView listView;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        connectButton = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView1);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, 0);
        listView.setAdapter(listAdapter);


    }
}
