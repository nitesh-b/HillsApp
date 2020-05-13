package com.hills.hills11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AlwaysLowPrice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.always_low_price );
        ImageView backPressed = findViewById ( R.id.backImg );
        backPressed.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                finish ( );
                startActivity ( new Intent ( AlwaysLowPrice.this , MainActivity.class ) );
            }
        } );


    }
}
