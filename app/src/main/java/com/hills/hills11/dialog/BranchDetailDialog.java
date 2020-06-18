package com.hills.hills11.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.type.LatLng;
import com.hills.hills11.R;
import com.hills.hills11.data.LocationDetailData;

public class BranchDetailDialog extends AppCompatDialogFragment {
    private TextView branchName, branchAddress, branchPhone;
    private TextView monday, tuesday, wednesday, thrusday, friday, saturday, sunday;
    private LocationDetailData locationDetailData;

    public BranchDetailDialog(LocationDetailData locationDetailData) {
        this.locationDetailData = locationDetailData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity () );
        LayoutInflater inflater = getActivity ().getLayoutInflater ();
        View view = inflater.inflate ( R.layout.map_dialog_box, null);

        branchName = view.findViewById ( R.id.locationName );
        branchAddress = view.findViewById ( R.id.location_address );
        branchPhone = view.findViewById ( R.id.location_phone );

        monday = view.findViewById ( R.id.location_monday );
        tuesday = view.findViewById ( R.id.location_tuesday );
        wednesday = view.findViewById ( R.id.location_wednesday );
        thrusday = view.findViewById ( R.id.location_thrusday );
        friday = view.findViewById ( R.id.location_friday );
        saturday = view.findViewById ( R.id.location_saturday );
        sunday = view.findViewById ( R.id.location_sunday );

        branchName.setText ( locationDetailData.getName () );
        branchAddress.setText ( locationDetailData.getAddress () );
        branchPhone.setText ( locationDetailData.getPhone () );

        monday.setText ( locationDetailData.getOpeningHours ().get ( "Monday" ) );
        tuesday.setText ( locationDetailData.getOpeningHours ().get ( "Tuesday" ) );
        wednesday.setText ( locationDetailData.getOpeningHours ().get ( "Wednesday" ) );
        thrusday.setText ( locationDetailData.getOpeningHours ().get ( "Thrusday" ) );
        friday.setText ( locationDetailData.getOpeningHours ().get ( "Friday" ) );
        saturday.setText ( locationDetailData.getOpeningHours ().get ( "Saturday" ) );
        sunday.setText ( locationDetailData.getOpeningHours ().get ( "Sunday" ) );

        builder.setView ( view )
                .setTitle ( "Branch Detail" )
                .setNegativeButton ( "Close" , new DialogInterface.OnClickListener ( ) {
                    @Override
                    public void onClick(DialogInterface dialog , int which) {

                    }
                } );

        return builder.create ();
    }

}
