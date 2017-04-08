package com.example.tankinhbui.mobile_convert_money;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;

public class MainActivity extends AppCompatActivity {


    //TextView monhoc, noihoc, website, fanpage;
    ListView lv;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //monhoc = (TextView) findViewById(R.id.monhoc);
        //noihoc = (TextView) findViewById(R.id.noihoc);
        //website = (TextView) findViewById(R.id.website);
        //fanpage = (TextView) findViewById(R.id.fanpage);
        lv = (ListView) findViewById(R.id.listview_item);
        btnBack = (Button) findViewById(R.id.buttonBack1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen2 = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(screen2);
            }
        });


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://query.yahooapis.com/v1/public/yql?format=json&q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDEUR%22%2C%20%22USDJPY%22%2C%20%22USDBGN%22%2C%20%22USDCZK%22%2C%20%22USDDKK%22%2C%20%22USDGBP%22%2C%20%22USDHUF%22%2C%20%22USDLTL%22%2C%20%22USDLVL%22%2C%20%22USDPLN%22%2C%20%22USDRON%22%2C%20%22USDSEK%22%2C%20%22USDCHF%22%2C%20%22USDNOK%22%2C%20%22USDHRK%22%2C%20%22USDRUB%22%2C%20%22USDTRY%22%2C%20%22USDAUD%22%2C%20%22USDBRL%22%2C%20%22USDCAD%22%2C%20%22USDCNY%22%2C%20%22USDHKD%22%2C%20%22USDIDR%22%2C%20%22USDILS%22%2C%20%22USDINR%22%2C%20%22USDKRW%22%2C%20%22USDMXN%22%2C%20%22USDMYR%22%2C%20%22USDNZD%22%2C%20%22USDPHP%22%2C%20%22USDSGD%22%2C%20%22USDTHB%22%2C%20%22USDZAR%22%2C%20%22USDISK%22%2C%20%22USDVND%22)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            }
        });
    }
    class ReadJSON extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String kq = docNoiDung_Tu_URL(params[0]);
            return kq;
        }
        protected void onPostExecute(final String s){
            //Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            try {

                JSONObject root = new JSONObject(s);
                JSONObject t1 = root.getJSONObject("query");
                JSONObject t2 = t1.getJSONObject("results");
                JSONArray array = t2.getJSONArray("rate");
                //final ArrayList<String> arrayList =new ArrayList<String>();
                //final ArrayList<String> arrayList1 =new ArrayList<String>();

                ArrayList<Money> arrayMoney = new ArrayList<Money>();
                for (int i = 0; i < array.length(); i++){
                    JSONObject item = array.getJSONObject(i);
                    //arrayList.add(item.getString("Name"));
                    //arrayList.add(item.getString("Rate"));
                    boolean a = arrayMoney.add(new Money(item.getString("Name"),parseFloat(item.getString("Rate"))));
                   // Log.e("aaaaaa", arrayMoney.get(i).getName());
                }

                ListAdapter adapter = new ListAdapter(
                        MainActivity.this,
                        R.layout.item,
                        arrayMoney
                );
                lv.setAdapter(adapter);
                /*
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        arrayList

                );
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(
                                MainActivity.this,
                                arrayList1.get(position),
                                Toast.LENGTH_SHORT
                        ).show();


                    }
                });
                */

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
