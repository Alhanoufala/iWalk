package com.example.iWalk;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class ProfileFragment extends Fragment {
    DBHelper DB;
    View layoutView;
    String nameFrom,email;
    TextView tv_name,tvEmail;


    public ProfileFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DB=new DBHelper(getContext());
        layoutView= inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("datastore", MODE_PRIVATE);
        String name = preferences.getString("Name", "No name was stored");
        Cursor profileData = DB.getDetails(name);
        if(profileData.getCount()==0){
            Toast.makeText(getContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
        }
        while(profileData.moveToNext()){
            nameFrom = profileData.getString(0);
            email = profileData.getString(2);

        }
        tv_name=(TextView) layoutView.findViewById(R.id.tv_name);
        tvEmail =(TextView) layoutView.findViewById(R.id.email);


        tv_name.setText(nameFrom);

        tvEmail.setText(email);

        return layoutView;
    }
}
