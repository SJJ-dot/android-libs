package sjj.permission;

import java.util.List;

import sjj.permission.model.Permission;

/**
 * Created by SJJ on 2017/7/30.
 */

public interface PermissionCallback {
    void onGranted(Permission permissions);
    void onDenied(Permission permissions);
}
