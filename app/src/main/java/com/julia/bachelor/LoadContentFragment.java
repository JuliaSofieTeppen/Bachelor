package com.julia.bachelor;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class LoadContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_SECTION_NUMBER = "section_number";

    public LoadContentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoadContentFragment newInstance(int sectionNumber) {
        LoadContentFragment fragment = new LoadContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_load_content, container, false);
        Database.getAnnetValues();
        Database.getBeholdningValues();
        Database.getBeholdningUtValues();
        Database.getBMValues();
        Database.getHjemmeValues();
        Database.getHonningType();
        Database.getVideresalgValues();
        ImageView bee = rootView.findViewById(R.id.LoadingBeeImage);
        bee.setBackgroundResource(R.drawable.animation);
        AnimationDrawable anim = (AnimationDrawable) bee.getBackground();
        anim.start();
        // Flying bee ....
        //  \ / />/>
        // (^_^)( || ||)>
        //   /// \\\
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
