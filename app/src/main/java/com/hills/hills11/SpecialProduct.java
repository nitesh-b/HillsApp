package com.hills.hills11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SpecialProduct extends AppCompatActivity {

    private ImageView backPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_special_product );

        backPressed = findViewById ( R.id.backImg );
        backPressed.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                finish ( );
                startActivity ( new Intent ( SpecialProduct.this , MainActivity.class ) );
            }
        } );
    }
}
