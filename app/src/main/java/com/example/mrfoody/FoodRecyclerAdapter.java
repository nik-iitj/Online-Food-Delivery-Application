package com.example.mrfoody;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder> {

    public List<FoodList>food_list;

    public FoodRecyclerAdapter(List<FoodList> food_list){
        this.food_list = food_list;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRecyclerAdapter.ViewHolder holder, int position) {

        String itemId = food_list.get(position).foodId;

        String name = food_list.get(position).getItem_name();
        String price = food_list.get(position).getPrice();
        String url = food_list.get(position).getImage();
        holder.setName(name);
        holder.setPrice(price);
        holder.setImage(url);
        holder.setTime(food_list.get(position).getTime());


        holder.fView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),IndividualFoodActivity.class);
                intent.putExtra("id",itemId);
                v.getContext().startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView foodName,price,foodTime;
        ImageView foodImg;
        View fView;


        public ViewHolder( View itemView) {
            super(itemView);
            fView = itemView;




        }

        public void setName (String name){

            foodName = fView.findViewById(R.id.foodName);
            foodName.setText(name);


        }


        public void setPrice(String fPrice) {
            price = fView.findViewById(R.id.itemPrice);
            price.setText("Rs "+fPrice);

        }
        public void setTime(String time){
            foodTime = fView.findViewById(R.id.foodTime);
            foodTime.setText("Gets ready in " + time +" minutes");



        }

        public void setImage(String image){
            foodImg = fView.findViewById(R.id.foodImg);
            Glide.with(fView.getContext()).load(image).into(foodImg);

        }
    }


}
