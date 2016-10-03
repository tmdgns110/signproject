package kr.soen.project_base_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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


/**
 * Created by 김승훈 on 2016-07-25.
 */
public class MBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String stype;

    MBackgroundWorker(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        stype=type;

            String login_url = "http://175.126.112.137/employee/main.php";
            try {
                String ID = params[1];
                String menu = params[2];
                String price = params[3];
                String info = params[4];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                        + URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8")+"&"
                        + URLEncoder.encode("menu", "UTF-8") + "=" + URLEncoder.encode(menu, "UTF-8")+"&"
                        +URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8")+"&"
                        +URLEncoder.encode("info","UTF-8")+"="+URLEncoder.encode(info,"UTF-8");
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


        return null;
    }



    protected void onPreExecute() {

            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Modify Status");

    }

    protected void onPostExecute(String result) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
                                public void run(){
                                    alertDialog.dismiss();
                                    Intent intent = new Intent(context, ModifyActivity.class);
                                    context.startActivity(intent);
                                }
                            }, 2000);

    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
