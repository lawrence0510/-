package com.example.co2k_java_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Recipe1> recipe1s;
        private Context context;

        public MyAdapter(List<Recipe1> recipe1s, Context context) {
                this.recipe1s = recipe1s;
                this.context = context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recipe_block, parent, false);
                return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position){
                final Recipe1 recipe1 = recipe1s.get(position);
                holder.tv_name.setText(recipe1.getName());

                Picasso.get()
                        .load(recipe1.getImage())
                        .into(holder.iv_recipe);

                holder._recipe_block_layout.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                                Bundle toStepBundle = new Bundle();
//                                System.out.println(recipe1.getUrl());
                                toStepBundle.putString("recipe1_url", recipe1s.get(position).getUrl());
                                toStepBundle.putString("recipe1_name", recipe1s.get(position).getName());
                                toStepBundle.putString("recipe1_img", recipe1s.get(position).getImage());

                                Navigation.findNavController(view).navigate(R.id.action_fragment_recipe_to_stepByStepFragment, toStepBundle);
                        }
                });
        }

        @Override
        public int getItemCount() {
                return recipe1s.size() ;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

                public TextView tv_name;
                public ImageView iv_recipe;
                public LinearLayout _recipe_block_layout;

                public ViewHolder(View v) {
                        super(v);
                        tv_name = (TextView) v.findViewById(R.id.name);
                        iv_recipe = (ImageView) v.findViewById(R.id.recipe_img);
                        _recipe_block_layout = (LinearLayout) v.findViewById(R.id.recipe_block_layout);
                }
        }

}
