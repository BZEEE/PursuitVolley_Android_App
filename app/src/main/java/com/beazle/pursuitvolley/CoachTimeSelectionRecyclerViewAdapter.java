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
import java.util.Locale;

public class CoachTimeSelectionRecyclerViewAdapter extends RecyclerView.Adapter<CoachTimeSelectionRecyclerViewAdapter.TimeSlotViewHolder> {

    private List<String> data;
    private Context context;
    private String coachId;
    private long selectedDate;

    public CoachTimeSelectionRecyclerViewAdapter(Context context, List<String> list, String coachId, long selectedData) {
        this.context = context;
        this.data = list;
        this.coachId = coachId;
        this.selectedDate = selectedData;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_time_slot, parent, false);
        return new TimeSlotViewHolder(this.context, view, this.coachId, this.selectedDate);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        int sessionBeginTime = Integer.parseInt(data.get(position));
        int sessionEndTime = sessionBeginTime + 1;
        String formattedSessionBeginTime;
        String formattedSessionEndTime;
        if (sessionBeginTime < 12) {
            formattedSessionBeginTime = String.format(Locale.ENGLISH, "%dAM", sessionBeginTime);
        } else if (sessionBeginTime < 13) {
            formattedSessionBeginTime = String.format(Locale.ENGLISH, "%dPM", sessionBeginTime);
        } else {
            formattedSessionBeginTime = String.format(Locale.ENGLISH, "%dPM", sessionBeginTime - 12);
        }

        if (sessionEndTime < 12) {
            formattedSessionEndTime = String.format(Locale.ENGLISH, "%dAM", sessionEndTime);
        } else if (sessionEndTime < 13) {
            formattedSessionEndTime = String.format(Locale.ENGLISH, "%dPM", sessionEndTime);
        } else {
            formattedSessionEndTime = String.format(Locale.ENGLISH, "%dPM", sessionEndTime - 12);
        }

        // set the formatted time slot to that specific viewHolder
        holder.timeSlot.setText(String.format(Locale.ENGLISH, "%s To %s", formattedSessionBeginTime, formattedSessionEndTime));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        TextView timeSlot;
        public static final String timeSlotTag = "TimeSlotViewHolderTag";

        public TimeSlotViewHolder(final Context context, View itemView, final String coachId, final long selectedDate) {
            super(itemView);
            timeSlot = itemView.findViewById(R.id.time_slot);

            // set onClickListener for each time slot
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        GoToPlayerCheckoutActivity(context, coachId, selectedDate);
                }
            });
        }

        private void GoToPlayerCheckoutActivity(Context context, String coachId, long selectedDate) {
            Intent intent = new Intent(context, PlayerCheckoutActivity.class);
            intent.putExtra(timeSlotTag, timeSlot.getText());
            intent.putExtra(CoachSelectionRecyclerViewAdapter.CoachCardViewHolder.coachSelectionViewHolderTag, coachId);
            intent.putExtra(CoachCalenderActivity.dateTAG, selectedDate);
            context.startActivity(intent);
        }

    }
}
