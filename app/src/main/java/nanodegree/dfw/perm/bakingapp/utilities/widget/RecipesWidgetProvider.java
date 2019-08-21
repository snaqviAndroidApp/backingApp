package nanodegree.dfw.perm.bakingapp.utilities.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.app.MainActivity;
import nanodegree.dfw.perm.bakingapp.data.background.services.WidgetService;
import nanodegree.dfw.perm.bakingapp.ui.DetailsActivity;
import timber.log.Timber;

import static nanodegree.dfw.perm.bakingapp.data.Strings.ACTION_APPWIDGET_UPDATE;
import static nanodegree.dfw.perm.bakingapp.data.Strings.WIDGETS_RECIPES_INGREDIENTS_STRING;
import static nanodegree.dfw.perm.bakingapp.data.Strings.WIDGETS_RECIPES_NAME;

public class RecipesWidgetProvider extends AppWidgetProvider {

    public static final int MAINACTIVITY_PENDING_REQUEST_CODE = 1004;
    static String recipeName;
    static String recipeIngredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(),  R.layout.recipes_widget);
        settingRemoteAdapter(context, views, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void settingRemoteAdapter(Context context, @NonNull final RemoteViews views, int appWId) {

        Intent inIntent = new Intent(context, WidgetService.class);
        inIntent.setAction(ACTION_APPWIDGET_UPDATE);
        inIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWId);         // apparently not-binding data
        inIntent.setData(Uri.parse(inIntent.toUri(Intent.URI_INTENT_SCHEME)));  // to see if Extras don't get ignored
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context
                , 0
                , inIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list, pendingIntent);

        Intent inIntent2 = new Intent(context, WidgetService.class);
        inIntent2.putExtra(WIDGETS_RECIPES_NAME, recipeName);
        inIntent2.putExtra(WIDGETS_RECIPES_INGREDIENTS_STRING,recipeIngredients);
        inIntent2.setData(Uri.parse(inIntent2.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widget_list, inIntent2);
    }

    @Override
    public void onEnabled(Context context) { }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        int[] ids = mgr.getAppWidgetIds(new ComponentName(context, RecipesWidgetProvider.class));

        if(intent.getAction().equals(ACTION_APPWIDGET_UPDATE)){
            recipeName = intent.getExtras().getString(WIDGETS_RECIPES_NAME);
            recipeIngredients = intent.getExtras().getString(WIDGETS_RECIPES_INGREDIENTS_STRING);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_item);

            Intent uIntent = new Intent(context, WidgetService.class);
            uIntent.putExtra(WIDGETS_RECIPES_NAME, recipeName);
            uIntent.putExtra(WIDGETS_RECIPES_INGREDIENTS_STRING, recipeIngredients);
            uIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
            uIntent.setData(Uri.parse(uIntent.toUri(Intent.URI_INTENT_SCHEME)));

            Timber.w("onReceive in Provider received name: %s", intent.getStringExtra("name"));
            rv.setRemoteAdapter(R.id.tvWidget_ingredient, uIntent);
            mgr.notifyAppWidgetViewDataChanged(ids, R.layout.recipe_widget_list_item);

            /** Manual Update **/
            for (int idIndex:ids) {
                RecipesWidgetProvider.updateAppWidget(context, mgr, idIndex);
            }
            /** Manual Update **/

            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);                                 /** update appWidget manually **/

            Intent updataIntent = new Intent(context, WidgetService.class);
            updataIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            updataIntent.setData(Uri.parse(updataIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rViews = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
            rViews.setRemoteAdapter(R.id.tvWidget_ingredient, updataIntent);
            rViews.setEmptyView(R.id.tvWidget_ingredient, R.id.empty_view);                          /** Empty View **/

            /** launching the MainActivity**/
            Intent intentLaunch = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, MAINACTIVITY_PENDING_REQUEST_CODE, intentLaunch, 0);

            // Get the layout for the App Widget and attach an on-click listener to the button
            rViews.setOnClickPendingIntent(R.id.widget_clips_image, pendingIntent);
            /** launching the MainActivity ENDS **/

            appWidgetManager.updateAppWidget(appWidgetId, rViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

