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


    override fun loadGiftWishListByEmployeeId(employeeId: Int, loadGiftCallback: GiftDataSource.LoadGiftCallback){
    }

    override fun loadGiftWishListByEmployeeEmail(email: String, loadGiftCallback: GiftDataSource.LoadGiftCallback) {
    }

    override fun getGiftWishList(loadGiftCallback: GiftDataSource.LoadGiftCallback){

    }

    override fun saveGiftWish(gift: Gift) {
    }

    override fun updateGiftWish(gift: Gift) {
    }

    override fun removeGiftFromWishList(gift: Gift) {
    }

    override fun onDestroy() {

    }

}