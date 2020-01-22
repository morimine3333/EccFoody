//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    private static final String TAG = "MyAdapter2";

    private String[] mDataset;
    private int[] imageViews;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView mTextView;
        public ImageView imageView;

        public MyViewHolder(View v){
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view2);
            mTextView = (TextView) v.findViewById(R.id.tv_text2);
            imageView = (ImageView)v.findViewById(R.id.iv_image2);
        }
    }

    public MyAdapter2(String[] myDataset, int[] imageViews){

        this.mDataset = myDataset;
        this.imageViews = imageViews;
    }



    @Override
    public MyAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item2, parent, false);
        MyAdapter2.MyViewHolder vh = new MyAdapter2.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter2.MyViewHolder holder, final int position){
        //View view = View.inflate(context, R.layout.layout_sample,root);
        holder.mTextView.setText(mDataset[position]);
        holder.imageView.setImageResource(imageViews[position]);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //クリック処理
                Log.d(TAG, String.valueOf(position));
            }
        });
    }

    @Override
    public int getItemCount() { return mDataset.length; }
}
