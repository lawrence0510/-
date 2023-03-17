package com.example.co2k_java_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import com.example.co2k_java_2.databinding.FragmentHomeBinding;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentRecipe extends Fragment {

//    private FragmentHomeBinding binding;
    protected Python py;
    protected Button bnt;
    protected TextView tv;
    protected ImageView iv;
    protected PyObject egStore, obj = null, func_1 = null, func_2 = null;
    protected BitmapDrawable drawable;
    protected Bitmap bitmap;
    protected String imageString = "", information, info[], get_input;
    protected List<Recipe1> recipe1s;
    protected Bitmap b = null;

    private GridView gvShow;


    public FragmentRecipe() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe, container, false);


        String getInput = getArguments().getString("test");
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(getContext()));

        py = Python.getInstance();
        obj = py.getModule("recipe1");


        func_1 = obj.callAttr("input_ingredients", getInput);
//        func_1 = obj.callAttr("input_ingredients", "番茄");
        func_2 = obj.callAttr("get_recipes");

        information = func_2.toString();
        info = information.split(",");

        recipe1s = new ArrayList<>();

        for(int i = 0; i < info.length; ++i){

            if(i%3 == 2){
                Recipe1 recipe1 = new Recipe1(info[i -2], info[i - 1], info[i]);
                recipe1s.add(recipe1);
            }
        }

        String[] name = new String[recipe1s.size()];
        for(int i = 0; i < recipe1s.size(); ++i){
            name[i] = recipe1s.get(i).getName();
        }


        MyAdapter adapter = new MyAdapter(recipe1s, getContext());
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, name);
//        GridView list_test = (GridView) view.findViewById(R.id.gvShow);
        RecyclerView list_test = (RecyclerView) view.findViewById(R.id.gvShow);
        list_test.setHasFixedSize(true);
        list_test.setLayoutManager(new LinearLayoutManager(getContext()));

        list_test.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}