package com.dandanplay.tv

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.StrictMode
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.seiko.common.app.AppDelegate
import com.seiko.common.app.AppSetupDelegate
import com.seiko.common.util.timber.NanoDebugTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application(), AppDelegate by AppSetupDelegate() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupARouter()
        // 注解
        startKoin {
            androidContext(this@App)
        }
        setupApplication()
        setupStrictModel()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        clearOnLowMemory()
    }

}

/**
 * 初始化日志
 */
private fun setupTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(NanoDebugTree())
    }
}

/**
 * 初始化路由
 */
private fun Application.setupARouter() {
    if (BuildConfig.DEBUG) {
        // 打印日志
        ARouter.openLog()
        //开启调试模式
        ARouter.openDebug()
    }
    ARouter.init(this)
}

/**
 * 开启严格模式
 */
private fun setupStrictModel() {
    if (BuildConfig.DEBUG) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectActivityLeaks()
//            .detectLeakedClosableObjects()
            .penaltyLog()
            .penaltyDeath()
            .build())
    }
}