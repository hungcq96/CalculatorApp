package com.hung.calculator.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hung.calculator.db.HistoryDao
import com.hung.calculator.model.History
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class CalculatorViewModel @Inject constructor(val historyDao: HistoryDao) : ViewModel() {

    val listHistory = historyDao.getAllHistoryWithLiveData()

    val exp = MutableLiveData<String>()
    val answer = MutableLiveData<String>()

    init {
        exp.value = ""
        answer.value = ""
    }

    fun resetNum() {
        exp.value = ""
        answer.value = ""
    }

    fun deleteNum() {
        var str: String = exp.value.toString()
        if (str != "") {
            str = str.substring(0, str.length - 1)
            exp.value = str
        }

    }

    fun addNumber(n: String) {
        var string = exp.value!!
        if (n == ".") {
            var lastDot = false
            for (i in string) {
                if (i.isDigit())
                    continue
                lastDot = i == '.'
            }
            if (lastDot)
                return
        }
        if (string.trim() == "0") {
            string = ""
        }
        if (n == "." && string == "") {
            string = "0"
        }
        string += n
        exp.value = string
    }

    fun addButtonPlus() {
        val s: String = exp.value!!
        if (!s.get(index = s.length - 1).equals("+"))
            exp.value = "$s+"
    }

    fun addButtonMin() {
        val s: String = exp.value!!
        if (!s.get(index = s.length - 1).equals("-"))
            exp.value = "$s-"
    }

    fun addButtonMul() {
        val s: String = exp.value!!
        if (!s.get(index = s.length - 1).equals("*"))
            exp.value = "$s*"
    }

    fun addButtonDiv() {
        val s: String = exp.value!!
        if (!s.get(index = s.length - 1).equals("/"))
            exp.value = "$s/"
    }


    suspend fun operatorSquare(history: History) {
        val d: Double = exp.value!!.toDouble()
        val square = d * d
        exp.value = "$d²"
        answer.value = square.toString()
        history.exp = "$d²"
        history.result = square.toString()
        //******************************//
        historyDao.insertHistory(history)

    }

    suspend fun operatorSqrt(history: History) {
        val str: String = exp.value!!
        val r = sqrt(str.toDouble())
        val result = r.toString()
        exp.value = "√$str"
        answer.value = result
        history.exp = "√$str"
        history.result = result
        //******************************//
        historyDao.insertHistory(history)
    }

    suspend fun operatorFact(history: History) {
        val num: Int = exp.value!!.toInt()
        val fact: Int = factorial(num)
        exp.value = "$num!"
        answer.value = fact.toString()
        history.exp = "$num!"
        history.result = fact.toString()
        //******************************//
        historyDao.insertHistory(history)
    }

    private fun factorial(num: Int): Int {
        return if (num == 1 || num == 0) 1 else num * factorial(num - 1)
    }

    suspend fun equal(history: History) {
        val s: String = exp.value!!
        val result: Any = evaluate(s)
        val r = result.toString()
        exp.value = s
        answer.value = r
        history.result = r
        //******************************//
        historyDao.insertHistory(history)
    }

    private fun evaluate(s: String): Any {
        return object : Any() {
            var position = -1
            var char = 0


            fun nextChar() {
                char = if (++position < s.length) s[position].toInt() else -1
            }

            // check the extra space present int the expression and removing it.
            fun check(charToCheck: Int): Boolean {
                while (char == ' '.toInt()) nextChar()
                if (char == charToCheck) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val a = parseExpression()
                if (position < s.length)
                    throw RuntimeException("Unexpected: " + char.toChar())
                return a
            }

            private fun parseExpression(): Double {
                var a = parseTerm()
                while (true) {
                    when {
                        check('+'.toInt()) -> a += parseTerm() //addition
                        check('-'.toInt()) -> a -= parseTerm() //subtraction
                        else -> return a
                    }
                }

            }

            private fun parseTerm(): Double {
                var a = parseFactor()
                while (true) {
                    when {
                        check('*'.toInt()) -> a *= parseFactor() // multiplication
                        check('/'.toInt()) -> a /= parseFactor() // division
                        else -> return a
                    }
                }
            }

            private fun parseFactor(): Double {
                //checking for addition  and subtraction and performing unary operations
                if (check('+'.toInt())) return parseFactor()
                if (check('-'.toInt())) return -parseFactor()

                var x: Double

                val startPosition = position

                if (check('('.toInt())) {
                    x = parseExpression()
                    check(')'.toInt())
                } else if (char >= '0'.toInt() && char <= '9'.toInt() || char == '.'.toInt()) {
                    while (char >= '0'.toInt() && char <= '9'.toInt() || char == '.'.toInt())
                        nextChar()
                    x = s.substring(startPosition, position).toDouble()
                } else if (char >= 'a'.toInt() && char <= 'z'.toInt()) {
                    while (char >= 'a'.toInt() && char <= 'z'.toInt())
                        nextChar()
                    val func = s.substring(startPosition, position)
                    x = parseFactor()

                    x = if (func == "sqrt") sqrt(x)
                    else throw RuntimeException(
                        "Unknown function: $func"
                    )
                } else {
                    throw RuntimeException("Unexpected: " + char.toChar())
                }
                return x
            }
        }.parse()
    }
}