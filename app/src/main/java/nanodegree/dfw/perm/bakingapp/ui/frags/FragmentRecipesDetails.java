package nanodegree.dfw.perm.bakingapp.ui.frags;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.Strings;
import nanodegree.dfw.perm.bakingapp.data.background.services.WidgetIntentService;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Ingredients;
import nanodegree.dfw.perm.bakingapp.data.handler.baking.Steps;
import nanodegree.dfw.perm.bakingapp.ui.IngredientsAdapter;
import nanodegree.dfw.perm.bakingapp.ui.PhoneActSteps;
import nanodegree.dfw.perm.bakingapp.ui.StepsAdapter;
import nanodegree.dfw.perm.bakingapp.ui.frags.tablet.StepClips;

import static nanodegree.dfw.perm.bakingapp.data.Strings.INGREDIENTS_List;
import static nanodegree.dfw.perm.bakingapp.data.Strings.NAME;
import static nanodegree.dfw.perm.bakingapp.data.Strings.STEPS_List;
import static nanodegree.dfw.perm.bakingapp.data.Strings.STEP_CLIP_TEXT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRecipesDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRecipesDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRecipesDetails extends Fragment implements StepsAdapter.StepsOnClickHandler {

    View rootDView;
    private Steps stepsInDetails;

    LinearLayoutManager ingredientsLayoutManager;
    LinearLayoutManager stepsLayoutManager;
    private RecyclerView mStepsRecyclerView, mIngredientsRecyclerView;
    private StepsAdapter stepsAdapter;
    private IngredientsAdapter ingredientsAdapter;
    private ArrayList<Steps> _dStepsList;
    private ArrayList<Ingredients> _dIngredientsList;
    private ArrayList<String> listIngredients;

    private String _dName;
    TextView vTitle;
    private Intent mIntent;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private boolean bTwoPaneFromAct_detail;

    public FragmentRecipesDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRecipesDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRecipesDetails newInstance(String param1, String param2) {
        FragmentRecipesDetails fragment = new FragmentRecipesDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        _dStepsList = new ArrayList<>();
        _dIngredientsList = new ArrayList<>();
        listIngredients = new ArrayList<>();
        _dName = null;
        mIntent = getActivity().getIntent();                        // extract details-info: Ingredients, Steps

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootDView =  inflater.inflate(R.layout.fragment_recipes_details, container, false);
        vTitle = rootDView.findViewById(R.id.tvOriginalTitle);
        intializeDetails(rootDView);

        mStepsRecyclerView = rootDView.findViewById(R.id.recyclerview_steps);               // Steps
        mStepsRecyclerView.setHasFixedSize(true);
        mIngredientsRecyclerView = rootDView.findViewById(R.id.recyclerVIngredients);       // Ingredients
        mIngredientsRecyclerView.setHasFixedSize(true);

//        if(bTwoPaneFromAct_detail){                                                       // Unnecessary conditionality
            ingredientsLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);
            mIngredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
            mIngredientsRecyclerView.setHasFixedSize(true);

            stepsLayoutManager = new LinearLayoutManager((getActivity().getBaseContext()), LinearLayoutManager.VERTICAL, false);
            mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
            mStepsRecyclerView.setHasFixedSize(true);

//        }else {
            ingredientsLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);
            mIngredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
            mIngredientsRecyclerView.setHasFixedSize(true);

            stepsLayoutManager = new LinearLayoutManager((getActivity().getBaseContext()), LinearLayoutManager.VERTICAL, false);
            mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
            mStepsRecyclerView.setHasFixedSize(true);
//        }

        setAdapter();
        return rootDView;
    }

    private void setAdapter() {
        ingredientsAdapter = new IngredientsAdapter();
        ingredientsAdapter.setValidReviews(listIngredients);
        mIngredientsRecyclerView.setAdapter(ingredientsAdapter);

        stepsAdapter = new StepsAdapter(this);
        stepsAdapter.setValidTrailers(_dStepsList);
        mStepsRecyclerView.setAdapter(stepsAdapter);
    }

    private void intializeDetails(View rootDView) {

        // TODO: Initialize fields/ views
        _dIngredientsList = (ArrayList<Ingredients>) mIntent.getExtras().get(INGREDIENTS_List);
        _dName = mIntent.getExtras().get(NAME).toString();
        vTitle.setText(_dName);
        _dIngredientsList.forEach(dINDetails -> {
            listIngredients.add(
                    "Quantity: " +  dINDetails.getQuantity()
                            + "\nIngredients: " +  dINDetails.getIngredientsList()
                            + "\nMeasure: " + dINDetails.getMeasure() + "\n"
            );
        });

        WidgetIntentService.startWidgetService(getActivity().getBaseContext(), listIngredients, _dName);        // Triggering Intent-Service
        _dStepsList = (ArrayList<Steps>) mIntent.getExtras().get(STEPS_List);
    }

    public void getTwoPaneValDetail(boolean bValDetail){
        bTwoPaneFromAct_detail = bValDetail;                        // update TwoPane condition
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onTrailerItemClickListener(Steps stepClicked, int adapterPos) {

        stepsInDetails = stepClicked;
        Bundle bStepsClips = new Bundle();
        bStepsClips.putString(Strings.STEP_CLIP_INDEX, stepsInDetails.getVideoURL());
        bStepsClips.putInt(Strings.STEP_INDEX, adapterPos);
        bStepsClips.putString(STEP_CLIP_TEXT, stepsInDetails.getDescription());
        if (bTwoPaneFromAct_detail) {
            FragmentManager fragDetailsSelf = getActivity().getSupportFragmentManager();
            StepClips _insideFragDetails = new StepClips();
            _insideFragDetails.setArguments(bStepsClips);
            _insideFragDetails.setBundle(true);                     // Sending Valid Clips to TabletStepDetails
            fragDetailsSelf.beginTransaction()
                    .replace(R.id.frDetails_container_land_sec, _insideFragDetails)          // inflating the right-Tablet (2nd) Fragment
                    .commit();
        } else {
            Intent phoneActIntent = new Intent(getActivity(), PhoneActSteps.class);
            phoneActIntent.putExtras(bStepsClips);
            startActivity(phoneActIntent);
        }
    }

}
