package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.t788340.lscan.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Database.DatabaseAccess;
import Database.DatabaseAccessBG;
import Database.DatabaseOpenHelperBG;
import mainMenu.MainMenu_Activity;
import model.BloodGlucoseModel;
import model.Memo;


/**
 * The type Data activity.
 */
public class Data_Activity extends Activity {

    private static final String TAG = "Data_Activity";
    /**
     * The Graph.
     */
    GraphView graph;
    /**
     * The Series.
     */
    LineGraphSeries<DataPoint> series;
    private Button btnAdd;
    private Button btnScan;
    private Button graph_button;
    private ListView listView;
    private DatabaseAccess databaseAccess;
    private DatabaseAccessBG databaseAccessBG;
    private DatabaseAccessBG databaseAccessBG1;
    private List<Memo> memos;
    private List<BloodGlucoseModel> BGs;
    private SQLiteDatabase sqliteDatabase;
    private DatabaseOpenHelperBG openHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Link View
        setContentView(R.layout.activity_data_);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);
        this.btnScan = (Button) findViewById(R.id.btnScan);
        this.listView = (ListView) findViewById(R.id.listView);
        this.graph = (GraphView) findViewById(R.id.graph);
        this.graph_button = (Button) findViewById(R.id.graph_button);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Data_Activity.this, Food_Activity.class));
            }
           /*@Override
            public void onClick(View v) {
                onAddClicked();
            }
            */
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Data_Activity.this, MainMenu_Activity.class));
            }
        });

        graph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGraph();
            }
        });
        //set database access
        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.databaseAccessBG = DatabaseAccessBG.getInstance(this);
        //link list view and button to views
        this.listView = (ListView) findViewById(R.id.listView);
        //add listener for button
        /*this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {jkg
                onAddClicked();
            }
        });
        */
        //set display of memos for listview. Set if memo is fully viewed or not.
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memo memo = memos.get(position);
                TextView txtMemo = (TextView) view.findViewById(R.id.txtMemo);
                if (memo.isFullDisplayed()) {
                    txtMemo.setText(memo.getShortText());
                    memo.setFullDisplayed(false);
                } else {
                    txtMemo.setText(memo.getText());
                    memo.setFullDisplayed(true);
                }
            }
        });

    }

    private void updateGraph() {
        //DataPoint[] dp = new DataPoint[databaseAccessBG.getData()];
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        Log.d(TAG, "updateGraph: " + d1);
        calendar.add(Calendar.DATE, 2);
        Date d2 = calendar.getTime();
        Log.d(TAG, "updateGraph: " + d2);
        calendar.add(Calendar.DATE, 3);
        Date d3 = calendar.getTime();
        Log.d(TAG, "updateGraph: " + d3);

        //databaseAccessBG1 = new DatabaseAccessBG(this);
        //sqliteDatabase=openHelper.getReadableDatabase();
        //String [] columns ={"date", "bloodglucose"};
        //Cursor cursor = sqliteDatabase.query("BG", columns, null, null, null, null, null, null);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
    }


    //set onResume
    @Override
    protected void onResume() {
        super.onResume();
        databaseAccess.open();
        this.memos = databaseAccess.getAllMemos();
        databaseAccess.close();
        MemoAdapter adapter = new MemoAdapter(this, memos);
        this.listView.setAdapter(adapter);


    }

    /**
     * On delete clicked.
     *
     * @param memo the memo
     */
//Set on delete clicked button
    public void onDeleteClicked(Memo memo) {
        databaseAccess.open();
        databaseAccess.delete(memo);
        databaseAccess.close();

        ArrayAdapter<Memo> adapter = (ArrayAdapter<Memo>) listView.getAdapter();
        adapter.remove(memo);
        adapter.notifyDataSetChanged();
    }

    //set private class for memo adapter
    private class MemoAdapter extends ArrayAdapter<Memo> {


        /**
         * Instantiates a new Memo adapter.
         *
         * @param context the context
         * @param objects the objects
         */
        public MemoAdapter(Context context, List<Memo> objects) {
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

            final Memo memo = memos.get(position);
            memo.setFullDisplayed(false);
            time.setText(memo.getDate());
            txtMemo.setText(memo.getText());


            //set listener for delete button
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(memo);
                }
            });
            return convertView;
        }
    }

    /**
     * Get data data point [ ].
     *
     * @return the data point [ ]
     */
    public DataPoint[] getData() {
        //read data from database
        //this.BGs = databaseAccessBG.getAllBGs();

        sqliteDatabase=openHelper.getReadableDatabase();
        String [] columns ={"date", "bloodglucose"};
        Cursor cursor = sqliteDatabase.query("BG", columns, null, null, null, null, null, null);
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        //cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            //final BloodGlucoseModel BG = BGs.get(i);
            Long time = (cursor.getLong(0));
            cursor.moveToNext();

            dp[i] = new DataPoint(time, cursor.getInt(1));
            //Log.d(TAG, "getData: " + time);
        }
        return dp;
    }


}


