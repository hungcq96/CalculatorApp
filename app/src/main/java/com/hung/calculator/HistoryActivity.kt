package com.hung.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.hung.calculator.adapter.HistoryAdapter
import com.hung.calculator.databinding.ActivityHistoryBinding
import com.hung.calculator.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HistoryAdapter()
        binding.rvHistory.adapter = adapter

        viewModel.listHistory.observe(this,{
            adapter.submit(it)
        })

        binding.imBack.setOnClickListener {
            val calculatorIntent = Intent (this, CalculatorActivity::class.java)
            startActivity(calculatorIntent)
        }

        binding.btnClearAll.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.deleteHistory()
            }
        }
    }
}