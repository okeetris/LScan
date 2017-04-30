package mainMenu;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t788340.lscan.R;
import java.util.List;

import Database.DatabaseAccessBG;
import activity.Data_Activity;
import activity.NFC_package.ReadNfcTask;
import model.BloodGlucoseModel;


public class MainMenu_Activity extends Activity {


    private NfcAdapter mAdapter;
    private static final String TAG = "MainMenuActivity";
    private DatabaseAccessBG databaseAccess;
    private ListView BGList;
    private List<BloodGlucoseModel> BGs;
    private TextView currentBG;
    private ImageButton food_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.databaseAccess = DatabaseAccessBG.getInstance(this);
        this.BGList = (ListView) findViewById(R.id.BGList);
        this.currentBG = (TextView) findViewById(R.id.currentBG);
        this.food_button = (ImageButton) findViewById(R.id.food_button);
        mAdapter = NfcAdapter.getDefaultAdapter(this);


        food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu_Activity.this, Data_Activity.class));
            }
           /*@Override
            public void onClick(View v) {
                onAddClicked();
            }
            */
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseAccess.open();
        this.BGs = databaseAccess.getAllMemos();
        databaseAccess.close();
        BGAdapter adapter = new BGAdapter(this, BGs);
        this.BGList.setAdapter(adapter);
        setupForegroundDispatch(this, mAdapter);
    }

    public void onDeleteClicked(BloodGlucoseModel BG) {
        databaseAccess.open();
        databaseAccess.delete(BG);
        databaseAccess.close();
        ArrayAdapter<BloodGlucoseModel> adapter = (ArrayAdapter<BloodGlucoseModel>) BGList.getAdapter();
        adapter.remove(BG);
        adapter.notifyDataSetChanged();
    }
    //set private class for memo adapter
    private class BGAdapter extends ArrayAdapter<BloodGlucoseModel> {

        public BGAdapter(Context context, List<BloodGlucoseModel> objects) {
            super(context, 0, objects);
        }
        //inflate view layout_list_item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_list_item, parent, false);
            }
            //link buttons to views within layout_list_item
            Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            TextView txtMemo = (TextView) convertView.findViewById(R.id.txtMemo);
            TextView time = (TextView) convertView.findViewById(R.id.time);

            final BloodGlucoseModel BG = BGs.get(position);
            time.setText(BG.getDate());
            String BGText = Double.toString(BG.getBG());
            txtMemo.setText(BGText);

            //set listener for delete button
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(BG);}
            });
            return convertView;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopForegroundDispatch(this, mAdapter);
    }

    /**
     * Stops foreground dispatch of NFC intents
     *
     * @param activity The Activity requesting the foreground dispatch.
     * @param adapter  The NfcAdapter used for the foreground dispatch.
     */
    public void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    /**
     * Sets up foreground dispatch for NFC intents for the Activity
     *
     * @param activity The corresponding Activity requesting the foreground dispatch.
     * @param adapter  The NfcAdapter used for the foreground dispatch.
     */
    public void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{new String[]{NfcV.class.getName()}};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        //ToDo: Read NFC in another task
        String action = intent.getAction();
        Log.d(TAG, "Tech Discovered");
        if (intent.getAction().equals(NfcAdapter.ACTION_TECH_DISCOVERED)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            ReadNfcTask readNfcTask = new ReadNfcTask(this, this);
            readNfcTask.execute(tag);
        }
    }
}