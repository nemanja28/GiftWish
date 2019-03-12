package rs.rbt.giftwishlist

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import rs.rbt.giftwishlist.data.AppRoomDatabase
import rs.rbt.giftwishlist.data.authorization.AuthorizationRepository
import rs.rbt.giftwishlist.data.authorization.source.local.AuthorizationSharedPreferencesDataSource
import rs.rbt.giftwishlist.data.authorization.source.remote.AuthorizationRemoteDataSource
import rs.rbt.giftwishlist.data.employee.source.EmployeeRepository
import rs.rbt.giftwishlist.data.employee.source.local.EmployeeLocalDataSource
import rs.rbt.giftwishlist.data.employee.source.remote.EmployeeRemoteDataSource
import rs.rbt.giftwishlist.data.gift.source.GiftRepository
import rs.rbt.giftwishlist.data.gift.source.local.GiftLocalDataSource
import rs.rbt.giftwishlist.data.gift.source.remote.GiftRemoteDataSource
import rs.rbt.giftwishlist.feed.employeeslist.EmployeesViewModel
import rs.rbt.giftwishlist.feed.mywishlist.MyWishListViewModel
import rs.rbt.giftwishlist.login.LoginViewModel
import rs.rbt.giftwishlist.splash.SplashViewModel

/**
 * Created by Nemanja Stosic on 1/15/19.
 */
object Injection {

    fun provideGiftRepository(context: Context): GiftRepository {
        val database = AppRoomDatabase.getInstance(context)
        return GiftRepository.getInstance(GiftRemoteDataSource, GiftLocalDataSource.getInstance(database.giftDao()))
    }

    fun provideSourceRepositories(application: Application): MutableMap<Class<out ViewModel>, ViewModel> {
        val database = AppRoomDatabase.getInstance(application.applicationContext)
        val myWishListViewModel = MyWishListViewModel(application, GiftRepository.getInstance(GiftLocalDataSource.getInstance(database.giftDao()), GiftRemoteDataSource))
        val employeesViewModel = EmployeesViewModel(application, EmployeeRepository.getInstance(EmployeeLocalDataSource.getInstance(database.employeeDao()), EmployeeRemoteDataSource))
        val loginViewModel = LoginViewModel(application, AuthorizationRepository(AuthorizationSharedPreferencesDataSource, AuthorizationRemoteDataSource))
        val splashViewModel = SplashViewModel(application,  AuthorizationRepository(AuthorizationSharedPreferencesDataSource, AuthorizationRemoteDataSource))


        val sources = mutableMapOf<Class<out ViewModel>, ViewModel>()
        sources[MyWishListViewModel::class.java] = myWishListViewModel
        sources[EmployeesViewModel::class.java] = employeesViewModel
        sources[LoginViewModel::class.java] = loginViewModel
        sources[SplashViewModel::class.java] = splashViewModel

        return sources
    }

    fun provideAuthCredentials(): Pair<String, String> = Pair("nemanja", "123")
}