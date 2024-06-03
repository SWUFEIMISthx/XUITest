package com.thx.xuitest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftViewHolder> {

    private final ArrayList<RightListBean> mList;
    private OnItemClickListener onItemClickListener;

    public LeftAdapter(ArrayList<RightListBean> list) {
        this.mList = list;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private RightListBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public LeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.left_list_item,parent,false);

        return new LeftViewHolder(view);

    }

    @Override
    public void onBindViewHolder(LeftViewHolder holder, int position) {
        RightListBean target = getItem(holder.getAdapterPosition());
        if (holder instanceof LeftAdapter.LeftViewHolder) {
            ((LeftAdapter.LeftViewHolder) holder).bindBean(target);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("onClick",holder.getAdapterPosition()+"\t");
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClicked(getItem(holder.getAdapterPosition()).getRightPosition());
                    }
                    for (RightListBean bean:mList){
                        bean.setSelect(false);
                    }
                    getItem(holder.getAdapterPosition()).setSelect(true);
                    notifyDataSetChanged();
                }
            });
        } else {
            throw new IllegalStateException("Illegal state Exception onBindviewHolder");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        LeftViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.drink_type);
        }

        void bindBean(RightListBean target)
        {
            tvTitle.setText(target.getTitle());
            if (target.isSelect()){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            }
        }
    }

    interface OnItemClickListener {
        void onItemClicked(int rightPosition);
    }

    public void setCurrentPosition(int rightPosition){
        for (int i = 0; i < mList.size(); i++){
            RightListBean bean = mList.get(i);
            if(i < mList.size()-1)
            {
                RightListBean nextBean = mList.get(i+1);
                bean.setSelect(bean.getRightPosition() <= rightPosition && rightPosition < nextBean.getRightPosition());
            }
            else
            {
                if(bean.getRightPosition() <= rightPosition)
                {
                    bean.setSelect(true);
                }
                else {
                    bean.setSelect(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String getCurrentTitle(){
        String currentTitle = "";
        for (RightListBean bean:mList){
            if (bean.isSelect()){
                currentTitle = bean.getTitle();
                break;
            }
        }
        return currentTitle;
    }
}
