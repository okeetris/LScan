package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.t788340.lscan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Database.DatabaseAccess;
import model.Memo;

import static android.content.ContentValues.TAG;

/**
 * The type Food activity.
 */
public class Food_Activity extends Activity {

    /**
     * The Edit text.
     */
    EditText editText;
    /**
     * The Progress bar.
     */
    ProgressBar progressBar;
    private ListView responseView;
    private Button btnSave;
    private Button btnCancel;
    /**
     * The constant final1.
     */
    public static String final1;
    private String[] all1 = {};
    /**
     * The Food.
     */
    String food;
    /**
     * The Regex.
     */
    String regex = "[a-zA-Z]\\w*";
    private Memo memo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_);

        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);
        this.responseView = (ListView) findViewById(R.id.responseView);
        editText = (EditText) findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Food_Activity.this, Data_Activity.class));
            }
        });
        Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide:
                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                onSearchClicked();
            }
        });
    }

    /**
     * On search clicked.
     */
    public void onSearchClicked(){

        String search = editText.getText().toString();
        if (search.matches(regex)){

            String[] result = search.split("\\s");
            int x;
            int y;
            int z;
            y = result.length;

            String search1 = editText.getText().toString();
            String[] nameParts = search1.split(" ");

            //Log.d("name", nameParts[0] +"%2520"+ nameParts[1]);
            z = nameParts.length;
            if (z == 1) {
                Log.d("name", nameParts[0]);
                final1 = nameParts[0];
            } else if (z == 2) {
                Log.d("name", nameParts[0] + "%25" + nameParts[1]);
                final1 = (nameParts[0] + "%25" + nameParts[1]);
            } else if (z == 3) {
                Log.d("name", nameParts[0] + "%25" + nameParts[1] + "25" + nameParts[2]);
                final1 = (nameParts[0] + "%25" + nameParts[1] + "25" + nameParts[2]);
            }


            new RetrieveFeedTask().execute(final1);

            //responseView.setText(all + "")
        }
        else if (!search.matches(regex)){
            Toast.makeText(this, "Please enter a valid search parameter!", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * On save clicked.
     */
//method for when save is clicked
    public void onSaveClicked() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Memo temp = new Memo();
        temp.setText(food);
        databaseAccess.save(temp);
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        databaseAccess.close();
        this.finish();
    }

    private class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        /**
         * The Api key.
         */
        static final String API_KEY = "7f9d6201c30e7f71fa27aa1249478478";
        /**
         * The Api id.
         */
        static final String API_ID = "9b2f1e3e";
        /**
         * The Api url.
         */
        static final String API_URL = "https://api.nutritionix.com/v1_1/search/";
        /**
         * The Api fields.
         */
        static final String API_FIELDS = "?results=0:20&fields=item_name,item_id,brand_name,nf_total_carbohydrate";


        //private Exception Exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... args) {

            String final1 = args[0];
            Log.d("creation", API_URL + final1 + API_FIELDS + "&appId=" + API_ID + "&appKey=" + API_KEY);


            //http://api.nutritionix.com/v1_1/search/cheddar%20cheese?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat&appId=9b2f1e3e&appKey=7f9d6201c30e7f71fa27aa1249478478
            //https://api.nutritionix.com/v1_1/search/mcdonalds?results=0:20&fields=item_name,brand_name,item_id,nf_calories&appId=9b2f1e3e&appKey=7f9d6201c30e7f71fa27aa1249478478
            try {
                URL url = new URL(API_URL + final1 + API_FIELDS + "&appId=" + API_ID + "&appKey=" + API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String Jsonresponse) {
            if (Jsonresponse == null) {
                Jsonresponse = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", Jsonresponse);
            ArrayList<String> itemNames = new ArrayList();
            ArrayList<String> brandNames = new ArrayList();
            ArrayList<String> Carbs = new ArrayList();
            ArrayList<String> all = new ArrayList();

            try {
                JSONObject object = new JSONObject(Jsonresponse);
                JSONArray hits = object.getJSONArray("hits");
                for (int i = 0; i < hits.length(); i++) {
                    JSONObject fields = hits.getJSONObject(i).getJSONObject("fields");
                    String itemName = fields.getString("item_name");
                    String brandName = fields.getString("brand_name");
                    String carbohydrate = fields.getString("nf_total_carbohydrate");
                    Log.d("HitTag", itemName + " " + brandName + " " + carbohydrate);
                    itemNames.add(itemName);
                    brandNames.add(brandName);
                    Carbs.add(carbohydrate);

                }
                for (int j = 0; j < itemNames.size(); j++) {
                    all.add(brandNames.get(j) + " " + itemNames.get(j) + " " + Carbs.get(j));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            all1 = all.toArray(new String[all.size()]);

            ArrayAdapter adapter = new ArrayAdapter(Food_Activity.this, android.R.layout.simple_list_item_single_choice, all1);
            responseView.setAdapter(adapter);
            responseView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            responseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CheckedTextView cv = (CheckedTextView) view;
                    cv.setChecked(!cv.isChecked());
                    food = (String)(responseView.getItemAtPosition(position));
                    Log.d(TAG, "cv "+ food);

                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSaveClicked();
                }
            });




        }


    }
}




