package com.example.co2k_java_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class NewAccountFragment extends Fragment {


    protected ImageButton _ib_newAccount;
    protected Button _bt_signup;
    public NewAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_account, container, false);

        _ib_newAccount = view.findViewById(R.id.ib_newAccount);

        _ib_newAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        _bt_signup = view.findViewById(R.id.bt_signup);
        _bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_newAccountFragment_to_fragment_profile);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            ImageButton newPhoto = getActivity().findViewById(R.id.ib_newAccount);
            newPhoto.setImageURI(selectedImage);
        }

    }
}