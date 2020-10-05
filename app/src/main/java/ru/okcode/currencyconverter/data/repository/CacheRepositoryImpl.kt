package ru.okcode.currencyconverter.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import ru.okcode.currencyconverter.data.db.cache.CacheDao
import ru.okcode.currencyconverter.data.db.cache.CacheMapper
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val cacheDao: CacheDao,
    private val cacheMapper: CacheMapper,
) : CacheRepository {


    private val cacheSubject: BehaviorProcessor<Rates> = BehaviorProcessor.create()


    init {
        cacheDao.getCacheFlowable()
            .map {
                cacheMapper.mapToModel(it)
            }
            .subscribe(cacheSubject)
    }

    override fun getRatesObservable(): Flowable<Rates> {
        return cacheSubject
            .doOnNext{
                Log.e(">>> 1 >>>", "$it")
            }

    }


    override fun getRatesSingle(): Single<Rates> {
        return cacheDao.getCacheSingle()
            .flatMap {
                val rates = cacheMapper.mapToModel(it)!!
                Single.just(rates)
            }
    }

    override fun saveCache(rates: Rates): Completable {
        return Completable.create { emitter ->
            try {
                cacheDao.insertToCache(cacheMapper.mapToEntity(rates))
                emitter.onComplete()
            } catch (error: Throwable) {
                emitter.onError(error)
            }
        }
    }

    override fun onClose() {
    }
}