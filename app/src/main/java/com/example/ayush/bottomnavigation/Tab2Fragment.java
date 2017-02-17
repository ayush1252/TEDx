package com.example.ayush.bottomnavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by ayush on 17/2/17.
 */

public class Tab2Fragment extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {
    String country;
    RadioGroup gender;
    EditText adress;
    EditText Locality;
    EditText know;
    EditText attend;
    String gender1;
    EditText city;
    EditText ideas;
    FloatingActionButton finish;
    public Tab2Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registration_second, container, false);

        Locale[] locale = Locale.getAvailableLocales();
        final ArrayList<String> countries = new ArrayList<String>();
         String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }

        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        Spinner citizenship = (Spinner) v.findViewById(R.id.spinner);
        citizenship.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countries);
        citizenship.setAdapter(adapter);
        return v;


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        country = parent.getItemAtPosition(position).toString();
        //Toast.makeText(getActivity(), country, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gender= (RadioGroup) view.findViewById(R.id.Gender);
        adress= (EditText) view.findViewById(R.id.Adress);
        Locality= (EditText) view.findViewById(R.id.locality);
        know= (EditText) view.findViewById(R.id.Know);
        city= (EditText) view.findViewById(R.id.city);
        attend= (EditText) view.findViewById(R.id.Attend);
        ideas= (EditText) view.findViewById(R.id.Ideas);
        finish= (FloatingActionButton) view.findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getActivity(), "Please Select a Gender ", Toast.LENGTH_SHORT).show();
                    return ;
                }

                else if(gender.getCheckedRadioButtonId()==R.id.male)
                {
                    gender1="Male";
                }
                else if(gender.getCheckedRadioButtonId()==R.id.female)
                {
                    gender1="Female";
                }
                else if(gender.getCheckedRadioButtonId()==R.id.others)
                {
                    gender1="Others";
                }
                RegistrationActivity.registrationform.setGender(gender1);

                if(adress.getText().toString().length()==0)
                {
                    adress.setError("This Cannot be Empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setAdress(adress.getText().toString());

                if(city.getText().toString().length()==0)
                {
                    city.setError("This Cannot be Empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setCity(city.getText().toString());

                if(Locality.getText().toString().length()==0)
                {
                    Locality.setError("This Cannot be Empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setLocality(Locality.getText().toString());

                if(know.getText().toString().length()==0)
                {
                    know.setError("This Cannot be Empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setKnow(know.getText().toString());

                RegistrationActivity.registrationform.setCountry(country);

                if(ideas.getText().toString().length()==0)
                {
                    ideas.setError("This Cannot be Empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setIdeas(ideas.getText().toString());

                if(attend.getText().toString().length()==0)
                {
                    attend.setError("This Cannot be Empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setAttend(attend.getText().toString());

                Toast.makeText(getActivity(), "FINISH", Toast.LENGTH_SHORT).show();
                //To Insert Network Call here


            }
        });

    }
}