package com.example.finalProject_meatector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.VersionVH> {

    List<Version> versionsList;

    public VersionAdapter (List<Version> versionsList) {
        this.versionsList = versionsList;
    }

    @NonNull
    @Override
    public VersionAdapter.VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        return  new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {

        Version version = versionsList.get(position);
        holder.CodeNameTxt.setText(version.getCode());
        holder.IsiTxt.setText(version.getIsi());

        boolean isExpandable = versionsList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);


    }

    @Override
    public int getItemCount() {
        return versionsList.size();
    }

    public class VersionVH extends  RecyclerView.ViewHolder {

        TextView CodeNameTxt, IsiTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public VersionVH(@NonNull  View itemView) {
            super(itemView);

            CodeNameTxt = itemView.findViewById(R.id.judulAwal);
            IsiTxt = itemView.findViewById(R.id.isi);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Version version = versionsList.get(getAdapterPosition());
                    version.setExpandable(!version.isExpandable());
                    notifyItemChanged(getAdapterPosition());

                }
            });


        }
    }
}
