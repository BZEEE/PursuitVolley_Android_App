package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayerDateSelectionScheduleViewAdapter extends ArrayAdapter {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private List<Date> dates;
    private Calendar currentDate;
    private List<Integer> availableSlots;
    private LayoutInflater inflater;


    public PlayerDateSelectionScheduleViewAdapter(
            @NonNull Context context,
            Calendar currentDate,
            List<Date> dates,
            List<Integer> availableSlots) {
        super(context, R.layout.player_date_selection_schedule_single_cell);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        this.dates = dates;
        this.currentDate = currentDate;
        this.availableSlots = availableSlots;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        int availableSlotsCount = availableSlots.get(position);
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.player_date_selection_schedule_single_cell, parent, false);
        }

        TextView dayNumber = view.findViewById(R.id.playerDateSelectionScheduleSingleCellCalenderDay);
        dayNumber.setText(String.valueOf(dayNo));

        TextView availableAppointments = view.findViewById(R.id.playerDateSelectionScheduleSingleCellAvailableSlotsCount);

        if (displayMonth == currentMonth && displayYear == currentYear && availableSlotsCount != 0) {
            // make these cells clickable in the grid view
            view.setBackgroundColor(getContext().getResources().getColor(R.color.appointmentsAvailableCellIndicatorColor, null));
            availableAppointments.setText(String.valueOf(availableSlotsCount));
        } else {
            // make these cells not clickablein the grid view
            view.setBackgroundColor(getContext().getResources().getColor(R.color.appointmentsNotAvailableCellIndicatorColor, null));
            availableAppointments.setText("");
        }

        // if there are no appointments for a certain date then set background color to grey
        // otherwise set background color to some indicator color

        // loading should not occur in the adapter
        // (1) loading/ async task taken care of by the view model
        // (2) schedule and adapter observe the view model in order to update the UI

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }
}
