package nanodegree.dfw.perm.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nanodegree.dfw.perm.bakingapp.R;
import nanodegree.dfw.perm.bakingapp.data.Strings;
import nanodegree.dfw.perm.bakingapp.ui.frags.tablet.StepClips;

import static nanodegree.dfw.perm.bakingapp.data.Strings.STEP_CLIP_TEXT;

public class PhoneActSteps extends AppCompatActivity {

    private String sClips, sText;
    private Integer sClipIndex;

    FragmentTransaction fragmentTransaction;
    private StepClips fragPhoneClips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_act_steps);

        if(savedInstanceState == null) {

            Bundle bundleIn = getIntent().getExtras();
            Bundle extBundle = InitInfo(bundleIn);

            fragPhoneClips = new StepClips();
            fragPhoneClips.setArguments(extBundle);
            fragPhoneClips.setBundle(true);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragmentTransaction
                            .add(R.id.frDetails_container_phPortrait, fragPhoneClips)
                            .commit();
                }
            });

        }
        // else {}
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Bundle InitInfo(Bundle bundleIn) {

        Bundle bundleStepsClips = bundleIn;
        sClips = bundleStepsClips.getString(Strings.STEP_CLIP_INDEX);
        sClipIndex = bundleStepsClips.getInt(Strings.STEP_INDEX);
        sText = bundleStepsClips.getString(STEP_CLIP_TEXT);
        return bundleStepsClips;
    }


    @Override
    protected void onPause() {
        super.onPause();

//        fragPhoneClips.onPause();         // causing crash on rotating phone while playing ExoPlayer
    }
}
