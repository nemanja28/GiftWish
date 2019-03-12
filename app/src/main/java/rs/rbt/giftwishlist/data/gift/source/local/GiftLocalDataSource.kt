package rs.rbt.giftwishlist.data.gift.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import rs.rbt.giftwishlist.data.gift.Gift
import rs.rbt.giftwishlist.data.gift.source.GiftDataSource
import kotlin.coroutines.CoroutineContext

/**
 * Created by Nemanja Stosic on 11/30/18.
 */
class GiftLocalDataSource(private val giftDao: GiftDao) : CoroutineScope, AnkoLogger, GiftDataSource {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private var observableGiftWishList: MediatorLiveData<List<Gift>> = MediatorLiveData()

    override fun loadData(employeeId: Int) {
        val giftWishList = loadGiftWishListByEmployeeId(employeeId)
        observableGiftWishList.addSource(giftWishList) { myGiftList ->
            observableGiftWishList.postValue(myGiftList)
        }
    }

    override fun loadGiftWishListByEmployeeId(employeeId: Int): LiveData<List<Gift>> {
        return giftDao.getGiftWishListByEmployeeId(employeeId)
    }

    override fun loadGiftWishListByEmployeeEmail(employeeId: Int): LiveData<List<Gift>>? {
        return null
    }

    override fun getGiftWishList(): LiveData<List<Gift>> = observableGiftWishList

    override fun saveGiftWish(gift: Gift) {
        launch { giftDao.insertAll(gift) }
    }

    override fun updateGiftWish(gift: Gift) {
        launch { giftDao.updateGift(gift) }
    }

    override fun removeGiftFromWishList(gift: Gift) {
        launch { giftDao.delete(gift) }
    }

    companion object {
        private var INSTANCE: GiftLocalDataSource? = null
        @JvmStatic
        fun getInstance(giftDao: GiftDao) = INSTANCE
            ?: synchronized(GiftLocalDataSource::javaClass) {
                GiftLocalDataSource(giftDao)
            }.also { INSTANCE = it }
    }
}