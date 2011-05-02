package com.taveiranet.slidescreen.trac;

import static com.larvalabs.slidescreen.PluginConstants.FIELDS_ARRAY;
import static com.larvalabs.slidescreen.PluginConstants.FIELD_DATE;
import static com.larvalabs.slidescreen.PluginConstants.FIELD_ID;
import static com.larvalabs.slidescreen.PluginConstants.FIELD_INTENT;
import static com.larvalabs.slidescreen.PluginConstants.FIELD_LABEL;
import static com.larvalabs.slidescreen.PluginConstants.FIELD_PRIORITY;
import static com.larvalabs.slidescreen.PluginConstants.FIELD_TEXT;
import static com.larvalabs.slidescreen.PluginConstants.FIELD_TITLE;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.lustin.trac.xmlprc2.TicketImpl;
import org.lustin.trac.xmlprc2.Trac;
import org.lustin.trac.xmlprc2.TracException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.larvalabs.slidescreen.PluginUtils;

/**
 * @author João Pedro Taveira
 */
public class SlideTracContentProvider extends ContentProvider {

    private static final String TAG = SlideTracContentProvider.class.getName();

    public static final Uri CONTENT_URI = Uri.parse("content://com.taveiranet.slidescreen.trac");

    public static interface MessageColumns extends BaseColumns {
        /**
         * The number of milliseconds since Jan. 1, 1970, midnight GMT.
         *
         * <P>Type: INTEGER (long)</P>
         */
        String SEND_DATE = "date";

        /**
         * <P>Type: TEXT</P>
         */
        String SENDER = "sender";

        /**
         * <P>Type: TEXT</P>
         */
        String SUBJECT = "subject";

        /**
         * <P>Type: TEXT</P>
         */
        String PREVIEW = "preview";

        String UNREAD = "unread";
        String ACCOUNT = "account";
        String URI = "uri";
        String DELETE_URI = "delUri";

        /**
         * @deprecated the field value is misnamed/misleading - present for compatibility purpose only. To be removed.
         */
        @Deprecated
        String INCREMENT = "id";
    }
    
    @SuppressWarnings("unused")
	private static final String[] DEFAULT_MESSAGE_PROJECTION = new String[] {
        MessageColumns._ID,
        MessageColumns.SEND_DATE,
        MessageColumns.SENDER,
        MessageColumns.SUBJECT,
        MessageColumns.PREVIEW,
        MessageColumns.UNREAD,
        MessageColumns.ACCOUNT,
        MessageColumns.URI,
        MessageColumns.DELETE_URI
    };
    
    public boolean onCreate() {
        Log.d(TAG, "* CREATED.");
        
        return true;
    }

    public Cursor query(Uri uri, String[] fields, String s, String[] strings1, String s1) {
        if (fields == null || fields.length == 0) {
            fields = FIELDS_ARRAY;
        }
        Log.d(TAG, "* QUERY Called.");

        Trac trac = new Trac(getContext(),"http://www.taveiranet.com/projects/mcs/login/rpc","smiff2","teste2");
        
//        trac.getSearchFilters();
//        Log.d(TAG,trac.systemGetAPIVersion().toString());
//        for(ComponentImpl component: trac.getAllComponents()){
//        	Log.d(TAG,component.toString());
//        }
//        for(MilestoneImpl milestone: trac.getAllMilestones()){
//        	Log.d(TAG,milestone.toString());
//        }
//        for(PriorityImpl priority: trac.getAllPriorities()){
//        	Log.d(TAG,priority.toString());
//        }
//        for(ResolutionImpl resolution: trac.getAllResolutions()){
//        	Log.d(TAG,resolution.toString());
//        }
//        for(SeverityImpl severity: trac.getAllSeverities()){
//        	Log.d(TAG,severity.toString());
//        }
//        for(TypeImpl type: trac.getAllTypes()){
//        	Log.d(TAG,type.toString());
//        }
//        for(VersionImpl version: trac.getAllVersions()){
//        	Log.d(TAG,version.toString());
//        }
//        for(StatusImpl status: trac.getAllStatus()){
//        	Log.d(TAG,status.toString());
//        }
        
		SharedPreferences mPrefs = getContext().getSharedPreferences("tracplugin.prefs", Context.MODE_PRIVATE);
		Map<String, ?> prefsKeys = mPrefs.getAll();
		for(String key: prefsKeys.keySet()){
			Log.d(TAG,"Key: " + key + " value: " + mPrefs.getBoolean(key, false));
		}
		
		MatrixCursor cursor = new MatrixCursor(fields);
		try {

			List<TicketImpl> queryResult = trac.query("max=0&owner=smiff&status!=closed");
			
			Collections.sort(queryResult, TicketImpl.getPriorityOrderComparator());

	        for(TicketImpl ticket: 	queryResult){
	        	Log.d(TAG,ticket.toString());
	        	MatrixCursor.RowBuilder builder = cursor.newRow();
				for (String field : fields) {
					if (FIELD_ID.equals(field)) {
						builder.add(ticket.getId());
					} else if (FIELD_TITLE.equals(field)) {
						builder.add(ticket.getSummary());
					} else if (FIELD_LABEL.equals(field)) {
						builder.add(ticket.getPriority().getName());
					} else if (FIELD_TEXT.equals(field)) {
						builder.add(ticket.getComponent().getName());
					} else if (FIELD_DATE.equals(field)) {
						builder.add(ticket.getChangeTime().getTime().getTime());
					} else if (FIELD_PRIORITY.equals(field)) {
						int priority = 3;
						String priorityString = ticket.getPriority().getName();						
						if("blocker".equals(priorityString)){
							priority = 1;
						}else if("critical".equals(priorityString)){
							priority = 2;
						}else if("major".equals(priorityString)){
							priority = 3;
						}else if("minor".equals(priorityString)){
							priority = 4;
						}else if("trivial".equals(priorityString)){
							priority = 5;
						}
						builder.add(100-priority);
					} else if (FIELD_INTENT.equals(field)) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						//intent.setData(Uri.parse(messageUri));
						builder.add(PluginUtils.encodeIntents(intent));
					} else {
						builder.add("");
					}
				}
	        }

		} catch (TracException e) {
			e.printStackTrace();
		}
		
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int delete(Uri uri, String s, String[] strings) {
        return -1;
    }

    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return -1;
    }

    public void sendUpdatedNotification() {
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
    }
    
}
