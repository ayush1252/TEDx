package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ayush.bottomnavigation.NetworkServices.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

import static android.content.Context.MODE_PRIVATE;

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
        citizenship.setPrompt("Country");
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
        SharedPreferences sharedprefrence=getActivity().getSharedPreferences(getString(R.string.MainInfo), MODE_PRIVATE);
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
//                final Map<String,String> mymap=new HashMap<String, String>();
//                try {
//                    mymap.put("username",jsonObject.getString("username"));
//                    mymap.put("fname",jsonObject.getString("fname"));
//                    mymap.put("lname",jsonObject.getString("lname"));
//                    mymap.put("contact",jsonObject.getString("contact"));
//                    mymap.put("college",jsonObject.getString("college"));
//                    mymap.put("dob",jsonObject.getString("dob"));
//                    mymap.put("address",jsonObject.getString("address"));
//                    mymap.put("locality",jsonObject.getString("locality"));
//                    mymap.put("city",jsonObject.getString("city"));
//                    mymap.put("gender",jsonObject.getString("gender"));
//                    mymap.put("how",jsonObject.getString("how"));
//                    mymap.put("why",jsonObject.getString("why"));
//                    mymap.put("ideas",jsonObject.getString("ideas"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.d("TAG", String.valueOf(mymap));



               /*This is for the GET APis testinG
                String url2= String.valueOf(URI.create("http://api.tedxdtu.in/api/user/application"));
//                JSONObject nejsonObject=new JSONObject();
//                try {
//                    nejsonObject.put("tedxdtu-token",getString(R.string.lala));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                JsonObjectRequest jsnObjReq=new JsonObjectRequest(Request.Method.GET,url2,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG",response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", String.valueOf(error.networkResponse.statusCode));
                        Log.d("TAG",error.toString()+error.networkResponse+error.networkResponse.statusCode);

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Log.d("TAG","HUA");
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("tedxdtu-token", "42418c448bca92a5115848f3b642dde3b8d7a3beaf181a22ffaea5c4f6fe87c8");
                        Log.d("TAG","HUA");
                        return params;
                    }

                };*/


//                JSONArray jsonArray= new JSONArray();
//                jsonArray.put(jsonObject);
//                JsonArrayRequest jsonArrayRequesr=new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        Toast.makeText(getActivity(), "YAHAN HUN", Toast.LENGTH_SHORT).show();
//                        Log.d("TAG",response.toString());
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), "NAhin hun", Toast.LENGTH_SHORT).show();
//                        Log.d("TAG",error.toString());
//                    }
//                });

//                StringRequest stringrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response1) {
//
//                        try {
//                            JSONObject response=new JSONObject(response1);
//                            Log.d("TAG",response.toString());
//                            Log.d("RESPONSE",response.toString());
//                            Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
//                            JSONObject error= null;
//                            try {
//                                error = response.getJSONObject("error");
//                                if(error==null)
//                                {
//                                    JSONObject user=response.getJSONObject("user");
//                                    String status=user.getString("user_status");
//                                    String apitoken=user.getString("api_token");
//                                    editor.putString(getString(R.string.Token),apitoken);
//                                    editor.putString(getString(R.string.Status),status);
//                                    Log.d("TAG",status);
//                                    Log.d("TAG",apitoken);
//                                    editor.commit();
//                                }
//                                else
//                                {
//                                    String message=error.getString("msg");
//                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show(); }
//                            } catch (JSONException e) {
//                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
//                                e.printStackTrace();
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), "We are facing some issues , please try again later", Toast.LENGTH_SHORT).show();
//                        String tag=String.valueOf(error.networkResponse.statusCode);
//                        Log.d("TAG",error.toString()+"\n"+ error.getMessage()+"\n"+error.networkResponse+"\n"+tag);
//                    }
//                }){
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Log.d("TAG","Hua");
//                        return mymap;
//                    }
//                };



//                Log.d("TAG", String.valueOf(retMap));
//                Log.d("TAG", String.valueOf(mymap));

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("TAG",response.toString());
                        Log.d("RESPONSE",response.toString());
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {

                            if(!response.has("error"))
                            {
                                Log.d("TAG","one");
                                JSONObject user=response.getJSONObject("user");
                                Log.d("TAG","one");
                                String status=user.getString("user_status");
                                String apitoken=user.getString("api_token");
                                editor.putString(getString(R.string.Token),apitoken);
                                editor.putString(getString(R.string.Status),status);
                                Log.d("RESPONSE",status);
                                Log.d("RESPONSE",apitoken);
                                editor.commit();
                                startActivity(new Intent(getActivity(),MainActivity.class));
                            }
                            else
                            {
                                JSONObject error = response.getJSONObject("error");
                                String message=error.getString("msg");
                                if(message.equals("Your filled application has the errors"))
                                {
                                    Toast.makeText(getActivity(), "Your form cannot be filled because it has errors.\nCity and Locality cannot have spaces", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                    if(message.equals("Your account is ready!. We encountered a problem when sending mail to your given email address."))
                                    {
                                        JSONObject jsonObject1=new JSONObject();
                                        String url1= String.valueOf(URI.create("http://api.tedxdtu.in/api/token"));
                                        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                                        try {
                                            jsonObject1.put("username",firebaseUser.getEmail());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.POST, url1, jsonObject1, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                if(response.has("error"))
                                                {
                                                    //Yahan Exist nahi karta
                                                    SharedPreferences sharedprefrence=getActivity().getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
                                                    SharedPreferences.Editor editor=sharedprefrence.edit();
                                                    editor.putString(getString(R.string.Status),getString(R.string.unregistered));
                                                    editor.putString(getString(R.string.Token)," ");
                                                    editor.commit();
                                                    Toast.makeText(getActivity(), "ABAB"+response.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                                                    String token="";
                                                    try {
                                                        token=response.getString("token");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    SharedPreferences sharedprefrence=getActivity().getSharedPreferences(getString(R.string.MainInfo), MODE_PRIVATE);
                                                    final SharedPreferences.Editor editor=sharedprefrence.edit();
                                                    if(token.length()!=0)
                                                    {
                                                        editor.putString(getString(R.string.Token),token);
                                                        editor.commit();
                                                        String url2=String.valueOf(URI.create("http://api.tedxdtu.in/api/user/application"));
                                                        final String finalToken = token;
                                                        JsonObjectRequest newjsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                Toast.makeText(getActivity(), "LALALAL\n"+response.toString(), Toast.LENGTH_SHORT).show();
                                                                if(response.has("error"))
                                                                {

                                                                }
                                                                else
                                                                { String status=getString(R.string.unregistered);
                                                                    try {
                                                                        status=response.getString("status");
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    editor.putString(getString(R.string.Status),status);
                                                                    editor.commit();
                                                                }

                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(getActivity(), "LALALAL\n"+error.toString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }){
                                                            @Override
                                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                                Log.d("TAG","HUA");
                                                                Map<String, String>  params = new HashMap<String, String>();
                                                                params.put("Content-Type", "application/json");
                                                                params.put("tedxdtu-token", finalToken);
                                                                Log.d("TAG","HUA");
                                                                return params;

                                                            }



                                                        };
                                                        VolleySingleton.getInstance(getActivity()).addToRequestQueue(newjsonObjectRequest);

                                                    }
                                                }}}, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest1);
                                        startActivity(new Intent(getActivity(),MainActivity.class));
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            Log.d("TAG",e.toString());
                            Log.d("TAG", String.valueOf(e.getStackTrace()));
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "We are facing some issues , please try again later", Toast.LENGTH_SHORT).show();
                        Integer tag=1;
                        Log.d("TAG",error.toString()+"\n"+ error.getMessage()+"\n"+error.networkResponse+"\n"+tag);
                    }
                });

                VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
                //YAHN SE MAIN ACTIVITY YA FIR JO BHI KHOLNA HAI KHOL DO


            }
        });

    }
}

