package nanodegree.dfw.perm.bakingapp.data;

import java.net.CookieManager;
import java.net.CookiePolicy;

//public final class Strings {
public class Strings {

    public static final String NAME = "Name";
    public static final String INGREDIENTS_List = "indexIngredients";
    public static final String STEPS_List = "indexSteps";
    public static final String STEP_INDEX = "stepIndex";
    public static final String STEP_CLIP_INDEX = "stepClipIndex";
    public static final String STEP_CLIP_TEXT = "stepClipText";

    /** ExoPlayer strings **/
    public static final String DRM_SCHEME_EXTRA = "drm_scheme";
    public static final String DRM_LICENSE_URL_EXTRA = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES_EXTRA = "drm_key_request_properties";
    public static final String DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
    public static final String PREFER_EXTENSION_DECODERS_EXTRA = "prefer_extension_decoders";

    public static final String ACTION_VIEW = "com.google.android.exoplayer.demo.action.VIEW";
    public static final String EXTENSION_EXTRA = "extension";

    public static final String KEY_PLAY_WHEN_READY = "play_when_ready";


    // For backwards compatibility only.
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";

    // Saved instance state keys.
    public static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    public static final String KEY_WINDOW = "window";
    public static final String KEY_POSITION = "position";
    public static final String KEY_AUTO_PLAY = "auto_play";

    public static final CookieManager DEFAULT_COOKIE_MANAGER;
    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }
    public static final String TAG_Exo = "exoPlayer_E";
    /** ExoPlayer strings ENDS**/

/** Widgets' **/
// public static final String INGREDIENT_LIST_FROM_DETAIL_ACTIVITY = "INGREDIENT_LIST_FROM_DETAIL_ACTIVITY";

/** Widgets' ENDS **/


}
