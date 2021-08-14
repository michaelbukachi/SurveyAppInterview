package com.chepsi.survey.app.data.repos

import com.chepsi.survey.app.data.db.ResponseEntity
import com.chepsi.survey.app.data.db.SurveyEntity
import com.chepsi.survey.app.data.network.SurveyApi
import com.chepsi.survey.app.data.network.utils.NetworkResult
import com.chepsi.survey.app.data.toDb
import com.chepsi.survey.app.domain.repos.Survey
import com.chepsi.survey.app.domain.repos.SurveyRepo
import com.chepsi.survey.app.domain.repos.SurveyResponse
import com.chepsi.survey.app.domain.repos.toSurvey
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.toFlow
import io.realm.kotlin.where
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.io.IOException

class SurveyRepoImpl(private val realm: Realm, private val surveyApi: SurveyApi) : SurveyRepo {

    override suspend fun addSurvey(survey: Survey) {
        realm.executeTransactionAwait {
            Timber.i("Insert into db")
            it.copyToRealmOrUpdate(survey.toDb())
        }
    }

    override suspend fun fetchSurvey(): Flow<Survey?> {
        if (realm.where<SurveyEntity>().count() == 0L) {
            when (val result = fetchFromNetwork()) {
                is NetworkResult.Success -> {
                    addSurvey(result.data)
                }
                else -> {

                }
            }
        }
        return realm.where<SurveyEntity>()
            .findAllAsync()
            .toFlow()
            .map {
                if (it.isNotEmpty()) {
                    return@map it.first()?.toSurvey()
                }
                return@map null
            }
    }

    private suspend fun fetchFromNetwork(): NetworkResult<Survey> {
        return try {
            Timber.i("Fetching from network")
            val response = surveyApi.fetchSurvey()
            when {
                response.isSuccessful -> {
                    val survey = response.body()!!.toSurvey()
                    addSurvey(survey)
                    NetworkResult.Success(survey)
                }
                else -> NetworkResult.APIError
            }

        } catch (e: IOException) {
            Timber.e(e)
            NetworkResult.NetworkError
        } catch (e: Exception) {
            Timber.e(e)
            NetworkResult.ServerError()
        }
    }

    override suspend fun submitSurvey(response: SurveyResponse) {
        addResponse(response)
    }


    private suspend fun addResponse(response: SurveyResponse) {
        realm.executeTransactionAwait {
            Timber.i("Insert into db")
            it.copyToRealmOrUpdate(response.toDb())
        }
    }

    override suspend fun fetchResponses(): Flow<List<SurveyResponse>> {
        return realm.where<ResponseEntity>()
            .findAllAsync()
            .toFlow()
            .map { it.map { it.toResponse() } }
    }

    override suspend fun sendSurvey(response: List<SurveyResponse>) {
        surveyApi.submitSurvey(response)
    }

}