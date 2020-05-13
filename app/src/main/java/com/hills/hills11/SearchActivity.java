package com.hills.hills11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hills.hills11.adapters.ProductItemSearchAdapter;
import com.hills.hills11.data.ProductItemDisplay;

import java.util.ArrayList;
import java.util.concurrent.RejectedExecutionException;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "faield";
    private SearchView mSearchView;
    private DatabaseReference dRef;
    private RecyclerView mRecyclerView;
    private ArrayList<ProductItemDisplay> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_search );
        FirebaseDatabase database = FirebaseDatabase.getInstance ( );

        dRef = database.getReference ( "items" );
        mRecyclerView = findViewById ( R.id.searchRecyclerView );
        LinearLayoutManager layoutManager = new LinearLayoutManager ( this );
        mRecyclerView.setLayoutManager ( layoutManager );

        mSearchView = findViewById ( R.id.searchView );

        mSearchView.setOnQueryTextListener ( new SearchView.OnQueryTextListener ( ) {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search ( newText );
                return true;
            }
        } );
    }

    private void search(String newText) {
        ArrayList<ProductItemDisplay> list = new ArrayList<> ( );
        for (ProductItemDisplay object : mList) {
            if ( object.getProductItemName ( ).toLowerCase ( ).contains ( newText.toLowerCase ( ) ) ) {
                list.add ( object );
            }
        }
        ProductItemSearchAdapter adapter = new ProductItemSearchAdapter ( list );

        mRecyclerView.setAdapter ( adapter );
    }


    @Override
    protected void onStart() {
        super.onStart ( );

        if ( dRef != null ) {
            try {
                dRef.addValueEventListener ( new ValueEventListener ( ) {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if ( dataSnapshot.exists ( ) ) {
                            mList = new ArrayList<> ( );
                            for (DataSnapshot ds : dataSnapshot.getChildren ( )) {
                                mList.add ( ds.getValue ( ProductItemDisplay.class ) );
                            }
                           /* ProductItemSearchAdapter adapter = new ProductItemSearchAdapter(mList);
                            mRecyclerView.setAdapter(adapter);*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d ( TAG , "onCancelled: faield to tead value" );
                    }
                } );

            } catch (RejectedExecutionException e) {
                Toast.makeText ( this , "Error :" + e + " has occured." , Toast.LENGTH_SHORT ).show ( );
            }
        }


    }

    @Override
    public void finish() {
        super.finish ( );
        overridePendingTransition ( R.anim.slidein_down , R.anim.slideout_down );

    }


}
