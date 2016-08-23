package kr.soen.project_base_2;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 김승훈 on 2016-08-23.
 */
public class GuestActivity extends AppCompatActivity {
    private ManagerActivity managerActivity = ManagerActivity.getInstance();

    String url = "http://175.126.112.137/search.php";

    TextView tv1;
    ScrollView sv1;

    public GettingPHP1 gPHP1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        managerActivity.addActivity(this);
        setContentView(R.layout.guestmode);

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
            try {
                URL phpUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while (true) {
                            String line = br.readLine();
                            if (line == null)
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
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
                list += "Status : " + jObject.get("Status");
                list += "\n";
                list += "Number of results : " + jObject.get("num_results");
                list += "\n";
                list += "results : \n";
                for (int i = 0; i < results.length(); ++i) {
                    JSONObject temp = results.getJSONObject(i);
                    list += "\tMenu : " + temp.get("menu");
                    list += "\tPrice : " + temp.get("price");
                    list += "\tInfo : " + temp.get("info");
                    list += "\n\t--------------------------------------------\n";
                }
                tv1.setText(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void go_main(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


