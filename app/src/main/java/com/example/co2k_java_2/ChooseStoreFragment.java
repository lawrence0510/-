package com.example.co2k_java_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseStoreFragment extends Fragment {

    protected String raw_info, info[], item_contents[];
    private ArrayList<Item> items;

    public ChooseStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_store, container, false);

        raw_info = getArguments().getString("rawInfo");
        info = raw_info.split(",");
        items = new ArrayList<>();
        item_contents = new String[info.length/3];
        int idx = 0;

        for(int i = 1; i < info.length; i++) {
            if(i % 3 == 2 ){
                item_contents[idx++] = (info[i - 2] +  "   " + info[i - 1]);
                Item item = new Item(info[i - 2], info[i - 1], info[i]);
                items.add(item);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, item_contents);
        ListView food_list = (ListView) view.findViewById(R.id.lv_show_item);
        food_list.setAdapter(adapter);

        food_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String url = items.get(position).getUrl();
                System.out.println(url);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

//        System.out.println(raw_info);

        return view;
    }
}