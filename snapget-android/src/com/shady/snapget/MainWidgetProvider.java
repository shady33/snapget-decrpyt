package com.shady.snapget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);

			Intent intent = new Intent(context, MainService.class);
			intent.setAction(MainService.UPDATEMOOD);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
			views.setOnClickPendingIntent(R.id.widgetBtn, pendingIntent);

			// Tell the AppWidgetManager to perform an update on the current App Widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}	
	@Override
	public void onReceive(Context context,Intent intent) {
		super.onReceive(context, intent);
		Toast.makeText(context, "Press button again if you don't get a toast message or supersu popup for permission!", Toast.LENGTH_SHORT).show();
	}
}
