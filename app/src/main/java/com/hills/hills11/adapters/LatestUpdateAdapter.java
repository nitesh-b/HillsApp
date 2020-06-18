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
import com.hills.hills11.data.LatestUpdateData;

import java.util.List;

public class LatestUpdateAdapter extends RecyclerView.Adapter<LatestUpdateAdapter.MyViewHolder> {

    private Context mContext;
    private List<LatestUpdateData> latestUpdateDataList;

    public LatestUpdateAdapter(Context mContext , List<LatestUpdateData> latestUpdateDataList) {
        this.mContext = mContext;
        this.latestUpdateDataList = latestUpdateDataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( mContext );
        View view = inflater.inflate ( R.layout.latest_update_layout, null );

        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder , int position) {
        final LatestUpdateData myData = latestUpdateDataList.get(position);
        holder.title.setText (myData.getTitle ());
        holder.description.setText ( myData.getDescription () );
        holder.date.setText ( myData.getDate () );
        Glide.with ( holder.image.getContext ())
                .load ( myData.getImage () )
                .into ( holder.image );

        holder.itemView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                final String url = myData.getLink ( );
                Intent browse = new Intent ( Intent.ACTION_VIEW , Uri.parse ( url ) );
                browse.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity ( browse );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return latestUpdateDataList.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, date;
        private ImageView image;
        private String url;
        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
          title = itemView.findViewById ( R.id.latest_update_title);
          description = itemView.findViewById ( R.id.latest_update_body );
          image = itemView.findViewById ( R.id.latestUpdate_image_icon );
          date = itemView.findViewById ( R.id.latest_update_time );

        }
    }
}
