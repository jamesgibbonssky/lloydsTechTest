package com.jgg.lloydstechtest.ui.utils

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject



object SearchViewObservable {

    fun getObservable(searchView: SearchView): Observable<String> {

        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                subject.onComplete()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return true
            }
        })

        return subject
    }
}