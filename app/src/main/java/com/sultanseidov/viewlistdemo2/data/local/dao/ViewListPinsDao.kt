package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.*
import com.sultanseidov.viewlistdemo2.data.entity.viewlistpin.PinsViewListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewListPinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPinViewList(viewList: PinsViewListModel)

    @Update
    suspend fun updatePinViewList(viewList: PinsViewListModel)

    @Query("SELECT * FROM view_list_pins_table WHERE pinId =:pinId")
    fun getPinViewList(pinId:Int): Flow<List<PinsViewListModel>>

    @Query("SELECT * FROM view_list_pins_table")
    fun getAllPinViewList(): Flow<List<PinsViewListModel>>

    @Query("DELETE FROM view_list_pins_table")
    suspend fun deleteAllPinViewList()
}