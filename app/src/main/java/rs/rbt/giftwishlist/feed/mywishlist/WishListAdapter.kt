package rs.rbt.giftwishlist.feed.mywishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_item_gift.view.*
import org.jetbrains.anko.AnkoLogger
import rs.rbt.giftwishlist.R
import rs.rbt.giftwishlist.data.gift.Gift


/**
 * Created by Nemanja Stosic on 11/30/18.
 */
class WishListAdapter(private val clickAction: (gift: Gift) -> Unit) : RecyclerView.Adapter<WishListAdapter.GiftViewHolder>(), AnkoLogger {

    private var giftWishList: MutableList<Gift>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GiftViewHolder(
            inflater.inflate(R.layout.rv_item_gift, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return giftWishList?.size ?: 0
    }

    override fun onBindViewHolder(holder: GiftViewHolder, position: Int) {
        val giftWishListFinal = giftWishList ?: return
        val gift = giftWishListFinal[position]
        holder.setData(gift)
        holder.itemView.setOnClickListener { clickAction.invoke(gift) }
    }

    fun setWishList(newGiftList: List<Gift>?) {
        if (giftWishList == null) {
            giftWishList = newGiftList?.toMutableList()
            notifyItemRangeInserted(0, newGiftList?.size ?: 0)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return giftWishList?.size ?: 0
                }

                override fun getNewListSize(): Int {
                    return newGiftList?.size ?: 0
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return giftWishList!![oldItemPosition].uid == newGiftList?.get(newItemPosition)?.uid
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newGift = newGiftList?.get(newItemPosition)
                    val oldGift = giftWishList?.get(oldItemPosition)
                    return (newGift?.uid == oldGift?.uid
                            && newGift?.name == oldGift?.name
                            && newGift?.link == oldGift?.link
                            && newGift?.price === oldGift?.price)
                }
            })
            giftWishList = newGiftList?.toMutableList()
            result.dispatchUpdatesTo(listUpdateCallback)
        }
    }

    class GiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(gift: Gift) {
            itemView.textViewGiftName.text = gift.name
            gift.price?.let { price ->
                if (price > 0) {
                    itemView.textViewGiftPrice.text = "$price${itemView.context.getString(R.string.euro_sign)}"
                }
            }
        }
    }

    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(position, count)
        }
    }
}
