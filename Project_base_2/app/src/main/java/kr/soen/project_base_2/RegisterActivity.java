package kr.soen.project_base_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private ManagerActivity managerActivity = ManagerActivity.getInstance();

    EditText RUsernameEt;
    EditText RPasswordEt;
    EditText RPasswordEtConf;
    EditText RStoreEt;
    EditText REmailEt;
    EditText RPnEt;
    EditText RGetAddress;
    String Lon;
    String Lat;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerActivity.addActivity(this);
        setContentView(R.layout.activity_register);

        RUsernameEt = (EditText) findViewById(R.id.RetUserName);
        RPasswordEt = (EditText) findViewById(R.id.RetPassword);
        RPasswordEtConf = (EditText) findViewById(R.id.RetPasswordConf);
        RStoreEt = (EditText) findViewById(R.id.RetStore);
        REmailEt = (EditText) findViewById(R.id.RetEmail);
        RPnEt = (EditText) findViewById(R.id.RetPn);
        RGetAddress = (EditText) findViewById(R.id.etGetAddress);


        RPasswordEtConf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                String password = RPasswordEt.getText().toString();
                String confirm = RPasswordEtConf.getText().toString();

                if (password.equals(confirm)) {
                    RPasswordEt.setBackgroundColor(Color.GREEN);
                    RPasswordEtConf.setBackgroundColor(Color.GREEN);
                } else {
                    RPasswordEt.setBackgroundColor(Color.RED);
                    RPasswordEtConf.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void getLocation(View view) {
        final TextView tv = (TextView) findViewById(R.id.textView);
        final Geocoder geocoder = new Geocoder(this);
        List<Address> list = null;
        String address = RGetAddress.getText().toString();
        try {
            list = geocoder.getFromLocationName
                    (address, // 지역 이름
                            10); // 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if (list != null) {
            if (list.size()==0) {
                tv.setText("해당되는 주소 정보는 없습니다");
            } else {
                tv.setText(list.get(0).getAddressLine(0).toString());
                Address addr = list.get(0);
                double lat = addr.getLatitude();
                double lon = addr.getLongitude();
                Lat = Double.toString(lat);
                Lon = Double.toString(lon);


                Intent intent = new Intent(this,MapsActivity.class);
                intent.putExtra("Lat",lat);
                intent.putExtra("Lon",lon);
                startActivityForResult(intent,0);

            }
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK) {
            return;
        }
        TextView tv = (TextView) findViewById(R.id.textView);

        Lat = data.getStringExtra("Lat");
        Lon = data.getStringExtra("Lon");
        String address = data.getStringExtra("address");
        tv.setText(address);

    }

    public void LBACK(View view) {
        finish();
    }

    public void OnCheck(View view) {
        String username = RUsernameEt.getText().toString();
        String type = "check";
        if (username.length() != 0 && username != "Email") {
            RBackgroundWorker rbackgroundWorker = new RBackgroundWorker(this);
            rbackgroundWorker.execute(type, username);
        } else {
            Toast.makeText(RegisterActivity.this, "Email을 입력하세요", Toast.LENGTH_SHORT).show();
            RUsernameEt.requestFocus();
            return;

        }


    }


    public void REGISTER(View view) {


        if ((RUsernameEt.getText().toString().length() == 0) && (RUsernameEt.getText().toString() != "ID")) {
            Toast.makeText(RegisterActivity.this, "ID을 입력하세요", Toast.LENGTH_SHORT).show();
            RUsernameEt.requestFocus();
            return;
        }

        if (RPasswordEt.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            RPasswordEt.requestFocus();
            return;
        }

        if (RPasswordEtConf.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
            RPasswordEtConf.requestFocus();
            return;
        }

        if (!RPasswordEt.getText().toString().equals(RPasswordEtConf.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            RPasswordEt.setText("");
            RPasswordEtConf.setText("");
            RPasswordEt.requestFocus();
            return;
        }

        if ((RStoreEt.getText().toString().length() == 0) && (RStoreEt.getText().toString() != "STORE")) {
            Toast.makeText(RegisterActivity.this, "가게명을 입력하세요", Toast.LENGTH_SHORT).show();
            RStoreEt.requestFocus();
            return;
        }

        if ((REmailEt.getText().toString().length() == 0) && (REmailEt.getText().toString() != "Email")) {
            Toast.makeText(RegisterActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            REmailEt.requestFocus();
            return;
        }

        if ((RPnEt.getText().toString().length() == 0) && (RPnEt.getText().toString() != "PhoneNumber")) {
            Toast.makeText(RegisterActivity.this, "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
            RPnEt.requestFocus();
            return;
        }

        if ((Lat.length() == 0) || (Lon.length() == 0)) {
            Toast.makeText(RegisterActivity.this, "주소를 입력하세요", Toast.LENGTH_SHORT).show();
            RPnEt.requestFocus();
            return;
        }

        if ((RUsernameEt.getText().toString().length() != 0) && (RPasswordEt.getText().toString().length() != 0) && (RPasswordEtConf.getText().toString().length() != 0)
                && (RPasswordEt.getText().toString().equals(RPasswordEtConf.getText().toString())) && (RStoreEt.getText().toString().length() != 0)
                && (RUsernameEt.getText().toString() != "ID") && (RStoreEt.getText().toString() != "STORE")
                && (REmailEt.getText().toString().length() != 0) && (REmailEt.getText().toString() != "Email")
                && (RPnEt.getText().toString().length() != 0) && (RPnEt.getText().toString() != "PhoneNumber")
                && (Lat.length() != 0) && (Lon.length() != 0)) {
            String username = RUsernameEt.getText().toString();
            String password = RPasswordEt.getText().toString();
            String store = RStoreEt.getText().toString();
            String email = REmailEt.getText().toString();
            String hp = RPnEt.getText().toString();
            String lati =  Lat;//RLatiEt.getText().toString();
            String longi = Lon;//RLongiEt.getText().toString();
            Toast.makeText(RegisterActivity.this, "" + "" + lati + "\n" + longi, Toast.LENGTH_LONG).show();
            String type = "register";
            RBackgroundWorker rbackgroundWorker = new RBackgroundWorker(this);
            rbackgroundWorker.execute(type, username, password, store, email, hp, lati, longi);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerActivity.removeActivity(this);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Register Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://kr.soen.project_base_2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Register Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://kr.soen.project_base_2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event){

        if(KeyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        return false;
    }

}

