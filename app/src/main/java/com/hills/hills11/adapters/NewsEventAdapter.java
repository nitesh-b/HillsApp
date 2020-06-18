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
import com.hills.hills11.data.NewsEventDetails;

import java.util.List;

public class NewsEventAdapter extends RecyclerView.Adapter<NewsEventAdapter.NewsEventViewholder> {

    private Context mContext;
    private List<NewsEventDetails> newsEventsList;
    private Intent browse;

    public NewsEventAdapter(Context mContext , List<NewsEventDetails> newsEventsList) {
        this.mContext = mContext;
        this.newsEventsList = newsEventsList;
    }


    @NonNull
    @Override
    public NewsEventAdapter.NewsEventViewholder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {

        LayoutInflater inflater = LayoutInflater.from ( mContext );
        View view = inflater.inflate ( R.layout.newseventview , parent , false );
        return new NewsEventViewholder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull NewsEventViewholder holder , int position) {


        final NewsEventDetails details = newsEventsList.get ( position );
        Glide.with ( holder.image.getContext ( ) )
                .load ( details.getImage ( ) )
                .into ( holder.image );
        holder.imgName.setText ( details.getImgName ( ) );
        holder.title.setText ( details.getTitle ( ) );
        holder.description.setText ( details.getDecription ( ) );
        holder.itemView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                final String url = details.getLink ( );
                browse = new Intent ( Intent.ACTION_VIEW , Uri.parse ( url ) );
                mContext.startActivity ( browse );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return newsEventsList.size ( );
    }

    class NewsEventViewholder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView imgName;
        private TextView title;
        private TextView description;
        private TextView link;


        public NewsEventViewholder(@NonNull View itemView) {
            super ( itemView );
            image = itemView.findViewById ( R.id.ne_image );
            imgName = itemView.findViewById ( R.id.ne_imageName );
            title = itemView.findViewById ( R.id.ne_title );
            description = itemView.findViewById ( R.id.ne_description );
        }
    }
}
