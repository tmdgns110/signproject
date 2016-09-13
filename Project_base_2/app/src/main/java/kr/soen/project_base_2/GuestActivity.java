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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
                int check = jObject.getInt("Status");
                String list = "";
                list += "Status : " + jObject.get("Status");
                list += "\n";
                list += "Number of results : " + jObject.get("num_results");
                list += "\n";
                list += "results : \n";

                if(check==100) {
                    for (int i = 0; i < results.length(); ++i) {
                        JSONObject temp = results.getJSONObject(i);
                        list += "\t" + temp.get("store");
                    }
                    tv1.setText(list);
                }
                else if(check==200)
                {
                    for (int i = 0; i < results.length(); ++i) {
                        JSONObject temp = results.getJSONObject(i);
                        list += "\tMenu : " + temp.get("menu");
                        list += "\tPrice : " + temp.get("price");
                        list += "\tInfo : " + temp.get("info");
                        list += "\n\t--------------------------------------------\n";
                    }
                    tv1.setText(list);
                }
                else if(check==300) {
                    dynamicLayout = (LinearLayout)findViewById(R.id.Layout);
                    for (int i = 0; i < results.length(); ++i) {
                        JSONObject temp = results.getJSONObject(i);
                        list += "\tstore : " + temp.get("store");
                        list += "\n\t--------------------------------------------\n";
                        pushButton(temp.getString("store"));
                        /*
                        LinearLayout item = new LinearLayout(mContext);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        item.setLayoutParams(params);
                        item.setOrientation(LinearLayout.HORIZONTAL);

                        store = temp.getString("store");
                        TextView tv = new TextView(mContext);
                        tv.setLayoutParams(params);
                        tv.setText(temp.getString("store"));
                        item.addView(tv);
                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String type = "recheck";
                                //Toast.makeText(GuestActivity.this, "" + "" + store + "\n", Toast.LENGTH_LONG).show();
                                SBackgroundWorker sbackgroundWorker = new SBackgroundWorker(GuestActivity.this);
                                sbackgroundWorker.execute(type, store);
                            }
                        });
                        layout.addView(item);
                        /*
                        layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                   */
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void pushButton(String str) {
        num++;
        final Button dynamicButton = new Button(this);
        dynamicButton.setId(num);
        dynamicButton.setText(str);
        dynamicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "recheck";
                Toast.makeText(GuestActivity.this, "" + "" + dynamicButton.getText(), Toast.LENGTH_LONG).show();
                SBackgroundWorker sbackgroundWorker = new SBackgroundWorker(GuestActivity.this);
                sbackgroundWorker.execute(type, String.valueOf(dynamicButton.getText()));
            }
        });
        dynamicLayout.addView(dynamicButton,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


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


