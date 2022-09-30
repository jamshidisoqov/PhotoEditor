package uz.gita.photoeditor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.photoeditor.navigation.Navigator
import uz.gita.photoeditor.navigation.NavigatorDispatcher
import uz.gita.photoeditor.navigation.NavigatorDispatcherImpl

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @Binds
    fun navigator(impl: NavigatorDispatcherImpl): Navigator

    @Binds
    fun navigatorDispatcher(impl: NavigatorDispatcherImpl): NavigatorDispatcher
}

