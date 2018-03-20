package com.tubb.prefetch.test;

import com.tubb.prefetch.FetchTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by tubingbing on 18/3/11.
 */

public class UserInfoFetchTask extends FetchTask<UserInfo> {
    @Override
    public Observable<UserInfo> execute() {
        return Observable.create(new ObservableOnSubscribe<UserInfo>() {
            @Override
            public void subscribe(ObservableEmitter<UserInfo> emitter) throws Exception {
                // just for test
                Thread.sleep(5000);
                UserInfo userInfo = new UserInfo();
                userInfo.name = "BingBing";
                emitter.onNext(userInfo);
            }
        });
    }
}
