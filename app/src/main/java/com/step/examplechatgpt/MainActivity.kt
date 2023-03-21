package com.step.examplechatgpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examplecleanarch.resource.Status
import com.google.gson.Gson
import com.step.examplechatgpt.adapter.MessageAdapter
import com.step.examplechatgpt.databinding.ActivityMainBinding
import com.step.examplechatgpt.utils.hideKeyboard
import com.step.examplechatgpt.utils.showShortToast
import com.step.examplechatgpt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var adapter: MessageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpAdapter()
        initViews()

        viewModel.liveData.observe(this) { response ->

            when (response.status) {
                Status.SUCCESS -> {

                    binding.progressCircular.visibility = View.GONE
                    Log.d(TAG, "setUpObserver: ")

                }
                Status.LOADING -> {

                    binding.progressCircular.visibility = View.VISIBLE
                    Log.d(TAG, "setUpObserver: ")

                }
                Status.ERROR -> {
                    Log.d(TAG, "onCreate: ")

                    binding.progressCircular.visibility = View.GONE
                    this.showShortToast(response.message!!)
                }
            }

        }

        lifecycleScope.launchWhenCreated {

            viewModel.setDaoObserver().observe(this@MainActivity) {
                Log.d(TAG, "onCreate: ")

                if (it.isNullOrEmpty()) {
                    // no data found
                    binding.nothingTextview.visibility = View.VISIBLE
                } else {
                    // data found
                    binding.nothingTextview.visibility = View.INVISIBLE
                    adapter.submitList(it)

                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.recyclerview.smoothScrollToPosition(it.size - 1)
                    }, 500)


                }

            }

        }

    }

    private fun initViews() {


        binding.progressCircular.setOnClickListener {

        }

        binding.sendImageview.setOnClickListener {

            if (!binding.editTextview.text.toString().isNullOrEmpty()) {
                viewModel.submitQuestion(binding.editTextview.text.toString())

                binding.editTextview.setText("")
                this.hideKeyboard()

            }


        }

    }

    private fun setUpAdapter() {

        adapter = MessageAdapter(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

    }
}