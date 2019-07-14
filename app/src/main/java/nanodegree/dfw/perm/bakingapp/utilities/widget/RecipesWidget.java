package nanodegree.dfw.perm.bakingapp.utilities.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.parceler.Parcels;
import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.R;

import nanodegree.dfw.perm.bakingapp.app.MainActivity;
import nanodegree.dfw.perm.bakingapp.data.background.WidgetGridRemoteViewService;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import nanodegree.dfw.perm.bakingapp.ui.DetailsActivity;
import timber.log.Timber;

import static nanodegree.dfw.perm.bakingapp.data.background.WidgetIngService.INGREDIENT_LIST_FROM_DETAIL_ACTIVITY;

/**
 * Implementation of App Widget functionality.
 */
public class RecipesWidget extends AppWidgetProvider {

    public static ArrayList<Ingredients> ingredientsList = new ArrayList<>();
    public static String recipeName = null;

//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager
//            , int appWidgetId) {
//
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager
            , int appWidgetId, String recipeName) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        //        views.setTextViewText(R.id.appwidget_text, widgetText);      // replaced with a static-ImageView
        // views.setImage
        // Construct the RemoteViews object




//        Intent recipeWIntent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntentRecipe = PendingIntent.getActivity(context
//                , 0
//                ,recipeWIntent
//                , 0);
//        views.setOnClickPendingIntent(R.id.widget_clips_image, pendingIntentRecipe);

        /** Clips PendingIntent getting BroadCastReceiver **/

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);


        /** to check if mainAct triggers **/
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addCategory(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
        /** to check if mainAct triggers ENDS **/



        views.setOnClickPendingIntent(R.id.gv_parent_for_widget, mainPendingIntent);




        Intent recipeWIntent = new Intent(context, DetailsActivity.class);
        PendingIntent pendingIntentRecipe = PendingIntent.getBroadcast(context
                , 0
                ,recipeWIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        Timber.w("provider somehow got the received recipeId %s", recipeName);

//        views.setOnClickPendingIntent(R.id.widget_clips_image, pendingIntentRecipe);

        views.setPendingIntentTemplate(R.id.gv_parent_for_widget, pendingIntentRecipe);       // doesn't work

        Intent intent1 = new Intent(context, WidgetGridRemoteViewService.class);
        intent1.putExtra("name", recipeName);

        views.setRemoteAdapter(R.id.gv_parent_for_widget, intent1);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void manualUpdateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds
            , String passedInName) {

//
//
//
//
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, passedInName);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Timber.w("widget enabled and trying to set text with recipe name as %s", recipeName);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(new ComponentName(context, RecipesWidget.class));

        String action = intent.getAction();
        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            ingredientsList = Parcels.unwrap(intent.getParcelableExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY));
            recipeName = intent.getStringExtra("name");
            Timber.w("onReceive in Provider received recipe name as %s", intent.getStringExtra("name"));
            manager.notifyAppWidgetViewDataChanged(ids, R.id.gv_parent_for_widget);
            RecipesWidget.manualUpdateRecipeWidgets(context, manager, ids, intent.getStringExtra("name"));
            super.onReceive(context, intent);
        }

    }

}

