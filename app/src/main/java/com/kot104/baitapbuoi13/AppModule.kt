package com.kot104.baitapbuoi13

import android.content.Context
import com.kot104.baitapbuoi13.repository.*
import com.kot104.baitapbuoi13.repository.Repository
import com.kot104.baitapbuoi13.room.StudentsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StudentsDB {
        return StudentsDB.getIntance(context)
    }

    @Provides
    @Singleton
    fun provideRepository(db: StudentsDB): Repository {
        return Repository(db)
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(db: StudentsDB): CustomerRepository {
        return CustomerRepository(db)
    }

    @Provides
    @Singleton
    fun providePropertyRepository(db: StudentsDB): PropertyRepository {
        return PropertyRepository(db)
    }

    @Provides
    @Singleton
    fun provideInteractionRepository(db: StudentsDB): InteractionRepository {
        return InteractionRepository(db)
    }
}