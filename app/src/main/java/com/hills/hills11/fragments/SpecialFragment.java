package com.hills.hills11.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hills.hills11.R;
import com.hills.hills11.adapters.SpecialProductAdapter;
import com.hills.hills11.data.PreferredBrandDetails;
import com.hills.hills11.data.ProductsDetails;
import com.hills.hills11.firebase.RetrieveFromFirestore;
import com.hills.hills11.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SpecialFragment extends Fragment implements RetrieveFromFirestore {

    private ViewPager viewPager;
    private SpecialProductAdapter adapter;
    private CollectionReference db;
    private RetrieveFromFirestore iFirebaseLoadDone;

    public SpecialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    private void loadProducts() {
        db.get ( )
                .addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> ( ) {
                    List<ProductsDetails> mList = new ArrayList<> ( );

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful ( ) ) {
                            for (QueryDocumentSnapshot document : task.getResult ( )) {

                                Log.d ( TAG , document.getId ( ) + " => " + document.getData ( ) );
                                ProductsDetails details = new ProductsDetails (
                                        document.get ( "productImage" ).toString ( ) ,
                                        document.getData ( ).get ( "productLink" ).toString ( ) ,
                                        document.getData ( ).get ( "productName" ).toString ( ) ,
                                        document.getData ( ).get ( "productNumber" ).toString ( ) ,
                                        document.getData ( ).get ( "productDescription" ).toString ( )
                                );
                                mList.add ( details );
                                iFirebaseLoadDone.onFireBaseLoadSucccess ( mList );

                            }
                        } else {
                            Log.d ( TAG , "Error getting documents: " , task.getException ( ) );
                            iFirebaseLoadDone.onFirebaseLoadFailed ( String.valueOf ( task.getException ( ) ) );
                        }
                    }
                } );
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_special , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        /*initialize Firestore*/
        db = FirebaseFirestore.getInstance ( ).collection ( "featured_products" );

        /*Initialize event*/
        iFirebaseLoadDone = this;
        loadProducts ( );
        viewPager = view.findViewById ( R.id.MainFrameLayout );
        viewPager.setPageTransformer ( true , new DepthPageTransformer ( ) );
    }

    @Override
    public void onFireBaseLoadSucccess(List<ProductsDetails> productList) {
        adapter = new SpecialProductAdapter ( viewPager.getContext ( ) , productList );
        adapter.notifyDataSetChanged ( );
        viewPager.setAdapter ( adapter );
    }

    @Override
    public void onFireBaseBrandLoadSucccess(List<PreferredBrandDetails> productList) {

    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText ( getActivity ( ) , "Database Loading Failed: " + message , Toast.LENGTH_SHORT ).show ( );

    }
}

