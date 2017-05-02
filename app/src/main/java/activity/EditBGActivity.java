package activity;

/**
 * Created by 788340 on 30/04/2017.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.t788340.lscan.R;

import Database.DatabaseAccessBG;
import model.BloodGlucoseModel;

/**
 * The type Edit bg activity.
 */
public class EditBGActivity extends ActionBarActivity {
    private EditText etText;
    private Button btnSave;
    private Button btnCancel;
    private BloodGlucoseModel BG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.etText = (EditText) findViewById(R.id.etText);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            BG = (BloodGlucoseModel) bundle.get("bloodglucose");
            if(BG != null) {
                String stringdouble = Double.toString(BG.getBG());
                this.etText.setText(stringdouble);
            }
        }

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });
    }

    /**
     * On save clicked.
     */
    public void onSaveClicked() {
        DatabaseAccessBG databaseAccess = DatabaseAccessBG.getInstance(this);
        databaseAccess.open();
        // Add new memo
        BloodGlucoseModel temp = new BloodGlucoseModel();
        databaseAccess.save(temp);
        databaseAccess.close();
        this.finish();
    }

    /**
     * On cancel clicked.
     */
    public void onCancelClicked() {
        this.finish();
    }
}