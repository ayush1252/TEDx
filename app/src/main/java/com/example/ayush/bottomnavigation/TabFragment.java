package com.example.ayush.bottomnavigation;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ayush on 16/2/17.
 */

public class TabFragment extends android.support.v4.app.Fragment implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    public static FloatingActionButton next;
    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Phone;
    EditText POW;
    EditText DOB;
    public TabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next = (FloatingActionButton) view.findViewById(R.id.next);
        final SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Login",MODE_PRIVATE);
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


//        if(next==null)
//            Toast.makeText(getActivity(), "Null hai", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(getActivity(), "Not Null", Toast.LENGTH_SHORT).show();
//

        FirstName= (EditText) view.findViewById(R.id.Fname);
        //FirstName.setFocusable(false);
        LastName= (EditText) view.findViewById(R.id.Lname);
        Email= (EditText) view.findViewById(R.id.Email);
        try {
            Email.setText(firebaseUser.getEmail());
           // Email.setFocusable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Phone= (EditText) view.findViewById(R.id.Phone);
        POW= (EditText) view.findViewById(R.id.Work);
        DOB= (EditText) view.findViewById(R.id.DOB);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GoogleAuthUtil.getToken(getActivity(),FirebaseAuth.getInstance().getCurrentUser(),)
                RegistrationActivity.registrationform.setId(sharedPreferences.getString("token",null));
               // RegistrationActivity.registrationform.setUsername(sharedPreferences.getString("username",null));

                if(FirstName.getText().toString().length()==0) {
                    FirstName.setError("This Cannot be empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setFirstname(FirstName.getText().toString());

                if(LastName.getText().toString().length()==0)
                {
                    LastName.setError("This Cannot be empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setSecondname(LastName.getText().toString());

                if(Email.getText().toString().length()==0)
                {
                    Email.setError("This Cannot be empty");
                    return;
                }
                if(!isValidEmail(Email.getText().toString()))
                {
                    Email.setError("Please enter valid Email ID");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setEmail(Email.getText().toString());

                if(Phone.getText().toString().length()==0)
                {
                    Phone.setError("This Cannot be empty");
                    return;
                }
                else if(!isValidMobile(Phone.getText().toString()))
                {
                    Phone.setError("Please enter valid Phone Number");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setPhone(Phone.getText().toString());

                if(POW.getText().toString().length()==0)
                {
                    POW.setError("This Cannot be empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setPlaceofwork(POW.getText().toString());

                if(DOB.getText().toString().length()==0)
                {
                    DOB.setError("This Cannot be empty");
                    return;
                }
                else
                    RegistrationActivity.registrationform.setDob(DOB.getText().toString());

                RegistrationActivity.viewPager.setCurrentItem(1,true);
                return;
            }
        });

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        (com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener) TabFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
                dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_1);
                dpd.show(getActivity().getFragmentManager(),"Date Picker Dialog");
            }
        });
        DOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(DOB.getText().toString().length()!=0)
                    return;
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        (com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener) TabFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
                dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_1);
                dpd.show(getActivity().getFragmentManager(),"Date Picker Dialog");

            }
        });

    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String str=""+dayOfMonth+"."+monthOfYear+"."+year;
        DOB.setText(str);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
