package com.example.giantsecret.util

import android.service.autofill.CharSequenceTransformation
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class Converters{

        @TypeConverter
        fun toDate(dateString: String?): LocalDate? {
            return if (dateString == null) {
                null
            } else {
                val charSequence:CharSequence = dateString

                LocalDate.parse(charSequence)
            }
        }

        @TypeConverter
        fun toDateString(date: LocalDate?): String? {
            return if (date == null) {
                null
            } else {
                date.toString()
            }
        }

}

