package nanodegree.dfw.perm.bakingapp.ui.frags;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import org.json.JSONException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.RecipiesHandler;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Steps;
import nanodegree.dfw.perm.bakingapp.ui.DetailsActivity;
import nanodegree.dfw.perm.bakingapp.ui.VersatileAdapter;
import nanodegree.dfw.perm.bakingapp.utilities.NetworkUtils;
import nanodegree.dfw.perm.bakingapp.utilities.jutilities.BakingJsonUtils;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static nanodegree.dfw.perm.bakingapp.data.Strings.INGREDIENTS_List;
import static nanodegree.dfw.perm.bakingapp.data.Strings.NAME;
import static nanodegree.dfw.perm.bakingapp.data.Strings.STEPS_List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentReceipesMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentReceipesMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReceipesMain extends Fragment implements VersatileAdapter.ListItemClickListener {

    View rootView;

    ArrayList<Ingredients> ingredientsIn;
    ArrayList<Steps> stepsIn;
    /** Code-related fields copied from MainActivity defined here **/

    private RecyclerView mRecyclerView;

    VersatileAdapter versatileAdapter;
    private ProgressBar mLoadIndicator;

    private long schPeriod;
    private int threadCounts;
    /** Code-related fields copied from MainActivity defined here **/



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private boolean bTwoPaneFromAct;


    public FragmentReceipesMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentReceipesMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentReceipesMain newInstance(boolean param1, String param2) {
        FragmentReceipesMain fragment = new FragmentReceipesMain();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _initialize();
        rootView = inflater.inflate(R.layout.recipes_main_views, container, false);

        mLoadIndicator = rootView.findViewById(R.id.mv_loading_indicator);              // needs to put in respective in layout files place
        mRecyclerView = rootView.findViewById(R.id.recyclerview_recipes);
        if(!bTwoPaneFromAct){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
        }else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),2,  GridLayoutManager.VERTICAL,false);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setHasFixedSize(true);
        }
        return rootView;
    }

    private void _initialize() {
        schPeriod = 1;
        threadCounts = 0;
        new ConnectionUtilities().getConnCheckHanlde();         // Checking Connectivity (internet)
    }

    public void getTwoPaneVal(boolean bVal){
        bTwoPaneFromAct = bVal;                                 // update TwoPane condition
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean ifTwoPane) {
        if (mListener != null) {
            mListener.onFragmentInteraction(ifTwoPane);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRecipesItemClickListener(RecipiesHandler recipeId) {                  // OnClickEvents coming from recipesMainRecyclerView

        // get Details Recipies data to be sent to details-View (Activity / Landscape: Fragment)
        ingredientsIn = recipeId.getIngredients();
        stepsIn = recipeId.getSteps();

//        Toast.makeText(getActivity().getBaseContext(), String.format("%d. %s ",recipeId.getId(), recipeId.getName()),  Toast.LENGTH_SHORT).show();

        Intent iDetailsAct = new Intent(getActivity(), DetailsActivity.class);
        iDetailsAct.putExtra(NAME, recipeId.getName());
        iDetailsAct.putParcelableArrayListExtra(INGREDIENTS_List, recipeId.getIngredients());
        iDetailsAct.putParcelableArrayListExtra(STEPS_List
                , recipeId.getSteps());
        startActivity(iDetailsAct);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(boolean uri);
    }


    /** ------------> Ported from MainActivity-all <------------------- **/
    public void getRecipes(boolean connection) {
        if(connection){
            new FetchRecipes().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);           // Apparently no difference
        }
    }

    /** Backing Recipes DataExtraction Threading: AsyncTask **/
    public class FetchRecipes extends AsyncTask<String, Void, ArrayList<RecipiesHandler>> {

        ArrayList<RecipiesHandler> bakingJDataParsed;
        String _jrawBakingData;

        @Override
        protected void onPreExecute() {
            mLoadIndicator.setVisibility(VISIBLE);
        }

        @Override
        protected ArrayList<RecipiesHandler> doInBackground(String... param) {
            _jrawBakingData = null;
            URL bakingInput = NetworkUtils.buildBakingUrl();
            try {
                _jrawBakingData = NetworkUtils.getResponseFromHttpUrl(bakingInput);
                bakingJDataParsed = BakingJsonUtils.parseBakingJnData(FragmentReceipesMain.this.getContext(), _jrawBakingData);  // getting all Recipies - jsonData parsed successfully

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return bakingJDataParsed;
        }

        @Override
        protected void onPostExecute(ArrayList<RecipiesHandler> recipiesHandlers) {
            mLoadIndicator.setVisibility(INVISIBLE);
            sendRecipesPosters(recipiesHandlers);
        }
    }
    /** Backing Recipes DataExtraction Threading: AsyncTask ENDS**/

    private void sendRecipesPosters(ArrayList<RecipiesHandler> recipiesHandlers) {
        versatileAdapter = new VersatileAdapter(FragmentReceipesMain.this);
        versatileAdapter.setRecipes(recipiesHandlers);                                     // Experimental definition for Recipes card pasting
        mRecyclerView.setAdapter(versatileAdapter);
    }

    /** Connection availability check **/
    final class ConnectionUtilities {
        private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable internNetCheck = new Runnable() {
            @Override
            public void run() {
                if (checkConnection()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            while (threadCounts < 1) {
                                getRecipes(true);
                                threadCounts++;
                            }
                        }
                    });
                }
            }
        };
        final ScheduledFuture<?> connCheckHanlde =
                scheduler.scheduleAtFixedRate(internNetCheck, 3, schPeriod, SECONDS);     // see if shorter 'initial delay works

        ScheduledFuture<?> getConnCheckHanlde() {
            return connCheckHanlde;
        }

        private boolean checkConnection() {
            try {
                int timeoutMs = 5500;
                Socket sock = new Socket();
                SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
                sock.connect(socketAddress, timeoutMs);
                sock.close();
                return true;
            } catch (IOException e) {
                Snackbar.make(requireNonNull(getActivity().getCurrentFocus())
                        , MessageFormat.format("Ah, no internet connetion", (Object) null)
                        , Snackbar.LENGTH_LONG).setAction("Action", null).show();
                threadCounts = 0;

                return false;
            }
        }
    }
    /** Connection availability check ENDS **/



    /** ------------> Ported from MainActivity-all ENDS <------------------- **/


}
