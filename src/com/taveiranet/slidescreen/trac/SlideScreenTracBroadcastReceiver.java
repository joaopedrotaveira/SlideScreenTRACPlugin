package com.taveiranet.slidescreen.trac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SlideScreenTracBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = SlideTracContentProvider.class.getName();
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
        Log.d(TAG, "* onReceive called.");
        
        Log.d(TAG, "Intent received: " + arg1.getAction());
		arg0.getContentResolver().notifyChange(SlideTracContentProvider.CONTENT_URI, null);
	}
}
