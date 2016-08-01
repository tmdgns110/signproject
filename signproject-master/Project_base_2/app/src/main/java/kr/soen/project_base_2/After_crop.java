package kr.soen.project_base_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;


public class After_crop extends Activity {

    public ImageView mPhotoImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_crop);

        mPhotoImageView=(ImageView)findViewById(R.id.image);

        Intent intent = getIntent();
        Bitmap image = intent.getParcelableExtra("photo");

        mPhotoImageView.setImageBitmap(image);



    }


}
