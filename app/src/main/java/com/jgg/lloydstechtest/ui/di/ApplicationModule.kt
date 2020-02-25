package com.jgg.lloydstechtest.ui.di

import com.jgg.lloydstechtest.MainActivity
import com.jgg.lloydstechtest.domain.schedulers.SchedulersProvider
import com.jgg.lloydstechtest.domain.schedulers.SchedulersProviderImpl
import com.jgg.lloydstechtest.ui.AlbumDetailsFragment
import com.jgg.lloydstechtest.ui.AlbumSearchFragment
import dagger.Module
import dagger.Binds
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationModule {
    @ContributesAndroidInjector
    internal abstract fun contributeActivityInjector(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeAlbumSearchFragmentInjector(): AlbumSearchFragment

    @ContributesAndroidInjector
    internal abstract fun contributeAlbumDetailsFragmentInjector(): AlbumDetailsFragment

    @Binds
    internal abstract fun provideSchedulerProvider(schedulersProviderImpl: SchedulersProviderImpl): SchedulersProvider

}