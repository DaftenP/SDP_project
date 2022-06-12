package com.jpl.sdp_project.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jpl.sdp_project.NewActivity;
import com.jpl.sdp_project.R;
import com.jpl.sdp_project.retrofit.Item;

import java.util.List;

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    //필드 정의 부분
    private Context mContext;
    private List<Item> itemAdapter;

    //생성자
    public RecyclerAdapter(Context context, List<Item> itemAdapter) {
        this.mContext = context;
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
    }
    @Override
    public int getItemCount() {
        try {   //예외처리 부분
            return itemAdapter.size();  //파싱돼서 넘어온 데이터의 크기가 0일때 list.size()가 예외발생!
        } catch (Exception e){
            return 0;                   //예외 발생시 0을 리턴하여 리사이클러뷰에 아이템을 생성하지 않음.
        }

    }

    //뷰홀더
     public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ingrName,itemName,entpName;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);

             //지역변수
             ingrName = itemView.findViewById(R.id.ingrName);
             itemName = itemView.findViewById(R.id.itemName);
             entpName = itemView.findViewById(R.id.entpName);

             itemView.setClickable(true);
             itemView.setOnClickListener(new View.OnClickListener() {   //리사이클러뷰 내의 아이템 OnClickListener
                 @Override
                 public void onClick(View v) {
                     int pos = getAdapterPosition();
                     if(pos != RecyclerView.NO_POSITION) {
                         Intent intent = new Intent(mContext, NewActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         intent.putExtra("TEXT", itemAdapter.get(pos).getIngrName());
                         mContext.startActivity(intent);
                     }
                 }
             });
         }
         public void setItem(Item item){
             ingrName.setText(item.getIngrName());
             itemName.setText(item.getItemName());
             entpName.setText(item.getEntpName());

         }

         }
     }



