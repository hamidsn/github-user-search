package com.example.githubsearch.observer

import android.text.Editable
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxMainViewObservable {
    companion object {

        fun fromTextView(textView: TextView): Observable<String> =
            PublishSubject.create<String>().also {
                textView.doAfterTextChanged { text: Editable? -> it.onNext(text.toString()) }
            }
    }
}
