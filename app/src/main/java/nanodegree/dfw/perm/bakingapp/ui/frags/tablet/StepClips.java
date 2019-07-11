package nanodegree.dfw.perm.bakingapp.ui.frags.tablet;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/** ExoPlayer: Un-Used imports **/

//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;

//import androidx.annotation.NonNull;                           AndroidX
//import androidx.annotation.Nullable;                          AndroidX
//import androidx.appcompat.app.AppCompatActivity;              AndroidX


import com.google.android.exoplayer2.C;
//import com.google.android.exoplayer2.C.ContentType;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.Player;
//import com.google.android.exoplayer2.RenderersFactory;
//import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
//import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
//import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
//import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
//import com.google.android.exoplayer2.drm.UnsupportedDrmException;
//import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
//import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
//import com.google.android.exoplayer2.offline.DownloadHelper;
//import com.google.android.exoplayer2.source.BehindLiveWindowException;
//import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.ads.AdsLoader;
//import com.google.android.exoplayer2.source.ads.AdsMediaSource;
//import com.google.android.exoplayer2.source.dash.DashMediaSource;
//import com.google.android.exoplayer2.source.hls.HlsMediaSource;
//import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;

import com.google.android.exoplayer2.ui.DebugTextViewHelper;

/** ExoPlayer: Un-Used imports ENDS **/



/** ExoPlayer attempt imports **/

/** might need later **/

//import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.ui.spherical.SphericalSurfaceView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
/** might need later ENDS **/


import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.source.TrackGroupArray;

/** ExoPlayer attempt imports ENDS**/

import java.text.MessageFormat;
import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.Strings;
import nanodegree.dfw.perm.bakingapp.ui.StepsAdapter;

//import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_TRACK_SELECTOR_PARAMETERS;
import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_AUTO_PLAY;
import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_PLAY_WHEN_READY;
import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_POSITION;
import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_TRACK_SELECTOR_PARAMETERS;
import static nanodegree.dfw.perm.bakingapp.data.Strings.KEY_WINDOW;
import static nanodegree.dfw.perm.bakingapp.data.Strings.SPHERICAL_STEREO_MODE_EXTRA;
import static nanodegree.dfw.perm.bakingapp.data.Strings.SPHERICAL_STEREO_MODE_LEFT_RIGHT;
import static nanodegree.dfw.perm.bakingapp.data.Strings.SPHERICAL_STEREO_MODE_MONO;
import static nanodegree.dfw.perm.bakingapp.data.Strings.SPHERICAL_STEREO_MODE_TOP_BOTTOM;
import static nanodegree.dfw.perm.bakingapp.utilities.NetworkUtils.buildStepsUrl;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepClips#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class StepClips extends Fragment implements StepsAdapter.StepsOnClickHandler {
public class StepClips extends Fragment implements StepsAdapter.StepsOnClickHandler
//        , OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener {
//        ,PlaybackPreparer, PlayerControlView.VisibilityListener
{

    private static final String PLAYBACK_TIME = "play_time";
    private int mCurrentPosition = 0;

    private View rootTabStepsView;
    private TextView textViewTabSteps;

    private Uri videoUriVPlayer;

//    private VideoView mVideoView;                   // Experimental
//    private MediaController controller;

    /** Adding ExoPlayer **/
    private SimpleExoPlayer simpleExoPlayer;
    private DataSource.Factory mediaDataSourceFactory;
    private TrackGroupArray astSeenTrackGroupArray = null;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private BandwidthMeter bandwidthMeter;

    private PlayerView playerView;

    private DataSource.Factory dataSourceFactory;
    private FrameworkMediaDrm mediaDrm;
    private MediaSource mediaSource;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private DebugTextViewHelper debugViewHelper;
    private TrackGroupArray lastSeenTrackGroupArray;

    private boolean startAutoPlay;
    private boolean shouldAutoPlay;
    private boolean playWhenReady;

    private int currentWindow = 0;
    private int startWindow;
    private long startPosition;
    private long playbackPosition;

    private Timeline.Window window;
    private ProgressBar progressBar;
    private ImageView ivHideControllerButton;

    // Fields used only for ad playback. The ads loader is loaded via reflection.

    private AdsLoader adsLoader;
    private Uri loadedAdTagUri;

    /** Adding ExoPlayer ENDS **/


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

//        String sphericalStereoMode = getActivity().getIntent().getStringExtra(SPHERICAL_STEREO_MODE_EXTRA);

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



//        mVideoView = rootTabStepsView.findViewById(R.id.vvStepClip);


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
        window = new Timeline.Window();

//        playerView.setControllerVisibilityListener(getActivity());
//        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        playerView.requestFocus();

//        if (sphericalStereoMode != null) {
//            int stereoMode;
//            if (SPHERICAL_STEREO_MODE_MONO.equals(sphericalStereoMode)) {
//                stereoMode = C.STEREO_MODE_MONO;
//            } else if (SPHERICAL_STEREO_MODE_TOP_BOTTOM.equals(sphericalStereoMode)) {
//                stereoMode = C.STEREO_MODE_TOP_BOTTOM;
//            } else if (SPHERICAL_STEREO_MODE_LEFT_RIGHT.equals(sphericalStereoMode)) {
//                stereoMode = C.STEREO_MODE_LEFT_RIGHT;
//            } else {
//                showToast(R.string.error_unrecognized_stereo_mode);
//
////                finish();                 // original
//                getActivity().finish();
////                return;                   // original
//                return null;                // experimental
//            }
//            ((SphericalSurfaceView) playerView.getVideoSurfaceView()).setDefaultStereoMode(stereoMode);
//        }

        if (savedInstanceState != null) {
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }
        /** ExoPlayer implementation attempt Ends **/


        if(bStepClipIn){
            if(!mParam2.isEmpty()){
                textViewTabSteps.setText(mParam2);
            }else textViewTabSteps.setText("No steps details available");

            if(mParam1 != null) {
            }else if(!mParam1.isEmpty()){

                /**-----------
                 *
                 * controller = new MediaController(getActivity().getApplicationContext());
                 * controller.setMediaPlayer(mVideoView);
                 * mVideoView.setMediaController(controller);
                 *
                 * ------------**/



                /** Intent video works, however not embedded **/
//            shouldOverrideUrlLoading(wTabView, mParam1);

            }


        }else {
            Snackbar.make(getActivity().findViewById(android.R.id.content)
                    , MessageFormat.format("Ah, No Clips", (Object) null)
                    , Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        return rootTabStepsView;
    }


    @Override
    public void onStart() {

        if (Util.SDK_INT > 23) {
            initializeExoPlayer();
        }

        super.onStart();

        /**----------- **/
//        initializePlayer();
//        mVideoView.setMediaController(controller);
        /**----------- **/


    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getBaseContext(), trackSelector);

        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.addListener(new PlayerEventListener());
        simpleExoPlayer.setPlayWhenReady(shouldAutoPlay);

        if(mParam1 != null) {
//            videoUriVPlayer = buildStepsUrl(mParam1);
            mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
//                    .createMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
                    .createMediaSource(Uri.parse(mParam1));
        }

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
    }


//    private void initializePlayer() {
//
//        // Show the "Buffering..." message while the video loads.
////        mBufferingTextView.setVisibility(VideoView.VISIBLE);
//
//        if(mParam1 != null){
//            videoUriVPlayer = buildStepsUrl(mParam1);
//
//            mVideoView.setVideoURI(videoUriVPlayer);
//            mVideoView.setOnPreparedListener(
//                    new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            // Hide buffering message.
////                            mBufferingTextView.setVisibility(VideoView.INVISIBLE);
//                             // Implementation here.
//                            if (mCurrentPosition > 0) {
//                                mVideoView.seekTo(mCurrentPosition);
//                            } else {
//                                // Skipping to 1 shows the first frame of the video.
//                                mVideoView.seekTo(1);
//                            }
//                            mVideoView.start();
//                        }
//
//                    });
//
//                    // Listener for onCompletion() event (runs after media has finished  playing).
//                    mVideoView.setOnCompletionListener(
//                    new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mediaPlayer) {
//                            Toast.makeText(getActivity().getApplicationContext(),
//                                    R.string.toast_message_videoPlayer,
//                                    Toast.LENGTH_SHORT).show();
//
//                            // Return the video position to the start.
//                            mVideoView.seekTo(0);
//                        }
//                    });
//
//
//        }
//        else {
////            mVideoView.setOnErrorListener(null);
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            mVideoView.pause();
//        }

        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        releasePlayer();            // stop VideoPlayer

        if (Util.SDK_INT > 23) {
            releaseExoPlayer();
        }

    }

//    private void release_Player() {
//        mVideoView.stopPlayback();
//    }


    public void setBundle(boolean b) {                          // Validates the inComing Bundle
        bStepClipIn = b;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        updateStartPosition();
//        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());

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

//    @Override
//    public void preparePlayback() {
//
//    }
//
//    @Override
//    public void onVisibilityChange(int visibility) {
//
//    }


    private void releaseExoPlayer() {
        if (simpleExoPlayer != null) {
            updateStartPosition();
            shouldAutoPlay = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
            trackSelector = null;
        }
    }




//    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {
//
//        @Override
//        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
//            String errorString = getString(R.string.error_generic);
//            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
//                Exception cause = e.getRendererException();
//                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
//                    // Special case for decoder initialization failures.
//                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
//                            (MediaCodecRenderer.DecoderInitializationException) cause;
//                    if (decoderInitializationException.decoderName == null) {
//                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
//                            errorString = getString(R.string.error_querying_decoders);
//                        } else if (decoderInitializationException.secureDecoderRequired) {
//                            errorString =
//                                    getString(
//                                            R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
//                        } else {
//                            errorString =
//                                    getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
//                        }
//                    } else {
//                        errorString =
//                                getString(
//                                        R.string.error_instantiating_decoder,
//                                        decoderInitializationException.decoderName);
//                    }
//                }
//
//            }
//            return null;
//        }
//
//    }

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
