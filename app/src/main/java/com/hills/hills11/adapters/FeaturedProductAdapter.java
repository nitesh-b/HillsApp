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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hills.hills11.R;
import com.hills.hills11.data.ProductsDetails;
import com.hills.hills11.firebase.RetrieveFromFirestore;

import java.util.List;

public class FeaturedProductAdapter extends RecyclerView.Adapter<FeaturedProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductsDetails> mList;

    public FeaturedProductAdapter(Context mContext , List<ProductsDetails> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {

        View view = LayoutInflater.from ( parent.getContext ( ) )
                .inflate ( R.layout.featuredview , parent , false );
        MyViewHolder viewHolder = new MyViewHolder ( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder , final int position) {
        Glide.with ( mContext )
                .load ( mList.get ( position ).getProductImageURL ( ) )
                /*.apply(RequestOptions.circleCropTransform())*/
                .into ( holder.image );
        holder.description.setText ( mList.get ( position ).getProductDescription ( ) );
        holder.supplier.setText ( mList.get ( position ).getProductSupplier ( ) );
        holder.sku.setText ( mList.get ( position ).getProductNumber ( ) );
        holder.itemView.setOnClickListener ( new View.OnClickListener ( ) {
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

    }

    @Override
    public int getItemCount() {
        return mList.size ( );
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        private TextView description, supplier, sku;

        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
            image = itemView.findViewById ( R.id.featuredProductImage );
            description = itemView.findViewById ( R.id.featuredProductDescription );
            supplier = itemView.findViewById ( R.id.productSupplierTextView );
            sku = itemView.findViewById ( R.id.productNumberTextView );

        }
    }
}
