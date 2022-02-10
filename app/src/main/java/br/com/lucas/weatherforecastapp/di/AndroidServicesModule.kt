package br.com.lucas.weatherforecastapp.di

import android.content.Context
import br.com.lucas.weatherforecastapp.helpers.PreferencesDelegate
import br.com.lucas.weatherforecastapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AndroidServicesModule {

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providePreferencesDelegate(context: Context): PreferencesDelegate =
        PreferencesDelegate(context, Constants.MY_PREFS)

}