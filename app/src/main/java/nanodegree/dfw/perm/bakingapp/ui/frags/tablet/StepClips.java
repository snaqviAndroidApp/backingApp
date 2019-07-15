package nanodegree.dfw.perm.bakingapp.ui.frags.tablet;


import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/** ExoPlayer: Un-Used imports **/

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
/** might need later ENDS **/


import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

/** ExoPlayer attempt imports ENDS**/

import java.text.MessageFormat;
import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.Strings;
import nanodegree.dfw.perm.bakingapp.ui.StepsAdapter;

import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_PLAY_WHEN_READY;
import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_POSITION;
import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_WINDOW;
import static nanodegree.dfw.perm.bakingapp.data.Strings.TAG_Exo;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepClips#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepClips extends Fragment implements StepsAdapter.StepsOnClickHandler {

    private static final String PLAYBACK_TIME = "play_time";
    private int mCurrentPosition = 0;

    private View rootTabStepsView;
    private TextView textViewTabSteps;


    /** Adding ExoPlayer **/
    private BandwidthMeter bandwidthMeter;
    private SimpleExoPlayer simpleExoPlayer;
    private DataSource.Factory mediaDataSourceFactory;
    private PlayerView playerView;
    private MediaSource mediaSource;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private boolean playWhenReady;
    private int currentWindow = 0;
    private long playbackPosition;
    private Timeline.Window window;
    private ProgressBar progressBar;
    private ImageView ivHideControllerButton;
    /** Adding ExoPlayer ENDS **/


    /** Widgetizing **/

    private Context contextClips;


     /** Widgetizing ENDS **/

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mParam3;
    private boolean bStepClipIn = false;


    public StepClips() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepClips.
     */
    // TODO: Rename and change types and number of parameters
    public static StepClips newInstance(String param1, String param2) {
        StepClips fragment = new StepClips();
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
            mParam1 = getArguments().getString(Strings.STEP_CLIP_INDEX);
            mParam2 = getArguments().getString(Strings.STEP_CLIP_TEXT);
            mParam3 = getArguments().getInt(Strings.STEP_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Resources resClips = contextClips.getResources(); // wrong approach, needs getActivity.getResources()
        Resources resClips = getActivity().getResources();

        if (savedInstanceState == null) {
            playWhenReady = true;
            currentWindow = 0;
            playbackPosition = 0;
        }else {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(KEY_WINDOW);
            playbackPosition = savedInstanceState.getLong(KEY_POSITION);
        }

        rootTabStepsView = inflater.inflate(R.layout.fragment_tablet_setp_details, container, false);      // instance of Layout
        textViewTabSteps = rootTabStepsView.findViewById(R.id.tvTabStepDetails);

        /** ExoPlayer implementation attempt **/
        playerView = rootTabStepsView.findViewById(R.id.video_view);
        window = new Timeline.Window();
        ivHideControllerButton = rootTabStepsView.findViewById(R.id.exo_player_controller);
        progressBar = rootTabStepsView.findViewById(R.id.progress_bar);
        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();

        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity()
                , Util.getUserAgent(getActivity().getBaseContext(),
                "mediaPlayerSample")
                , (TransferListener<? super DataSource>) bandwidthMeter);

        /** ExoPlayer implementation attempt Ends **/
        if(bStepClipIn){
            if(!mParam2.isEmpty()){
                textViewTabSteps.setText(mParam2);
            }else textViewTabSteps.setText("No steps details available");

            if(mParam1 != null) {
                window = new Timeline.Window();
                playerView.requestFocus();
            }else {
                Snackbar.make(getActivity().findViewById(android.R.id.content)
                        , MessageFormat.format("No visuals available", (Object) null)
                        , Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
        return rootTabStepsView;
    }

    @Override
    public void onStart() {
        if (Util.SDK_INT > 23) {
            initializeExoPlayer();
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializeExoPlayer();
        }
    }

    private void initializeExoPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        if(!mParam1.isEmpty() && mParam1 != null) {
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getBaseContext(), trackSelector);

        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.addListener(new PlayerEventListener());
        simpleExoPlayer.setPlayWhenReady(shouldAutoPlay);
        mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse(mParam1));
        boolean haveStartPosition = currentWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);
        }
        simpleExoPlayer.prepare(mediaSource, !haveStartPosition, false);
        ivHideControllerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView.hideController();
            }
        });

        }else {
            progressBar.setVisibility(View.INVISIBLE);
            Snackbar.make(getActivity().findViewById(android.R.id.content)
                    , MessageFormat.format("No visual aid available", (Object) null)
                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseExoPlayer();
        }
    }

    public void setBundle(boolean b) {                          // Validates the inComing Bundle
        bStepClipIn = b;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(!mParam1.isEmpty() && mParam1 != null) {
            updateStartPosition();
        }
        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);
        outState.putInt(KEY_WINDOW, currentWindow);
        outState.putLong(KEY_POSITION, playbackPosition);

        super.onSaveInstanceState(outState);
    }

    private void updateStartPosition() {
        playbackPosition = simpleExoPlayer.getCurrentPosition();
        currentWindow = simpleExoPlayer.getCurrentWindowIndex();
        playWhenReady = simpleExoPlayer.getPlayWhenReady();
    }

    private void releaseExoPlayer() {

        if (simpleExoPlayer != null)
        {
            updateStartPosition();
            shouldAutoPlay = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
            trackSelector = null;
        }
    }

    private class PlayerEventListener extends Player.DefaultEventListener {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_IDLE:       // The player does not have any media to play yet.
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_BUFFERING:  // The player is buffering (loading the content)
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_READY:      // The player is able to immediately play
                        progressBar.setVisibility(View.GONE);
                        break;
                    case Player.STATE_ENDED:      // The player has finished playing the media
                        progressBar.setVisibility(View.GONE);
                        break;
                }
            }
    }

}
