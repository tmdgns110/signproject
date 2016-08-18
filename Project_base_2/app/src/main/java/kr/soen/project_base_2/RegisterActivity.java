package kr.soen.project_base_2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private ManagerActivity managerActivity = ManagerActivity.getInstance();

    EditText RUsernameEt, RPasswordEt, RPasswordEtConf, RStoreEt, RBranchEt, REmailEt, RPnEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerActivity.addActivity(this);
        setContentView(R.layout.activity_register);
        RUsernameEt = (EditText)findViewById(R.id.RetUserName);
        RPasswordEt = (EditText)findViewById(R.id.RetPassword);
        RPasswordEtConf = (EditText)findViewById(R.id.RetPasswordConf);
        RStoreEt = (EditText)findViewById(R.id.RetStore);
        RBranchEt = (EditText)findViewById(R.id.RetBranch);
        REmailEt = (EditText)findViewById(R.id.RetEmail);
        RPnEt = (EditText)findViewById(R.id.RetPn);



        RPasswordEtConf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                String password = RPasswordEt.getText().toString();
                String confirm = RPasswordEtConf.getText().toString();

                if(password.equals(confirm)){
                    RPasswordEt.setBackgroundColor(Color.GREEN);
                    RPasswordEtConf.setBackgroundColor(Color.GREEN);
                }
                else {
                    RPasswordEt.setBackgroundColor(Color.RED);
                    RPasswordEtConf.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void LBACK(View view) {
        finish();
    }

    public void OnCheck(View view) {
        String username = RUsernameEt.getText().toString();
        String type = "check";
        if(username.length()!=0 && username!="Email") {
            RBackgroundWorker rbackgroundWorker = new RBackgroundWorker(this);
            rbackgroundWorker.execute(type, username);
        }
        else{
            Toast.makeText(RegisterActivity.this,"Email을 입력하세요", Toast.LENGTH_SHORT).show();
            RUsernameEt.requestFocus();
            return;

        }


    }

/*
    public void CStore(View view) {
        String store = RStoreEt.getText().toString();
        String type = "check1";
        if(store.length()!=0) {
            RBackgroundWorker rbackgroundWorker = new RBackgroundWorker(this);
            rbackgroundWorker.execute(type, store);
        }
        else{
            Toast.makeText(RegisterActivity.this,"가게명을 입력하세요", Toast.LENGTH_SHORT).show();
            RStoreEt.requestFocus();
            return;

        }

    }
*/
    public void CBranch(View view) {
        String store = RStoreEt.getText().toString();
        String branch = RBranchEt.getText().toString();
        String type = "check2";
        if((store.length()!=0) && (branch.length()!=0) && (store!="STORE") && (branch!="BRANCH")) {
            RBackgroundWorker rbackgroundWorker = new RBackgroundWorker(this);
            rbackgroundWorker.execute(type, store,branch);
        }
        else{
            Toast.makeText(RegisterActivity.this,"점포명을 입력하세요", Toast.LENGTH_SHORT).show();
            RBranchEt.requestFocus();
            return;

        }
    }




    public void REGISTER(View view) {
        if((RUsernameEt.getText().toString().length()==0) && (RUsernameEt.getText().toString() !="ID")){
            Toast.makeText(RegisterActivity.this,"ID을 입력하세요", Toast.LENGTH_SHORT).show();
            RUsernameEt.requestFocus();
            return;
        }

        if(RPasswordEt.getText().toString().length()==0){
            Toast.makeText(RegisterActivity.this,"비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            RPasswordEt.requestFocus();
            return;
        }

        if(RPasswordEtConf.getText().toString().length()==0){
            Toast.makeText(RegisterActivity.this,"비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
            RPasswordEtConf.requestFocus();
            return;
        }

        if( !RPasswordEt.getText().toString().equals(RPasswordEtConf.getText().toString())){
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

        if ((RBranchEt.getText().toString().length() == 0) && (RBranchEt.getText().toString() !="BRANCH")) {
            Toast.makeText(RegisterActivity.this, "지점을 입력하세요", Toast.LENGTH_SHORT).show();
            RBranchEt.requestFocus();
            return;
        }

        if ((REmailEt.getText().toString().length() == 0) && (REmailEt.getText().toString() !="Email")) {
            Toast.makeText(RegisterActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            REmailEt.requestFocus();
            return;
        }

        if ((RPnEt.getText().toString().length() == 0) && (RPnEt.getText().toString() !="PhoneNumber")) {
            Toast.makeText(RegisterActivity.this, "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
            RPnEt.requestFocus();
            return;
        }

        if((RUsernameEt.getText().toString().length()!=0) && (RPasswordEt.getText().toString().length()!=0) && (RPasswordEtConf.getText().toString().length()!=0)
                && ( RPasswordEt.getText().toString().equals(RPasswordEtConf.getText().toString())) && (RStoreEt.getText().toString().length() != 0)
                && (RBranchEt.getText().toString().length() != 0) && (RUsernameEt.getText().toString() !="ID") && (RStoreEt.getText().toString() != "STORE")
                && (RBranchEt.getText().toString() !="BRANCH") && (REmailEt.getText().toString().length() != 0) && (REmailEt.getText().toString() !="Email")
                && (RPnEt.getText().toString().length() != 0) && (RPnEt.getText().toString() !="PhoneNumber")) {
            String username = RUsernameEt.getText().toString();
            String password = RPasswordEt.getText().toString();
            String store = RStoreEt.getText().toString();
            String branch = RBranchEt.getText().toString();
            String email = REmailEt.getText().toString();
            String hp = RPnEt.getText().toString();
            String type = "register";
            RBackgroundWorker rbackgroundWorker = new RBackgroundWorker(this);
            rbackgroundWorker.execute(type, username, password, store, branch, email, hp);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerActivity.removeActivity(this);
    }



}
