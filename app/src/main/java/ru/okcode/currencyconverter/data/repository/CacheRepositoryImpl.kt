package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ru.okcode.currencyconverter.data.db.cache.CacheDao
import ru.okcode.currencyconverter.data.db.cache.CacheMapper
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val cacheDao: CacheDao,
    private val cacheMapper: CacheMapper,
) : CacheRepository {

    override fun getRates(): Observable<Rates> {
        return cacheDao.getCache()
            .toObservable()
            .flatMap {
                val rates = cacheMapper.mapToModel(it)!!
                Observable.just(rates)
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
}