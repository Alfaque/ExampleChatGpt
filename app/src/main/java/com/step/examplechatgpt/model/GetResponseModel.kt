package com.step.examplechatgpt.model

data class GetResponseModel(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)