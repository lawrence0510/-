package com.example.co2k_java_2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentProfile extends Fragment {

    private View view;
    protected Button bt_login, bt_about, bt_contact;
    protected TextView _tv_newAccount;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        bt_login = (Button) view.findViewById(R.id.btLogin);
        bt_about = (Button) view.findViewById(R.id.btAbout);
        bt_contact = (Button) view.findViewById(R.id.btContact);
        _tv_newAccount = (TextView) view.findViewById(R.id.tvNewAccount);

        Bundle bundle = new Bundle();

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_profile_to_fragment_login);
            }
        });

        bt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("state", 1);
                Navigation.findNavController(view).navigate(R.id.action_fragment_profile_to_aboutFragment, bundle);
            }
        });

        bt_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("state", 2);
                Navigation.findNavController(view).navigate(R.id.action_fragment_profile_to_aboutFragment, bundle);
            }
        });

        _tv_newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("state", 2);
                Navigation.findNavController(view).navigate(R.id.action_fragment_profile_to_newAccountFragment);
            }
        });


        return view;
    }
}