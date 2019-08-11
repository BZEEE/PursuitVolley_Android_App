package com.beazle.pursuitvolley.TextMessageServices;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.beazle.pursuitvolley.DebugTags.DebugTags;

public final class PursuitVolleySMSManager {

    public static void SendSMSMessage(Context context, String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.d(DebugTags.DebugTAG, "getPackageManager is null, when trying to send SMS");
        }
    }
}
