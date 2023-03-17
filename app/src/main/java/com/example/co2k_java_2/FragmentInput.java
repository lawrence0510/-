package com.example.co2k_java_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class FragmentInput extends Fragment {
    private View view;
    protected static final int CAMERA_PHOTO = 111;
    protected ImageView _iv_take_photo;
    protected Button bnt, _bt_next;
    public ArrayList<String> ingredientItems;
    protected ListView ingredient_list;
    protected int idx;


    public FragmentInput() {
        // Required empty public constructor
    }

    private void captureCameraImage() {
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(chooserIntent, CAMERA_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_PHOTO) {
            Uri selectedImage = data.getData();
            Bitmap image = (Bitmap) data.getExtras().get("data");
            _iv_take_photo.setImageBitmap(image);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_input, container, false);
        bnt = (Button) view.findViewById(R.id.btHandInput);
        _bt_next = view.findViewById(R.id.btCameraContinue);
        _iv_take_photo = view.findViewById(R.id.iv_take_photo);

        ingredientItems = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, ingredientItems);
        ingredient_list = (ListView) view.findViewById(R.id.lv_show_food);
        ingredient_list.setAdapter(adapter);


        _iv_take_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                captureCameraImage();
            }
        });

        String[] _food = {"花椰菜", "胡蘿蔔", "手機", "胡蘿蔔", "花椰菜" };
        _bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ingredientItems.add(_food[idx++]);
                adapter.notifyDataSetChanged();
            }
        });

        ingredient_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                ingredientItems.remove(pos);
                adapter.notifyDataSetChanged();

                return true;
            }
        });



        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("foods", ingredientItems);
                Navigation.findNavController(view).navigate(R.id.action_fragment_input_to_fragmentHandInput, bundle);
            }
        });
        return view;
    }
}