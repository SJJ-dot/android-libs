package sjj.permission.util;

import android.app.Activity;
import android.app.FragmentManager;

import sjj.permission.PermissionCallback;
import sjj.permission.PermissionFragment;

/**
 * Created by SJJ on 2017/7/30.
 */

public class PermissionUtil {
    private static final String TAG = "sjj.permission.util.PermissionUtil.TAG";

    public static void requestPermissions(Activity activity, String[] permissions, PermissionCallback callback) {
        PermissionFragment fragment = getPermissionsFragment(activity);
        fragment.requestPermissions(permissions,callback);

    }
    private static PermissionFragment getPermissionsFragment(Activity activity) {
        FragmentManager manager = activity.getFragmentManager();
        PermissionFragment fragment = (PermissionFragment) manager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new PermissionFragment();
            manager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
        return fragment;
    }
}
