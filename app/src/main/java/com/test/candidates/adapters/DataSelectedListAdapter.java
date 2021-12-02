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

public class DataSelectedListAdapter extends RecyclerView.Adapter<DataSelectedListAdapter.ViewHolder>{

    Context context;
    Presenter presenter;

    public DataSelectedListAdapter(Presenter presenter, Context context) {
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @NotNull
    @Override
    public DataSelectedListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new DataSelectedListAdapter.ViewHolder(view,presenter);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataSelectedListAdapter.ViewHolder holder, int position) {
        String nameStr = presenter.getSelectedData().get(position).getFirstName()+ " " + presenter.getSelectedData().get(position).getLastName();
        holder.name.setText(nameStr);
        holder.age.setText(String.valueOf(presenter.getSelectedData().get(position).getAge()));

        Glide.with(context)
                .load(presenter.getSelectedData().get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
       return presenter.getSelectedData().size();
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


          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (presenter != null) {
                        presenter.onItemInteraction(getAdapterPosition());
                    }
                }
            });*/

        }

    }
}
