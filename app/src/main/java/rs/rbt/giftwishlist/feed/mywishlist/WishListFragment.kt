package rs.rbt.giftwishlist.feed.mywishlist

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_wishlist.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import rs.rbt.giftwishlist.R
import rs.rbt.giftwishlist.data.gift.Gift
import rs.rbt.giftwishlist.feed.FeedActivity
import rs.rbt.giftwishlist.widget.SwipeController
import rs.rbt.giftwishlist.widget.SwipeControllerActions
import kotlin.coroutines.CoroutineContext


/**
 * Created by Nemanja Stosic on 11/30/18.
 */
class WishListFragment : Fragment(), CoroutineScope, AnkoLogger {

    lateinit var myWishListViewModel: MyWishListViewModel
    private var myGiftWishListAdapter: WishListAdapter? = null

    //region CoroutineScope
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        myWishListViewModel = (activity as FeedActivity).obtainMyWishListViewModel()
        initView(view)
        return view
    }

    private fun initView(view: View) {
        setupFab()
        myGiftWishListAdapter = WishListAdapter {
            myWishListViewModel.giftItemClicked(it)
        }
        view.recyclerViewWishlist.layoutManager = LinearLayoutManager(context)
        view.recyclerViewWishlist.adapter = myGiftWishListAdapter

        val swipeController = SwipeController(object : SwipeControllerActions() {
            override fun onRightClicked(position: Int) {
                myWishListViewModel.removeGiftFromWishListWithPosition(position)
            }

            override fun onLeftClicked(position: Int) {
                myWishListViewModel.editGiftWish(position)
            }
        })

        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(view.recyclerViewWishlist)

        view.recyclerViewWishlist.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeController.onDraw(c)
            }
        })
        subscribeUi(myWishListViewModel.myGiftWishList())
    }

    private fun setupFab() {
        activity?.findViewById<View>(R.id.fabAddGift)?.setOnClickListener {
            myWishListViewModel.addNewGiftWish()
        }
    }


    private fun subscribeUi(liveData: LiveData<List<Gift>>) {
        // Update the list when the data changes
        liveData.observe(this, Observer { myGifts ->
            warn { "giftList - Observe = ${myGifts?.size}" }
            myGiftWishListAdapter?.setWishList(myGifts)
        })
    }

    companion object {
        fun newInstance(): WishListFragment {
            val fragment = WishListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}