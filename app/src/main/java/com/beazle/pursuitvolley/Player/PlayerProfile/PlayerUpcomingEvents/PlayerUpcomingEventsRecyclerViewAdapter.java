package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerUpcomingEvents;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerUpcomingEventsRecyclerViewAdapter extends RecyclerView.Adapter<PlayerUpcomingEventsRecyclerViewAdapter.UpcomingEventViewHolder> {

    private Context context;
    private List<PlayerUpcomingEvent> data;

    public PlayerUpcomingEventsRecyclerViewAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void SetPlayerUpcomingEventsData(List<PlayerUpcomingEvent> playerUpcomingEventsList) {
        this.data = playerUpcomingEventsList;
    }

    @NonNull
    @Override
    public PlayerUpcomingEventsRecyclerViewAdapter.UpcomingEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.list_item_upcoming_event, parent, false);
        return new PlayerUpcomingEventsRecyclerViewAdapter.UpcomingEventViewHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerUpcomingEventsRecyclerViewAdapter.UpcomingEventViewHolder holder, int position) {
        holder.upcomingEventTitle.setText(data.get(position).GetEventTitle());
        holder.upcomingEventDate.setText(data.get(position).GetEventDate());
        holder.upcomingEventLocation.setText(data.get(position).GetEventLocation());
//        holder.upcomingEventPursuitVolleyLogo.setImageResource(); // set default logo from the resources/drawable folder
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class UpcomingEventViewHolder extends RecyclerView.ViewHolder {

        public static final String coachSelectionViewHolderTag = "CoachSelectionViewHolderTag";

        private LinearLayout upcomingEventLayoutListItem;
        private ImageView upcomingEventPursuitVolleyLogo;
        private TextView upcomingEventTitle;
        private TextView upcomingEventDate;
        private TextView upcomingEventLocation;

        public UpcomingEventViewHolder(final Context context, View itemView) {
            super(itemView);

            upcomingEventLayoutListItem = itemView.findViewById(R.id.upcomingEventLayoutListItem);
            upcomingEventPursuitVolleyLogo = itemView.findViewById(R.id.upcomingEventPursuitVolleyLogo);
            upcomingEventTitle = itemView.findViewById(R.id.upcomingEventTitle);
            upcomingEventDate = itemView.findViewById(R.id.upcomingEventDateAndTime);
            upcomingEventLocation = itemView.findViewById(R.id.upcomingEventLocation);


            upcomingEventLayoutListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewDetailsOfUpcomingEvent(context);
                }
            });
        }

        private void ViewDetailsOfUpcomingEvent(Context context) {
            Intent intent = new Intent(context, UpcomingEventDetailsActivity.class);
            intent.putExtra("upcomingEventTitle", upcomingEventTitle.getText().toString());
            intent.putExtra("upcomingEventDate", upcomingEventDate.getText().toString());
            intent.putExtra("upcomingEventLocation", upcomingEventLocation.getText().toString());
            context.startActivity(intent);
        }

    }
}
