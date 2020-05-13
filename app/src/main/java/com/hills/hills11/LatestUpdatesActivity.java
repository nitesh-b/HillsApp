package com.hills.hills11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LatestUpdatesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_latest_updates );
        backBtn = findViewById ( R.id.backImg );
        backBtn.setOnClickListener ( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ( )) {
            case R.id.backImg:
                finish ( );
                startActivity ( new Intent ( this , MainActivity.class ) );
                break;

        }

    }
}
