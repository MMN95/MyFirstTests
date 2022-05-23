package ru.mmn.myfirsttests

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler

}

internal class SearchSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
    override fun io(): Scheduler {
        return Schedulers.io()
    }
}
