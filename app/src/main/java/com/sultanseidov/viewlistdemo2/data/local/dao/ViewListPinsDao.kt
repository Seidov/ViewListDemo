package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.*
import com.sultanseidov.viewlistdemo2.data.entity.myviewlist.MyViewListModel
import com.sultanseidov.viewlistdemo2.data.entity.viewlistpin.ViewListPinModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewListPinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPinViewList(viewList: ViewListPinModel)

    @Update
    suspend fun updatePinViewList(viewList: ViewListPinModel)

    @Query("SELECT * FROM view_list_pins_table WHERE pinId =:pinId")
    fun getPinViewList(pinId:Int): Flow<List<ViewListPinModel>>

    @Query("SELECT * FROM view_list_pins_table")
    fun getAllPinViewList(): Flow<List<ViewListPinModel>>

    @Query("DELETE FROM view_list_pins_table")
    suspend fun deleteAllPinViewList()
}