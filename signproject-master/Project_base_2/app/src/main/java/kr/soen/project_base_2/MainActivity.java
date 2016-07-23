package kr.soen.project_base_2;

import java.io.File;


import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private static final int PICK_FROM_CAMERA =0;
    private static final int CROP_FROM_CAMERA =1;

    private Uri mImageCaptureUri;
//    private ImageView mPhotoImageView;
    private ImageButton mButton;
    private ImageButton eButton;

//    뒤로 버튼 누를 때 창 뜨는
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (ImageButton)findViewById(R.id.Photo);
        eButton = (ImageButton)findViewById(R.id.Exit);

//        mPhotoImageView = (ImageView)findViewById(R.id.image);


        mButton.setOnClickListener(this);
        eButton.setOnClickListener(this);

        backPressCloseHandler = new BackPressCloseHandler(this);
    }





    private void doTakePhotoAction()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + "jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK)
        {
            return;
        }

        switch(requestCode)
        {
            case CROP_FROM_CAMERA:
            {
                final Bundle extras = data.getExtras();

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
//                    mPhotoImageView.setImageBitmap(photo);

                    Intent intent = new Intent(getBaseContext(),After_crop.class);

                    intent.putExtra("photo",photo);




                    startActivity(intent);
                    break;
                }

                File f =new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
                break;

        }

            case PICK_FROM_CAMERA:
            {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");

                intent.putExtra("outputX",90);
                intent.putExtra("outputY",90);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.Photo) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }

        if(v.getId() == R.id.Exit){
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
            alert_confirm.setMessage("프로그램을 종료 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            moveTaskToBack(true);
                            finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                    }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }

            });
            AlertDialog alert = alert_confirm.create();
            alert.show();



    }}

    public void LOGIN(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


public class BackPressCloseHandler{

    private long BackKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context){
        this.activity = context;
    }

    public void onBackPressed(){
        if(System.currentTimeMillis() > BackKeyPressedTime + 2000){
            BackKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }

        if(System.currentTimeMillis() <= BackKeyPressedTime + 2000){
            activity.finish();
            toast.cancel();
        }
    }

    private void showGuide(){
        toast = Toast.makeText(activity, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다",Toast.LENGTH_SHORT);
        toast.show();
    }

}


    @Override
    public void onBackPressed()
    {
        backPressCloseHandler.onBackPressed();
    }



}

