package com.example.okul.kombikontrol;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.util.logging.Handler;


public class kombiayarlarfrm extends ActionBarActivity {
    SharedPreferences preferences;
    public static String URL = "http://turulay.com/kombiisim4.php";//Bilgisayarýn IP adresi
    String CihazID;
    String saatsec1;
    String derecesec1;
    String saatsec2;
    String derecesec2;
    String saatsec3;
    String derecesec3;
    String saatsec4;
    String derecesec4;
    String saatsec5;
    String derecesec5;
    String saatsec6;
    String derecesec6;
    String durum="0";
    String saat="00";
    String dakika="00";
    int degeral;
    String saataldeger;
    int saatkarsilastir;
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
        setContentView(R.layout.activity_kombiayarlarfrm);
        Button gonder1=(Button) findViewById(R.id.gonder1);
        Button iptal=(Button) findViewById(R.id.iptal);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        CihazID=preferences.getString("CihazID", "Cihaz ID Gir");
        //Button saatsec11txt=(Button)findViewById(R.id.saatsec11);
        final Button saatsec1txt = (Button) findViewById(R.id.saatsec1);
        final Button derecesec1txt = (Button) findViewById(R.id.derecesec1);
        final Button saatsec2txt = (Button) findViewById(R.id.saatsec2);
        final Button derecesec2txt = (Button) findViewById(R.id.derecesec2);
        final Button saatsec3txt = (Button) findViewById(R.id.saatsec3);
        final Button derecesec3txt = (Button) findViewById(R.id.derecesec3);
        final Button saatsec4txt = (Button) findViewById(R.id.saatsec4);
        final Button derecesec4txt = (Button) findViewById(R.id.derecesec4);
        final Button saatsec5txt = (Button) findViewById(R.id.saatsec5);
        final Button derecesec5txt = (Button) findViewById(R.id.derecesec5);
        final Button saatsec6txt = (Button) findViewById(R.id.saatsec6);
        final Button derecesec6txt = (Button) findViewById(R.id.derecesec6);
        durum="0";
        threadcalistir();
        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gonder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                durum = "1";
                //Button saatsec11txt=(Button)findViewById(R.id.saatsec11);
                final Button saatsec1txt = (Button) findViewById(R.id.saatsec1);
                saatsec1 = saatsec1txt.getText().toString();
                final Button derecesec1txt = (Button) findViewById(R.id.derecesec1);
                derecesec1 = derecesec1txt.getText().toString();
                final Button saatsec2txt = (Button) findViewById(R.id.saatsec2);
                saatsec2 = saatsec2txt.getText().toString();
                final Button derecesec2txt = (Button) findViewById(R.id.derecesec2);
                derecesec2 = derecesec2txt.getText().toString();
                final Button saatsec3txt = (Button) findViewById(R.id.saatsec3);
                saatsec3 = saatsec3txt.getText().toString();
                final Button derecesec3txt = (Button) findViewById(R.id.derecesec3);
                derecesec3 = derecesec3txt.getText().toString();
                final Button saatsec4txt = (Button) findViewById(R.id.saatsec4);
                saatsec4 = saatsec4txt.getText().toString();
                final Button derecesec4txt = (Button) findViewById(R.id.derecesec4);
                derecesec4 = derecesec4txt.getText().toString();
                final Button saatsec5txt = (Button) findViewById(R.id.saatsec5);
                saatsec5 = saatsec5txt.getText().toString();
                final Button derecesec5txt = (Button) findViewById(R.id.derecesec5);
                derecesec5 = derecesec5txt.getText().toString();
                final Button saatsec6txt = (Button) findViewById(R.id.saatsec6);
                saatsec6 = saatsec6txt.getText().toString();
                final Button derecesec6txt = (Button) findViewById(R.id.derecesec6);
                derecesec6 = derecesec6txt.getText().toString();
                threadcalistir();
                finish();



            }
        });
        derecesec1txt.setOnClickListener(new View.OnClickListener() {
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
                degeral=Integer.parseInt(derecesec1txt.getText().toString());
                np.setValue(degeral);
                np.setWrapSelectorWheel(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result

                                derecesec1txt.setText(Integer.toString(np.getValue()));
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

                /*NumberPicker np;
                np = (NumberPicker) findViewById(R.id.numberPicker1);
                np.setMinValue(10);
                np.setMaxValue(40);
                np.setWrapSelectorWheel(false);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        derecesec1txt.setText(Integer.toString(newVal));
                    }
                });*/
            }
        });
        derecesec2txt.setOnClickListener(new View.OnClickListener() {
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
                degeral=Integer.parseInt(derecesec2txt.getText().toString());
                np.setValue(degeral);
                np.setWrapSelectorWheel(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result

                                derecesec2txt.setText(Integer.toString(np.getValue()));
                            }
                        })
                        .setNegativeButton("Ýptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
            }
        });
        derecesec3txt.setOnClickListener(new View.OnClickListener() {
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
                degeral=Integer.parseInt(derecesec3txt.getText().toString());
                np.setValue(degeral);
                np.setWrapSelectorWheel(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result

                                derecesec3txt.setText(Integer.toString(np.getValue()));
                            }
                        })
                        .setNegativeButton("Ýptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
            }
        });
        derecesec4txt.setOnClickListener(new View.OnClickListener() {
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
                degeral=Integer.parseInt(derecesec4txt.getText().toString());
                np.setValue(degeral);
                np.setWrapSelectorWheel(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result

                                derecesec4txt.setText(Integer.toString(np.getValue()));
                            }
                        })
                        .setNegativeButton("Ýptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
            }
        });
        derecesec5txt.setOnClickListener(new View.OnClickListener() {
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
                degeral=Integer.parseInt(derecesec5txt.getText().toString());
                np.setValue(degeral);
                np.setWrapSelectorWheel(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result

                                derecesec5txt.setText(Integer.toString(np.getValue()));
                            }
                        })
                        .setNegativeButton("Ýptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
            }
        });
        derecesec6txt.setOnClickListener(new View.OnClickListener() {
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
                degeral=Integer.parseInt(derecesec6txt.getText().toString());
                np.setValue(degeral);
                np.setWrapSelectorWheel(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result

                                derecesec6txt.setText(Integer.toString(np.getValue()));
                            }
                        })
                        .setNegativeButton("Ýptal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
            }
        });
        saatsec2txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour;
                int minute;
                saatkarsilastir=Integer.parseInt(saatsec1txt.getText().toString().substring(0,2));
                //Integer.parseInt(saatkarsilastir.substring(0,2);
                saataldeger=saatsec2txt.getText().toString();
                if(saataldeger.length()==5){
                    hour=Integer.parseInt(saataldeger.substring(0,2));
                    //minute=Integer.parseInt(saataldeger.substring(4,5));
                    minute=0;
                }
                else{
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldýk
                    //minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayý aldýk
                    minute=0;
                     }
                TimePickerDialog timePicker; //Time Picker referansýmýzý oluþturduk
                  //TimePicker objemizi oluþturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(kombiayarlarfrm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedHour<=saatkarsilastir){
                            saat=Integer.toString(saatkarsilastir);
                        }else
                        {
                            saat=Integer.toString(selectedHour);
                        }
                        dakika=Integer.toString(selectedMinute);
                        if(saat.length()==1){
                            saat="0"+saat;
                        }
                        if(dakika.length()==1){
                            dakika="0"+dakika;
                        }
                        saatsec2txt.setText(saat + ":" + dakika);//Ayarla butonu týklandýðýnda textview'a yazdýrýyoruz
                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                //timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Ýptal", timePicker);

                timePicker.show();
            }
        });
        saatsec3txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour;
                int minute;
                saatkarsilastir=Integer.parseInt(saatsec2txt.getText().toString().substring(0,2));
                //Integer.parseInt(saatkarsilastir.substring(0,2);
                saataldeger=saatsec3txt.getText().toString();
                if(saataldeger.length()==5){
                    hour=Integer.parseInt(saataldeger.substring(0,2));
                    //minute=Integer.parseInt(saataldeger.substring(4,5));
                    minute=0;
                }
                else{
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldýk
                    //minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayý aldýk
                    minute=0;
                }
                TimePickerDialog timePicker; //Time Picker referansýmýzý oluþturduk

                //TimePicker objemizi oluþturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(kombiayarlarfrm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour<=saatkarsilastir){
                            saat=Integer.toString(saatkarsilastir);
                        }else
                        {
                            saat=Integer.toString(selectedHour);
                        }
                        dakika=Integer.toString(selectedMinute);
                        if(saat.length()==1){
                            saat="0"+saat;
                        }
                        if(dakika.length()==1){
                            dakika="0"+dakika;
                        }
                        saatsec3txt.setText(saat + ":" + dakika);//Ayarla butonu týklandýðýnda textview'a yazdýrýyoruz

                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                //timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Ýptal", timePicker);

                timePicker.show();
            }

        });
        saatsec4txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour;
                int minute;
                saatkarsilastir=Integer.parseInt(saatsec3txt.getText().toString().substring(0,2));
                //Integer.parseInt(saatkarsilastir.substring(0,2);
                saataldeger=saatsec4txt.getText().toString();
                if(saataldeger.length()==5){
                    hour=Integer.parseInt(saataldeger.substring(0,2));
                    //minute=Integer.parseInt(saataldeger.substring(4,5));
                    minute=0;
                }
                else{
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldýk
                    //minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayý aldýk
                    minute=0;
                }
                TimePickerDialog timePicker; //Time Picker referansýmýzý oluþturduk

                //TimePicker objemizi oluþturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(kombiayarlarfrm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour<=saatkarsilastir){
                            saat=Integer.toString(saatkarsilastir);
                        }else
                        {
                            saat=Integer.toString(selectedHour);
                        }
                        dakika=Integer.toString(selectedMinute);
                        if(saat.length()==1){
                            saat="0"+saat;
                        }
                        if(dakika.length()==1){
                            dakika="0"+dakika;
                        }
                        saatsec4txt.setText(saat + ":" + dakika);//Ayarla butonu týklandýðýnda textview'a yazdýrýyoruz
                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                //timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Ýptal", timePicker);

                timePicker.show();
            }
        });
        saatsec5txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour;
                int minute;
                saatkarsilastir=Integer.parseInt(saatsec4txt.getText().toString().substring(0,2));
                //Integer.parseInt(saatkarsilastir.substring(0,2);
                saataldeger=saatsec5txt.getText().toString();
                if(saataldeger.length()==5){
                    hour=Integer.parseInt(saataldeger.substring(0,2));
                    //minute=Integer.parseInt(saataldeger.substring(4,5));
                    minute=0;
                }
                else{
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldýk
                    //minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayý aldýk
                    minute=0;
                }
                TimePickerDialog timePicker; //Time Picker referansýmýzý oluþturduk

                //TimePicker objemizi oluþturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(kombiayarlarfrm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour<=saatkarsilastir){
                            saat=Integer.toString(saatkarsilastir);
                        }else
                        {
                            saat=Integer.toString(selectedHour);
                        }
                        dakika=Integer.toString(selectedMinute);
                        if(saat.length()==1){
                            saat="0"+saat;
                        }
                        if(dakika.length()==1){
                            dakika="0"+dakika;
                        }
                        saatsec5txt.setText(saat + ":" + dakika);//Ayarla butonu týklandýðýnda textview'a yazdýrýyoruz
                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                //timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Ýptal", timePicker);

                timePicker.show();
            }
        });
        saatsec6txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour;
                int minute;
                saatkarsilastir=Integer.parseInt(saatsec5txt.getText().toString().substring(0,2));
                //Integer.parseInt(saatkarsilastir.substring(0,2);
                saataldeger=saatsec6txt.getText().toString();
                if(saataldeger.length()==5){
                    hour=Integer.parseInt(saataldeger.substring(0,2));
                    //minute=Integer.parseInt(saataldeger.substring(4,5));
                    minute=0;
                }
                else{
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldýk
                    //minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayý aldýk
                    minute=0;
                }
                TimePickerDialog timePicker; //Time Picker referansýmýzý oluþturduk

                //TimePicker objemizi oluþturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(kombiayarlarfrm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedHour<=saatkarsilastir){
                            saat=Integer.toString(saatkarsilastir);
                        }else
                        {
                            saat=Integer.toString(selectedHour);
                        }
                        dakika=Integer.toString(selectedMinute);
                        if(saat.length()==1){
                            saat="0"+saat;
                        }
                        if(dakika.length()==1){
                            dakika="0"+dakika;
                        }
                        saatsec6txt.setText(saat + ":" + dakika);//Ayarla butonu týklandýðýnda textview'a yazdýrýyoruz
                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                //timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Ýptal", timePicker);

                timePicker.show();
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kombiayarlarfrm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static String connect(String url,String CihazID,String saatsec1,String derecesec1,String saatsec2,String derecesec2,String saatsec3,String derecesec3,String saatsec4,String derecesec4,String saatsec5,String derecesec5,String saatsec6,String derecesec6,String durum){
        HttpClient httpClient=new DefaultHttpClient();
        //HttpGet httpget = new HttpGet(url);
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=UTF-8");
        httppost.addHeader("User-Agent", "Mozilla/4.0");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("cihazID", CihazID));
        params.add(new BasicNameValuePair("saatsec1", saatsec1));
        params.add(new BasicNameValuePair("derecesec1", derecesec1));
        params.add(new BasicNameValuePair("saatsec2", saatsec2));
        params.add(new BasicNameValuePair("derecesec2", derecesec2));
        params.add(new BasicNameValuePair("saatsec3", saatsec3));
        params.add(new BasicNameValuePair("derecesec3", derecesec3));
        params.add(new BasicNameValuePair("saatsec4", saatsec4));
        params.add(new BasicNameValuePair("derecesec4", derecesec4));
        params.add(new BasicNameValuePair("saatsec5", saatsec5));
        params.add(new BasicNameValuePair("derecesec5", derecesec5));
        params.add(new BasicNameValuePair("saatsec6", saatsec6));
        params.add(new BasicNameValuePair("derecesec6", derecesec6));
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
                String ret = connect(params[0],CihazID,saatsec1,derecesec1,saatsec2,derecesec2,saatsec3,derecesec3,saatsec4,derecesec4,saatsec5,derecesec5,saatsec6,derecesec6,durum);
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
        Button saatsec1txt = (Button) findViewById(R.id.saatsec1);
        Button derecesec1txt = (Button) findViewById(R.id.derecesec1);
        Button saatsec2txt = (Button) findViewById(R.id.saatsec2);
        Button derecesec2txt = (Button) findViewById(R.id.derecesec2);
        Button saatsec3txt = (Button) findViewById(R.id.saatsec3);
        Button derecesec3txt = (Button) findViewById(R.id.derecesec3);
        Button saatsec4txt = (Button) findViewById(R.id.saatsec4);
        Button derecesec4txt = (Button) findViewById(R.id.derecesec4);
        Button saatsec5txt = (Button) findViewById(R.id.saatsec5);
        Button derecesec5txt = (Button) findViewById(R.id.derecesec5);
        Button saatsec6txt = (Button) findViewById(R.id.saatsec6);
        Button derecesec6txt = (Button) findViewById(R.id.derecesec6);

        System.out.println(ogrenciJson);
        try {
            //saatsec11txt.setText(ogrenciJson.getString("saatsec1"));
            saatsec1txt.setText(ogrenciJson.getString("saatsec1"));
            derecesec1txt.setText(ogrenciJson.getString("derecesec1"));
            saatsec2txt.setText(ogrenciJson.getString("saatsec2"));
            derecesec2txt.setText(ogrenciJson.getString("derecesec2"));
            saatsec3txt.setText(ogrenciJson.getString("saatsec3"));
            derecesec3txt.setText(ogrenciJson.getString("derecesec3"));
            saatsec4txt.setText(ogrenciJson.getString("saatsec4"));
            derecesec4txt.setText(ogrenciJson.getString("derecesec4"));
            saatsec5txt.setText(ogrenciJson.getString("saatsec5"));
            derecesec5txt.setText(ogrenciJson.getString("derecesec5"));
            saatsec6txt.setText(ogrenciJson.getString("saatsec6"));
            derecesec6txt.setText(ogrenciJson.getString("derecesec6"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public  void threadcalistir(){
        fetchJsonTask a = new fetchJsonTask();

        a.execute(URL);
        Thread t6 = new Thread() {
            public void run() {

                try {
                    //sleep(5000);
                    fetchJsonTask b = new fetchJsonTask();
                    Thread.sleep(10000);
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
            Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz yok", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz var. Ýþlemin Tamamlanmasý Ýçin 15 Saniye Bekleyiniz.", Toast.LENGTH_LONG).show();
            t6.start();


        }
    }
}
