package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.*
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PinViewListDao {
    @Query("SELECT * FROM view_list_pins_table")
    fun getAllPins(): Flow<List<PinViewListModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPin(pin: PinViewListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPins(pins: List<PinViewListModel>)

    @Update
    suspend fun updatePin(viewList: PinViewListModel)

    @Query("DELETE FROM view_list_pins_table")
    suspend fun deleteAllPins()
}