package com.tubb.taskbus.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tubb.taskbus.AdvanceTask;
import com.tubb.taskbus.TaskBus;

/**
 * Created by tubingbing on 18/3/11.
 */

public class UserInfoActivity extends AppCompatActivity implements AdvanceTask.Listener<UserInfo> {
    private static final String TAG = "TaskBus";
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
        TaskBus.instance().registerListener(taskId, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TaskBus.instance().unregisterListener(taskId);
    }

    @Override
    public void onExecuting() {
        tv_user_name.setText(taskId + " task executing");
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        tv_user_name.setText(userInfo.name);
        TaskBus.instance().completedTask(taskId);
    }

    @Override
    public void onError(Throwable throwable) {
        tv_user_name.setText(taskId + " task error");
        Log.e(TAG, taskId + " task", throwable);
        TaskBus.instance().completedTask(taskId);
    }
}
