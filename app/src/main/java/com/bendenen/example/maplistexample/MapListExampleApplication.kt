package com.bendenen.example.maplistexample

import android.app.Activity
import android.app.Application
import com.bendenen.example.maplistexample.di.components.DaggerAppComponent
import com.bendenen.example.maplistexample.di.modules.AppModule
import com.bendenen.example.maplistexample.di.modules.NetModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MapListExampleApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()


        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .netModule(NetModule(BuildConfig.URL))
                .build()
                .inject(this)

    }


    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}