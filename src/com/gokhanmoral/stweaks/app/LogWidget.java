package com.gokhanmoral.stweaks.app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class LogWidget extends AppWidgetProvider {
    private static final String PICK_LOG = "logButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.log_widget);
        watchWidget = new ComponentName(context, LogWidget.class);

        remoteViews.setOnClickPendingIntent(R.id.log_button, getPendingSelfIntent(context, PICK_LOG));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (PICK_LOG.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName logWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.log_widget);
            logWidget = new ComponentName(context, LogWidget.class);

            Utils.executeRootCommandInThread("/res/customconfig/actions/push-actions/debug_to_sd + 1");
            Toast toast = Toast.makeText(context, R.string.log_done, Toast.LENGTH_LONG);
            toast.show();
            Log.i("STweaks", "Picked log from widget button");

            appWidgetManager.updateAppWidget(logWidget, remoteViews);

        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
