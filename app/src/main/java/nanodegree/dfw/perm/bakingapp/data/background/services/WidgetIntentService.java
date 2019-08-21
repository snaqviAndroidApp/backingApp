package nanodegree.dfw.perm.bakingapp.data.background.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import timber.log.Timber;

import static nanodegree.dfw.perm.bakingapp.data.Strings.ACTION_APPWIDGET_UPDATE;
import static nanodegree.dfw.perm.bakingapp.data.Strings.WIDGETS_RECIPES_INGREDIENTS;
import static nanodegree.dfw.perm.bakingapp.data.Strings.WIDGETS_RECIPES_INGREDIENTS_STRING;
import static nanodegree.dfw.perm.bakingapp.data.Strings.WIDGETS_RECIPES_NAME;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class WidgetIntentService extends IntentService {

    Context context = null;
    static Intent intent;
    static String nameToSend;
    static String ingredientsToSend;
    private static ArrayList<String> ingredientsList;

    public WidgetIntentService() {
        super("WidgetIntentService");   // Name provided to the 'Background / Worker' Thread
    }

    /** added methods to implement broadCastReceiver **/
    public static void startWidgetService(Context context, ArrayList<String> ingredientsListFromActivity, String name) {
        intent = new Intent(context, WidgetIntentService.class);
        intent.putStringArrayListExtra(WIDGETS_RECIPES_INGREDIENTS, ingredientsListFromActivity);   // original
        intent.putExtra(WIDGETS_RECIPES_NAME, name);
        context.startService(intent);
    }
    /** added methods to implement broadCastReceiver ENDS **/


    /** Not calling onStartCommand(); below, as it is only handling BroadCastReceiver()
     * **********
     *
     * @Override
     *     public int onStartCommand(@androidx.annotation.Nullable Intent intent, int flags, int startId) {
     *         return super.onStartCommand(intent, flags, startId);
     *     }
     *
     ***********
     */


    /** Handling Background tasks: here Broadcast **/
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            context = getApplication().getBaseContext();
            ingredientsList = intent.getStringArrayListExtra(WIDGETS_RECIPES_INGREDIENTS);
            StringBuilder strToSend = new StringBuilder();
            ingredientsList.forEach(inIndex -> {
                strToSend.append(inIndex);
            });
            ingredientsToSend = strToSend.toString();
            nameToSend = intent.getStringExtra("name");

            handleWidgetUpdate();
        }
    }

    private void handleWidgetUpdate() {
        Intent bIntent = new Intent(ACTION_APPWIDGET_UPDATE);
//        bIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");                   // might be redundant
        bIntent.putExtra(WIDGETS_RECIPES_INGREDIENTS_STRING, ingredientsToSend);
        bIntent.putExtra(WIDGETS_RECIPES_NAME, nameToSend);
        Timber.d("_just before sendBroadcast()..");

        sendBroadcast(bIntent);     // check Extras()
    }

}
