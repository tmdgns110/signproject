package kr.soen.project_base_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.MapView;

/**
 * Created by 김승훈 on 2016-08-25.
 */
public class Map extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("7413f11affecbd9bd5d08667bf398350");

//xml에 선언된 map_view 레이아웃을 찾아온 후, 생성한 MapView객체 추가
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);

        container.addView(mapView);

    }

}
