package com.example.smartstoming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*
import android.widget.Toast
import android.os.Environment.getExternalStorageDirectory
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.comfirmButton
import kotlinx.android.synthetic.main.activity_main.view.wordToTranslate
import java.io.*
import java.lang.Math.random
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val comfirmButton = findViewById<Button>(R.id.comfirmButton)
        var actionComfirmButton = 0
        val wordToTranslate = findViewById<TextView>(R.id.wordToTranslate)
        val allWords = ArrayList<Word>()

        val csvFile = this.resources.openRawResource(R.raw.fr_nl_words)
        val lines = csvFile.bufferedReader().use { it.readLines() }

        // Read the file line by line starting from the second line
        for ( idx in lines ){
            val tokens = idx.split(delimiters = *charArrayOf(','))
            if (tokens.size > 0) {
                val oneWord = Word(tokens[0],tokens[1])
                allWords.add(oneWord)
            }
        }

        // Print the new customer list
        for (word in allWords) {
            println(word.dutch)
        }

        var currentWordToTranslate: Word = randomWordGenerator(allWords)
        wordToTranslate.text = currentWordToTranslate.french
        var answerUser = findViewById<EditText>(R.id.answerUser)
        comfirmButton.text = "Comfirm"

        comfirmButton.setOnClickListener {
            if (actionComfirmButton == 0) {
                if (answerUser != null) {
                    val finalAnswer = answerUser.editableText.toString().trim()
                    if (finalAnswer == currentWordToTranslate.dutch) {
                        wordToTranslate.text = "Correct"
                    } else {
                        wordToTranslate.text = "Incorrect"
                    }
                    comfirmButton.text = "Next"
                    answerUser.text = null
                    actionComfirmButton = 1
                    answerUser.visibility = View.INVISIBLE
                }
            }else {
                comfirmButton.text = "Comfirm"
                answerUser.visibility = View.VISIBLE
                currentWordToTranslate = randomWordGenerator(allWords)
                wordToTranslate.text = currentWordToTranslate.french
                actionComfirmButton = 0
            }
        }
    }
}

fun randomWordGenerator (allWords: ArrayList<Word>): Word {
    return allWords[Random.nextInt(0,5)]
}
