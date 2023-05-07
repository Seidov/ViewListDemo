package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.*
import com.sultanseidov.viewlistdemo2.data.model.viewlistpin.PinsViewListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PinsViewListDao {
    @Query("SELECT * FROM view_list_pins_table")
    fun getAllPins(): Flow<List<PinsViewListModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPin(pin: PinsViewListModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPins(pins: List<PinsViewListModel>)

    @Update
    suspend fun updatePin(viewList: PinsViewListModel)
    @Query("DELETE FROM view_list_pins_table")
    suspend fun deleteAllPins()
}