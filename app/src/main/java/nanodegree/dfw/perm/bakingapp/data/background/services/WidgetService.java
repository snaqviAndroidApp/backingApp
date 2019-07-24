package nanodegree.dfw.perm.bakingapp.data.background.services;


import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

//        return new RecipeRemoteViewsFactory(this, intent);
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}
