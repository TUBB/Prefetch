# Prefetch
![](https://img.shields.io/badge/minSdkVersion-14-brightgreen.svg) ![](https://img.shields.io/badge/release-v0.0.5-brightgreen.svg) [![](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Prefetch data universal library.
- Simple, light-weight and low intrusiveness.
- Reuse existing `Data Layer`.
- Support RxJava. 

# Preview
![Preview](https://github.com/TUBB/Prefetch/blob/master/art/preview.gif)

# Download
```groovy
implementation 'com.tubb:prefetch:0.0.5'
```

# Usage
- Extends the [FetchTask](https://github.com/TUBB/Prefetch/blob/master/library/src/main/java/com/tubb/prefetch/FetchTask.java) class, define the fetch task.
```java
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
```
- Execute the fetch task, prefetch the data and return the `taskId` you will need.
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
        taskId = Prefetch.instance().executeTask(new UserInfoFetchTask());
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
public class UserInfoActivity extends AppCompatActivity implements FetchTask.Listener<UserInfo> {
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
        tv_user_name.setText(String.format("%s task executing", taskId));
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        tv_user_name.setText(userInfo.name);
        Prefetch.instance().finishTask(taskId);
    }

    @Override
    public void onError(Throwable throwable) {
        tv_user_name.setText(String.format("%s task error", taskId));
        Log.e(TAG, taskId + " task", throwable);
        Prefetch.instance().finishTask(taskId);
    }
}
```
# Note
- A task can only execute once. If you want reuse the fetch task, you should finish the fetch task first!
```java
Prefetch.instance().finishTask(taskId);
```
- Unregister the task listener right, otherwise will leak the `Activity` or other holder instance.
```java
Prefetch.instance().unregisterListener(taskId);
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