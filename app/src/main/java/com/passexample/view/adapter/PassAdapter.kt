package com.passexample.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.passexample.R
import com.passexample.data.Pass

class PassAdapter(private val list: List<Pass>, private val adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<PassAdapter.PassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.pass_item_layout, parent, false)
            .let {
                PassViewHolder(it)
            }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        holder.tvPassType.text = "${list[position].passType.name}  PASS : ${list[position].amount}"
        refreshActivateButton(holder, position)
        holder.buttonActivate.setOnClickListener {
            val activateStatus = list[position].activate
            if (!activateStatus) {
                list[position].activate = !activateStatus
                refreshActivateButton(holder, position)
                adapterCallback.onPassChange(list[position])
            }
        }
        holder.tvAddTime.text = list[position].addTime
    }

    private fun refreshActivateButton(holder: PassViewHolder, position: Int) {
        if (list[position].activate) {
            holder.buttonActivate.text =
                holder.buttonActivate.context.getString(R.string.deactivate)
        }
    }


    class PassViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPassType: TextView = view.findViewById(R.id.title_pass_type)
        val tvAddTime: TextView = view.findViewById(R.id.title_add_time)
        val buttonActivate: Button = view.findViewById(R.id.button_activate)
    }

    interface AdapterCallback {
        fun onPassChange(pass: Pass)
    }
}