package com.example.tankinhbui.mobile_convert_money;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;

public class ConvertActivity extends AppCompatActivity {

    Spinner spinnerName1, spinnerName2;
    Button btnConvert, btnBack ;
    TextView txtResult;
    EditText txtinput;

    final float[] tien1 = {0};
    final float[] tien2 = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        spinnerName1 = (Spinner) findViewById(R.id.spinnerName1);
        spinnerName2 = (Spinner) findViewById(R.id.spinnerName2);
        btnConvert = (Button) findViewById(R.id.buttonDoi);
        txtResult = (TextView) findViewById(R.id.textViewResult) ;
        txtinput = (EditText) findViewById(R.id.editTextInput);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://query.yahooapis.com/v1/public/yql?format=json&q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDEUR%22%2C%20%22USDJPY%22%2C%20%22USDBGN%22%2C%20%22USDCZK%22%2C%20%22USDDKK%22%2C%20%22USDGBP%22%2C%20%22USDHUF%22%2C%20%22USDLTL%22%2C%20%22USDLVL%22%2C%20%22USDPLN%22%2C%20%22USDRON%22%2C%20%22USDSEK%22%2C%20%22USDCHF%22%2C%20%22USDNOK%22%2C%20%22USDHRK%22%2C%20%22USDRUB%22%2C%20%22USDTRY%22%2C%20%22USDAUD%22%2C%20%22USDBRL%22%2C%20%22USDCAD%22%2C%20%22USDCNY%22%2C%20%22USDHKD%22%2C%20%22USDIDR%22%2C%20%22USDILS%22%2C%20%22USDINR%22%2C%20%22USDKRW%22%2C%20%22USDMXN%22%2C%20%22USDMYR%22%2C%20%22USDNZD%22%2C%20%22USDPHP%22%2C%20%22USDSGD%22%2C%20%22USDTHB%22%2C%20%22USDZAR%22%2C%20%22USDISK%22%2C%20%22USDVND%22)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtinput.getText().toString().isEmpty()){
                    Toast.makeText(ConvertActivity.this, "nhập số tiền cần chuyển đổi   ", Toast.LENGTH_SHORT).show();
                }
                else {
                    float rate = parseFloat(txtinput.getText().toString());
                    float result = tien1[0] / tien2[0] * rate;
                    txtResult.setText(String.valueOf(result));
                }
            }
        });
        btnBack = (Button) findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen2 = new Intent(ConvertActivity.this, MainActivity.class);
                startActivity(screen2);
            }
        });
    }
    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String kq = docNoiDung_Tu_URL(params[0]);
            return kq;
        }
        protected void onPostExecute(final String s){
            //Toast.makeText(ShowActivity.this, s, Toast.LENGTH_LONG).show();
            try {
                JSONObject root = new JSONObject(s);
                JSONObject t1 = root.getJSONObject("query");
                JSONObject t2 = t1.getJSONObject("results");
                final JSONArray array = t2.getJSONArray("rate");

                ArrayList<Money> arrayMoney = new ArrayList<Money>();
                final ArrayList<String> arrayList = new ArrayList<String>();
                for (int i = 0; i < array.length(); i++){
                    JSONObject item = array.getJSONObject(i);
                    String str = item.getString("Name").substring(4);
                    arrayList.add(str);
                    boolean a = arrayMoney.add(new Money(item.getString("Name"),parseFloat(item.getString("Rate"))));
                }

                final ArrayAdapter arrayAdapter = new ArrayAdapter(
                        ConvertActivity.this,
                        android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                spinnerName1.setAdapter(arrayAdapter);
                spinnerName1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            JSONObject item = array.getJSONObject(position);
                            tien1[0] = parseFloat(item.getString("Rate"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("tien 1", String.valueOf(tien1[0]));
                       // Toast.makeText(ConvertActivity.this,arrayList.get(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                final ArrayAdapter arrayAdapter1 = new ArrayAdapter(
                        ConvertActivity.this,
                        android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                spinnerName2.setAdapter(arrayAdapter1);
                spinnerName2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            JSONObject item = array.getJSONObject(position);
                            tien2[0] = parseFloat(item.getString("Rate"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(ConvertActivity.this, arrayList.get(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                Log.e("tien 1", String.valueOf(tien1[0]));
                Log.e("tien 2", String.valueOf(tien2));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

}
