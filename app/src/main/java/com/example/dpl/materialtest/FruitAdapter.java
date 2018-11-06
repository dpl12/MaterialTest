package com.example.dpl.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private Context context;
    private List<Fruit> mFruitList;
    FruitAdapter(List<Fruit> fruitList) {
        mFruitList=fruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context==null){
            context=viewGroup.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.fruit_item,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Fruit fruit=mFruitList.get(position);
                Intent intent=new Intent(context,FruitActivity.class);//跳转到水果详情页面
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                context.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Fruit fruit=mFruitList.get(i);
        viewHolder.fruitName.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(viewHolder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView fruitImage;
        private TextView fruitName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView= (CardView) itemView;//整个view使用的卡片布局
            fruitImage= (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName= (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }
}

