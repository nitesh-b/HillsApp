package com.hills.hills11.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.hills.hills11.FeaturedProducts;
import com.hills.hills11.R;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;


public class FeaturedFragment extends Fragment {
    final int[] images = {R.drawable.accesscontrol_feature , R.drawable.cctv_feature , R.drawable.ict_feature};
    private ImageView imageView;
    public FeaturedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_featured , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        CarouselView mCarouselView = view.findViewById ( R.id.carouselViewFeatured );
        mCarouselView.setResource ( R.layout.carausel_image_featured);
        mCarouselView.setSize ( images.length );
        mCarouselView.setCarouselViewListener ( new CarouselViewListener ( ) {
            @Override
            public void onBindView(View view , final int position) {
                imageView = view.findViewById ( R.id.carouselImageVIewFeatured );
                Glide.with ( getContext ( ) )
                        .load ( images[position] )
                        .fitCenter ( )
                        .into ( imageView );
                imageView.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        switch (position){
                            case 0:
                                startCateloguePage ( position );
                                break;
                            case 1:
                                startCateloguePage ( position );
                                break;
                            case 2:
                                startCateloguePage ( position );
                                break;
                        }
                    }
                } );

            }
        } );
        mCarouselView.hideIndicator ( true );
        mCarouselView.show ();


    }

    private void startCateloguePage(int i) {
        Intent intent = new Intent ( getContext ( ) , FeaturedProducts.class );
        intent.putExtra ( "selection" , i );
        startActivity ( intent );
    }

}
