package kr.soen.project_base_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * Created by 김승훈 on 2016-07-25.
 */
public class SBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;

    SBackgroundWorker(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String store = params[1];

        if (type.equals("chkstore")) {
            String login_url = "http://175.126.112.137/search.php";
            String latitude = params[2];
            String longitude = params[3];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("store", "UTF-8") + "=" + URLEncoder.encode(store, "UTF-8")+"&"
                        + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8")+"&"
                        + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       else if (type.equals("recheck")) {
            String login_url = "http://175.126.112.137/search.php";
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("store", "UTF-8") + "=" + URLEncoder.encode(store, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Checking wait...");

    }

   protected void onPostExecute(String result) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                alertDialog.dismiss();
                Intent intent = new Intent(context, GuestActivity.class);
                context.startActivity(intent);
            }
           }, 1000);
/*
        if(Objects.equals(type, "yes")) {
            alertDialog.setMessage("you want this  " + rstore + "  right?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            alertDialog.dismiss();
                            Intent intent = new Intent(context, GuestActivity.class);
                            context.startActivity(intent);
                        }
                    }, 2000);
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.cancel();
                    inputDialog = new AlertDialog.Builder(context).create();
                    final View innerView = inputDialog.getLayoutInflater().inflate(R.layout.sub_main, null);
                    inputDialog.setTitle("Input store name");
                    inputDialog.setView(innerView);
                    inputDialog.setButton(DialogInterface.BUTTON_POSITIVE, "yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    inputDialog.dismiss();
                                    Intent intent = new Intent(context, GuestActivity.class);
                                    context.startActivity(intent);
                                }
                            }, 2000);
                        }
                    });
                    inputDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sstore = (EditText) innerView.findViewById(R.id.etstore);
                            rstore = sstore.getText().toString();

                            type = "recheck";
                            SBackgroundWorker sbackgroundWorker = new SBackgroundWorker(context);
                            sbackgroundWorker.execute(type, rstore);
                        }
                    });
                    inputDialog.show();
                }
            });
            alertDialog.show();
        }
        else{
            Intent intent = new Intent(context, GuestActivity.class);
            context.startActivity(intent);
        }

        */
    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
