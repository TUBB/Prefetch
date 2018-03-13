# TaskBus
![](https://img.shields.io/badge/minSdkVersion-14-brightgreen.svg) ![](https://img.shields.io/badge/release-v0.0.3-brightgreen.svg) [![](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Task bus for App，use for prefetch data.
- Simple, lightweight and low intrusiveness.
- Reuse the `Data Layer` before.
- Support RxJava. 

# Preview
![Preview](https://github.com/TUBB/TaskBus/blob/master/art/preview.gif)

# Download
```groovy
implementation 'com.tubb.taskbus:taskbus:0.0.3'
```

# Usage
- Extends the [AdvanceTask](https://github.com/TUBB/TaskBus/blob/master/library/src/main/java/com/tubb/taskbus/AdvanceTask.java) class, define the fetch data task.
```java
public class UserInfoAdvanceTask extends AdvanceTask<UserInfo> {
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
```
- Execute the task prefetch the data and return the `taskId` you will need.
```java
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
```
- Register the listener to get the loaded data.
```java
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
        tv_user_name.setText(String.format("%s task executing", taskId));
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        tv_user_name.setText(userInfo.name);
        TaskBus.instance().completedTask(taskId);
    }

    @Override
    public void onError(Throwable throwable) {
        tv_user_name.setText(String.format("%s task error", taskId));
        Log.e(TAG, taskId + " task", throwable);
        TaskBus.instance().completedTask(taskId);
    }
}
```
# Note
- A task can only execute once. If you want reuse the task, you should completed the task first!
```java
TaskBus.instance().completedTask(taskId);
```
- Unregister the task listener right, otherwise will leak the `Activity` or other holder instance.
```java
TaskBus.instance().unregisterListener(taskId);
```

# License

    Copyright 2018 TUBB

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.