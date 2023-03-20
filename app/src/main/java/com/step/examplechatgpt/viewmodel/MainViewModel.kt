package com.step.examplechatgpt.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examplecleanarch.network.ApiService
import com.example.examplecleanarch.resource.Resource
import com.step.examplechatgpt.model.GetResponseModel
import com.step.examplechatgpt.model.ReqestReponseModel
import com.step.examplechatgpt.utils.TOKEN
import com.step.examplechatgpt.utils.getToken
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    var apiService: ApiService,
) : ViewModel() {

    val liveData = MutableLiveData<Resource<GetResponseModel>>()


    fun submitQuestion(question: String) {

        liveData.postValue(Resource.loading())

        val model = ReqestReponseModel(
            4000,
            "text-davinci-003",
            question,
            0
        )

        apiService.postWithTokenCall(token = TOKEN.getToken(), "completions", model)
            .enqueue(object : Callback<GetResponseModel> {
                override fun onResponse(
                    call: Call<GetResponseModel>,
                    response: Response<GetResponseModel>
                ) {
                    Log.d("TAG", "onResponse: ")
                    if (response.isSuccessful && response.body() != null && response.code() == 200) {

                        response.body()?.let {

                            liveData.postValue(Resource.success(it, ""))


                        }

                    } else {
                        liveData.postValue(Resource.error("Response is unsuccessful"))

                    }

                }

                override fun onFailure(call: Call<GetResponseModel>, t: Throwable) {

                    Log.d("TAG", "onFailure: ")
                    liveData.postValue(Resource.error(t.message!!))

                }
            })

    }


}