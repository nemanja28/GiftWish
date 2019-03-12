package rs.rbt.giftwishlist.data.gift.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.AnkoLogger
import rs.rbt.giftwishlist.data.gift.Gift
import rs.rbt.giftwishlist.data.gift.source.GiftDataSource

/**
 * Created by Nemanja Stosic on 11/30/18.
 */
object GiftRemoteDataSource : GiftDataSource, AnkoLogger {

    override fun loadData(employeeId: Int) {
    }

    override fun loadGiftWishListByEmployeeId(employeeId: Int): LiveData<List<Gift>>? {
        return null
    }

    override fun loadGiftWishListByEmployeeEmail(employeeId: Int): LiveData<List<Gift>>? {
        return null
    }

    override fun getGiftWishList(): LiveData<List<Gift>> = MutableLiveData()

    override fun saveGiftWish(gift: Gift) {
    }

    override fun updateGiftWish(gift: Gift) {
    }

    override fun removeGiftFromWishList(gift: Gift) {
    }

}