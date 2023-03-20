package com.step.examplechatgpt.utils

import android.app.Activity
import android.widget.Toast

fun String.getToken(): String {
    return "Bearer $this"
}

fun Activity.showShortToast(text: String) {

    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

}