package com.yafidaffaaa.uas_pam

import android.app.Application
import com.yafidaffaaa.uas_pam.dependenciesinjection.AppContainer
import com.yafidaffaaa.uas_pam.dependenciesinjection.Container

class MonitoringApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}