package com.jamsnap.facebook;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Get PackageManager from Activity::getPackageManager()
 */
public class Util {
    public static void printAppKeyHash(PackageManager manager) {
        try {
            PackageInfo info = manager.getPackageInfo("com.jamsnap", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Error: ", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.d("Error: ", e.getMessage());
        }
    }
}
