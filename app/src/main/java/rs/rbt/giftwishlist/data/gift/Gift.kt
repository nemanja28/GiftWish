package rs.rbt.giftwishlist.data.gift

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import rs.rbt.giftwishlist.data.employee.Employee


/**
 * Created by Nemanja Stosic on 11/30/18.
 */
@Parcelize
@Entity(foreignKeys = [ForeignKey(entity = Employee::class, parentColumns = arrayOf("uid"), childColumns = arrayOf("employeeUid"), onDelete = ForeignKey.CASCADE)])
data class Gift(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "link") var link: String?,
    @ColumnInfo(name = "price") var price: Double?,
    @ColumnInfo(name = "employeeUid") var employeeUid: Int

) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}