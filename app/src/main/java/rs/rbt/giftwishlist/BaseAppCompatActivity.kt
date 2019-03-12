package rs.rbt.giftwishlist

import androidx.appcompat.app.AppCompatActivity
import rs.rbt.giftwishlist.util.DialogHelper


/**
 * Created by Nemanja Stosic on 11/30/18.
 */
abstract class BaseAppCompatActivity : AppCompatActivity() {

    fun showProgressDialog(message: String? = null) {
        DialogHelper.showProgressDialog(this, message)
    }

    fun hideProgressDialog() {
        DialogHelper.hideProgressDialog()
    }
}