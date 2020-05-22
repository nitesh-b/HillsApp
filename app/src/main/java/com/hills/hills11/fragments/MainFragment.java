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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hills.hills11.R;
import com.hills.hills11.data.CarouselImageLink;
import com.hills.hills11.data.ProductsDetails;
import com.hills.hills11.firebase.BannerImageRetrieve;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class MainFragment extends Fragment implements View.OnClickListener, BannerImageRetrieve {

    private TextView mSearchText;
    private CarouselView carouselView;
    private FirebaseFirestore db;
    private ImageView imageView;


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

        db.collection ( "CarouselImages" ).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> ( ) {
            List<CarouselImageLink> mList = new ArrayList<> ( );
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful ()){
                for(QueryDocumentSnapshot documentSnapshot: task.getResult ()){
                    CarouselImageLink imageLink = new CarouselImageLink (
                    documentSnapshot.getData ().get ( "image" ).toString (),
                    documentSnapshot.getData ().get("link" ).toString ());
            mList.add ( imageLink );
                }
                iImageLoadDone.onCarouselImageLoadSuccess ( mList );
            }else{
                iImageLoadDone.onFireBaseImageLoadFailed ( "Problem Retrieving Carousel Images." );
            }



            }
        } );

       /* db.collection ( "BannerImages" )
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
*/

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
    public void onCarouselImageLoadSuccess(final List<CarouselImageLink> mList){
        carouselView.setResource ( R.layout.carousel_image );
        carouselView.setSize( mList.size ( ));
        carouselView.setCarouselViewListener(new CarouselViewListener () {
            @Override
            public void onBindView(View view, final int position) {
                // setting up a full image carousel
                ImageView imageView = view.findViewById ( R.id.carouselImageVIew );
                imageView.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        gotoWebsite ( mList.get ( position ).getLink () );
                    }
                } );

                Glide.with ( getContext ( ) )
                        .load ( mList.get ( position ).getImage () )
                        .fitCenter ( )
                        .into ( imageView );


            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();


    }
 /*   public void onFireBaseImageLoadSuccess(final Map<String, Object> mapVal) {
        final List<Object> list = new ArrayList<> ( mapVal.values ( ) );

        Log.d ( TAG , "onFireBaseImageLoadSuccess: converted" + list );

        carouselView.setResource ( R.layout.carousel_image );
        carouselView.setSize(list.size ());
        carouselView.setCarouselViewListener(new CarouselViewListener () {
            @Override
            public void onBindView(View view, int position) {
                // setting up a full image carousel
                ImageView imageView = view.findViewById ( R.id.carouselImageVIew );
                imageView.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {


                    }
                } );

                Glide.with ( getContext ( ) )
                        .load ( list.get ( position ) )
                        .fitCenter ( )
                        .into ( imageView );


            }
        });
        // After you finish setting up, show the CarouselView
        carouselView.show();
    }*/

    @Override
    public void onFireBaseImageLoadFailed(String message) {
        Log.d ( TAG , "onFireBaseImageLoadFailed: " + message );

    }
}


