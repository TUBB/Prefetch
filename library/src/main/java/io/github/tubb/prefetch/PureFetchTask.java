package io.github.tubb.prefetch;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

/**
 * The abstract fetch data task
 * Created by tubingbing on 18/3/11.
 */

public abstract class PureFetchTask<D> extends FetchTask<D, D>{
    /**
     * Execute the task
     * @return pure data
     */
    @WorkerThread
    @NonNull
    @Override
    public abstract D execute();
}
