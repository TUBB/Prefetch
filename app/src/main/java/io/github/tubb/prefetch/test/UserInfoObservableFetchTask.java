package io.github.tubb.prefetch.test;

import android.support.annotation.NonNull;

import io.github.tubb.prefetch.ObservableFetchTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by tubingbing on 18/3/11.
 */

public class UserInfoObservableFetchTask extends ObservableFetchTask<UserInfo> {
    @Override
    @NonNull
    public Observable<UserInfo> execute() {
        return Observable.create(new ObservableOnSubscribe<UserInfo>() {
            @Override
            public void subscribe(ObservableEmitter<UserInfo> emitter) throws Exception {
                // just for test
                Thread.sleep(5000);
                UserInfo userInfo = new UserInfo();
                userInfo.userId = String.valueOf(System.currentTimeMillis());
                userInfo.name = "BingBing";
                emitter.onNext(userInfo);
            }
        });
    }
}
