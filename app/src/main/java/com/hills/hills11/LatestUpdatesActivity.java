package com.hills.hills11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.ObjectValue;
import com.hills.hills11.adapters.LatestUpdateAdapter;
import com.hills.hills11.data.LatestUpdateData;
import com.hills.hills11.firebase.LatestUpdateRetrieve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LatestUpdatesActivity extends AppCompatActivity implements View.OnClickListener, LatestUpdateRetrieve {

    private ImageView backBtn;
    private RecyclerView latestUpdateRecyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance ( );
    private LatestUpdateAdapter adapter;
    private LatestUpdateRetrieve firebaseLoadDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_latest_updates );
        backBtn = findViewById ( R.id.backImg );
        backBtn.setOnClickListener ( this );
        latestUpdateRecyclerView = findViewById ( R.id.latest_update_recyclerview );
        firebaseLoadDone = this;
        getLatestUpdateDataFromFirestore();


    }

    private void getLatestUpdateDataFromFirestore() {
        final List<LatestUpdateData> datalist = new ArrayList<> (  );
        db.collection ( "Latest Updates" ).orderBy ( "date" ).get ().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> ( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful ()){
                    for(QueryDocumentSnapshot snapshot: task.getResult ()){
                   LatestUpdateData data = new LatestUpdateData ( snapshot.getData ().get ( "image" ).toString (),
                           snapshot.getData ().get ( "title" ).toString (),
                           snapshot.getData ().get("description").toString (),
                           snapshot.getData ().get("date").toString (),
                           snapshot.getData ().get ( "link" ).toString ());

                        datalist.add ( data );
                    }
                    firebaseLoadDone.onFireBaseLoadSucccess ( datalist );
                }else{
                    firebaseLoadDone.onFirebaseLoadFailed ( "Failed to fetch Data" );
                }
            }
        } );

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

    @Override
    public void onFireBaseLoadSucccess(List<LatestUpdateData> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( getApplicationContext () );
        Collections.reverse ( list);
        latestUpdateRecyclerView.setLayoutManager ( layoutManager );

        adapter = new LatestUpdateAdapter ( getApplicationContext (), list);
        latestUpdateRecyclerView.setAdapter ( adapter );
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText ( this , message , Toast.LENGTH_SHORT ).show ( );
    }
}
