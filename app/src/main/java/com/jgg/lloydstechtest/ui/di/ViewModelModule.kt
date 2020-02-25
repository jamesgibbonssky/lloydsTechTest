package com.jgg.lloydstechtest.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jgg.lloydstechtest.ui.viewmodel.AlbumDetailsViewModel
import com.jgg.lloydstechtest.ui.viewmodel.AlbumSearchViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlbumSearchViewModel::class)
    internal abstract fun provideAlbumSearchViewModel(viewModel: AlbumSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel::class)
    internal abstract fun provideAlbumDetailsViewModel(viewModel: AlbumDetailsViewModel): ViewModel

    //Add more ViewModels here
}