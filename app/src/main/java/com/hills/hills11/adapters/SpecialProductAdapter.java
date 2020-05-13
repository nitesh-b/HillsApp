package com.hills.hills11.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hills.hills11.R;
import com.hills.hills11.data.ProductsDetails;

import java.util.List;

public class SpecialProductAdapter extends PagerAdapter {

    private Context mContext;
    private List<ProductsDetails> mList;

    public SpecialProductAdapter(Context mContext , List<ProductsDetails> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container , final int position) {

        final View view = LayoutInflater.from ( mContext ).inflate ( R.layout.cardview , container , false );


        try {
            /*Initializing Product objects*/
            ImageView imageView = view.findViewById ( R.id.productImage );
            TextView textView = view.findViewById ( R.id.productName );
            TextView textViewProductNumber = view.findViewById ( R.id.productNumber );
            TextView textViewProductDescription = view.findViewById ( R.id.productDescription );


            /*Positioning product elements*/
            textView.setText ( mList.get ( position ).getProductDescription ( ) );
            textViewProductNumber.setText ( mList.get ( position ).getProductNumber ( ) );
            textViewProductDescription.setText ( mList.get ( position ).getProductDescription ( ) );
            Glide.with ( mContext )
                    .load ( mList.get ( position ).getProductImageURL ( ) )
                    .apply ( RequestOptions.circleCropTransform ( ) )
                    .into ( imageView );
            view.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    try {
                        Intent browse = new Intent ( Intent.ACTION_VIEW , Uri.parse ( mList.get ( position ).getProductLink ( ) ) );
                        mContext.startActivity ( browse );

                    } catch (ActivityNotFoundException e) {
                        Toast.makeText ( mContext , "The Link is Broken: Please contact Customer Care.." , Toast.LENGTH_SHORT ).show ( );
                    }

                }
            } );

            imageView.setScaleType ( ImageView.ScaleType.CENTER_CROP );
            container.addView ( view );

        } catch (Exception e) {
            Toast.makeText ( mContext , "Array error: " + e , Toast.LENGTH_SHORT ).show ( );
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container , int position , @NonNull Object object) {
        container.removeView ( (View) object );
    }

    @Override
    public int getCount() {
        return mList.size ( );
    }

    @Override
    public boolean isViewFromObject(@NonNull View view , Object object) {
        return (view == object);
    }

}