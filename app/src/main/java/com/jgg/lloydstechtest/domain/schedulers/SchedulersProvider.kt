package com.jgg.lloydstechtest.domain.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun mainThread(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler
}