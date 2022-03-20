package com.hung.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.hung.calculator.adapter.HistoryAdapter
import com.hung.calculator.databinding.ActivityCalculatorBinding
import com.hung.calculator.model.History
import com.hung.calculator.viewmodel.CalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalculatorActivity : AppCompatActivity() {
    private val viewModel: CalculatorViewModel by viewModels()

    private lateinit var exp: TextView
    private lateinit var result: TextView
    private lateinit var adapter: HistoryAdapter

    private lateinit var binding: ActivityCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HistoryAdapter()
        viewModel.listHistory.observe(this, {
            adapter.submit(it)
        })
        exp = binding.tvExpression
        result = binding.tvResult

        // Screen Expression and Screen Answer
        viewModel.exp.observe(this, {
            exp.text = it
        })

        viewModel.answer.observe(this, {
            result.text = it
        })

        binding.vm = viewModel

        // Button Number
        binding.button0.setOnClickListener {
            clickedNum(0)
        }
        binding.button1.setOnClickListener {
            clickedNum(1)
        }
        binding.button2.setOnClickListener {
            clickedNum(2)
        }
        binding.button3.setOnClickListener {
            clickedNum(3)
        }
        binding.button4.setOnClickListener {
            clickedNum(4)
        }
        binding.button5.setOnClickListener {
            clickedNum(5)
        }
        binding.button6.setOnClickListener {
            clickedNum(6)
        }
        binding.button7.setOnClickListener {
            clickedNum(7)
        }
        binding.button8.setOnClickListener {
            clickedNum(8)
        }
        binding.button9.setOnClickListener {
            clickedNum(9)
        }
        binding.buttonDot.setOnClickListener {
            clickedNum(-1)
        }
//------------------------------------------------------------------------------------------------//
        //Button Open and Close
        binding.buttonOpen.setOnClickListener {
            binding.tvExpression.append("(")
        }
        binding.buttonClose.setOnClickListener {
            binding.tvExpression.append(")")
        }
//------------------------------------------------------------------------------------------------//
        //Button Operators ( Sqrt , Square ,Fact )
        binding.buttonSqrt.setOnClickListener {
            val exp = binding.tvExpression.text.toString()
            val ans = binding.tvResult.text.toString()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.operatorSqrt(History(exp = exp, result = ans))
            }
        }
        binding.buttonSquare.setOnClickListener {
            val exp = binding.tvExpression.text.toString()
            val ans = binding.tvResult.text.toString()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.operatorSquare(History(exp = exp, result = ans))
            }
        }
        binding.buttonFact.setOnClickListener {
            val exp = binding.tvExpression.text.toString()
            val ans = binding.tvResult.text.toString()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.operatorFact(History(exp = exp, result = ans))
            }
        }
        // Button Equal
        binding.buttonEqual.setOnClickListener {
            val exp = binding.tvExpression.text.toString()
            val ans = binding.tvResult.text.toString()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.equal(History(exp = exp, result = ans))
            }
        }
//------------------------------------------------------------------------------------------------//
        binding.buttonHistory.setOnClickListener {
            val historyIntent = Intent(this, HistoryActivity::class.java)
            startActivity(historyIntent)
        }
    }
    //------------------------------------------------------------------------------------------------//
    private fun clickedNum(n: Int) {
        if (n != -1)
            viewModel.addNumber(n.toString())
        else
            viewModel.addNumber(".")
    }
}