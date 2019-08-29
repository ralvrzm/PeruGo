package pe.edu.pucp.perugopf.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.presentation.utils.AdapterClickListener;

public class NewEventAdapter extends RecyclerView.Adapter<NewEventAdapter.NewEventViewHolder>  {

    private List<NewEvent> newEventList;
    private AdapterClickListener clickListener;

    public NewEventAdapter(List<NewEvent> newEventList) {
        this.newEventList = newEventList;
    }

    public void setOnItemClickListener(AdapterClickListener clickListener){
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NewEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_event, parent, false);
        return new NewEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewEventViewHolder holder, int position) {
        NewEvent event = newEventList.get(position);
        if(event.getPathPhoto() != null){
            Glide.with(holder.itemView.getContext())
                    .load(event.getPathPhoto())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.logo_cibertec))
                    .into(holder.ivPhoto);
        } else {
            holder.ivPhoto.setVisibility(View.GONE);
        }
        holder.textViewTitle.setText(event.getTitle());
        holder.textViewBody.setText(event.getContent());
        holder.tvFecha.setText(event.getFecha());//RAM
    }

    @Override
    public int getItemCount() {
        return newEventList.size();
    }

    public class NewEventViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPhoto;
        TextView textViewTitle;
        TextView textViewBody;
        TextView tvFecha;//RAM

        public NewEventViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewBody = itemView.findViewById(R.id.textViewBody);
            tvFecha = itemView.findViewById(R.id.tvFecha);//RAM
            itemView.setOnClickListener(v -> clickListener.onClick(getAdapterPosition()));
        }
    }
}


