package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import Database.DatabaseAccess;
import model.Memo;

import com.example.t788340.lscan.R;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class Data_Activity extends Activity {

    private Button btnAdd;
    private Button btnScan;
    private ListView listView;
    private DatabaseAccess databaseAccess;
    private List<Memo> memos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Link View
        setContentView(R.layout.activity_data_);

        this.btnAdd = (Button) findViewById(R.id.btnAdd);
        this.btnScan = (Button) findViewById(R.id.btnScan);
        this.listView = (ListView) findViewById(R.id.listView);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        //GraphView.setTitle("foo");
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling


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
                startActivity(new Intent(Data_Activity.this, Abbott.class));
            }
        });

        //set database access
        this.databaseAccess = DatabaseAccess.getInstance(this);
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
                onDeleteClicked(memo);}
        });
        return convertView;
    }
}
}


