package com.beazle.pursuitvolley;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CoachPaymentSettingsFragment extends Fragment {

    private Button enterNewDirectDepositInfoButtonToggle;
    private Button viewCurrentDirectDepositInfoButtonToggle;
    private RelativeLayout editDirectDepositInfoLayout;
    private RelativeLayout coachCurrentDirectDepositInfoLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View paymentSettingFragmentView = inflater.inflate(R.layout.fragment_payment_settings, container, false);

        enterNewDirectDepositInfoButtonToggle = paymentSettingFragmentView.findViewById(R.id.enterNewDirectDepositInfoButtonToggle);
        viewCurrentDirectDepositInfoButtonToggle = paymentSettingFragmentView.findViewById(R.id.viewCurrentDirectDepositInfoButtonToggle);
        editDirectDepositInfoLayout = paymentSettingFragmentView.findViewById(R.id.editDirectDepositInfoLayout);
        coachCurrentDirectDepositInfoLayout = paymentSettingFragmentView.findViewById(R.id.coachCurrentDirectDepositInfoLayout);

        editDirectDepositInfoLayout.setVisibility(View.VISIBLE);
        coachCurrentDirectDepositInfoLayout.setVisibility(View.INVISIBLE);

        enterNewDirectDepositInfoButtonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDirectDepositInfoEditingLayout();
            }
        });

        viewCurrentDirectDepositInfoButtonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCurrentDirectDepositInfoLayout();
            }
        });

        return paymentSettingFragmentView;
    }

    private void ShowDirectDepositInfoEditingLayout() {
        editDirectDepositInfoLayout.setVisibility(View.VISIBLE);
        coachCurrentDirectDepositInfoLayout.setVisibility(View.INVISIBLE);
        enterNewDirectDepositInfoButtonToggle.setBackgroundResource(R.drawable.rounded_corners_left_blue_background);
        viewCurrentDirectDepositInfoButtonToggle.setBackgroundResource(R.drawable.rounded_corners_right_grey_background);
    }

    private void ShowCurrentDirectDepositInfoLayout() {
        editDirectDepositInfoLayout.setVisibility(View.INVISIBLE);
        coachCurrentDirectDepositInfoLayout.setVisibility(View.VISIBLE);
        enterNewDirectDepositInfoButtonToggle.setBackgroundResource(R.drawable.rounded_corners_left_grey_background);
        viewCurrentDirectDepositInfoButtonToggle.setBackgroundResource(R.drawable.rounded_corners_right_blue_background);
    }
}
