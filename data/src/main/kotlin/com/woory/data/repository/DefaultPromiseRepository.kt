package com.woory.data.repository

import com.woory.data.model.GeoPointModel
import com.woory.data.model.LocationSearchModel
import com.woory.data.model.MagneticInfoModel
import com.woory.data.model.PromiseAlarmModel
import com.woory.data.model.PromiseDataModel
import com.woory.data.model.PromiseModel
import com.woory.data.model.UserHpModel
import com.woory.data.model.UserLocationModel
import com.woory.data.model.UserModel
import com.woory.data.source.DatabaseDataSource
import com.woory.data.source.FirebaseDataSource
import com.woory.data.source.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultPromiseRepository @Inject constructor(
    private val databaseDataSource: DatabaseDataSource,
    private val firebaseDataSource: FirebaseDataSource,
    private val networkDataSource: NetworkDataSource
) : PromiseRepository {

    override suspend fun setPromise(promiseDataModel: PromiseDataModel): Result<String> {
        val result = firebaseDataSource.setPromise(promiseDataModel)
            .onSuccess { code ->
                setPromiseAlarmByPromiseModel(PromiseModel(code, promiseDataModel))
            }
            .onFailure {
                return Result.failure(it)
            }
        return result
    }

    override suspend fun getPromiseAlarm(promiseCode: String): Result<PromiseAlarmModel> =
        databaseDataSource.getPromiseAlarmWhereCode(promiseCode)

    override suspend fun getAllPromiseAlarms(): Result<List<PromiseAlarmModel>> =
        databaseDataSource.getAll()

    override suspend fun setPromiseAlarmByPromiseModel(promiseModel: PromiseModel): Result<Unit> =
        databaseDataSource.setPromiseAlarmByPromiseModel(promiseModel)

    override suspend fun setPromiseAlarmByPromiseAlarmModel(promiseAlarmModel: PromiseAlarmModel): Result<Unit> =
        databaseDataSource.setPromiseAlarmByPromiseAlarmModel(promiseAlarmModel)

    override suspend fun getAddressByPoint(geoPointModel: GeoPointModel): Result<String> =
        networkDataSource.getAddressByPoint(geoPointModel)

    override suspend fun getPromiseByCode(promiseCode: String): Result<PromiseModel> =
        firebaseDataSource.getPromiseByCode(promiseCode)

    override suspend fun getPromiseByCodeAndListen(promiseCode: String): Flow<Result<PromiseModel>> =
        firebaseDataSource.getPromiseByCodeAndListen(promiseCode)

    override suspend fun setUserLocation(userLocationModel: UserLocationModel): Result<Unit> =
        firebaseDataSource.setUserLocation(userLocationModel)

    override suspend fun setUserHp(gameToken: String, userHpModel: UserHpModel): Result<Unit> =
        firebaseDataSource.setUserHp(gameToken, userHpModel)

    override suspend fun getUserLocation(userId: String): Flow<Result<UserLocationModel>> =
        firebaseDataSource.getUserLocationById(userId)

    override suspend fun getUserHp(userId: String, gameToken: String): Flow<Result<UserHpModel>> =
        firebaseDataSource.getUserHpById(userId, gameToken)

    override suspend fun addPlayer(code: String, user: UserModel): Result<Unit> =
        firebaseDataSource.addPlayer(code, user)

    override suspend fun getSearchedLocationByKeyword(keyword: String): Result<List<LocationSearchModel>> =
        networkDataSource.searchLocationByKeyword(keyword)

    override suspend fun getJoinedPromiseList(): Result<List<PromiseAlarmModel>> =
        databaseDataSource.getAll()

    override suspend fun getMagneticInfoByCode(promiseCode: String): Result<MagneticInfoModel> =
        firebaseDataSource.getMagneticInfoByCode(promiseCode)

    override suspend fun getMagneticInfoByCodeAndListen(promiseCode: String): Flow<Result<MagneticInfoModel>> =
        firebaseDataSource.getMagneticInfoByCodeAndListen(promiseCode)

    override suspend fun updateMagneticRadius(gameCode: String, radius: Double): Result<Unit> =
        firebaseDataSource.updateMagneticRadius(gameCode, radius)

    override suspend fun decreaseMagneticRadius(gameCode: String) =
        firebaseDataSource.decreaseMagneticRadius(gameCode)
}