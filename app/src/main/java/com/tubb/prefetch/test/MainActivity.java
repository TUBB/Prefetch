package com.tubb.prefetch.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tubb.prefetch.Prefetch;

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
        // taskId = Prefetch.instance().executeTask(new UserInfoObservableFetchTask());
        taskId = Prefetch.instance().executeTask(new UserInfoPureFetchTask());
    }

    public void viewClick(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra("taskId", taskId);
        startActivity(intent);
    }
}
