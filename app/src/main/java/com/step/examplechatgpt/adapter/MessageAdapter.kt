package com.step.examplechatgpt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.step.examplechatgpt.databinding.ItemMessageBinding
import com.step.examplechatgpt.model.Choice
import com.step.examplechatgpt.utils.GPT
import com.step.examplechatgpt.utils.USER

class MessageAdapter(var context: Context) :
    ListAdapter<Choice, MessageAdapter.MyHolder>(DIFF_CALLBACK) {

    companion object {
        var DIFF_CALLBACK = object : DiffUtil.ItemCallback<Choice>() {
            override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean {

                return oldItem.dateTime.compareTo(newItem.dateTime) == 0
            }

            override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean {

                return oldItem.equals(newItem);

            }

        }
    }


    class MyHolder(var binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        return MyHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val model = getItem(position)

        if (model.sender == GPT) {

            holder.binding.userLayout.visibility = View.GONE
            holder.binding.gptLayout.visibility = View.VISIBLE
            holder.binding.gptTextview.text = model.text

        } else if (model.sender == USER) {

            holder.binding.userLayout.visibility = View.VISIBLE
            holder.binding.gptLayout.visibility = View.GONE

            holder.binding.userTextview.text = model.text


        }


    }
}