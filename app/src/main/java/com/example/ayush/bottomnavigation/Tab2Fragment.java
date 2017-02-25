package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ayush.bottomnavigation.NetworkServices.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.http.POST;

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
        SharedPreferences sharedprefrence=getActivity().getSharedPreferences(getString(R.string.MainInfo),Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedprefrence.edit();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer genderx=1;
                if(gender.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getActivity(), "Please Select a Gender ", Toast.LENGTH_SHORT).show();
                    return ;
                }


                else if(gender.getCheckedRadioButtonId()==R.id.male)
                {
                    gender1="Male";
                    genderx=1;
                }
                else if(gender.getCheckedRadioButtonId()==R.id.female)
                {
                    gender1="Female";
                    genderx=2;
                }
                else if(gender.getCheckedRadioButtonId()==R.id.others)
                {
                    gender1="Others";
                    genderx=3;

                }
                RegistrationActivity.registrationform.setGender(gender1);
                RegistrationActivity.registrationform.setgender(genderx);

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

                JSONObject jsonObject=RegistrationActivity.registrationform.toJSON();
                String url= String.valueOf(URI.create("http://api.tedxdtu.in/api/register"));
                Log.d("TAG",jsonObject.toString());
                Log.d("TAG",url);
                StringRequest stringrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        try {
                            JSONObject response=new JSONObject(response1);
                            Log.d("TAG",response.toString());
                            Log.d("RESPONSE",response.toString());
                            Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject error= null;
                            try {
                                error = response.getJSONObject("error");
                                if(error==null)
                                {
                                    JSONObject user=response.getJSONObject("user");
                                    String status=user.getString("user_status");
                                    String apitoken=user.getString("api_token");
                                    editor.putString(getString(R.string.Token),apitoken);
                                    editor.putString(getString(R.string.Status),status);
                                    Log.d("TAG",status);
                                    Log.d("TAG",apitoken);
                                    editor.commit();
                                }
                                else
                                {
                                    String message=error.getString("msg");
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show(); }
                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "We are facing some issues , please try again later", Toast.LENGTH_SHORT).show();
                        String tag=String.valueOf(error.networkResponse.statusCode);
                        Log.d("TAG",error.toString()+"\n"+ error.getMessage()+"\n"+error.networkResponse+"\n"+tag);
                    }
                });

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("TAG",response.toString());
                        Log.d("RESPONSE",response.toString());
                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        JSONObject error= null;
                        try {
                            error = response.getJSONObject("error");
                            if(error==null)
                            {
                                JSONObject user=response.getJSONObject("user");
                                String status=user.getString("user_status");
                                String apitoken=user.getString("api_token");
                                editor.putString(getString(R.string.Token),apitoken);
                                editor.putString(getString(R.string.Status),status);
                                Log.d("TAG",status);
                                Log.d("TAG",apitoken);
                                editor.commit();
                            }
                            else
                            {
                                String message=error.getString("msg");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show(); }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "We are facing some issues , please try again later", Toast.LENGTH_SHORT).show();
                        String tag=String.valueOf(error.networkResponse.statusCode);
                        Log.d("TAG",error.toString()+"\n"+ error.getMessage()+"\n"+error.networkResponse+"\n"+tag);
                    }
                });

                VolleySingleton.getInstance(getContext()).addToRequestQueue(stringrequest);
                VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
            }
        });

    }
}

