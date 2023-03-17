package com.example.co2k_java_2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class FragmentRefrigerator extends Fragment {

     protected String[] food_name;
     Button _bt_go_reipe;

    public FragmentRefrigerator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);

//        food_name = getArguments().getStringArray("str_food");
//        food_name = new String[]{"花椰菜", "胡蘿蔔", "番茄"};
        food_name = getArguments().getStringArray("foods");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, food_name);
        GridView list_test = (GridView) view.findViewById(R.id.gv_refridge);
        list_test.setAdapter(adapter);

        _bt_go_reipe = view.findViewById(R.id.bt_go_recipe);
        _bt_go_reipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle2 = new Bundle();
                String __input = "";
                for(String i: food_name){
                    String temp = String.format(" %s", i);
                    __input += temp;
                }

                bundle2.putString("test", __input);

                Navigation.findNavController(view).navigate(R.id.action_fragment_refrigerator_to_fragment_recipe, bundle2);
            }
        });

        return view;
    }
}