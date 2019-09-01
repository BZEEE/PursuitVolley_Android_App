package com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointmentsPage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointment;
import com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointmentsPage.CoachAppointmentPage.CoachScheduleAppointmentPage;
import com.beazle.pursuitvolley.R;

import java.util.List;

public class CoachScheduleAppointmentsPageRecyclerViewAdapter extends RecyclerView.Adapter<CoachScheduleAppointmentsPageRecyclerViewAdapter.AppointmentViewHolder> {

    private List<CoachAppointment> data;
    private Context context;

    public CoachScheduleAppointmentsPageRecyclerViewAdapter(Context context, List<CoachAppointment> list) {
        this.context = context;
        this.data = list;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_coach_appointment, parent, false);
        return new AppointmentViewHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        // list of appointment objects
        // set appointments to specific viewHolder
        holder.appointmentBeginTime.setText(data.get(position).GetAppointmentBeginTime());
        holder.appointmentEndTime.setText(data.get(position).GetAppointmentEndTime());
        holder.appointmentLocation.setText(data.get(position).GetAppointmentLocation());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView appointmentBeginTime;
        TextView appointmentEndTime;
        TextView appointmentLocation;
        public static final String eventTag = "eventViewHolderTag";

        public AppointmentViewHolder(final Context context, View eventView) {
            super(eventView);
            appointmentBeginTime = itemView.findViewById(R.id.coachScheduleEventBeginTime);
            appointmentEndTime = itemView.findViewById(R.id.coachScheduleEventEndTime);
            appointmentLocation = itemView.findViewById(R.id.coachScheduleEventLocation);

            // set onClickListener for each time slot
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        GoToSpecificEvent(context);
                }
            });
        }

        private void GoToSpecificEvent(Context context) {
            Intent intent = new Intent(context, CoachScheduleAppointmentPage.class);

            context.startActivity(intent);
        }

    }
}
