package com.passexample.view

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.passexample.R
import com.passexample.data.Pass
import com.passexample.data.PassType
import com.passexample.view.adapter.PassAdapter
import com.passexample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val data: MutableList<Pass> = arrayListOf()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initView()
        observeViewModelData()
    }

    private fun initView() {
        val adapter = PassAdapter(
            data,
            object : PassAdapter.AdapterCallback {
                override fun onPassChange(pass: Pass) {
                    viewModel.adjustExpirationDate(pass)
                }
            })

        recyclerview_pass_list.layoutManager = LinearLayoutManager(this)
        recyclerview_pass_list.adapter = adapter

        button_add_day_pass.setOnClickListener {
            val amount = edit_text_day_pass.text.toString().toInt()
            data.add(Pass(PassType.DAY, amount, createAddTime(), false))
            adapter.notifyDataSetChanged()
            hideKeyboard()
        }

        button_hour_pass.setOnClickListener {
            val amount = edit_text_hour_pass.text.toString().toInt()
            data.add(Pass(PassType.HOUR, amount, createAddTime(), false))
            adapter.notifyDataSetChanged()
            hideKeyboard()
        }

        text_pass_time.text = DateFormat.getDateTimeInstance().format(Date())
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModelData() {
        viewModel.expiredDate.observe(this, androidx.lifecycle.Observer {
            text_pass_time.text = DateFormat.getDateTimeInstance().format(it)
        })
    }

    private fun createAddTime(): String {
        val date = Date(System.currentTimeMillis())
        return DateFormat.getDateTimeInstance().format(date)
    }
}
