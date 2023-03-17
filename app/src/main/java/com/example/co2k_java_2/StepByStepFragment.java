package com.example.co2k_java_2;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

public class StepByStepFragment extends Fragment implements TextToSpeech.OnInitListener {

    protected Python py;
    protected PyObject recipe = null, store = null, func_1 = null, func_2 = null, func_3 = null, func_4 = null, func_5 = null;
    protected String recipe_url, recipe_name, recipe_img, food[], _content, food_name[], food_amount[], food_contents[], raw_info, steps_content[];
    protected ArrayList<Ingredient> ingredients;
    protected ArrayList<Step> steps;
    protected Button _bt_next;
    protected ImageButton _ib_speaker, _ib_timer;
    protected ImageView _iv_img;
    protected TextView _tv_title, _tv_content, _tv_title2, _tv_count_time;
    protected TextToSpeech txts;
    //將來會成為requestCode驗證，一定要他但可以不用管他
    protected static final int MY_DATA_CHECK_CODE = 1234;
    protected  Timer timer = new Timer();
    protected int counter, count_time;


    public StepByStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_by_step, container, false);

        recipe_url = getArguments().getString("recipe1_url");
        recipe_name = getArguments().getString("recipe1_name");
        recipe_img = getArguments().getString("recipe1_img");

        _tv_title = view.findViewById(R.id.tv_title);
        _tv_content = view.findViewById(R.id.tv_content);
        _iv_img = view.findViewById(R.id.iv_img);
//        _ib_speaker = view.findViewById(R.id.ib_speaker);




        if(!Python.isStarted())
            Python.start(new AndroidPlatform(getContext()));

        py = Python.getInstance();

        System.out.println(recipe_url);

        recipe = py.getModule("recipe2");
        func_1 = recipe.callAttr("input_url", recipe_url);
        func_2 = recipe.callAttr("get_ingredientsandamounts");

        food = func_2.toString().split(",");
        ingredients = new ArrayList<>();
        food_name = new String[food.length/2];
        food_amount = new String[food.length/2];
        food_contents = new String[food.length/2];

        int idx = 0;
        for(int i = 1; i < food.length; ++i){
            if(i % 2 == 1){
                food_contents[idx++] = (food[i - 1] + "   " + food[i]);
                Ingredient ingredient = new Ingredient(food[i - 1], food[i]);
                ingredients.add(ingredient);
            }
        }

        char[] temp = (recipe.callAttr("get_peopleamounts").toString() + " / " + recipe.callAttr("get_time").toString()).toCharArray();
        for(int i = 0; i < temp.length; ++i){
            if(temp[i] == '\n'){
                temp[i] = ' ';
            }
        }
        _content = String.valueOf(temp);

        _tv_title.setText(recipe_name);
        Glide.with(view).load(recipe_img).into(_iv_img);


        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        _ib_speaker = view.findViewById(R.id.ib_speaker);
        TextView tv_speak_content = (TextView) view.findViewById(R.id.tv_title2);
        System.out.println(tv_speak_content.getText().toString());
        final String[] speak_content = {""};

        _ib_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){


                speak_content[0] = tv_speak_content.getText().toString();
                txts.speak(speak_content[0], TextToSpeech.QUEUE_FLUSH, null);

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, food_contents);
        ListView food_list = (ListView) view.findViewById(R.id.lv_show_food);
        food_list.setAdapter(adapter);

        food_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String food_name = ingredients.get(position).getName();
                store = py.getModule("ecommercegrocery");
                func_3 = store.callAttr("input_ingredients", food_name);
                raw_info = store.callAttr("get_all").toString();

                Bundle toStepBundle = new Bundle();
                toStepBundle.putString("rawInfo", raw_info);

                Navigation.findNavController(view).navigate(R.id.action_stepByStepFragment_to_chooseStoreFragment, toStepBundle);
            }
        });

        _tv_content.setText(_content);


        func_4 = recipe.callAttr("get_stepsandimagesurls");
        String temp_content = func_4.toString();
        steps = new ArrayList<>();
        if( temp_content.contains("@")){
            steps_content = temp_content.split("@");
            for(int i = 0; i < steps_content.length; ++i){
                if(i % 2 == 1){
                    Step step = new Step(steps_content[i - 1], steps_content[i]);
                    steps.add(step);
                }
            }
        }else{
            steps_content = temp_content.split(",");
            for(int i = 0; i < steps_content.length; ++i){
                Step step = new Step(steps_content[i]);
                steps.add(step);
            }
        }

        final int[] step_idx = {0};
        _bt_next = view.findViewById(R.id.bt_next);
        _tv_title2 = view.findViewById(R.id.tv_title2);
        _bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ts = String.valueOf(++step_idx[0]);

                if(step_idx[0] <= steps.size()){

                    System.out.println(ts);
                    _tv_content.setTextSize(35);
                    _tv_content.setTypeface(null, Typeface.BOLD);
                    _tv_content.setText(ts);
                    _tv_title2.setTextSize(25);
                    _tv_title2.setTypeface(null, Typeface.NORMAL);
                    _bt_next.setText("下一步");
                    food_list.setVisibility(View.GONE);

                    String addContent = steps.get(step_idx[0] - 1).step_content();
//                ts += ("\n" + addContent);
                    _tv_title2.setText(addContent);

                }else{
                    _tv_content.setTextSize(20);
                    _tv_content.setTypeface(null, Typeface.NORMAL);
                    _tv_content.setText("所需食材");
                    _tv_title2.setText("");
                    food_list.setVisibility(View.VISIBLE);
                    _bt_next.setText("結束");
                    if(step_idx[0] > steps.size() + 1){
                        Navigation.findNavController(view).navigate(R.id.action_stepByStepFragment_to_fragment_input );
                    }
                }
            }
        });

        _ib_timer = view.findViewById(R.id.ib_timer);
        _tv_count_time = view.findViewById(R.id.tv_count_time);
        _ib_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                count_time++;
                String show_time = (String.valueOf(count_time) + "分鐘\n按下即開始計時");
                _tv_count_time.setText(show_time);
            }
        });


        _tv_count_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int downTime = 1000 * 60 * count_time;
                new CountDownTimer(downTime, 1000){
                    public void onTick(long millisUntilFinished){
                        _tv_count_time.setText(String.valueOf(60 * count_time - counter));
                        counter++;
                    }
                    public void onFinish(){
                        _tv_count_time.setText("FINISH!!");
                        counter = 0;
                        count_time = 0;
                        txts.speak("時間到拉拉拉", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }.start();
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        TextView txt = (TextView) getView().findViewById(R.id.tv_title2);
        if (requestCode == MY_DATA_CHECK_CODE)
        {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                // success, create the TTS instance
                txts = new TextToSpeech(getActivity(), (TextToSpeech.OnInitListener) this);

            }//下面的else也可刪掉，但留著比較保險
            else
            {
                txt.setText("Missing");
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    @Override
    //Called to signal the completion of the TextToSpeech engine initialization.
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            txts.setLanguage(Locale.TAIWAN);
        }
    }

}