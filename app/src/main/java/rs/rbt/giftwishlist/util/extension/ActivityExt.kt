package rs.rbt.giftwishlist.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import rs.rbt.giftwishlist.ViewModelFactory

/**
 * Created by Nemanja Stosic on 1/15/19.
 */

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)