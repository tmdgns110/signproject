package kr.soen.project_base_2;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
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
import java.net.URLEncoder;
import java.util.Objects;

/**
 * Created by 김승훈 on 2016-08-23.
 */
public class GuestActivity extends AppCompatActivity {
    private ManagerActivity managerActivity = ManagerActivity.getInstance();

    String url = "http://175.126.112.137/search.php";

    TextView tv1;
    ScrollView sv1;
    Context mContext;
    LinearLayout dynamicLayout;
    int num =0;

    public GettingPHP1 gPHP1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        managerActivity.addActivity(this);
        setContentView(R.layout.guestmode);
        mContext = this;
        gPHP1 = new GettingPHP1();


        tv1 = (TextView) findViewById(R.id.LIST1);
        sv1 = (ScrollView) findViewById(R.id.SV1);
        tv1.setMovementMethod(new ScrollingMovementMethod());
        tv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                sv1.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        gPHP1.execute(url);
    }

    class GettingPHP1 extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            Intent intent = getIntent();
             String store = intent.getStringExtra("store");
             String Lat= intent.getStringExtra("Lat");
             String Lon = intent.getStringExtra("Lon");
            try {
                URL phpUrl = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) phpUrl.openConnection();
                if (httpURLConnection != null) {
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("store", "UTF-8") + "=" + URLEncoder.encode(store, "UTF-8")+"&"
                            + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(Lat, "UTF-8")+"&"
                            + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(Lon, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                        while (true) {
                            String line = br.readLine();
                            if (line == null)
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            try {
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                JSONObject jObject = new JSONObject(str);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("results");
                String list = "";
                    for (int i = 0; i < results.length(); ++i) {
                        JSONObject temp = results.getJSONObject(i);
                        list += "\n\t--------------------------------------------\n";
                        list += "\tMenu : " + temp.get("menu") + "\n";
                        list += "\tPrice : " + temp.get("price") + "\n";
                        list += "\tInfo : " + temp.get("info") + "\n";
                        list += "\n\t--------------------------------------------\n";
                    }
                    tv1.setText(list);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean onKeyDown(int KeyCode, KeyEvent event) {

        if (KeyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return false;
    }

    public void go_main(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


