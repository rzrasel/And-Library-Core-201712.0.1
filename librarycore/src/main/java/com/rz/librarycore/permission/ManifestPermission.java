package com.rz.librarycore.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.rz.librarycore.log.LogWriter;

/**
 * Created by Rz Rasel on 2017-12-11.
 */

public class ManifestPermission {
    private Context context;
    private static ManifestPermission instance = null;
    private OnEventListenerHandler onEventListenerHandler;

    public static ManifestPermission getInstance(Context argContext) {
        if (instance == null) {
            instance = new ManifestPermission(argContext);
        }
        return instance;
    }

    public ManifestPermission(Context argContext) {
        context = argContext;
    }

    public boolean havePermission(String[] argPermissions) {
        /*String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);*/
        boolean hasPermission = true;
        /*for (int i = 0; i < argPermissionSet.length; i++) {
            int permission = 0;
            //Manifest.permission.INTERNET
            permission = ContextCompat.checkSelfPermission((Activity) argContext, argPermissionSet[i]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                LogWriter.Log("NEED_PERMISSION: " + argPermissionSet[i] + " - PERMISSION_NEED_TO_SET");
                hasPermission = false;
            }
        }*/
        for (String permissionItem : argPermissions) {
            int permission = 0;
            //Manifest.permission.INTERNET
            permission = ContextCompat.checkSelfPermission((Activity) context, permissionItem);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                LogWriter.Log("NEED_PERMISSION: " + permissionItem + " - PERMISSION_NEED_TO_SET");
                hasPermission = false;
            }
        }
        return hasPermission;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onRequest(String argPermissions[], int argPermissionRequestCode) {
        //((Activity) context).requestPermissions(argPermissions, argPermissionRequestCode);
        ActivityCompat.requestPermissions((Activity) context, argPermissions, argPermissionRequestCode);
    }

    public void onSetEventListener(OnEventListenerHandler argOnEventListenerHandler) {
        onEventListenerHandler = argOnEventListenerHandler;
    }

    public void onRequestPermissionsResult(int argRequestCode, String argPermissions[], int[] argGrantResults, int argPermissionRequestCode) {
        //if(Build.VERSION.SDK_INT==Build.VERSION_CODES.M)
        if (argRequestCode == argPermissionRequestCode) {
            if (argGrantResults.length > 0 && argGrantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! do the
                // calendar task you need to do.
                if (onEventListenerHandler != null) {
                    onEventListenerHandler.onPermissionGranted(true);
                }
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                if (onEventListenerHandler != null) {
                    onEventListenerHandler.onPermissionGranted(false);
                }
            }
            return;
        }
        // other 'switch' lines to check for other
        // permissions this app might request
    }

    public interface OnEventListenerHandler {
        public void onPermissionGranted(boolean argIsGranted);
        //public void onPermissionForbidden();
    }
}