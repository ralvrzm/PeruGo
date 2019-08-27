package pe.edu.pucp.perugopf.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.data.entities.Event;
import pe.edu.pucp.perugopf.presentation.utils.AdapterClickListener;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private AdapterClickListener clickListener;
    private List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void setOnItemClickListener(AdapterClickListener clickListener){
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textViewTitle.setText(event.getTitle());
        holder.textViewBody.setText(event.getText());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewBody;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null) {
                        clickListener.onClick(getAdapterPosition());
                    }
                }
            });
            textViewBody = itemView.findViewById(R.id.textViewBody);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
