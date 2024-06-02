package com.example.finalProject_meatector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterHistory extends RecyclerView.Adapter<adapterHistory.ViewHolderHistory> {

    private Context context;
    private ArrayList image,predict,date;

    public adapterHistory(Context context,ArrayList image, ArrayList predict, ArrayList date) {
        this.context = context;
        this.image = image;
        this.predict = predict;
        this.date = date;
    }

    @NonNull
    @Override
    public ViewHolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cv_history, parent, false);

        return new ViewHolderHistory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistory holder, int position) {


        Bitmap bm = null;

        bm = BitmapFactory.decodeFile(image.get(position).toString());


        holder.ivHistory.setImageBitmap(bm);
//        holder.ivHistory.setImageURI(Uri.parse(image.get(position).toString()));
        holder.tvPredict.setText(predict.get(position).toString());
        holder.tvDate.setText(date.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return predict.size();
    }

    public class ViewHolderHistory extends RecyclerView.ViewHolder{

        ImageView ivHistory;
        TextView tvPredict,tvDate;

        public ViewHolderHistory(@NonNull View itemView) {
            super(itemView);

            ivHistory = itemView.findViewById(R.id.imgHistory);
            tvPredict = itemView.findViewById(R.id.textPredict);
            tvDate = itemView.findViewById(R.id.textDate);


        }
    }





}
