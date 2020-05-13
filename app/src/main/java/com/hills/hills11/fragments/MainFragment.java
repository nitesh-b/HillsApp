package com.hills.hills11.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hills.hills11.R;
import com.hills.hills11.SearchActivity;
import com.hills.hills11.firebase.BannerImageRetrieve;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class MainFragment extends Fragment implements View.OnClickListener, BannerImageRetrieve {

    private TextView mSearchText;
    private CarouselView carouselView;
    private FirebaseFirestore db;


    private BannerImageRetrieve iImageLoadDone;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_main , container , false );

    }


    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        iImageLoadDone = this;
        db = FirebaseFirestore.getInstance ( );
        ImageView mAccessControl = view.findViewById ( R.id.accesscontrolImg );
        mAccessControl.setOnClickListener ( this );
        ImageView mCctv = view.findViewById ( R.id.cctvImg );
        mCctv.setOnClickListener ( this );
        ImageView mIct = view.findViewById ( R.id.ictImg );
        mIct.setOnClickListener ( this );

        //     mSearchText = view.findViewById(R.id.searchText);
        //   mSearchText.setOnClickListener(this);

        carouselView = view.findViewById ( R.id.mobileBanner );

        populateBanner ( );



        /*Fragment for Featured Products*/
        Fragment featuredFragment = new FeaturedFragment ( );
        FragmentTransaction transaction2 = getActivity ( ).getSupportFragmentManager ( ).beginTransaction ( );
        transaction2.replace ( R.id.featured_frameLayout , featuredFragment );
        transaction2.commit ( );

        /*Fragment for Preferred Brand*/
        Fragment preferredFragment = new PreferredBrandFragment ( );
        FragmentTransaction transaction3 = getActivity ( ).getSupportFragmentManager ( ).beginTransaction ( );
        transaction3.replace ( R.id.preferredBrand_frameLayout , preferredFragment );
        transaction3.commit ( );

    }


    private void populateBanner() {

        db.collection ( "BannerImages" )
                .document ( "Images" )
                .get ( ).addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> ( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if ( task.isSuccessful ( ) ) {
                    Map<String, Object> value = task.getResult ( ).getData ( );
                    Log.d ( TAG , "onComplete: keyVal" + value );
                    iImageLoadDone.onFireBaseImageLoadSuccess ( value );
                } else {
                    Log.d ( TAG , "Cached get failed: " , task.getException ( ) );
                    iImageLoadDone.onFireBaseImageLoadFailed ( "No image obtained" );
                }
            }
        } );


    }


    @Override
    public void onClick(View v) {
        switch (v.getId ( )) {
            case R.id.accesscontrolImg:
                gotoWebsite ( "https://www.hills.com.au/c/products/security_products/fire_security" );
                break;
            case R.id.cctvImg:
                gotoWebsite ( "https://www.hills.com.au/c/products/security_products/cctv_surveillance" );
                break;
            case R.id.ictImg:
                gotoWebsite ( "https://www.hills.com.au/c/products/information_technology" );
                break;
                /*case R.id.searchText:
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidein_up, R.anim.slideout_up);

                    break;*/
        }

    }

    private void gotoWebsite(String url) {
        Intent browse = new Intent ( Intent.ACTION_VIEW , Uri.parse ( url ) );
        startActivity ( browse );
    }

    @Override
    public void onFireBaseImageLoadSuccess(final Map<String, Object> mapVal) {
        final List<Object> list = new ArrayList<> ( mapVal.values ( ) );


        Log.d ( TAG , "onFireBaseImageLoadSuccess: converted" + list );
        carouselView.setImageListener ( new ImageListener ( ) {
            @Override
            public void setImageForPosition(int position , ImageView imageView) {
                Glide.with ( getContext ( ) )
                        .load ( list.get ( position ) )
                        .fitCenter ( )
                        .into ( imageView );
            }
        } );
        carouselView.setPageCount ( mapVal.size ( ) );
    }

    @Override
    public void onFireBaseImageLoadFailed(String message) {
        Log.d ( TAG , "onFireBaseImageLoadFailed: " + message );

    }
}


