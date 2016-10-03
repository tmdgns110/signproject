package kr.soen.project_base_2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import android.Manifest;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;



class ManagerActivity{

    private static ManagerActivity managerActivity = null;
    private ArrayList<Activity>activityList=null;

    private ManagerActivity(){
        activityList = new ArrayList<Activity>();
    }

    public static ManagerActivity getInstance() {
        if(ManagerActivity.managerActivity == null){
            managerActivity = new ManagerActivity();
        }
        return managerActivity;
    }

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public boolean removeActivity(Activity activity){
        return activityList.remove(activity);
    }

    public void finishAllActivity() {
        for(Activity activity : activityList){
            activity.finish();
        }
    }

}




public class MainActivity extends Activity implements OnClickListener {

    private static final int CAPTURE_FROM_PAD =2 ;
    private ManagerActivity managerActivity = ManagerActivity.getInstance();
    private static final int PICK_FROM_CAMERA = 0;
    private static final int CROP_FROM_CAMERA = 1;
    public static final String PACKAGE_NAME = "kr.soen.project_base_2";
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/SimpleAndroidOCR/";
    public static final String lang = "eng";

    private static final String TAG = "mainActivity.java";
    protected EditText _field;
    protected String _path;
    protected boolean _taken;

    LocationManager manager;
    double Latitude;
    double Longitude;
    String Capture_path = DATA_PATH + "test_picture1";

    protected static final String PHOTO_TAKEN = "photo_taken";

    public static Context mContext;
    private Uri mImageCaptureUri;
    private ImageView mPhotoImageView;
    private ImageButton mButton;
    private ImageButton eButton;
    private ImageButton pButton;
    //    뒤로 버튼 누를 때 창 뜨는
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        //퍼미션 권한 설정
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        // TedPermission 라이브러리 이용 권한 얻어오기
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.CAMERA)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.GET_ACCOUNTS)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();


        String[] paths = new String[]{DATA_PATH, DATA_PATH + "tessdata/"};

        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }

        }

        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
            try {

                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
                //GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(DATA_PATH
                        + "tessdata/" + lang + ".traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                //while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                //gin.close();
                out.close();

                Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
            }
        }


        // _image = (ImageView) findViewById(R.id.image);
        View v = getLayoutInflater().inflate(R.layout.guestmode, null);
        _field = (EditText) v.findViewById(R.id.field);

        _path = DATA_PATH + "/ocr.jpg";

        managerActivity.addActivity(this);
        SharedPreferences mPref = getSharedPreferences("mPref", MODE_PRIVATE);
        String maintainid = mPref.getString("maintainid", "");
        String maintainpw = mPref.getString("maintainpw", "");
        if (Objects.equals(maintainid, "") || Objects.equals(maintainpw, "")) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_maintain);
        }


        mButton = (ImageButton) findViewById(R.id.Photo);
        eButton = (ImageButton) findViewById(R.id.Exit);
        pButton = (ImageButton) findViewById(R.id.Pad);

        mPhotoImageView = (ImageView) findViewById(R.id.image);


        mButton.setOnClickListener(this);
        eButton.setOnClickListener(this);
        pButton.setOnClickListener(this);

        backPressCloseHandler = new BackPressCloseHandler(this);



    }

    private void startLocationService() {

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location lastLocation = null;
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastLocation != null) {
                double latitude = lastLocation.getLatitude();
                double longitude = lastLocation.getLongitude();
                Latitude = latitude;
                Longitude = longitude;
            } else {
                Toast.makeText(getApplicationContext(), "GPS 서비스를 실행시켜주십시오.", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }


    }

    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String msg = "" + latitude + "\n" + longitude;
            Log.i("GPSListener", msg);

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }


    private void doTakePhotoAction() {

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mImageCaptureUri = Uri.fromFile(new File(_path));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void doTakeCaptureAction() {
        Intent intent = new Intent(this, PadActivity.class);
        startActivityForResult(intent,CAPTURE_FROM_PAD);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "resultCode: " + resultCode);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_FROM_CAMERA: {
                final Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    // mPhotoImageView.setImageBitmap(photo);
                    File copyFile = new File(_path);
                    BufferedOutputStream out = null;
                    try {
                        copyFile.createNewFile();
                        out = new BufferedOutputStream(new FileOutputStream(copyFile));
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onPhotoTaken();
                }

                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

                break;

            }

            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 90);
                intent.putExtra("outputY", 90);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }

            case CAPTURE_FROM_PAD: {
                Capture_path = data.getStringExtra("path");
                _path = Capture_path;
                onPhotoTaken();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(MainActivity.PHOTO_TAKEN, _taken);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState()");
        if (savedInstanceState.getBoolean(MainActivity.PHOTO_TAKEN)) {
            onPhotoTaken();
        }
    }

    protected void onPhotoTaken() {
        _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(_path, options);

        options.inScaled = true;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;


        int iWidth = bitmap.getWidth();      //비트맵이미지의 넓이
        int iHeight = bitmap.getHeight();     //비트맵이미지의 높이
        int dstWidth = iWidth;
        int dstHeight = iHeight;
        int maxResolution = (int) Math.sqrt(width * height);
        float rate = 0.0f;
        //이미지의 가로 세로 비율에 맞게 조절
        if (iWidth > iHeight) {
            if (maxResolution < iWidth) {
                rate = maxResolution / (float) iWidth;
                dstHeight = (int) (iHeight * rate);
                dstWidth = maxResolution;
            }
        } else {
            if (maxResolution < iHeight) {
                rate = maxResolution / (float) iHeight;
                dstWidth = (int) (iWidth * rate);
                dstHeight = maxResolution;
            }
        }

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
        mPhotoImageView.setImageBitmap(resized);

        try {
            ExifInterface exif = new ExifInterface(_path);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            Log.v(TAG, "Orient: " + exifOrientation);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            Log.v(TAG, "Rotation: " + rotate);

            if (rotate != 0) {

                // Getting width & height of the given image.
                int w = resized.getWidth();
                int h = resized.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                resized = Bitmap.createBitmap(resized, 0, 0, w, h, mtx, false);
            }

            // Convert to ARGB_8888, required by tess
            resized = resized.copy(Bitmap.Config.ARGB_8888, true);

        } catch (IOException e) {
            Log.e(TAG, "Couldn't correct orientation: " + e.toString());
        }

        // _image.setImageBitmap( bitmap );

        Log.v(TAG, "Before baseApi");

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(DATA_PATH, lang);
        baseApi.setImage(resized);

        String recognizedText = baseApi.getUTF8Text();

        baseApi.end();

        // You now have the text in recognizedText var, you can do anything with it.
        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
        // so that garbage doesn't make it to the display.

        Log.v(TAG, "OCRED TEXT: " + recognizedText);

        if (lang.equalsIgnoreCase("eng")) {
            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
        }

        recognizedText = recognizedText.trim();
        startLocationService();

        final String Lati = Double.toString(Latitude);
        final String Longi = Double.toString(Longitude);

        if (recognizedText.length() != 0) {
            _field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
            _field.setSelection(_field.getText().toString().length());

            Intent intent = new Intent(this, GuestActivity.class);
            intent.putExtra("store", recognizedText);
            intent.putExtra("Lat", Lati);
            intent.putExtra("Lon", Longi);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Photo) {
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

        if (v.getId() == R.id.Pad) {
            DialogInterface.OnClickListener captureListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeCaptureAction();
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
                    .setPositiveButton("Pad 실행", captureListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();

        }
        if (v.getId() == R.id.Exit) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
            alert_confirm.setMessage("프로그램을 종료 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            moveTaskToBack(true);
                            finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }

            });
            AlertDialog alert = alert_confirm.create();
            alert.show();


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerActivity.removeActivity(this);
    }

    public void LOGIN(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void LOGOUT(View view) {
        SharedPreferences mPref = getSharedPreferences("mPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Modify(View view) {
        Intent intent = new Intent(this, ModifyActivity.class);
        startActivity(intent);
    }


    public class BackPressCloseHandler {

        private long BackKeyPressedTime = 0;
        private Toast toast;

        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > BackKeyPressedTime + 2000) {
                BackKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }

            if (System.currentTimeMillis() <= BackKeyPressedTime + 2000) {
                activity.finish();
                toast.cancel();
                ManagerActivity.getInstance().finishAllActivity();
            }
        }

        private void showGuide() {
            toast = Toast.makeText(activity, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }



}

