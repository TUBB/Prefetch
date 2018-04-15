package com.tubb.prefetch;

import android.support.annotation.NonNull;

/**
 * The abstract fetch data task
 * Created by tubingbing on 18/3/11.
 */

public abstract class PureFetchTask<D> extends FetchTask<D, D>{
    /**
     * Execute the task
     * @return pure data
     */
    @NonNull
    @Override
    public abstract D execute();
}
