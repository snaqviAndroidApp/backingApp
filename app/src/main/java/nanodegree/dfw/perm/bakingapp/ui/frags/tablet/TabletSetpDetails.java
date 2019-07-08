package nanodegree.dfw.perm.bakingapp.ui.frags.tablet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import nanodegree.dfw.perm.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabletSetpDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabletSetpDetails extends Fragment {

    View rootTabStepsView;
    WebView wTabView;
    TextView textViewTabSteps;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TabletSetpDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabletSetpDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static TabletSetpDetails newInstance(String param1, String param2) {
        TabletSetpDetails fragment = new TabletSetpDetails();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootTabStepsView = inflater.inflate(R.layout.fragment_tablet_setp_details, container, false);
        wTabView = rootTabStepsView.findViewById(R.id.wvSteps);
        textViewTabSteps = rootTabStepsView.findViewById(R.id.tvTabStepDetails);

        return rootTabStepsView;
    }




    //    public void onTrailerItemClickListener(String trailerId, int adapterItem) {
//        adapterItem++;

//        String frameVideo = "<html><body> Trailer no." + adapterItem + " <br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/" + trailerId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//        WebSettings webSettings = wtrailersView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        wtrailersView.loadData(frameVideo, "text/html", "utf-8");


//        Toast.makeText(this, "Inside Steps-recyvlerV", Toast.LENGTH_SHORT).show();


//        Snackbar.make(getWindow().getDecorView().getRootView()
//                    , MessageFormat.format("No Trailer Available", (Object) null)
//                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();

//    }


//    private class inWebView extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(strTrailer);
//            return true;
//        }
//    }



}
