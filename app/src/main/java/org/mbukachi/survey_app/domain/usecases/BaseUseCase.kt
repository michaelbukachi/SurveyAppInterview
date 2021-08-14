package com.chepsi.survey.app.domain.usecases

interface BaseUseCase<in Parameter, out Result> {
    suspend operator fun invoke(params: Parameter): Result
}