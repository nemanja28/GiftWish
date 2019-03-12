package rs.rbt.giftwishlist.feed.mywishlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.AnkoLogger
import rs.rbt.giftwishlist.SingleLiveEvent
import rs.rbt.giftwishlist.data.gift.Gift
import rs.rbt.giftwishlist.data.gift.source.GiftRepository

/**
 * Created by Nemanja Stosic on 12/4/18.
 */
class MyWishListViewModel(application: Application, private val giftRepository: GiftRepository) : AndroidViewModel(application), AnkoLogger {

    private var mObservableGiftWishList: LiveData<List<Gift>> = MutableLiveData()
    val addNewGiftWishEvent = SingleLiveEvent<Void>()
    val editGiftWishEvent = SingleLiveEvent<Gift>()
    val giftItemClickEvent = SingleLiveEvent<Gift>()

    init {
        start()
    }

    private fun start() {
        giftRepository.loadData(1)
        mObservableGiftWishList = giftRepository.getGiftWishList()
    }


    fun addNewGiftWish() {
        addNewGiftWishEvent.call()
    }

    fun editGiftWish(position: Int) {
        mObservableGiftWishList.value?.get(position)?.let {
            editGiftWishEvent.value = it
        }
    }

    fun giftItemClicked(gift: Gift) {
        giftItemClickEvent.value = gift
    }


    fun myGiftWishList(): LiveData<List<Gift>> {
        return mObservableGiftWishList
    }

    fun removeGiftFromWishListWithPosition(position: Int) {
        val gift = mObservableGiftWishList.value?.get(position) ?: return
        giftRepository.removeGiftFromWishList(gift)
    }

    fun saveNewGiftWish(name: String, url: String, price: String) {
        val gift = Gift(name, url, price.toDouble(), 1)
        giftRepository.saveGiftWish(gift)
    }

    fun updateGiftWishIfNeeded(oldGiftId: Int, newName: String, newUrl: String, newPrice: String) {
        val oldGift = mObservableGiftWishList.value?.find { it.uid == oldGiftId }
        oldGift?.let {
            if (it.name != newName || it.link != newUrl || it.price != newPrice.toDouble()) {
                oldGift.name = newName
                oldGift.link = newUrl
                oldGift.price = newPrice.toDouble()
                giftRepository.updateGiftWish(oldGift)
            }
        }
    }
}