package com.example.co2k_java_2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentHandInput extends Fragment {

    protected Button bnt_cam, bnt_OK,  bnt_next, bnt_tofrdge;
    protected EditText _et_input;
    public ArrayList<String> ingredientItems;
    protected ListView ingredient_list;

    public FragmentHandInput() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_hand_input, container, false);
        ingredientItems = getArguments().getStringArrayList("foods");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, ingredientItems);
        ingredient_list = (ListView) view.findViewById(R.id.lv_ingredient);
        ingredient_list.setAdapter(adapter);

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



        bnt_cam = (Button) view.findViewById(R.id.btCameraInput);
        bnt_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragmentHandInput_to_fragment_input);
            }
        });


        bnt_OK = (Button) view.findViewById(R.id.btCameraFinish);
        bnt_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle2 = new Bundle();
                String __input = "";
                for(String i: ingredientItems){
                    String temp = String.format(" %s", i);
                    __input += temp;
                }

                bundle2.putString("test", __input);

                Navigation.findNavController(view).navigate(R.id.action_fragmentHandInput_to_fragment_recipe, bundle2);
            }
        });



        _et_input = view.findViewById(R.id.et_input);
        bnt_next = (Button) view.findViewById(R.id.btCameraContinue);
        bnt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String __input = _et_input.getText().toString();
                ingredientItems.add(__input);
                adapter.notifyDataSetChanged();

            }
        });

        bnt_tofrdge = (Button) view.findViewById(R.id.bt_toFridge);
        bnt_tofrdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String[] trans = new String[ingredientItems.size()];
                for(int i = 0; i < ingredientItems.size(); i++) {
                    trans[i] = ingredientItems.get(i);
                }
                bundle.putStringArray("foods", trans);
                Navigation.findNavController(view).navigate(R.id.action_fragmentHandInput_to_fragment_refrigerator, bundle);
            }
        });



        return view;
    }
}