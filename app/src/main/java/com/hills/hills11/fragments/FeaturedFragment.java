package com.hills.hills11.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hills.hills11.FeaturedProducts;
import com.hills.hills11.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class FeaturedFragment extends Fragment {
    final int[] images = {R.drawable.accesscontrol_feature , R.drawable.cctv_feature , R.drawable.ict_feature};

    public FeaturedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_featured , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        CarouselView mCarouselView = view.findViewById ( R.id.carouselView );
        mCarouselView.setImageListener ( new ImageListener ( ) {
            @Override
            public void setImageForPosition(int position , ImageView imageView) {
                imageView.setImageResource ( images[position] );
                imageView.setScaleType ( ImageView.ScaleType.FIT_CENTER );
            }
        } );
        mCarouselView.setPageCount ( images.length );
        mCarouselView.setImageClickListener ( new ImageClickListener ( ) {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        // Toast.makeText(getContext(), "Send Access Control", Toast.LENGTH_SHORT).show();
                        startCateloguePage ( position );
                        break;
                    case 1:
                        // Toast.makeText(getContext(), "Send cctv", Toast.LENGTH_SHORT).show();
                        startCateloguePage ( position );
                        break;
                    case 2:
                        // Toast.makeText(getContext(), "Send ict", Toast.LENGTH_SHORT).show();
                        startCateloguePage ( position );
                        break;
                }
            }
        } );
    }

    private void startCateloguePage(int i) {
        Intent intent = new Intent ( getContext ( ) , FeaturedProducts.class );
        intent.putExtra ( "selection" , i );
        startActivity ( intent );
    }

}
