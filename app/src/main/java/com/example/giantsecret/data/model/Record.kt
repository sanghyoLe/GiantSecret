package com.example.giantsecret.data.model


import androidx.room.*
import java.time.LocalDate
import java.util.*

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true) var recordId: Long,
    @ColumnInfo var recordInRoutineId:Long,
    @ColumnInfo var date: LocalDate,
    @ColumnInfo var memo:String

)
data class RecordAndRoutine(
    @Embedded val record: Record,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "recordInRoutineId"
    )
    val routine:Routine

)