package com.example.foodapp.domain.usecase

interface BaseUseCase<in Param, out Result> : UseCaseInterface {

    suspend operator fun invoke(param: Param): Result

}
