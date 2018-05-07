package io.github.tubb.prefetch.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import io.github.tubb.prefetch.ObservableFetchTask;
import io.github.tubb.prefetch.Prefetch;

/**
 * Created by tubingbing on 18/3/11.
 */

public class UserInfoActivity extends AppCompatActivity implements ObservableFetchTask.Listener<UserInfo> {
    private static final String TAG = "Prefetch";
    private long taskId;
    private TextView tv_user_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        tv_user_name = findViewById(R.id.tv_user_name);
        taskId = getIntent().getLongExtra("taskId", 0L);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Prefetch.instance().registerListener(taskId, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Prefetch.instance().unregisterListener(taskId);
    }

    @Override
    public void onExecuting() {
        tv_user_name.setText(String.format("[%s] task is executing", taskId));
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        tv_user_name.setText(String.format("loaded data: \n%s", userInfo.toString()));
        Prefetch.instance().finishTask(taskId);
    }

    @Override
    public void onError(Throwable throwable) {
        tv_user_name.setText(String.format("[%s] task on error", taskId));
        Log.e(TAG, taskId + " task", throwable);
        Prefetch.instance().finishTask(taskId);
    }
}
