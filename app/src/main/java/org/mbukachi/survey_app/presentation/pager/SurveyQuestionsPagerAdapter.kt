package com.chepsi.survey.app.presentation.pager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chepsi.survey.app.domain.repos.Survey
import com.chepsi.survey.app.presentation.fragments.SurveyQuestionFragment

class SurveyQuestionsPagerAdapter(
    activity: AppCompatActivity,
    private val survey: Survey
) :
    FragmentStateAdapter(activity) {
    override fun getItemCount() = survey.questions.size

    override fun createFragment(position: Int): Fragment {
        return SurveyQuestionFragment.newInstance(position, survey.questions.size)
    }
}