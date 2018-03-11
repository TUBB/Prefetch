package com.tubb.taskbus.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tubb.taskbus.TaskBus;

public class MainActivity extends AppCompatActivity {

    private long taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // prefetch user info data
        taskId = TaskBus.instance().executeTask(new UserInfoAdvanceTask());
    }

    public void viewClick(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra("taskId", taskId);
        startActivity(intent);
    }
}
