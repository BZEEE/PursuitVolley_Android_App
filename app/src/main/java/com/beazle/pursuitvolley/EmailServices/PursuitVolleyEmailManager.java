package com.beazle.pursuitvolley.EmailServices;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.beazle.pursuitvolley.DebugTags.DebugTags;

public final class PursuitVolleyEmailManager {
    // Only send emails that can be handled by Email apps, for instance, SMS messaging apps should not be able
    // to open/handle the emails sent from the pursuit volley app

    public static void ComposeEmail(Context context, String[] addresses, String subject, String bodyText) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setDataAndType(Uri.parse("mailto:"), "text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, bodyText);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.d(DebugTags.DebugTAG, "android packageManager is null");
        }
    }
}
