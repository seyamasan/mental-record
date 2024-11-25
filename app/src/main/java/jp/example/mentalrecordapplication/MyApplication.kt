package jp.example.mentalrecordapplication

import android.app.Application
import jp.example.mentalrecordapplication.di.moodModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Koinの初期化
        startKoin {
            androidContext(this@MyApplication)
            modules(moodModule) // 定義したDIモジュールを登録
        }
    }
}