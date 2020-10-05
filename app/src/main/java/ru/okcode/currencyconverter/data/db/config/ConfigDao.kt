package ru.okcode.currencyconverter.data.db.config

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Dao
interface ConfigDao {

    @Query("SELECT * FROM ConfigEntity")
    fun getConfig(): Flowable<ConfigEntity>

    @Query("SELECT * FROM ConfigEntity")
    fun checkForEmptyConfig(): Single<ConfigEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(config: ConfigEntity)

    @Query("DELETE FROM ConfigEntity")
    fun clear()
}