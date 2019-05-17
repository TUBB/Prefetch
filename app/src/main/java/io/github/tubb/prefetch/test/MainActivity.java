package io.github.tubb.prefetch.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.tubb.prefetch.Prefetch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewClick(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnPureTask:
                long pureTaskId = Prefetch.instance().executeTask(new UserInfoPureFetchTask());
                intent.putExtra("taskId", pureTaskId);
                startActivity(intent);
                break;
            case R.id.btnObservableTask:
                long observableTaskId = Prefetch.instance().executeTask(new UserInfoObservableFetchTask());
                intent.putExtra("taskId", observableTaskId);
                startActivity(intent);
                break;
        }
    }
}
