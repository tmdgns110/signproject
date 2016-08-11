package kr.soen.project_base_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ModifyActivity extends AppCompatActivity {

    EditText MenuEt, PriceEt, InfoEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MenuEt = (EditText)findViewById(R.id.etMenu);
        PriceEt = (EditText)findViewById(R.id.etPrice);
        InfoEt = (EditText)findViewById(R.id.etInfo);
    }

    public void Modify(View view) {
        String menu = MenuEt.getText().toString();
        String price = PriceEt.getText().toString();
        String info = InfoEt.getText().toString();
        String type = "modi";
        MBackgroundWorker mbackgroundWorker = new MBackgroundWorker(this);
        mbackgroundWorker.execute(type, menu, price, info);


    }


    }
