package com.colledk.mobilehelp.hilt

import android.content.Context
import androidx.room.Room
import com.colledk.mobilehelp.data.UserRepositoryImpl
import com.colledk.mobilehelp.data.local.AppDatabase
import com.colledk.mobilehelp.data.remote.UserDatabaseRemote
import com.colledk.mobilehelp.domain.repository.UserRepository
import com.colledk.mobilehelp.domain.usecase.GetAllUsersUseCase
import com.colledk.mobilehelp.domain.usecase.GetUserUseCase
import com.colledk.mobilehelp.domain.usecase.ListenToUsersUseCase
import com.colledk.mobilehelp.domain.usecase.SaveUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "UserDB"
        ).build()
    }

    @Provides
    fun providesRemoteDatabase(): UserDatabaseRemote{
        return UserDatabaseRemote()
    }

    @Provides
    fun providesUserRepository(database: AppDatabase, remoteDatabase: UserDatabaseRemote): UserRepository {
        return UserRepositoryImpl(database = database, remoteDatabase = remoteDatabase)
    }

    @Provides
    fun providesGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository = userRepository)
    }

    @Provides
    fun providesSaveUserUseCase(userRepository: UserRepository): SaveUserUseCase {
        return SaveUserUseCase(userRepository = userRepository)
    }

    @Provides
    fun providesGetAllUsersUseCase(userRepository: UserRepository): GetAllUsersUseCase {
        return GetAllUsersUseCase(userRepository = userRepository)
    }

    @Provides
    fun providesListenToUsersUseCase(userRepository: UserRepository): ListenToUsersUseCase {
        return ListenToUsersUseCase(userRepository = userRepository)
    }
}