package com.hills.hills11.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hills.hills11.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResourcesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResourcesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResourcesFragment extends Fragment implements View.OnClickListener {

    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_resoruces , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        ImageView imgAV = getActivity ( ).findViewById ( R.id.resavimage );
        ImageView imgFinance = getActivity ( ).findViewById ( R.id.resFinanceImage );
        ImageView imgPartner = getActivity ( ).findViewById ( R.id.resPartnerImage );
        ImageView imgLFD = getActivity ( ).findViewById ( R.id.resLegacyImage );

        imgAV.setOnClickListener ( this );
        imgFinance.setOnClickListener ( this );
        imgPartner.setOnClickListener ( this );
        imgLFD.setOnClickListener ( this );

    }

    @Override
    public void onClick(View v) {
        String url = null;
        if ( v.getId ( ) == R.id.resavimage ) url = "https://www.hills.com.au/articles-and-videos";
        else if ( v.getId ( ) == R.id.resFinanceImage ) url = "https://www.hills.com.au/finance";
        else if ( v.getId ( ) == R.id.resPartnerImage )
            url = "https://www.hills.com.au/partner-program";
        else if ( v.getId ( ) == R.id.resLegacyImage )
            url = "http://cdn-tp3.mozu.com/24421-m3/cms/files/5a126d89-9064-4cd3-999a-0996f0c8b653";
        startBrowser ( url );
    }

    private void startBrowser(String url) {
        Intent browse = new Intent ( Intent.ACTION_VIEW , Uri.parse ( url ) );
        startActivity ( browse );
    }
}
