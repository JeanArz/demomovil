package com.example.applezedfc.inventario;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;



public class BuscarSoftware extends AppCompatActivity {

    private String URL = "http://inventario2018.herokuapp.com/api_software?user_hash=12345&action=get&";
    private String URL1 = "http://inventario2018.herokuapp.com/api_software?user_hash=12345&action=put&nombre=Excel&no_serie=25&precio=8.0";
    private ListView lv_contacts_list;
    private ArrayAdapter adapter;
    private String queryParams = "";
    private String URL2 = "";
    private String URL3 = "";
    private Button btn_buscar;
    private Button btn_insertar;
    private Button btn_search;
    private EditText et_id_software;
    private TextView tv_nombre;
    private TextView tv_no_serie;
    private TextView tv_precio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_software);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        webServiceRest(URL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        et_id_software = (EditText) findViewById(R.id.et_id_software);
        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_no_serie = (TextView) findViewById(R.id.tv_no_serie);
        tv_precio = (TextView) findViewById(R.id.tv_precio);

        btn_buscar = (Button) findViewById(R.id.btn_buscar);
        btn_buscar.setOnClickListener(onClickListener);

        btn_insertar = (Button) findViewById(R.id.btn_insertar);
        btn_insertar.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btn_buscar)
                btn_buscar_onClick();
        }
    };

    private void btn_buscar_onClick() {
        String id_software= et_id_software.getText().toString();

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("id_software",id_software);
        String queryParams = builder.build().getEncodedQuery();
        URL2=URL;
        URL2+=queryParams;
        Log.d("mensaje", URL2);
        webServiceRest(URL2);
    }

    private View.OnClickListener onClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btn_insertar)
                btn_buscar_onClick();
        }
    };

    private void btn_insertar_onClick() {
        String id_software= et_id_software.getText().toString();

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("id_software",id_software);
        String queryParams = builder.build().getEncodedQuery();
        URL3=URL1;
        URL3+=queryParams;
        Log.d("mensaje", URL3);
        webServiceRest(URL3);
    }


    private void webServiceRest(String requestURL) {
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult = "";
            while ((line = bufferedReader.readLine()) != null) {
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult) {
        JSONArray jsonArray = null;
        String id_software;
        String nombre;
        String no_serie;
        String precio;
        try {
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null){
            Log.d("jsonArray ",""+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    id_software = jsonObject.getString("id_software");
                    nombre = jsonObject.getString("nombre");
                    no_serie = jsonObject.getString("no_serie");
                    precio = jsonObject.getString("precio");

                    tv_nombre.setText(nombre);
                    tv_no_serie.setText(no_serie);
                    tv_precio.setText(precio);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            tv_nombre.setText("No encontrado");
            tv_no_serie.setText("No encontrado");
            tv_precio.setText("No encontrado");
            Message("Error", "Registro no encontrado");
        }
    }

    private void Message(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();

    }
}







