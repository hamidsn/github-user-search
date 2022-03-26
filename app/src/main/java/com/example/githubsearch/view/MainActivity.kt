package com.example.githubsearch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.di.DaggerAppComponent
import com.example.githubsearch.observer.RxMainViewObservable
import com.example.githubsearch.view.viewmodel.MainViewModel
import com.example.githubsearch.view.viewmodel.MainViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val disposable = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAppComponent.builder().withContext(applicationContext).build().inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        subscribeView()
    }


    private fun subscribeView() {

        disposable.add(
            RxMainViewObservable.fromTextView(binding.searchComponent)
                .debounce(TIME_OUT_OBSERVABLE, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //todo
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    companion object {
        private const val TIME_OUT_OBSERVABLE: Long = 500

    }
}