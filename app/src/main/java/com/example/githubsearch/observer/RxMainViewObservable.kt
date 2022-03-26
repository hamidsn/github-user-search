package com.example.githubsearch.observer

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxMainViewObservable {
    companion object {

        fun fromTextView(textView: TextView): Observable<String> {
            val subject = PublishSubject.create<String>()

            textView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //do nothing
                }


                override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    subject.onNext(charSequence.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                    //do nothing
                }
            })
            return subject
        }
    }
}
