package com.tubb.prefetch.test;

import android.support.annotation.NonNull;

import com.tubb.prefetch.ObservableFetchTask;
import com.tubb.prefetch.PureFetchTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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
        userInfo.name = "BingBing";
        return userInfo;
    }
}
