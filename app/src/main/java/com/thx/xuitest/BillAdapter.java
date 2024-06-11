package com.thx.xuitest;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter {
    private final ArrayList<Order> mList;
    private final LayoutInflater mLI;
    BillAdapter(LayoutInflater layoutInflater, ArrayList<Order> list){
        this.mList = list;
        mLI = layoutInflater;
        System.out.println("use BillAdapter successfully");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BillViewHolder(
                mLI.inflate(R.layout.order_item, viewGroup, false)
        );
    }
    private Order getItem(int position){
        return mList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order target = getItem(position);
        if(holder instanceof  BillAdapter.BillViewHolder){
            ((BillAdapter.BillViewHolder) holder).bindBean(target);
        }
        else{
            throw new IllegalStateException("Illegal State Exception Happened");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    private static class BillViewHolder extends  RecyclerView.ViewHolder{
        private final TextView TextNumber;
        private final TextView TextTime;
        private final TextView TextTakeAway;
        private final TextView TextCost;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            TextNumber = (TextView) itemView.findViewById(R.id.textView_number);
            TextTime = (TextView) itemView.findViewById(R.id.textView_time);
            TextTakeAway = (TextView) itemView.findViewById(R.id.textView_takeAway);
            TextCost = (TextView) itemView.findViewById(R.id.textView_cost);
        }
        @SuppressLint("SetTextI18n")
        void bindBean(final Order bean){
            TextNumber.setText("编号：" + bean.getOrder_number());
            TextTime.setText(bean.getTime());
            if(bean.getTakeAway().equals("1")){
                TextTakeAway.setText("打包");
            }
            else{
                TextTakeAway.setText("堂食");
            }
            TextCost.setText("总价：￥" + bean.getCost());
        }
    }
}
