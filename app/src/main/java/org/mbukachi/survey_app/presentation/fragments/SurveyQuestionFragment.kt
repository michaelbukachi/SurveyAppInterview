package com.chepsi.survey.app.presentation.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.chepsi.survey.app.R
import com.chepsi.survey.app.domain.repos.*
import com.chepsi.survey.app.presentation.main.MainActivity
import com.chepsi.survey.app.presentation.main.MainViewModel
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SurveyQuestionFragment : Fragment() {
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var question: Question

    private var fragmentView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        question = mainViewModel.getQuestion(requireArguments().getInt(POSITION))
        fragmentView = when (QuestionType.valueOf(question.questionType)) {
            QuestionType.SELECT_ONE -> inflater.inflate(R.layout.option_question, container, false)
            QuestionType.FREE_TEXT -> inflater.inflate(R.layout.text_question, container, false)
        }
        setupQuestion()

        setupValidation()
        return fragmentView
    }


    private fun setupValidation() {
        val next = fragmentView?.findViewById<View>(R.id.next)
        when (QuestionType.valueOf(question.questionType)) {
            QuestionType.FREE_TEXT -> {
                fragmentView?.findViewById<TextInputEditText>(R.id.response)
                    ?.addTextChangedListener {
                        next?.isEnabled = it.toString().isNotEmpty()
                    }
            }
            QuestionType.SELECT_ONE -> {
                radioCheck.observe(viewLifecycleOwner) {
                    next?.isEnabled = it != null
                }
            }
        }

        if (thisIsLastQuestion()) {
            (next as Button).text = getString(R.string.btn_submit)
        }

        next?.setOnClickListener {
            val answer = getAnswer()
            mainViewModel.saveAnswer(Answer(question = question.questionText, answer = answer))

            if (thisIsLastQuestion()) {
                val dto = mainViewModel.surveyResponses
                val surveyResponse = SurveyResponse(id = "", answers = dto.answers.toList())
                mainViewModel.saveResponse(surveyResponse)
                (requireActivity() as MainActivity).viewPagerReset()

            } else {
                (requireActivity() as MainActivity).swipeNext()
            }
        }
    }

    private fun thisIsLastQuestion(): Boolean {
        val position = requireArguments().getInt(POSITION)
        val totalItems = requireArguments().getInt(NUMBER_OF_PAGES)
        return position + 1 == totalItems
    }

    private fun getAnswer() =
        when (QuestionType.valueOf(question.questionType)) {
            QuestionType.FREE_TEXT -> {
                fragmentView?.findViewById<TextInputEditText>(R.id.response)?.text.toString()
            }
            QuestionType.SELECT_ONE -> {
                radioCheck.value.toString()
            }
        }

    private fun setupQuestion() {
        val currentPosition = requireArguments().getInt(POSITION)
        question = mainViewModel.getQuestion(currentPosition)
        fragmentView?.findViewById<TextView>(R.id.question)?.text = question.questionText

        when (QuestionType.valueOf(question.questionType)) {
            QuestionType.FREE_TEXT -> {
                configureInputField(question.answerType)
            }
            QuestionType.SELECT_ONE -> {
                configureOption(question.options)
            }
        }
    }

    private val radioCheck = MutableLiveData<String?>()
    private fun configureOption(options: List<Option>) {
        options.forEach {
            val button: RadioButton = LayoutInflater.from(requireContext())
                .inflate(R.layout.radio_button, null) as RadioButton
            button.text = it.displayText
            button.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    radioCheck.value = it.value
            }
            fragmentView?.findViewById<RadioGroup>(R.id.radioGroup)?.addView(button)
        }
    }

    private fun configureInputField(answerType: String) {
        val inputField: TextInputEditText? = fragmentView?.findViewById(R.id.response)
        when (AnswerType.valueOf(answerType)) {
            AnswerType.INTEGER -> {
                inputField?.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            }
            AnswerType.SINGLE_LINE_TEXT -> Unit
        }
    }


    companion object {
        private const val POSITION = "position"
        private const val NUMBER_OF_PAGES = "pages"

        fun newInstance(position: Int, numberOfPages: Int): SurveyQuestionFragment {
            val args = Bundle()
            args.putInt(POSITION, position)
            args.putInt(NUMBER_OF_PAGES, numberOfPages)
            val fragment = SurveyQuestionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}