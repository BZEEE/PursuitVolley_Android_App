package com.beazle.pursuitvolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
        return new CoachCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachCardViewHolder holder, int position) {
        holder.coachName.setText(data.get(position).GetName());
        holder.coachImg.setImageResource(data.get(position).GetThumbnail());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CoachCardViewHolder extends RecyclerView.ViewHolder {

        ImageView coachImg;
        TextView coachName;

        public CoachCardViewHolder(View itemView) {
            super(itemView);

            coachImg = itemView.findViewById(R.id.cardCoachImage);
            coachName = itemView.findViewById(R.id.cardCoachName);
        }

    }
}
