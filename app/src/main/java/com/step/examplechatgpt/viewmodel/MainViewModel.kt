package com.step.examplechatgpt.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.example.examplecleanarch.network.ApiService
import com.example.examplecleanarch.resource.Resource
import com.step.examplechatgpt.dao.ChoiceDao
import com.step.examplechatgpt.model.Choice
import com.step.examplechatgpt.model.GetResponseModel
import com.step.examplechatgpt.model.ReqestReponseModel
import com.step.examplechatgpt.utils.GPT
import com.step.examplechatgpt.utils.TOKEN
import com.step.examplechatgpt.utils.USER
import com.step.examplechatgpt.utils.getToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private var apiService: ApiService,
    private var dao: ChoiceDao,
) : ViewModel() {

    val liveData = MutableLiveData<Resource<List<Choice>>>()


    fun submitQuestion(question: String) {

        liveData.postValue(Resource.loading())

        val questionModel = Choice(
            finish_reason = "",
            index = 0,
            text = question,
            sender = USER,
            dateTime = Calendar.getInstance().getTime().time
        )

        saveMessage(questionModel)


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

                            liveData.postValue(Resource.success(null, ""))

                            val m = it.choices[0]
                            m.sender = GPT
                            m.dateTime = Calendar.getInstance().getTime().time

                            saveMessage(m)
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

    private fun saveMessage(model: Choice) {

        viewModelScope.launch(Dispatchers.IO) {
            dao.insertChannel(model)

        }
    }

    fun setDaoObserver() = dao.getData()

}