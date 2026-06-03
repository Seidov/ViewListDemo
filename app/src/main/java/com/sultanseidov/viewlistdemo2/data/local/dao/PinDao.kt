package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.local.entity.PinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPin(pin: PinEntity): Long

    @Query("DELETE FROM pins_table")
    suspend fun clearAllPins()

    @Query("SELECT * FROM pins_table WHERE pin_id = :pinId")
    suspend fun getPinById(pinId: Long): PinEntity?

    @Query("SELECT * FROM pins_table")
    fun getActivePinsFlow(): Flow<List<PinEntity>>
}
