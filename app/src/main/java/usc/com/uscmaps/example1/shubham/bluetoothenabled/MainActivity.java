package usc.com.uscmaps.example1.shubham.bluetoothenabled;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button connectButton;
    ListView listView;
    ArrayAdapter<String> listAdapter;
    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> devicesArray;
    ArrayList<String> pairedDevices;
    IntentFilter filter;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(btAdapter == null){
            Toast.makeText(getApplicationContext(), "No Bluetooth detected", Toast.LENGTH_SHORT).show();
        }
        else{
            // If BT not enabled
            if(!btAdapter.isEnabled()){
                Log.e("gurjfdk", "isEnabled");
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1);
            }
        }
        
        getPairedDevices();
    }

    private void getPairedDevices() {
        devicesArray = btAdapter.getBondedDevices();
        if(devicesArray.size() > 0){
            for(BluetoothDevice device: devicesArray){
                pairedDevices.add(device.getName());
            }
        }

    }

    private void init() {
        connectButton = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView1);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, 0);
        listView.setAdapter(listAdapter);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = new ArrayList<>();

        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    listAdapter.add(device.getName()+"\n"+device.getAddress());
                }
            }
        };
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("nvkdm", ""+requestCode+"df: "+resultCode+ " d: "+RESULT_CANCELED);

        if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "GIVE IT TO ME", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
