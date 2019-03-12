package rs.rbt.giftwishlist.data.gift.source

import androidx.lifecycle.LiveData
import rs.rbt.giftwishlist.data.gift.Gift

/**
 * Created by Nemanja Stosic on 1/11/19.
 */
interface GiftDataSource {

    fun loadData(employeeId: Int)

    fun getGiftWishList(): LiveData<List<Gift>>

    fun loadGiftWishListByEmployeeId(employeeId: Int): LiveData<List<Gift>>?

    fun loadGiftWishListByEmployeeEmail(employeeId: Int): LiveData<List<Gift>>?

    fun saveGiftWish(gift: Gift)

    fun updateGiftWish(gift: Gift)

    fun removeGiftFromWishList(gift: Gift)

}