package com.beazle.pursuitvolley;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoachTimeSelectionRecyclerViewAdapter extends RecyclerView.Adapter<CoachTimeSelectionRecyclerViewAdapter.TimeSlotViewHolder> {

    private List<String> data;
    private Context context;

    public CoachTimeSelectionRecyclerViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.data = list;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_time_slot, parent, false);
        return new TimeSlotViewHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        holder.timeSlot.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        TextView timeSlot;
        public static final String timeSlotTag = "TimeSlotViewHolderTag";

        public TimeSlotViewHolder(final Context context, View itemView) {
            super(itemView);
            timeSlot = itemView.findViewById(R.id.time_slot);

            // set onClickListener for each time slot
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        GoToPlayerCheckoutActivity(context);
                }
            });
        }

        private void GoToPlayerCheckoutActivity(Context context) {
            Intent intent = new Intent(context, PlayerCheckoutActivity.class);
            intent.putExtra(TimeSlotViewHolder.timeSlotTag, timeSlot.getText());
            context.startActivity(intent);
        }

    }
}
