package kr.soen.project_base_2;

/**
 * Created by 김승훈 on 2016-07-20.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Toast;
import android.content.SharedPreferences;

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
 * Created by 김승훈 on 2016-07-18.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker(Context ctx) {
        context = ctx;
    }
    String loginid;
    String loginpw;


    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String username =params[1];
        String password = params[2];
        loginid = username;
        loginpw = password;
        String login_url = "http://175.126.112.137/employee/login.php";
        if(type.equals("login")) {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    result+=line;
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

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
            alertDialog.setMessage(result);
            alertDialog.show();

        if(Objects.equals(result, "login not success")) {

        }
        else{

            SharedPreferences mPref = context.getSharedPreferences("mPref",context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString("maintainid",loginid);
           editor.putString("maintainpw",loginpw);
            editor.commit();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run(){
                    alertDialog.dismiss();
                    Intent intent = new Intent(context, ModifyActivity.class);
                    context.startActivity(intent);
                }
            }, 2000);
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}

