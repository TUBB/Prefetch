package io.github.tubb.prefetch.test;

import android.support.annotation.NonNull;

import io.github.tubb.prefetch.PureFetchTask;

/**
 * Created by tubingbing on 18/3/11.
 */

public class UserInfoPureFetchTask extends PureFetchTask<UserInfo> {
    @Override
    @NonNull
    public UserInfo execute() {
        // just for test
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.userId = String.valueOf(System.currentTimeMillis());
        userInfo.name = "BingBing";
        return userInfo;
    }
}
