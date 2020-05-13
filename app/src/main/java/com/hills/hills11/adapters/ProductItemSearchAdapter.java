package com.hills.hills11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hills.hills11.R;
import com.hills.hills11.data.ProductItemDisplay;

import java.util.ArrayList;

public class ProductItemSearchAdapter extends RecyclerView.Adapter<ProductItemSearchAdapter.MyViewHolder> {

    private ArrayList<ProductItemDisplay> list;

    public ProductItemSearchAdapter(ArrayList<ProductItemDisplay> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.product_item_list , parent , false );
        return new MyViewHolder ( v );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder , int position) {
        holder.mProductItemTextView.setText ( list.get ( position ).getProductItemName ( ) );

    }

    @Override
    public int getItemCount() {
        return list.size ( );
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mProductItemTextView;

        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
            mProductItemTextView = itemView.findViewById ( R.id.productItemTextView );
        }
    }
}
