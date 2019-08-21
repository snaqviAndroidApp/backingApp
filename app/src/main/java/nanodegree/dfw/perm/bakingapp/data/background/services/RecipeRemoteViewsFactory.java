package nanodegree.dfw.perm.bakingapp.data.background.services;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.utilities.widget.WidgetRecipesData;
import timber.log.Timber;

import static nanodegree.dfw.perm.bakingapp.R.*;
import static nanodegree.dfw.perm.bakingapp.data.Strings.WIDGETS_RECIPES_INGREDIENTS_STRING;
import static nanodegree.dfw.perm.bakingapp.data.Strings.WIDGETS_RECIPES_NAME;

public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<WidgetRecipesData> mCollection = new ArrayList<>();
    private String nameFactory, ingredientsFactory;
    private int appwidgetId;

    Context mDataContext;
    Intent mIntent;

    public RecipeRemoteViewsFactory(Context context, Intent intent) {
        this.mDataContext = context;
        this.mIntent = intent;

        appwidgetId = mIntent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
                , AppWidgetManager.INVALID_APPWIDGET_ID);
        nameFactory = mIntent.getStringExtra(WIDGETS_RECIPES_NAME);
        if(mIntent.getExtras().get(WIDGETS_RECIPES_INGREDIENTS_STRING) != null){
            ingredientsFactory = (String) mIntent.getExtras().get(WIDGETS_RECIPES_INGREDIENTS_STRING);
        }
        else ingredientsFactory = "";
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection != null ? mCollection.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(mDataContext.getPackageName(), layout.recipe_widget_list_item);
        remoteView.setTextViewText(id.tvRecipesName, mCollection.get(position).getName());
        remoteView.setTextViewText(id.tvWidget_ingredient, mCollection.get(position).getIngredientsAccumulated());
        try {
            Timber.d("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData(){
        mCollection.clear();
        mCollection.add(new WidgetRecipesData(nameFactory, ingredientsFactory));
    }
}


