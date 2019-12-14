package com.example.okul.kombikontrol;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends ActionBarActivity {
    SharedPreferences preferences;
    public static String URL = "http://turulay.com/kombiisim4.php";//Bilgisayarýn IP adresi
    public static String URL2 = "http://turulay.com/kombiisim5.php";
    String CihazID;
    String durum="0";
    int degeral;
    String degeral2;
    SwipeRefreshLayout swipeLayout;
    final Context context = this;
    boolean internetBaglantisiVarMi() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;

        } else {

            return false;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        threadcalistir();
                        /*LayoutInflater layoutInflater = LayoutInflater.from(context);
                        View promptView = layoutInflater.inflate(R.layout.numberpickeralert, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(promptView);
                        AlertDialog alertD = alertDialogBuilder.create();

                        alertD.show();*/


                        // burada ise Swipe Reflesh olduðunda ne yapacaksanýz onu eklemeniz yeterlidir. Örneðin bir listeyi clear edebilir yada yeniden veri doldurabilirsiniz.
                    }
                }, 2000);
            }
        });

        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        Button ayarlarbtn=(Button) findViewById(R.id.ayarlarbtn);
        Button anlikbt=(Button) findViewById(R.id.anlikbtn);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        CihazID=preferences.getString("CihazID", "Cihaz ID Gir");
        durum="0";
        threadcalistir();
        /*final android.os.Handler handler = new android.os.Handler();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        threadcalistir();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(doAsynchronousTask, 15000, 15000);*/
        //
        ayarlarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                durum = "0";
                threadcalistir();
                Intent i = new Intent(getApplicationContext(), kombiayarlarfrm.class);
                startActivity(i);

            }
        });
        anlikbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.numberpickeralert, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptView);
                final NumberPicker np;
                np = (NumberPicker) promptView.findViewById(R.id.sayi11);
                np.setMinValue(10);
                np.setMaxValue(40);
                //degeral=Integer.parseInt(derecesec1txt.getText().toString());
                degeral=24;
                np.setValue(degeral);
                np.setWrapSelectorWheel(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                durum = Integer.toString(np.getValue());

                                threadcalistir2();
                                //degeral2=Integer.toString(np.getValue());
                                //durum = "0";
                                //URL = "http://turulay.com/kombiisim4.php";
                            }
                        })
                        .setNegativeButton("Ýptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Toast.makeText(this, "ayarlar sayfasý gelecek", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),ayarlarfrm.class);
                startActivity(i);
                break;

            case R.id.item2:
                Toast.makeText(this,"Program Sonlanacak",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);


            //noinspection SimplifiableIfStatement
            //if (id == R.id.action_settings) {
            //return true;
        }

        //return super.onOptionsItemSelected(item);
        return true;
    }
    public static String connect(String url,String CihazID,String durum){
        HttpClient httpClient=new DefaultHttpClient();
        //HttpGet httpget = new HttpGet(url);
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=UTF-8");
        httppost.addHeader("User-Agent", "Mozilla/4.0");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("cihazID", CihazID));
        params.add(new BasicNameValuePair("durum", durum));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response;
        try {
            response=httpClient.execute(httppost);
            HttpEntity entity=response.getEntity();
            if(entity!=null){
                InputStream instream=entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return sb.toString();
            }
        } catch (Exception e) {
        }
        return null;
    }


    class fetchJsonTask extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                String ret = connect(params[0],CihazID,durum);
                ret = ret.trim();
                JSONObject jsonObj = new JSONObject(ret);
                return jsonObj;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result != null) {
                parseJson(result);
                /*TextView mesaj1 = (TextView) findViewById(R.id.mesaj1);
                mesaj1.setText(result.toString());
                mesaj1.setTextColor(Color.RED);*/
            } else {
                TextView mesaj1 = (TextView) findViewById(R.id.mesaj1);
                mesaj1.setText("Kayýt Bulunamadý");
                mesaj1.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), "Sistemden Herhangi bir bilgi gelmedi.",
                        Toast.LENGTH_LONG).show();
            }

        }
    }
    public void parseJson(JSONObject ogrenciJson) {
        //Button saatsec11txt=(Button)findViewById(R.id.saatsec11);
        TextView cihazID = (TextView) findViewById(R.id.cihazID);
        TextView cihazad = (TextView) findViewById(R.id.cihazad);
        TextView simdikiderece = (TextView) findViewById(R.id.simdikiderece);
        TextView ayarlanansicaklik=(TextView) findViewById(R.id.ayarsicaklik);
        TextView kombidurumu=(TextView) findViewById(R.id.kombidurumu);
        TextView simdikisaat = (TextView) findViewById(R.id.simdikisaat);
        ImageView kombiresim=(ImageView) findViewById(R.id.kombiresim);
        Integer kombidurums;
        TextView guntarih = (TextView) findViewById(R.id.guntarih);
        TextView cihazip = (TextView) findViewById(R.id.cihazip);
        TextView mesaj1 = (TextView) findViewById(R.id.mesaj1);
        mesaj1.setText("");
        System.out.println(ogrenciJson);
        try {
            //saatsec11txt.setText(ogrenciJson.getString("saatsec1"));
            cihazID.setText("CihazID: "+ogrenciJson.getString("cihazID"));
            cihazad.setText("Cihaz Adý: "+ogrenciJson.getString("cihazad"));
            simdikiderece.setText("Ev Sýcaklýðý: "+ogrenciJson.getString("simdikiderece"));
            ayarlanansicaklik.setText("Ayarlanan Sýcaklýk: "+ogrenciJson.getString("ayarsicaklik"));
            kombidurums=Integer.parseInt(ogrenciJson.getString("kombidurum"));
            if(kombidurums==0)
            {
                kombiresim.setBackgroundResource(R.drawable.kapalianim);
                AnimationDrawable kapaliAnim=(AnimationDrawable) kombiresim.getBackground();
                kapaliAnim.start();
                //kombiresim.setImageResource(R.drawable.acik);
                kombidurumu.setText("Kombi Durumu: Kapalý");
            }
            else
            {
                kombiresim.setBackgroundResource(R.drawable.acikanim);
                AnimationDrawable acikAnim=(AnimationDrawable) kombiresim.getBackground();
                acikAnim.start();
                //kombiresim.setImageResource(R.drawable.kapali);
                kombidurumu.setText("Kombi Durumu: Açýk");
            }
            simdikisaat.setText("Cihaz Saati: "+ogrenciJson.getString("simdikisaat"));
            cihazip.setText("IP NO: "+ogrenciJson.getString("ipno"));
            guntarih.setHintTextColor(getResources().getColor(R.color.material_blue_grey_900));
            guntarih.setText("Güncelleme: " + ogrenciJson.getString("tarih"));


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public  void threadcalistir(){
        fetchJsonTask a = new fetchJsonTask();

        //a.execute(URL);
        Thread t6 = new Thread() {
            public void run() {

                try {
                    //sleep(5000);
                    fetchJsonTask b = new fetchJsonTask();
                    Thread.sleep(1000);
                    b.execute(URL);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //finish();
                }

            }
            //public void finish(){

            //}


        };

        if (!internetBaglantisiVarMi()) {
            Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz yok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Güncelleniyor. Ýþlemin Tamamlanmasý Ýçin 5 Saniye Bekleyiniz.", Toast.LENGTH_SHORT).show();
            t6.start();


        }
    }
    public  void threadcalistir2(){
        fetchJsonTask a = new fetchJsonTask();

        //a.execute(URL);
        Thread t6 = new Thread() {
            public void run() {

                try {
                    //sleep(5000);
                    fetchJsonTask b = new fetchJsonTask();
                    Thread.sleep(1000);
                    b.execute(URL2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //finish();
                }

            }
            //public void finish(){

            //}


        };

        if (!internetBaglantisiVarMi()) {
            Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz yok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Güncelleniyor. Ýþlemin Tamamlanmasý Ýçin 5 Saniye Bekleyiniz.", Toast.LENGTH_SHORT).show();
            t6.start();


        }
    }
}
