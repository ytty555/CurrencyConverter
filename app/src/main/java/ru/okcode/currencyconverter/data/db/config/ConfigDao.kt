package ru.okcode.currencyconverter.data.db.config

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ConfigDao {

    @Query("SELECT * FROM ConfigEntity")
    fun getConfig(): Flowable<ConfigEntity>

    @Query("SELECT * FROM ConfigEntity")
    fun getConfigSingle(): Single<ConfigEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(config: ConfigEntity)

    @Query("DELETE FROM ConfigEntity")
    fun clear()
}