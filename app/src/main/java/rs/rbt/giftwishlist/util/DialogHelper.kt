package rs.rbt.giftwishlist.util

import android.app.ProgressDialog
import android.content.Context
import org.jetbrains.anko.indeterminateProgressDialog
import rs.rbt.giftwishlist.R

/**
 * Created by Nemanja Stosic on 11/30/18.
 */
object DialogHelper {

    private var progressDialog: ProgressDialog? = null


    fun showProgressDialog(context: Context, message: String? = null) {
        progressDialog = context.indeterminateProgressDialog(message ?: context.getString(R.string.please_wait_a_moment))
        progressDialog?.setCancelable(false)
    }

    fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

}