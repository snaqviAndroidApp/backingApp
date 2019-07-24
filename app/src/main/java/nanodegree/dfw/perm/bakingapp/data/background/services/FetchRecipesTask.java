package nanodegree.dfw.perm.bakingapp.data.background.services;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.data.handler.baking.RecipesHandler;
import nanodegree.dfw.perm.bakingapp.utilities.NetworkUtils;
import nanodegree.dfw.perm.bakingapp.utilities.OnRunInBackground;
import nanodegree.dfw.perm.bakingapp.utilities.jutilities.BakingJsonUtils;
import timber.log.Timber;

public class FetchRecipesTask extends AsyncTask<String, Void, ArrayList<RecipesHandler>> {

    private OnRunInBackground<ArrayList<RecipesHandler>> mCallBack;
    private Context mContext;
    public Exception mException;
    ArrayList<RecipesHandler> bakingJDataParsed;
    String _jRawBakingData;

    public FetchRecipesTask(Context context, OnRunInBackground callback){
        mCallBack = callback;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        Timber.d("inside PreExecute()");
    }

    @Override
    protected ArrayList<RecipesHandler> doInBackground(String... param) {
        _jRawBakingData = null;
        URL bakingInput = NetworkUtils.buildBakingUrl();
        try {
            _jRawBakingData = NetworkUtils.getResponseFromHttpUrl(bakingInput);
            bakingJDataParsed = BakingJsonUtils.parseBakingJnData(mContext, _jRawBakingData);
            return bakingJDataParsed;
        } catch (IOException e) {
            mException = e;
            e.printStackTrace();
        } catch (JSONException e) {
            mException = e;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<RecipesHandler> recipesHandlers) {
        if(mCallBack != null){
            if(mException == null){
                mCallBack.onSuccess(recipesHandlers);
            }else {
                mCallBack.onFailure(mException);
            }
        }
    }
}