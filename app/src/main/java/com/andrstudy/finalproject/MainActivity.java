package com.andrstudy.finalproject;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public String myJSON;
    private Button button;
    private ListView listView;
    private static String ip = null;
    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "menuName";
    private static final String TAG_NAME = "price";
    private static final String TAG_ADD = "isSpecial";
    private SharedPreferences preferences;

    JSONArray peoples = null;
    private ArrayList<HashMap<String, String>> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        listView = findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();

        preferences = getSharedPreferences("ip",MODE_PRIVATE);
        String getIp = preferences.getString("ip", null);

        if(getIp !=null)
            getData("http://"+getIp+"/final/menu.php");
        //getData("http://"+ip+"/final/menu.php");

    }

    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<peoples.length(); i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);

                HashMap<String, String> persons = new HashMap<String,String>();

                if(address == "true") {
                    persons.put(TAG_ID, id);
                    persons.put(TAG_NAME, name);
                    personList.add(persons);
                }
            }

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ID, TAG_NAME},
                    new int[]{R.id.id, R.id.price,}
            );
            listView.setAdapter(adapter);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void getData(String url){
        class GetDataJson extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                if(params==null || params.length<1)
                    return null;
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!=null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();
                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url);
    }


    @Override
    public void onClick(View view) {
        alertShow();
    }

    void alertShow() {
        final EditText editText = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ip 입력칸");
        builder.setMessage("ip를 입력해주세요.");
        builder.setView(editText);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ip = editText.getText().toString();
                        Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("ip", ip);
                        editor.commit();
                        getData("http://"+ip+"/final/menu.php");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }





}



