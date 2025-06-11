package com.example.foodapp.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<in Param, out Result> {

    open suspend operator fun invoke(param: Param): Result {
        throw NotImplementedError("Override suspend invoke if needed.")
    }

    open fun invokeStream(param: Param): Flow<Result> {
        throw NotImplementedError("Override invokeStream if needed.")
    }



}
