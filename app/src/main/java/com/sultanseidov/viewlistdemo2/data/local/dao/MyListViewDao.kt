package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sultanseidov.viewlistdemo2.data.entity.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.entity.myviewlist.MyViewListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListViewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMyListView(myViewList: MyViewListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMyPopularListView(myViewList: List<MovieModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMyListView(myViewList: List<MyViewListModel>)

    @Update
    suspend fun updateMyListView(myViewList: MyViewListModel)

    @Query("SELECT * FROM my_view_list_movies_table WHERE movieId =:movieId")
    fun getMyListView(movieId:Int): Flow<List<MyViewListModel>>

    @Query("SELECT * FROM my_view_list_movies_table")
    fun getAllMyListView(): Flow<List<MyViewListModel>>

    @Query("DELETE FROM my_view_list_movies_table")
    suspend fun deleteAllMyListView()
}