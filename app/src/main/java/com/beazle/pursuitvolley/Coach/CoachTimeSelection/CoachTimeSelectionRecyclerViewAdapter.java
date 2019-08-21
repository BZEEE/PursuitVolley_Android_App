package com.beazle.pursuitvolley.Coach.CoachTimeSelection;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.IntentTags.IntentTags;
import com.beazle.pursuitvolley.Player.PlayerPaymentFlow.PlayerCheckoutActivity;
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.PlayerAppointmentReceiptParcelable;
import com.beazle.pursuitvolley.R;

import java.util.List;
import java.util.Locale;

public class CoachTimeSelectionRecyclerViewAdapter extends RecyclerView.Adapter<CoachTimeSelectionRecyclerViewAdapter.TimeSlotViewHolder> {

    private List<String> data;
    private Context context;
    private PlayerAppointmentReceiptParcelable receipt;

    public CoachTimeSelectionRecyclerViewAdapter(Context context, List<String> list, PlayerAppointmentReceiptParcelable receipt) {
        this.context = context;
        this.data = list;
        this.receipt = receipt;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_time_slot, parent, false);
        return new TimeSlotViewHolder(this.context, view, this.receipt);
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

        public TimeSlotViewHolder(final Context context, View itemView, final PlayerAppointmentReceiptParcelable receipt) {
            super(itemView);
            timeSlot = itemView.findViewById(R.id.time_slot);

            // set onClickListener for each time slot
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        GoToPlayerCheckoutActivity(context, receipt);
                }
            });
        }

        private void GoToPlayerCheckoutActivity(Context context, PlayerAppointmentReceiptParcelable receipt) {
            Intent intent = new Intent(context, PlayerCheckoutActivity.class);
            receipt.setCurrentAppointmentBeginTime(timeSlot.getText().toString());
            intent.putExtra(IntentTags.currentReceiptTAG, receipt);
            context.startActivity(intent);
        }

    }
}
