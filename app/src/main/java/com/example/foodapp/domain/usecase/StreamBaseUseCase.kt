package com.example.foodapp.domain.usecase

import kotlinx.coroutines.flow.Flow

interface StreamBaseUseCase<in Param, out Result> : UseCaseInterface {

    fun invokeStream(param: Param): Flow<Result>
}