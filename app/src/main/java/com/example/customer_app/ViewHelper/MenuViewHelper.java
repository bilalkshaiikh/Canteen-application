package com.example.customer_app.ViewHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_app.Interface.ItemClickListener;
import com.example.customer_app.R;

public class MenuViewHelper extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;
    public MenuViewHelper(@NonNull View itemView) {
        super(itemView);

        txtMenuName = (TextView)itemView.findViewById(R.id.menu_name);
        imageView =(ImageView)itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTxtMenuName(TextView txtMenuName) {
        this.txtMenuName = txtMenuName;
    }

    @Override
    public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
