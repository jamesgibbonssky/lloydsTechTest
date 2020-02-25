package com.jgg.lloydstechtest

import android.app.Application
import com.jgg.lloydstechtest.ui.di.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MainApplication : Application(), HasAndroidInjector {

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.create().inject(this)
    }

    override fun androidInjector(): DispatchingAndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}