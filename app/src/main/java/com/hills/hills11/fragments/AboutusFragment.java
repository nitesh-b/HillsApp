package com.hills.hills11.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.hills.hills11.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutusFragment extends Fragment implements View.OnClickListener {

    private TextView historyDetails, visionDetails, missionDetails;


    public AboutusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_aboutus , container , false );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        TextView aboutUsText = Objects.requireNonNull ( getActivity ( ) ).findViewById ( R.id.aboutUsText );
        aboutUsText.setJustificationMode ( Layout.JUSTIFICATION_MODE_INTER_WORD );

        LinearLayout dropHistory = getActivity ( ).findViewById ( R.id.linearLayoutHistory );
        LinearLayout dropMission = getActivity ( ).findViewById ( R.id.linearLayoutMission );
        LinearLayout dropVision = getActivity ( ).findViewById ( R.id.linearLayoutVision );

        ImageView dropIconHistory = getActivity ( ).findViewById ( R.id.icon1 );
        ImageView dropIconVision = getActivity ( ).findViewById ( R.id.icon2 );
        ImageView dropIconMission = getActivity ( ).findViewById ( R.id.icon3 );

        historyDetails = getActivity ( ).findViewById ( R.id.historyDetails );
        visionDetails = getActivity ( ).findViewById ( R.id.visionDetails );
        missionDetails = getActivity ( ).findViewById ( R.id.missionDetails );

        dropVision.setOnClickListener ( this );
        dropMission.setOnClickListener ( this );
        dropHistory.setOnClickListener ( this );


    }

    @Override
    public void onClick(View v) {
        switch (v.getId ( )) {
            case R.id.linearLayoutHistory:
                historyDetails.setVisibility ( historyDetails.isShown ( ) ? View.GONE : View.VISIBLE );
                break;
            case R.id.linearLayoutVision:
                visionDetails.setVisibility ( visionDetails.isShown ( ) ? View.GONE : View.VISIBLE );
                break;
            case R.id.linearLayoutMission:
                missionDetails.setVisibility ( missionDetails.isShown ( ) ? View.GONE : View.VISIBLE );
                break;
            default:
        }

    }
}
