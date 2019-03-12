package rs.rbt.giftwishlist.feed


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.dialog_add_gift.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import rs.rbt.giftwishlist.BaseAppCompatActivity
import rs.rbt.giftwishlist.R
import rs.rbt.giftwishlist.data.gift.Gift
import rs.rbt.giftwishlist.feed.employeeslist.EmployeeListFragment
import rs.rbt.giftwishlist.feed.employeeslist.EmployeesViewModel
import rs.rbt.giftwishlist.feed.mywishlist.MyWishListViewModel
import rs.rbt.giftwishlist.feed.mywishlist.WishListFragment
import rs.rbt.giftwishlist.util.Http
import rs.rbt.giftwishlist.util.extension.obtainViewModel
import rs.rbt.giftwishlist.util.validateFullUrlAddress
import kotlin.coroutines.CoroutineContext

class FeedActivity : BaseAppCompatActivity(),FeedNavigator, CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private lateinit var myWishListViewModel: MyWishListViewModel
    private lateinit var employeesViewModel: EmployeesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, WishListFragment.newInstance()).commit()
        setupNavigation()
        myWishListViewModel = obtainMyWishListViewModel().apply {
            editGiftWishEvent.observe(this@FeedActivity, Observer {
                this@FeedActivity.editGiftWish(it)
            })
            addNewGiftWishEvent.observe(this@FeedActivity, Observer {
                this@FeedActivity.addNewGiftWish()
            })

            giftItemClickEvent.observe(this@FeedActivity, Observer {
                this@FeedActivity.showGiftUrl(it.link)
            })
        }

        employeesViewModel = obtainEmployeesViewModel()
    }

    private fun setupNavigation() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.nav_my_gift_list -> {
                    changeFragment(WishListFragment.newInstance())
                    fabAddGift.visibility = View.VISIBLE

                }
                R.id.nav_employees -> {
                    changeFragment(EmployeeListFragment.newInstance())
                    fabAddGift.visibility = View.GONE
                }
                R.id.nav_logout -> finish()
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun showGiftUrl(url: String?) {
        url?.let {
            var finalUrl = it
            if (!it.validateFullUrlAddress()) {
                finalUrl = String.Http + url
            }
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl)))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun addNewGiftWish() {
        val view = layoutInflater.inflate(R.layout.dialog_add_gift, null)
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.add_new_gift_wish)
            .setView(view)
            .setPositiveButton(R.string.add) { dialog, _ ->
                val name = view.editTextGiftName.text.toString()
                val url = view.editTextLink.text.toString()
                val price = view.editTextPrice.text.toString()

                myWishListViewModel.saveNewGiftWish(name, url, price)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    override fun editGiftWish(gift: Gift) {
        val view = layoutInflater.inflate(R.layout.dialog_add_gift, null)
        view.editTextGiftName.setText(gift.name)
        view.editTextLink.setText(gift.link)
        view.editTextPrice.setText(gift.price.toString())
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.add_new_gift_wish)
            .setView(view)
            .setPositiveButton(R.string.save) { dialog, _ ->
                val name = view.editTextGiftName.text.toString()
                val url = view.editTextLink.text.toString()
                val price = view.editTextPrice.text.toString()
                myWishListViewModel.updateGiftWishIfNeeded(gift.uid, name, url, price)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) EmployeeListFragment.newInstance() else WishListFragment.newInstance()
        }

        override fun getCount(): Int {
            return 2
        }
    }


    fun obtainMyWishListViewModel(): MyWishListViewModel = obtainViewModel(MyWishListViewModel::class.java)
    fun obtainEmployeesViewModel(): EmployeesViewModel = obtainViewModel(EmployeesViewModel::class.java)
}
