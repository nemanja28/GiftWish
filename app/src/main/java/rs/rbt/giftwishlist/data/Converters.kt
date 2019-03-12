package rs.rbt.giftwishlist.data

import androidx.room.TypeConverter
import java.util.*


/**
 * Created by Nemanja Stosic on 11/30/18.
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}