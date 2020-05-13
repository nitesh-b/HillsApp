package com.hills.hills11.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hills.hills11.R;
import com.hills.hills11.adapters.NewsEventAdapter;
import com.hills.hills11.data.NewsEventDetails;

import java.util.ArrayList;
import java.util.List;


public class NewsEventsFragment extends Fragment {


    private static final String TAG = "News and Event Fragment";
    private RecyclerView neRecycler;
    private NewsEventAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance ( );

    public NewsEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_news_events , container , false );
        neRecycler = view.findViewById ( R.id.news_recyclerView );
        List<NewsEventDetails> mylist = retrieveFromFirestore ( );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( view.getContext ( ) );
        neRecycler.setLayoutManager ( layoutManager );
        Log.d ( TAG , "array: " + mylist );
        adapter = new NewsEventAdapter ( getContext ( ) , mylist );

        neRecycler.setAdapter ( adapter );

        return view;
    }

    private List<NewsEventDetails> retrieveFromFirestore() {
        final ArrayList<NewsEventDetails> newsEventDetailsList = new ArrayList<> ( );
        db.collection ( "news and events" )
                .get ( )
                .addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful ( ) ) {
                            for (QueryDocumentSnapshot document : task.getResult ( )) {
                                adapter.notifyDataSetChanged ( );
                                Log.d ( TAG , document.getId ( ) + " => " + document.getData ( ) );
                                NewsEventDetails details = new NewsEventDetails (
                                        document.getData ( ).get ( "description" ).toString ( ) ,
                                        document.getData ( ).get ( "image" ).toString ( ) ,
                                        document.getData ( ).get ( "imgName" ).toString ( ) ,
                                        document.getData ( ).get ( "link" ).toString ( ) ,
                                        document.getData ( ).get ( "title" ).toString ( )
                                );
                                newsEventDetailsList.add ( details );

                            }
                        } else {
                            Log.d ( TAG , "Error getting documents: " , task.getException ( ) );
                        }
                    }
                } );

        return newsEventDetailsList;
    }

}
