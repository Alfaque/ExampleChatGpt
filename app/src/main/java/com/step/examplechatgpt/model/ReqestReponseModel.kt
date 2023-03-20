package com.step.examplechatgpt.model

data class ReqestReponseModel(
    val max_tokens: Int,
    val model: String,
    val prompt: String,
    val temperature: Int
)