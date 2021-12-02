package com.test.candidates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.candidates.R;
import com.test.candidates.presenter.Presenter;

import org.jetbrains.annotations.NotNull;

public class DataCandidateListAdapter extends RecyclerView.Adapter<DataCandidateListAdapter.ViewHolder>{

    Context context;
    Presenter presenter;

    public DataCandidateListAdapter(Presenter presenter, Context context) {
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @NotNull
    @Override
    public DataCandidateListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new DataCandidateListAdapter.ViewHolder(view,presenter);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataCandidateListAdapter.ViewHolder holder, int position) {
        String nameStr = presenter.getOrginalData().get(position).getFirstName()+ " " + presenter.getOrginalData().get(position).getLastName();
        holder.name.setText(nameStr);
        holder.age.setText(String.valueOf(presenter.getOrginalData().get(position).getAge()));

        Glide.with(context)
                .load(presenter.getOrginalData().get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
       return presenter.getOrginalData().size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder /*implements View.OnClickListener */{
        ImageView image;
        TextView name,age;
        Presenter presenter;

        public ViewHolder(@NonNull @NotNull View itemView, Presenter presenter) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.nametxt);
            age = itemView.findViewById(R.id.agetxt);
            this.presenter = presenter;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (presenter != null) {
                        presenter.onItemInteraction(getAdapterPosition());
                    }
                }
            });


        }

    }
}
