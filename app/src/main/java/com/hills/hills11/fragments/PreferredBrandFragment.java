package com.hills.hills11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hills.hills11.R;
import com.hills.hills11.adapters.PreferredBrandAdapter;
import com.hills.hills11.data.PreferredBrandDetails;
import com.hills.hills11.data.ProductsDetails;
import com.hills.hills11.firebase.RetrieveFromFirestore;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PreferredBrandFragment extends Fragment implements RetrieveFromFirestore {

    private Context mContext;
    private List<PreferredBrandDetails> mList;
    private RecyclerView preferredRecyclerView;
    private PreferredBrandAdapter adapter;
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private int brandCounter = 0;

    private RetrieveFromFirestore iFirebaseLoadDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_preferred_brand , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        iFirebaseLoadDone = this;
        leftBtn = view.findViewById ( R.id.leftImgBtn );
        rightBtn = view.findViewById ( R.id.rightImgBtn );
        preferredRecyclerView = view.findViewById ( R.id.preferred_recyclerView );
        preferredRecyclerView.setHasFixedSize ( true );
        LinearLayoutManager layoutManager = new LinearLayoutManager ( getContext ( ) , LinearLayoutManager.HORIZONTAL , false );
        preferredRecyclerView.setLayoutManager ( layoutManager );
        PagerSnapHelper helper = new PagerSnapHelper ( );
        helper.attachToRecyclerView ( preferredRecyclerView );
        brandCounter = helper.findTargetSnapPosition ( layoutManager , 1 , 0 );
        Log.d ( TAG , "onViewCreated: brandCounter" + brandCounter );
        loadBrand ( );

    }

    private void loadBrand() {
        FirebaseFirestore.getInstance ( ).collection ( "Key Partners" ).get ( )
                .addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> ( ) {
                    List<PreferredBrandDetails> mList = new ArrayList<> ( );

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful ( ) ) {
                            for (QueryDocumentSnapshot document : task.getResult ( )) {

                                Log.d ( TAG , document.getId ( ) + " => " + document.getData ( ) );
                                PreferredBrandDetails details = new PreferredBrandDetails (
                                        document.getData ( ).get ( "logo" ).toString ( ) ,
                                        document.getData ( ).get ( "link" ).toString ( )
                                );
                                mList.add ( details );
                            }
                            iFirebaseLoadDone.onFireBaseBrandLoadSucccess ( mList );
                        } else {
                            Log.d ( TAG , "Error getting documents: " , task.getException ( ) );
                            iFirebaseLoadDone.onFirebaseLoadFailed ( String.valueOf ( task.getException ( ) ) );
                        }
                    }
                } );
    }

    @Override
    public void onFireBaseLoadSucccess(List<ProductsDetails> productList) {

    }

    @Override
    public void onFireBaseBrandLoadSucccess(final List<PreferredBrandDetails> brandList) {

        adapter = new PreferredBrandAdapter ( getContext ( ) , brandList );
        preferredRecyclerView.setAdapter ( adapter );


        leftBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                brandCounter = brandCounter - 1;
                if ( brandCounter < 0 ) brandCounter = 0;


                preferredRecyclerView.smoothScrollToPosition ( brandCounter );
            }
        } );
        rightBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                brandCounter = brandCounter + 1;
                if ( brandCounter > brandList.size ( ) ) brandCounter = brandList.size ( );
                preferredRecyclerView.smoothScrollToPosition ( brandCounter );
            }
        } );
    }

    @Override
    public void onFirebaseLoadFailed(String message) {

        Log.d ( TAG , "onFirebaseLoadFailed: Loading Failed" );
    }
}
