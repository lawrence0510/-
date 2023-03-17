package com.example.co2k_java_2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    String text1, text2;
    TextView _content;
    int sta;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        sta = getArguments().getInt("state");

        _content = view.findViewById(R.id.tv_about);
        text1 = String.format("Hello 大家好，我們是\"二進位\"團隊。\n\n 這支參加比賽的作品叫做\"瘋狂做菜！\"\n\n很高興可以參加這場比賽。\n\n謝謝大家~~");
        text2 = String.format("聯絡人：王曉明\n\n電話：0912345678\n\n信箱：CO2K@gmail.com");
        _content.setTextSize(20);

        if(sta == 1){
            _content.setText(text1);
        }else{
            _content.setText(text2);
        }
        return view;
    }
}