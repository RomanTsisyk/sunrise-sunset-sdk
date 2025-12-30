package io.github.romantsisyk.kmpdemo.android

import android.app.Application
import io.github.romantsisyk.kmpdemo.di.androidModule
import io.github.romantsisyk.kmpdemo.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KmpDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KmpDemoApplication)
            modules(commonModule, androidModule)
        }
    }
}
