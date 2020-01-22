//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//                                                      変更
public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder>{
    private static final String TAG = "MyAdapter1";

    private Context context;

    private List<String> storeNames;
    private List<String> storeImgs;
    private List<String> lunchBudgets;
    private List<String> dinnerBudgets;
    private List<String> genres;



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView storeName;
        public ImageView storeImg;
        public ImageView lunchIcon;
        public TextView lunchBudget;
        public ImageView dinnerIcon;
        public TextView dinnerBudget;
        public TextView genre;

        public MyViewHolder(View v){
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view1);
            storeName = (TextView)v.findViewById(R.id.store_name);
            storeImg = (ImageView)v.findViewById(R.id.store_img);
            lunchIcon = (ImageView)v.findViewById(R.id.lunch_icon);
            lunchBudget = (TextView) v.findViewById(R.id.lunch_budget);
            dinnerIcon = (ImageView)v.findViewById(R.id.dinner_icon);
            dinnerBudget = (TextView)v.findViewById(R.id.dinner_budget);
            genre = (TextView)v.findViewById(R.id.genre_text);
        }
    }

    public MyAdapter1(List<String> storeNames, List<String> storeImgs, List<String> lunchBudgets, List<String> dinnerBudgets, List<String> genres){
        this.storeNames = storeNames;
        this.storeImgs = storeImgs;
        this.lunchBudgets = lunchBudgets;
        this.dinnerBudgets = dinnerBudgets;
        this.genres = genres;
    }

    @Override
    //      変更
    public MyAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //                                                              変更
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item1, parent, false);
        //変更                                変更
        MyAdapter1.MyViewHolder vh = new MyAdapter1.MyViewHolder(v);
        return vh;
    }

    @Override
    //                              変更
    public void onBindViewHolder(MyAdapter1.MyViewHolder holder, final int position){
        //View view = View.inflate(context, R.layout.layout_sample,root);
        holder.storeName.setText(storeNames.get(position));
        holder.storeImg.setImageResource(R.drawable.img_1);
        holder.lunchIcon.setImageResource(R.drawable.img_1);
        holder.lunchBudget.setText(lunchBudgets.get(position));
        holder.dinnerIcon.setImageResource(R.drawable.img_1);
        holder.dinnerBudget.setText(dinnerBudgets.get(position));
        holder.genre.setText(genres.get(position));

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //クリック処理
                Log.d(TAG, String.valueOf(position));
            }
        });

//
//        holder.nTextView.setText(nDataset[position]);
//        holder.dTextView.setText(dDataset[position]);
//
//        holder.imageView.setImageResource(imageViews[position]);

//        GlideApp.(holder.storeImg)
    }

    @Override
    public int getItemCount() { return storeNames.size(); }
}
