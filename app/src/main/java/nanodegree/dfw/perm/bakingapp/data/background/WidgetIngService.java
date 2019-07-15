package nanodegree.dfw.perm.bakingapp.data.background;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;

import org.parceler.Parcels;

import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import timber.log.Timber;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class WidgetIngService extends IntentService {

    public static final String INGREDIENT_LIST_FROM_DETAIL_ACTIVITY = "INGREDIENT_LIST_FROM_DETAIL_ACTIVITY";

    public WidgetIngService() {
        super("WidgetIngService");
    }

    /** added methods to implement broadCastReceiver **/
    public static void startWidgetService(Context context, ArrayList<Ingredients> ingredientsListFromActivity, String name) {
        Intent intent = new Intent(context, WidgetIngService.class);

        intent.putExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY, Parcels.wrap(ingredientsListFromActivity)); // original
        intent.putExtra("name", name);
        Timber.w("service class received name as %s", name);

        context.startService(intent);
    }

    /** added methods to implement broadCastReceiver ENDS **/

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            //make new arraylist from the intent and handle it in other method
            ArrayList<Ingredients> ingredients = Parcels.unwrap(intent.getParcelableExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY));
            Timber.w("service class received name as %s and passing to handle widget update", intent.getStringExtra("name"));
            handleWidgetUpdate(ingredients, intent.getStringExtra("name"));

        }
    }

    private void handleWidgetUpdate(ArrayList<Ingredients> ingredientsListFromActivity, String name) {

        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");

        intent.putExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY, Parcels.wrap(ingredientsListFromActivity));
        intent.putExtra("name", name);
        sendBroadcast(intent);
    }

}
