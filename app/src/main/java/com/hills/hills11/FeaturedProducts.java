package com.hills.hills11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hills.hills11.adapters.FeaturedProductAdapter;
import com.hills.hills11.data.PreferredBrandDetails;
import com.hills.hills11.data.ProductsDetails;
import com.hills.hills11.firebase.RetrieveFromFirestore;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FeaturedProducts extends AppCompatActivity implements RetrieveFromFirestore {
    private RecyclerView mRecyclerView;
    private Button mButton;
    private FeaturedProductAdapter adapter;
    private RetrieveFromFirestore iFirebaseLoadDone;
    private Spinner mSpinner;
    private ImageView mBackImg;
    private TextView mEmptyTextView;
    private String docType = "access_control_and_intrusion";
    private String[] list = {"Access Control & Intrusion" , "CCTV and Surveillace" , "ITC"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_featured_products );
        int t = getIntent ( ).getIntExtra ( "selection" , 0 );
        mEmptyTextView = findViewById ( R.id.textViewEmpty );
        mSpinner = findViewById ( R.id.spinner );
        mBackImg = findViewById ( R.id.backImg );
        mBackImg.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                finish ( );
                startActivity ( new Intent ( FeaturedProducts.this , MainActivity.class ) );
            }
        } );
        ArrayAdapter<String> adapter = new ArrayAdapter<String> ( this , android.R.layout.simple_spinner_dropdown_item , list );
        adapter.setDropDownViewResource ( R.layout.spinner_item );
        mSpinner.setAdapter ( adapter );
        mSpinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                switch (position) {
                    case 0:
                        mSpinner.setSelection ( position );
                        loadProduct ( "access_control" );
                        break;
                    case 1:
                        mSpinner.setSelection ( position );
                        loadProduct ( "cctv" );
                        break;
                    case 2:
                        mSpinner.setSelection ( position );
                        loadProduct ( "itc" );
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        iFirebaseLoadDone = this;
        mRecyclerView = findViewById ( R.id.featured_recyclerView );

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager ( this );
        layoutManager.setFlexDirection ( FlexDirection.ROW);
        layoutManager.setJustifyContent ( JustifyContent.CENTER );
        layoutManager.setFlexWrap ( FlexWrap.WRAP );

        mRecyclerView.setLayoutManager ( layoutManager );

        /*GridLayoutManager gridLayoutManager = new GridLayoutManager ( this , 2 );
        gridLayoutManager.setOrientation ( RecyclerView.VERTICAL );
        mRecyclerView.setLayoutManager ( gridLayoutManager );*/
        switch (t) {
            case 0:
                mSpinner.setSelection ( t );
                loadProduct ( "access_control" );
                break;
            case 1:
                mSpinner.setSelection ( t );
                loadProduct ( "cctv" );
                break;
            case 2:
                mSpinner.setSelection ( t );
                loadProduct ( "itc" );
                break;

        }

    }

    private void loadProduct(String productCategory) {

        FirebaseFirestore.getInstance ( ).collection ( "Products" ).document ( "featured_products" ).collection ( productCategory ).get ( )
                .addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> ( ) {
                    List<ProductsDetails> mList = new ArrayList<> ( );

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful ( ) ) {
                            for (QueryDocumentSnapshot document : task.getResult ( )) {
                                Log.d ( TAG , document.getId ( ) + " => " + document.getData ( ) );
                                if ( document.getData ( ).isEmpty ( ) ) {
                                    mEmptyTextView.setText ( "Currently there are no items for this category " );
                                    ProductsDetails emptyDetails = new ProductsDetails (
                                            "empty" ,
                                            "empty" ,
                                            "empty" ,
                                            "empty" ,
                                            "empty" );

                                } else {
                                    mEmptyTextView.setText (null);
                                    ProductsDetails details = new ProductsDetails (
                                            document.get ( "productImage" ).toString ( ) ,
                                            document.getData ( ).get ( "productLink" ).toString ( ) ,
                                            document.getData ( ).get ( "productSupplier" ).toString ( ) ,
                                            document.getData ( ).get ( "productDescription" ).toString ( ) ,
                                            document.getId ( )
                                    );
                                    mList.add ( details );
                                }
                            }
                            iFirebaseLoadDone.onFireBaseLoadSucccess ( mList );
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                            iFirebaseLoadDone.onFirebaseLoadFailed ( String.valueOf ( task.getException ( ) ) );
                        }
                    }
                } );
    }

    @Override
    public void onFireBaseLoadSucccess(List<ProductsDetails> productList) {

        adapter = new FeaturedProductAdapter ( this , productList );
        mRecyclerView.setAdapter ( adapter );
    }

    @Override
    public void onFireBaseBrandLoadSucccess(List<PreferredBrandDetails> productList) {

    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText ( this , "Loading Failed with error code: " + message , Toast.LENGTH_SHORT ).show ( );

    }
}
