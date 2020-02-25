package com.jgg.lloydstechtest.ui.di

import com.jgg.lloydstechtest.data.di.DataModule
import com.jgg.lloydstechtest.MainApplication
import dagger.Component
import javax.inject.Singleton
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    ViewModelModule::class,
    NetworkModule::class,
    DataModule::class
])
interface ApplicationComponent : AndroidInjector<MainApplication> {
}