package com.alex_podolian.stackexchangetestapp.business.usecases

import com.alex_podolian.stackexchangetestapp.data.model.Users
import com.alex_podolian.stackexchangetestapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FetchUsersCase {
    suspend operator fun invoke(query: String): Flow<Users>
}

class DefaultFetchUsersCase(private val userRepository: UserRepository) : FetchUsersCase {
    override suspend fun invoke(query: String): Flow<Users> = flow {
        emit(userRepository.getUsers(query))
    }
}