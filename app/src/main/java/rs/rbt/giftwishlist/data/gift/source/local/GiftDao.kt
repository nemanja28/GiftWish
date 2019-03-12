package rs.rbt.giftwishlist.data.gift.source.local

import androidx.lifecycle.LiveData
import androidx.room.*

import rs.rbt.giftwishlist.data.gift.Gift


/**
 * Created by Nemanja Stosic on 11/30/18.
 */
@Dao
interface GiftDao {

    @Query("SELECT * FROM gift")
    fun getAll(): List<Gift>

    @Query("SELECT * FROM gift WHERE employeeUid = :employeeId")
    fun getGiftWishListByEmployeeId(employeeId: Int): LiveData<List<Gift>>

    @Query("SELECT * FROM gift WHERE employeeUid = :employeeId")
    fun getGiftWishListByEmployeeIdSync(employeeId: Int): List<Gift>

    @Insert
    fun insertAll(vararg gifts: Gift)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGift(vararg gift: Gift)

    @Delete
    suspend fun delete(gift: Gift)

    @Query("DELETE FROM gift WHERE uid = :giftId")
    fun deleteGiftWithId(giftId: Int)

}