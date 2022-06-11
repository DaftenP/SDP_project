package com.jpl.sdp_project.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jpl.sdp_project.R;
import com.jpl.sdp_project.retrofit.Item;

import java.util.List;

//Adapter : listview를 만들어서 recyclerview와 연결해줌 , Holder에서 만들어 준 listView를 inflater를 이용해 객체화 시키고 실제 데이터를 담아줌
//즉, Holder가 listview 그릇을 만들면 Adapter가 실제 데이터를 담은 listView를 만들어주는 것.
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private List<Item> itemAdapter;

    public RecyclerAdapter(List<Item> itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemAdapter.get(position);
        holder.setItem(item);
//        holder.name.setText(itemAdapter.get(position).getIngrName());
      /*  holder.numOfStar.setText("" + itemAdapter.get(position).getItemName());
        holder.numOfLike.setText("" + itemAdapter.get(position).getEntpName());
       */
    }

    @Override
    public int getItemCount() {
        return itemAdapter.size();
    }

    //Holder: 레이아웃과 연결해서 listView를 만들어주는 역할 (단순 연결)
     public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ingrName,itemName,entpName;
//         TextView numOfStar;
//         TextView numOfLike;
         public MyViewHolder(@NonNull View itemView) {
             super(itemView);

             ingrName = itemView.findViewById(R.id.ingrName);
             itemName = itemView.findViewById(R.id.itemName);
             entpName = itemView.findViewById(R.id.entpName);
         }
         public void setItem(Item item){
             ingrName.setText(item.getIngrName());
             itemName.setText(item.getItemName());
             entpName.setText(item.getEntpName());

         }
     }
}
