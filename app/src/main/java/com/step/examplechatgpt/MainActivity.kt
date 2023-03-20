package com.step.examplechatgpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.examplecleanarch.resource.Status
import com.google.gson.Gson
import com.step.examplechatgpt.utils.showShortToast
import com.step.examplechatgpt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.submitQuestion("Question")

        viewModel.liveData.observe(this) { response ->

            when (response.status) {
                Status.SUCCESS -> {

                    Log.d(TAG, "setUpObserver: ")

                }
                Status.LOADING -> {

                    Log.d(TAG, "setUpObserver: ")


                }
                Status.ERROR -> {
                    Log.d(TAG, "onCreate: ")

//                    this.showShortToast(response.message!!)


                }
            }

        }

    }
}