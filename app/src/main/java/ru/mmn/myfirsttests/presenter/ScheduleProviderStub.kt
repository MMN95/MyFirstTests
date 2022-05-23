package ru.mmn.myfirsttests.presenter

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.mmn.myfirsttests.SchedulerProvider

class ScheduleProviderStub : SchedulerProvider {

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}
