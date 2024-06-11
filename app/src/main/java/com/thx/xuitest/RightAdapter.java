package com.thx.xuitest;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.RightViewHolder> {

    private final ArrayList<Drinks> mList;
    private final LayoutInflater mLayoutInflater;
    private MyClickListener mListener;

    RightAdapter(LayoutInflater layoutInflater, ArrayList<Drinks> list) {
        this.mList = list;
        mLayoutInflater = layoutInflater;
        System.out.println("rightAdapter used");
    }

    public void buttonSetOnClick(MyClickListener mListener) {
        this.mListener = mListener;
    }

    public interface MyClickListener {
        void onclick(View v, int position);
    }

    private Drinks getItem(int position) {
        return mList.get(position);
    }

    @NonNull
    @Override
    public RightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RightViewHolder(
                mLayoutInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RightViewHolder holder, int position) {
        Drinks target = getItem(position);
        holder.bindBean(target);
        holder.chooseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onclick(v, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class RightViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkType;
        private final TextView drinkName;
        private final TextView drinkIntro;
        private final TextView drinkPrice;
        private final Button chooseBt;
        private final ImageView drinkImg;

        RightViewHolder(View itemView) {
            super(itemView);
            drinkType = itemView.findViewById(R.id.Text_drinkType);
            drinkName = itemView.findViewById(R.id.Text_drinkName);
            drinkIntro = itemView.findViewById(R.id.Text_drinkIntro);
            drinkPrice = itemView.findViewById(R.id.Text_drinkPrice);
            drinkImg = itemView.findViewById(R.id.img_drink);
            chooseBt = itemView.findViewById(R.id.BT_choose);
        }

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        void bindBean(final Drinks bean) {
            drinkName.setText(bean.get_name() + "  #" + (bean.get_number() + 1));
            if (bean.get_type() != null) {
                drinkType.setText(bean.get_type());
            } else {
                drinkType.setText(null);
            }
            Glide.with(itemView.getContext()).load(bean.getImageUrl()).into(drinkImg);
            drinkPrice.setText(String.format("￥ %.0f", bean.get_price()));
            drinkIntro.setText(bean.get_introduction());
        }
    }
}
