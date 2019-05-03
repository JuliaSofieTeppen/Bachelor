package com.julia.bachelor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddBeholdningFragment extends Fragment {
    EditText dato, som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende;
    List<EditText> verdier;

    public AddBeholdningFragment() {
    }

    public static AddBeholdningFragment newInstance() {
        return new AddBeholdningFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_beholdning, container, false);
        dato = rootView.findViewById(R.id.Bdato);
        som1kg = rootView.findViewById(R.id.Bsom1kg);
        som05kg = rootView.findViewById(R.id.Bsom05kg);
        som025kg = rootView.findViewById(R.id.Bsom025kg);
        lyng1kg = rootView.findViewById(R.id.Blyn1kg);
        lyng05kg = rootView.findViewById(R.id.Blyn05kg);
        lyng025kg = rootView.findViewById(R.id.Blyn025kg);
        ingf05kg = rootView.findViewById(R.id.Binf05kg);
        ingf025kg = rootView.findViewById(R.id.Binf025kg);
        flytende = rootView.findViewById(R.id.Bflyt);
        verdier = new ArrayList<>(Arrays.asList(som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende));

        dato.setText(Beregninger.getDate());
        return rootView;
    }

    public void lagre(View v) {
        int tell = 0;
        if (Beregninger.checkDate(dato.getText().toString())) {
            for (EditText verdi : verdier) {
                if (verdi.getText().toString().equals("")) {
                    verdi.setText("0");
                } else {
                    tell++;
                }
            }
            if (tell == 0) {
                Toast.makeText(getActivity(), "Legg til minst et produkt", Toast.LENGTH_SHORT).show();
            } else {
                insertIntoDB();
                Toast.makeText(getActivity(), "Beholdning lagret", Toast.LENGTH_SHORT).show();
                //this.finish();
            }
        } else {
            Toast.makeText(getActivity(), "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }

    String getBeholdning() {
        String[] strings = {"Sommer", "SommerH", "SommerK", "Lyng", "LyngH", "LyngK", "IngeferH", "IngeferK", "Flytende"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]).append("=").append(verdier.get(i).getText().toString()).append("&");
        }
        return sb.toString();
    }

    void insertIntoDB() {
        Database.executeOnDB("http://www.honningbier.no/PHP/BeholdningIn.php/?" + getBeholdning() + "&Dato=" + dato.getText().toString());
    }
}
