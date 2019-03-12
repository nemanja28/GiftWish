package rs.rbt.giftwishlist.data.employee

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

/**
 * Created by Nemanja Stosic on 11/29/18.
 */

@Entity
@Parcelize
data class Employee(
    @ColumnInfo(name = "first_name") val firsName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "birthday") val birthday: Date
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}