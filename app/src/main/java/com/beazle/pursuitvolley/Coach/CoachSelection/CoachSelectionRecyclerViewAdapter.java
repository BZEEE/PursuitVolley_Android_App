package com.beazle.pursuitvolley.Coach.CoachSelection;

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

import com.beazle.pursuitvolley.Coach.CoachDateSelection.CoachDateSelectionActivity;
import com.beazle.pursuitvolley.IntentTags.IntentTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerCurrentAppointments.PlayerAppointmentReceiptParcelable;
import com.beazle.pursuitvolley.R;

import java.util.List;

public class CoachSelectionRecyclerViewAdapter extends RecyclerView.Adapter<CoachSelectionRecyclerViewAdapter.CoachCardViewHolder> {

    private Context context;
    private List<Coach> data;

    public CoachSelectionRecyclerViewAdapter(Context context, List<Coach> list) {
        this.context = context;
        this.data = list;
    }

    @NonNull
    @Override
    public CoachCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_item_coach, parent, false);
        return new CoachCardViewHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachCardViewHolder holder, int position) {
        holder.coachName.setText(data.get(position).GetName());
        holder.coachImg.setImageBitmap(data.get(position).GetThumbnail());
        holder.coachLocation.setText(data.get(position).GetLocation());
        holder.coachUniqueId = data.get(position).GetUniqueId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CoachCardViewHolder extends RecyclerView.ViewHolder {

        LinearLayout coachCardLayout;
        ImageView coachImg;
        TextView coachName;
        TextView coachLocation;
        String coachUniqueId;

        public CoachCardViewHolder(final Context context, View itemView) {
            super(itemView);

            coachImg = itemView.findViewById(R.id.cardCoachImage);
            coachName = itemView.findViewById(R.id.cardCoachName);
            coachLocation = itemView.findViewById(R.id.cardCoachLocation);
            coachCardLayout = itemView.findViewById(R.id.coachCardLayout);


            coachCardLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoToCoachCalenderSelectionActivity(context);
                }
            });
        }

        private void GoToCoachCalenderSelectionActivity(Context context) {
            Intent intent = new Intent(context, CoachDateSelectionActivity.class);
            PlayerAppointmentReceiptParcelable receipt = new PlayerAppointmentReceiptParcelable();
            receipt.setCurrentAppointmentCoachName(coachName.getText().toString());
            receipt.setCurrentAppointmentCoachUid(coachUniqueId);
            receipt.setCurrentAppointmentLocation(coachLocation.getText().toString());
            intent.putExtra(IntentTags.currentReceiptTAG, receipt);
            context.startActivity(intent);
        }

    }
}
