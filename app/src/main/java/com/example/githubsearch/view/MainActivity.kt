package com.example.githubsearch.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.R
import com.example.githubsearch.adapter.UserAdapter
import com.example.githubsearch.adapter.UserViewHolder
import com.example.githubsearch.data.model.GithubUser
import com.example.githubsearch.data.model.NetworkResult
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

class MainActivity : AppCompatActivity(), UserViewHolder.OnUserClickListener {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private val disposable = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val mUserAdapter = UserAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().withContext(applicationContext).build().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        initUserRecyclerView()
        subscribeView()
    }

    private fun initUserRecyclerView() {
        binding.recyclerUser.apply {
            this.layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            adapter = mUserAdapter
        }
    }

    private fun subscribeView() {
        viewModel.userPagedList.observe(this, {
            mUserAdapter.submitList(it)
        })

        disposable.add(
            RxMainViewObservable.fromTextView(binding.searchComponent)
                .debounce(TIME_OUT_OBSERVABLE, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.setQuery(it)
                }
        )

        viewModel.networkStatus.observe(this, {
            binding.searchProgress.visibility = it.status.progressbarVisibility
            binding.networkStatus = it.also {
                it.message = when (it.status) {
                    NetworkResult.NOT_FOUND -> {
                        getString(R.string.error_not_found, viewModel.query.value)
                    }
                    NetworkResult.ERROR -> {
                        if (viewModel.query.value.isNullOrEmpty()) getString(R.string.error_no_query) else getString(
                            R.string.error_connection
                        )
                    }
                    else -> {
                        ""
                    }
                }
                binding.recyclerUser.visibility =
                    if (it.message.isNotEmpty() || it.status == NetworkResult.LOADING) View.GONE else View.VISIBLE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onUserClick(user: GithubUser) {
        //todo native handling?
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl)))
    }

    companion object {
        private const val TIME_OUT_OBSERVABLE: Long = 500
    }
}