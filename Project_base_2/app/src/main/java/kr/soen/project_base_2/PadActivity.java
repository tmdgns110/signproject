package kr.soen.project_base_2;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by maeku on 2016-10-02.
 */
public class PadActivity extends AppCompatActivity{
    RelativeLayout container;
    private ManagerActivity managerActivity = ManagerActivity.getInstance();
    String result;
    MainActivity Main;
    HandwritingView handwriting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);


        container = (RelativeLayout) findViewById(R.id.heyboard);
        setHandWriting();
        findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener(){
                                                              @Override
                                                              public void onClick(View view){
                                                                  container.buildDrawingCache();
                                                                  Bitmap captureView = container.getDrawingCache();
                                                                  result = save(captureView,"/SimpleAndroidOCR/","test_picture1");
                                                                  Intent intent = new Intent();
                                                                  intent.putExtra("path",result);
                                                                  setResult(RESULT_OK,intent);
                                                                  finish();
                                                              } // ONCLICK
                                                          }
        );}


    public String save(Bitmap bitmap, String folder, String name){
        String storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folder_name= folder;
        String file_name = name + ".jpg";
        String string_path = storage + folder_name;
        String Capture_path = string_path + file_name;
        File file_path;

        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(Capture_path);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();
        }catch(FileNotFoundException e){
            Log.e("FNFE",e.getMessage());
        }catch(IOException e){
            Log.e("IOE",e.getMessage());
        }

        return Capture_path;
    }

    public void setHandWriting(){
        RelativeLayout C_layout = (RelativeLayout)findViewById(R.id.heyboard);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        handwriting = new HandwritingView(this);
        handwriting.setLayoutParams(params);
        handwriting.setPadding(2,2,2,2);
        C_layout.addView(handwriting);
    }


}
