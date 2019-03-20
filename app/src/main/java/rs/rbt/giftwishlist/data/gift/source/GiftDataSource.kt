package rs.rbt.giftwishlist.data.gift.source

import androidx.lifecycle.LiveData
import rs.rbt.giftwishlist.data.gift.Gift

/**
 * Created by Nemanja Stosic on 1/11/19.
 */
interface GiftDataSource {

    interface LoadGiftCallback {

        fun onGiftsLoaded(gifts: List<Gift>)

        fun onDataNotAvailable()
    }

    fun getGiftWishList(loadGiftCallback: LoadGiftCallback)

    fun loadGiftWishListByEmployeeId(employeeId: Int, loadGiftCallback: LoadGiftCallback)

    fun loadGiftWishListByEmployeeEmail(email: String, loadGiftCallback: LoadGiftCallback)

    fun saveGiftWish(gift: Gift)

    fun updateGiftWish(gift: Gift)

    fun removeGiftFromWishList(gift: Gift)

    fun onDestroy()

}