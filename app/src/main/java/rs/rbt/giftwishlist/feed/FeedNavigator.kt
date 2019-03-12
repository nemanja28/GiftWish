package rs.rbt.giftwishlist.feed

import rs.rbt.giftwishlist.data.gift.Gift

/**
 * Created by Nemanja Stosic on 3/1/19.
 */
interface FeedNavigator {

    fun addNewGiftWish()
    fun editGiftWish(gift: Gift)
}