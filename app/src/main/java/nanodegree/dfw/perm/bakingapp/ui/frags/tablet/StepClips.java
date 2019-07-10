package nanodegree.dfw.perm.bakingapp.ui.frags.tablet;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.MessageFormat;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.Strings;
import nanodegree.dfw.perm.bakingapp.ui.StepsAdapter;

import static nanodegree.dfw.perm.bakingapp.utilities.NetworkUtils.buildStepsUrl;

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

    private Uri videoUriVPlayer;

//    private WebView wTabView;
//    private WebSettings webSettings;

    private TextView mBufferingTextView;
    private VideoView mVideoView;                   // Experimental
    private MediaController controller;

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

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        rootTabStepsView = inflater.inflate(R.layout.fragment_tablet_setp_details, container, false);
        textViewTabSteps = rootTabStepsView.findViewById(R.id.tvTabStepDetails);
        mVideoView = rootTabStepsView.findViewById(R.id.vvStepClip);
        if(bStepClipIn){

            if(!mParam2.isEmpty()){
                textViewTabSteps.setText(mParam2);
            }else textViewTabSteps.setText("No steps details available");

            if(mParam1 != null) {
            }else if(!mParam1.isEmpty()){

                controller = new MediaController(getActivity().getApplicationContext());
                controller.setMediaPlayer(mVideoView);
                mVideoView.setMediaController(controller);

                /** Intent video works, however not embedded **/
//            shouldOverrideUrlLoading(wTabView, videoUrl);

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
        super.onStart();
        initializePlayer();
        mVideoView.setMediaController(controller);

    }

    private void initializePlayer() {

        // Show the "Buffering..." message while the video loads.
//        mBufferingTextView.setVisibility(VideoView.VISIBLE);

        if(mParam1 != null){
            videoUriVPlayer = buildStepsUrl(mParam1);
            mVideoView.setVideoURI(videoUriVPlayer);

            mVideoView.setOnPreparedListener(
                    new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            // Hide buffering message.
//                            mBufferingTextView.setVisibility(VideoView.INVISIBLE);
                             // Implementation here.
                            if (mCurrentPosition > 0) {
                                mVideoView.seekTo(mCurrentPosition);
                            } else {
                                // Skipping to 1 shows the first frame of the video.
                                mVideoView.seekTo(1);
                            }
                            mVideoView.start();
                        }

                    });

                    // Listener for onCompletion() event (runs after media has finished  playing).
                    mVideoView.setOnCompletionListener(
                    new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    R.string.toast_message_videoPlayer,
                                    Toast.LENGTH_SHORT).show();

                            // Return the video position to the start.
                            mVideoView.seekTo(0);
                        }
                    });
        }
        else {
            mVideoView.setOnErrorListener(null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();            // stop VideoPlayer
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    public void setBundle(boolean b) {                          // Validates the inComing Bundle
        bStepClipIn = b;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }


    /** Alternate approach to play video, but in instantiating the dafault/System-Explorer
     * Please note: YouTube couldn't play that
     * **/
    //    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        if (url.endsWith(".mp3")) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.parse(url), "audio/*");
//            view.getContext().startActivity(intent);
//            return true;
//        } else if (url.endsWith(".mp4") || url.endsWith(".3gp")) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.parse(url), "video/*");
//            view.getContext().startActivity(intent);
//            return true;
//        } else {
////            return rootTabStepsView.shouldOverrideUrlLoading(view, url);
//            return this.shouldOverrideUrlLoading(view, url);
//        }
//    }

}
