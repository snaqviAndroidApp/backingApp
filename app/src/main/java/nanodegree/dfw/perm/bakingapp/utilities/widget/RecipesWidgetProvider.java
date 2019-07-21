package nanodegree.dfw.perm.bakingapp.utilities.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;



import nanodegree.dfw.perm.bakingapp.R;

import nanodegree.dfw.perm.bakingapp.data.background.services.WidgetService;


/**
 * Implementation of App Widget functionality.
 */
public class RecipesWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(),  R.layout.recipes_widget);

        /** Udacity webCast approach for 'static-data' to be inflated the View with **/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            setRemoteAdapter(context, views);
        }else {
            setRemoteAdapterV11(context, views);
        }
        /** Udacity webCast approach for 'static-data' to be inflated the View with ENDS **/

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list,
                new Intent(context, WidgetService.class));
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetService.class));          // static data
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);        // updates appWidget
            RemoteViews rViews = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
            rViews.setEmptyView(android.R.id.text1, R.id.empty_view);

            appWidgetManager.updateAppWidget(appWidgetId, rViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

