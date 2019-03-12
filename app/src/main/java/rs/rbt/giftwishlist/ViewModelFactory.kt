package rs.rbt.giftwishlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Nemanja Stosic on 1/14/19.
 */

class ViewModelFactory private constructor(private val creators: Map<Class<out ViewModel>, ViewModel>) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return viewModel as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) = INSTANCE ?: synchronized(ViewModelFactory::class.java) {
            INSTANCE ?: ViewModelFactory(
                Injection.provideSourceRepositories(application)
            ).also { INSTANCE = it }
        }
    }
}