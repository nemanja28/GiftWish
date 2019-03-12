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

    override fun loadData(employeeId: Int) {
        giftLocalDataSource.loadData(employeeId)
    }

    override fun getGiftWishList(): LiveData<List<Gift>> {
        //TODO: Add implementation for remote data source
        return giftLocalDataSource.getGiftWishList()
    }

    override fun loadGiftWishListByEmployeeId(employeeId: Int): LiveData<List<Gift>>? {
        //TODO: Add implementation for remote data source
        return giftLocalDataSource.loadGiftWishListByEmployeeId(employeeId)
    }

    override fun loadGiftWishListByEmployeeEmail(employeeId: Int): LiveData<List<Gift>>? {
        //TODO: Add implementation for remote data source
        return giftLocalDataSource.loadGiftWishListByEmployeeEmail(employeeId)
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