package com.sultanseidov.viewlistdemo2.data.model.pinviewlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanseidov.viewlistdemo2.util.Constants.VIEW_LIST_PINS_TABLE

@Entity(tableName = VIEW_LIST_PINS_TABLE)
class PinViewListModel(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    val pinId: Int,
    val pinName: String?,
    val pinValue: String?,
) {
}