package com.test.candidates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.candidates.R;
import com.test.candidates.model.Model;
import com.test.candidates.presenter.Presenter;

import org.jetbrains.annotations.NotNull;

public class DataAdapter extends ListAdapter<Model, DataAdapter.ViewHolder> {
    
    Context context;
    Presenter presenter;

    public DataAdapter(Presenter presenter,Context context) {
        super(DIFF_CALLBACK);
        this.presenter = presenter;
        this.context = context;
    }

    //to check weather to items have same id or not
    private static final DiffUtil.ItemCallback<Model> DIFF_CALLBACK = new
            DiffUtil.ItemCallback<Model>() {
                @Override
                public boolean areItemsTheSame(Model oldItem, Model newItem) {
                    return (oldItem.getFirstName().equalsIgnoreCase(newItem.getFirstName())) &&
                            (oldItem.getLastName().equalsIgnoreCase(newItem.getLastName()));
                }

                @Override
                public boolean areContentsTheSame(@NonNull @NotNull Model oldItem, @NonNull @NotNull Model newItem) {
                    return (oldItem.getFirstName().equalsIgnoreCase(newItem.getFirstName())) &&
                            (oldItem.getLastName().equalsIgnoreCase(newItem.getLastName())) &&
                            (oldItem.getAge() == newItem.getAge());
                }

            };

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view,presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String nameStr = getItem(position).getFirstName()+ " " + getItem(position).getLastName();
        holder.name.setText(nameStr);
        holder.age.setText(String.valueOf(getItem(position).getAge()));

        Glide.with(context)
                .load(getItem(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    public class ViewHolder  extends RecyclerView.ViewHolder /*implements View.OnClickListener */{
        ImageView image;
        TextView name,age;
        Presenter presenter;

        public ViewHolder(@NonNull @NotNull View itemView,Presenter presenter) {
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
