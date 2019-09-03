package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerCurrentAppointments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerAppointmentsRecyclerViewAdapter extends RecyclerView.Adapter<PlayerAppointmentsRecyclerViewAdapter.CurrentAppointmentViewHolder> {

    private Context context;
    private List<PlayerAppointment> data;

    public PlayerAppointmentsRecyclerViewAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void SetPlayerAppointmentData(List<PlayerAppointment> playerAppointmentsList) {
        this.data = playerAppointmentsList;
    }

    @NonNull
    @Override
    public PlayerAppointmentsRecyclerViewAdapter.CurrentAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.list_item_current_appointment, parent, false);
        return new PlayerAppointmentsRecyclerViewAdapter.CurrentAppointmentViewHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAppointmentsRecyclerViewAdapter.CurrentAppointmentViewHolder holder, int position) {
        holder.appointmentCoachName.setText(data.get(position).GetAppointmentCoachName());
        holder.appointmentDateAndTime.setText(data.get(position).GetAppointmentDate());
        holder.appointmentLocation.setText(data.get(position).GetAppointmentLocation());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CurrentAppointmentViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout appointmentLayoutListItem;
        private TextView appointmentCoachName;
        private TextView appointmentDateAndTime;
        private TextView appointmentLocation;

        public CurrentAppointmentViewHolder(final Context context, View itemView) {
            super(itemView);

            appointmentLayoutListItem = itemView.findViewById(R.id.currentAppointmentLayoutListItem);
            appointmentCoachName = itemView.findViewById(R.id.currentAppointmentCoachName);
            appointmentDateAndTime = itemView.findViewById(R.id.currentAppointmentDateAndTime);
            appointmentLocation = itemView.findViewById(R.id.currentAppointmentLocation);

            appointmentLayoutListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewDetailsOfCurrentAppointment(context);
                }
            });
        }

        private void ViewDetailsOfCurrentAppointment(Context context) {
            // check out the details of the current appointment in a new activity
//            Intent intent = new Intent(context, CurrentAppointmentDetails.class);
//            context.startActivity(intent);
        }

    }
}
