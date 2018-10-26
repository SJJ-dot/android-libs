package sjj.permission;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sjj.permission.model.Permission;

/**
 * Created by SJJ on 2017/7/30.
 */

public class PermissionFragment extends Fragment {
    private static final int requestCode = R.id.requestCode;
    private final Map<String, Set<PermissionCallback>> map = new HashMap<>();

    public PermissionFragment() {
        setRetainInstance(true);
    }

    public void requestPermissions(@NonNull String[] permissions, @NonNull PermissionCallback callback) {
        if (!isMarshmallow()) {
            for (String s : permissions) {
                callback.onGranted(new Permission(s, true, false));
            }
        } else {
            List<String> permission_denied = new ArrayList<>(permissions.length);
            for (String s : permissions) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(s) == PackageManager.PERMISSION_GRANTED) {
                    callback.onGranted(new Permission(s, true, true));
                } else {
                    permission_denied.add(s);
                    Set<PermissionCallback> permissionCallbacks = map.get(s);
                    if (permissionCallbacks == null) {
                        map.put(s, permissionCallbacks = new HashSet<>());
                    }
                    permissionCallbacks.add(callback);
                }
            }
            if (permission_denied.size() > 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permission_denied.toArray(new String[permission_denied.size()]), requestCode);
                }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionFragment.requestCode) {
            for (int i = 0; i < permissions.length; i++) {
                String permissionName = permissions[i];
                boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                Set<PermissionCallback> permissionCallbacks = map.remove(permissionName);
                if (permissionCallbacks != null) {
                    Permission permission = new Permission(permissionName, granted, shouldShowRequestPermissionRationale(permissionName));
                    for (PermissionCallback callback : permissionCallbacks) {
                        if (granted) {
                            callback.onGranted(permission);
                        } else {
                            callback.onDenied(permission);
                        }
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
