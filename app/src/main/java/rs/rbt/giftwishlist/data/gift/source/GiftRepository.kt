package rs.rbt.giftwishlist.data.gift.source

import androidx.lifecycle.LiveData
import rs.rbt.giftwishlist.data.gift.Gift

/**
 * Created by Nemanja Stosic on 1/14/19.
 */
class GiftRepository(
    private val giftLocalDataSource: GiftDataSource,
    private val giftRemoteDataSource: GiftDataSource
) : GiftDataSource {

    override fun getGiftWishList(loadGiftCallback: GiftDataSource.LoadGiftCallback) {
        //TODO: Add implementation for remote data source
         giftLocalDataSource.getGiftWishList(loadGiftCallback)
    }

    override fun loadGiftWishListByEmployeeId(employeeId: Int, loadGiftCallback: GiftDataSource.LoadGiftCallback) {
        //TODO: Add implementation for remote data source
        return giftLocalDataSource.loadGiftWishListByEmployeeId(employeeId, loadGiftCallback)
    }

    override fun loadGiftWishListByEmployeeEmail(email: String,  loadGiftCallback: GiftDataSource.LoadGiftCallback) {
        //TODO: Add implementation for remote data source
        return giftLocalDataSource.loadGiftWishListByEmployeeEmail(email, loadGiftCallback)
    }

    override fun saveGiftWish(gift: Gift) {
        //TODO: Add implementation for remote data source
        giftLocalDataSource.saveGiftWish(gift)
    }

    override fun updateGiftWish(gift: Gift) {
        //TODO: Add implementation for remote data source
        giftLocalDataSource.updateGiftWish(gift)
    }

    override fun removeGiftFromWishList(gift: Gift) {
        //TODO: Add implementation for remote data source
        giftLocalDataSource.removeGiftFromWishList(gift)
    }

    override fun onDestroy() {
        giftLocalDataSource.onDestroy()
        giftRemoteDataSource.onDestroy()
    }

    companion object {

        private var INSTANCE: GiftRepository? = null

        @JvmStatic
        fun getInstance(giftLocalDataSource: GiftDataSource, giftRemoteDataSource: GiftDataSource) = INSTANCE
            ?: synchronized(GiftRepository::class.java) {
                INSTANCE ?: GiftRepository(
                    giftLocalDataSource,
                    giftRemoteDataSource
                ).also { INSTANCE = it }
            }
    }
}