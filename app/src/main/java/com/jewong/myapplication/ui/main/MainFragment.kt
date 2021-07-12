package com.jewong.myapplication.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jewong.myapplication.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initializeObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.toggleRunnable(true)
    }

    override fun onPause() {
        viewModel.toggleRunnable(false)
        super.onPause()
    }

    private fun initializeObservers() {
        viewModel.timers.observe(viewLifecycleOwner, Observer { timers ->
            if (!timers.isNullOrEmpty()) updateRecyclerView(timers)
        })
        viewModel.initializeTimers()
    }

    private fun updateRecyclerView(timers: List<MainViewModel.Timer>) {
        if (context == null) return
        val view = view?.findViewById<RecyclerView>(R.id.recycler_view)
        if (view?.adapter == null || view.layoutManager == null) {
            view?.layoutManager = LinearLayoutManager(context)
            view?.adapter = Adapter(timers) { position ->
                viewModel.toggleTimer(position)
            }
        } else {
            val adapter: Adapter = view.adapter as Adapter
            adapter.update(timers)
        }
    }

}