package com.taveiranet.slidescreen.trac;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import com.larvalabs.slidescreen.PluginReceiver;

/**
 * @author João Pedro Taveira
 */
public class SlidescreenReceiver extends PluginReceiver {

    public static final String TAG = SlidescreenReceiver.class.getName();

    @Override
    public int getColor() {
        return Color.rgb(194, 0, 0);
    }

    @Override
    public Uri getContentProviderURI() {
        return SlideTracContentProvider.CONTENT_URI;
    }

    @Override
    public String getName() {
        return "Trac Plugin";
    }

    @Override
    public int getIconResourceId() {
        return R.raw.trac;
    }

    @Override
    public Intent[] getSingleTapShortcutIntents() {
        Intent groupIntent = new Intent(Intent.ACTION_MAIN);
        //groupIntent.setType("vnd.android-dir/mms-sms");
        groupIntent.setComponent(new ComponentName("com.fsck.k9","com.fsck.k9.activity.Accounts"));
        return new Intent[]{groupIntent};
    }

    @Override
    public Intent[] getLongpressShortcutIntents() {
        Intent longIntent = new Intent(Intent.ACTION_VIEW);
        //longIntent.setComponent(new ComponentName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity"));
        longIntent.setComponent(new ComponentName("com.fsck.k9","com.fsck.k9.activity.FolderList"));
        return new Intent[]{longIntent};
    }

    @Override
    public Intent getPreferenceActivityIntent() {
    	Intent prefs = new Intent(Intent.ACTION_MAIN);
        prefs.setComponent(new ComponentName("com.taveiranet.slidescreen.trac",
                "com.taveiranet.slidescreen.trac.preference.SlideTracPluginPreferences"));
        return prefs;
    }

    @Override
    public void markedAsRead(String itemId) {
        Log.d(TAG, "Received item marked as read: " + itemId);
    }
}
