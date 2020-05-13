package com.hills.hills11.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hills.hills11.R;
import com.hills.hills11.data.PreferredBrandDetails;

import java.util.List;

public class PreferredBrandAdapter extends RecyclerView.Adapter<PreferredBrandAdapter.MyViewHolder> {

    private Context mContext;
    private List<PreferredBrandDetails> mList;

    public PreferredBrandAdapter(Context mContext , List<PreferredBrandDetails> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public PreferredBrandAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {

        View view = LayoutInflater.from ( parent.getContext ( ) )
                .inflate ( R.layout.preferredbrandview , parent , false );
        PreferredBrandAdapter.MyViewHolder viewHolder = new PreferredBrandAdapter.MyViewHolder ( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreferredBrandAdapter.MyViewHolder holder , final int position) {
        Glide.with ( mContext )
                .load ( mList.get ( position ).getLogo ( ) )
                .into ( holder.image );

        holder.image.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent ( Intent.ACTION_VIEW , Uri.parse ( mList.get ( position ).getLink ( ) ) );
                mContext.startActivity ( browse );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return mList.size ( );
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
            image = itemView.findViewById ( R.id.preferredBrandInage );

        }
    }
}
