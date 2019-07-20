package com.beazle.pursuitvolley;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentSettingsFragment extends Fragment {

    private Button enterNewDirectDepositInfoButtonToggle;
    private Button viewCurrentDirectDepositInfoButtonToggle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        enterNewDirectDepositInfoButtonToggle.setVisibility(View.INVISIBLE);
        enterNewDirectDepositInfoButtonToggle.setEnabled(false);
        viewCurrentDirectDepositInfoButtonToggle.setVisibility(View.VISIBLE);
        viewCurrentDirectDepositInfoButtonToggle.setEnabled(true);

        return inflater.inflate(R.layout.fragment_payment_settings, container, false);
    }
}
