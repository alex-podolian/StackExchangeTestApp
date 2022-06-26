package com.alex_podolian.stackexchangetestapp.business.usecases

import com.alex_podolian.stackexchangetestapp.data.model.TopTags
import com.alex_podolian.stackexchangetestapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FetchUsersTopTagsCase {
    suspend operator fun invoke(userId: String): Flow<TopTags>
}

class DefaultFetchUsersTopTagsCase(private val userRepository: UserRepository) : FetchUsersTopTagsCase {
    override suspend fun invoke(userId: String): Flow<TopTags> = flow {
        emit(userRepository.getTopTags(userId))
    }
}