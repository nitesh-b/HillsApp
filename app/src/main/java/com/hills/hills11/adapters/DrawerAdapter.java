package com.hills.hills11.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hills.hills11.LatestUpdatesActivity;
import com.hills.hills11.R;
import com.hills.hills11.notification.NotificationDetails;

import java.util.List;

import static android.content.ContentValues.TAG;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
    private Context mContext;
    private List<NotificationDetails> notificationList;

    public DrawerAdapter(Context mContext , List<NotificationDetails> notificationList) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public DrawerAdapter.DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( mContext );
        View view = inflater.inflate ( R.layout.drawercardview , null );
        return new DrawerViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerAdapter.DrawerViewHolder holder , int position) {

        /*TODO when notifications are fetched online, these have to come out from comment section*/

        final NotificationDetails notify = notificationList.get ( position );
        holder.title.setText ( notify.getTitle ( ) );
        holder.time.setText ( notify.getTime ( ) );
        holder.content.setText ( notify.getDescription ( ) );


        holder.itemView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if(notify.getUrl ()==null){
                    Intent myIntent = new Intent ( mContext , LatestUpdatesActivity.class );
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity ( myIntent);
                } else
                {
                    Toast.makeText ( mContext , notify.getUrl () , Toast.LENGTH_SHORT ).show ( );
                    Intent myIntent = new Intent ( Intent.ACTION_VIEW , Uri.parse ( notify.getUrl () ) );
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContext.startActivity ( myIntent );
                }



            }
        } );

    }

    @Override
    public int getItemCount() {
        return notificationList.size ( ); //TODO
    }


    class DrawerViewHolder extends RecyclerView.ViewHolder {

        private TextView title, time, content;
        private String url;

        public DrawerViewHolder(@NonNull View itemView) {
            super ( itemView );

            title = itemView.findViewById ( R.id.drawer_item_title );
            time = itemView.findViewById ( R.id.drawer_item_time );
            content = itemView.findViewById ( R.id.drawer_item_content );

        }
    }
}
