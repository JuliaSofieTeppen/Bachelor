package com.julia.bachelor;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriserFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    Database database;
    TextView kg1;
    TextView kg05;
    TextView kg025;
    TextView ingf05kg;
    TextView ingf025kg;
    TextView flyt;
    List<TextView> verdier;
    List<Honning> honningtype;

    public PriserFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PriserFragment newInstance(int sectionNumber) {
        PriserFragment fragment = new PriserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_priser, container, false);
        kg1 = rootView.findViewById(R.id.kg1pris);
        kg05 = rootView.findViewById(R.id.kg05pris);
        kg025 = rootView.findViewById(R.id.kg025pris);
        ingf05kg=rootView.findViewById(R.id.ingf05kgpris);
        ingf025kg=rootView.findViewById(R.id.ingf025kgpris);
        flyt=rootView.findViewById(R.id.flytpris);
        verdier = new ArrayList<>(Arrays.asList(kg1,kg05,kg025,ingf05kg,ingf025kg,flyt));
        honningtype = new ArrayList<>();
        try {
            honningtype = (ArrayList<Honning>) getArguments().getSerializable("params");

            if(honningtype.get(0)!=null)
            kg1.setText(""+honningtype.get(0).getHjemmePris());
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }


}
