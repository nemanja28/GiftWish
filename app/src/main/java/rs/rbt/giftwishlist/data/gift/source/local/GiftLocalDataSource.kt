package rs.rbt.giftwishlist.data.gift.source.local

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
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
    private var giftWishListObserver: Observer<List<Gift>>? = null


    override fun loadGiftWishListByEmployeeId(employeeId: Int, loadGiftCallback: GiftDataSource.LoadGiftCallback) {
        giftWishListObserver = Observer {
            loadGiftCallback.onGiftsLoaded(it)
        }
        giftWishListObserver?.apply {
            observableGiftWishList.observeForever(this)
        }

        val giftWishList = giftDao.getGiftWishListByEmployeeId(employeeId)
        observableGiftWishList.addSource(giftWishList) { myGiftList ->
            observableGiftWishList.postValue(myGiftList)
        }
    }

    override fun loadGiftWishListByEmployeeEmail(email: String, loadGiftCallback: GiftDataSource.LoadGiftCallback) {
    }

    override fun getGiftWishList(loadGiftCallback: GiftDataSource.LoadGiftCallback) {
        observableGiftWishList.value?.let {
            loadGiftCallback.onGiftsLoaded(it)
        } ?: run { loadGiftCallback.onDataNotAvailable() }
    }

    override fun saveGiftWish(gift: Gift) {
        launch { giftDao.insertAll(gift) }
    }

    override fun updateGiftWish(gift: Gift) {
        launch { giftDao.updateGift(gift) }
    }

    override fun removeGiftFromWishList(gift: Gift) {
        launch { giftDao.delete(gift) }
    }

    override fun onDestroy() {
        giftWishListObserver?.let {
            observableGiftWishList.removeObserver(it)
        }
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