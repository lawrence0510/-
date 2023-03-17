package com.example.co2k_java_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FragmentLogin extends Fragment {
//    private static final int RESULT_OK = ;
    private View view;

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_login, container, false);
        Button changeName = (Button) view.findViewById(R.id.btChangeName);
        changeName.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setMessage("請輸入新的暱稱");
                EditText editText = new EditText(getActivity().getApplicationContext());
                alert.setView(editText);
                alert.setNeutralButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView textView = (TextView) getActivity().findViewById(R.id.lbname);
                        textView.setText(editText.getText().toString());
                    }
                });
                alert.show();
           }
        });

        Button changePassword = (Button) view.findViewById(R.id.btChangePassword);
        changePassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setMessage("請輸入新的密碼");
                EditText editText = new EditText(getActivity().getApplicationContext());
                alert.setView(editText);
                alert.setNeutralButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        TextView textView = (TextView) getActivity().findViewById(R.id.lbname);
//                        textView.setText(editText.getText().toString());
                    }
                });
                alert.show();
            }
        });

        Button changePhoto = (Button) view.findViewById(R.id.btChangePhoto);
        changePhoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });


        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            ImageView imageView = getActivity().findViewById(R.id.profile_image);
            imageView.setImageURI(selectedImage);
        }

    }
}