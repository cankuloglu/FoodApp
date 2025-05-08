package com.example.foodapp.di
import com.example.foodapp.domain.util.Constants
import com.example.foodapp.data.repository.RecipeRepositoryImpl
import com.example.foodapp.data.remote.SpoonacularApiService
import com.example.foodapp.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): SpoonacularApiService {
        return retrofit.create(SpoonacularApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiKey(): String = Constants.API_KEY

    @Provides
    @Singleton
    fun provideRecipeRepository(
        api: SpoonacularApiService,
        apiKey: String
    ): RecipeRepository {
        return RecipeRepositoryImpl(api, apiKey)
    }
}
