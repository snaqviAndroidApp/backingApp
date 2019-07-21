package nanodegree.dfw.perm.bakingapp.data.background.services;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import timber.log.Timber;


public class RecipeDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Ingredients> mCollection = new ArrayList<>();
    private String name;
    private int appwidgetId;

    ArrayList<String> mCollectionDummy = new ArrayList<>();
    Context mDataContext;
//    Intent intent;

    public RecipeDataProvider(Context context, Intent intent) {
        this.mDataContext = context;

        appwidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
                , AppWidgetManager.INVALID_APPWIDGET_ID);
//        name = intent.getStringExtra("name");           // Experimenting

        Timber.d("WidgetService factory received name: %s", "Dummy Name");
    }

    @Override
    public void onCreate() {
        initData();                     // Dummy data
    }

    @Override
    public void onDataSetChanged() {
        initData();
//        mCollection = ingredientsList;

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
//        return mCollection != null ? mCollection.size() : 0;
        return mCollectionDummy != null ? mCollectionDummy.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Timber.d("GridRemoteView Service getViewAt received in ingredient id of %d", position);

        /** Testing with static Data **/
        RemoteViews remoteView = new RemoteViews(mDataContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        remoteView.setTextViewText(android.R.id.text1, mCollectionDummy.get(position));
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
        mCollectionDummy.clear();
        for (int i = 0; i < 5; i++){
            mCollectionDummy.add("Dummy no. " + i);
        }
    }
}


