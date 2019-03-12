package rs.rbt.giftwishlist.data


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*
import org.jetbrains.anko.AnkoLogger
import rs.rbt.giftwishlist.WishListApplication
import rs.rbt.giftwishlist.data.employee.source.local.EmloyeeDao
import rs.rbt.giftwishlist.data.employee.Employee
import rs.rbt.giftwishlist.data.gift.Gift
import rs.rbt.giftwishlist.data.gift.source.local.GiftDao
import rs.rbt.giftwishlist.data.gift.source.GiftRepository
import java.util.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by Nemanja Stosic on 11/30/18.
 */

@Database(entities = [Employee::class, Gift::class], version = AppRoomDatabase.DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase : RoomDatabase(), AnkoLogger {


    private val isDatabaseCreated = MutableLiveData<Boolean>()

    abstract fun employeeDao(): EmloyeeDao
    abstract fun giftDao(): GiftDao

    fun getDatabaseCreated(): LiveData<Boolean> {
        return isDatabaseCreated
    }

    companion object : CoroutineScope {
        private val job = Job()
        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.Default

        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "giftWishList.db"


        private var INSTANCE: AppRoomDatabase? = null

        @JvmStatic fun getInstance(context: Context) = INSTANCE ?: synchronized(GiftRepository::class.java) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
                updateDatabaseCreated()
            }
        }

        private fun buildDatabase(context: Context): AppRoomDatabase {
            return synchronized(AppRoomDatabase::class) {
                Room.databaseBuilder(context, AppRoomDatabase::class.java, DATABASE_NAME).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        launch {
                            initEmployees()
                            setDatabaseCreated()
                        }
                    }
                }).build()
            }
        }

        suspend fun initEmployees() {
            val employees = mutableListOf<Employee>()
            employees.add(Employee("Nemanja", "Stosic", "nemanja.stosic@rbt.rs", Date(621388800000)))
            employees.add(Employee("Mihael", "Farkas", "mihael.farkas@rbt.rs", Date(784425600000)))
            employees.add(Employee("Dusan", "Ristic", "dusan.ristic@rbt.rs", Date(735523200000)))
            employees.add(Employee("Stefan", "Ilic", "stefan.ilic@rbt.rs", Date(587001600000)))
            employees.add(Employee("Boris", "Nikolic", "boris.nikolic@rbt.rs", Date(579484800000)))
            employees.add(Employee("Nikola", "Markovic", "nikola.markovic@rbt.rs", Date(757123200000)))
            withContext(coroutineContext) {
                INSTANCE?.employeeDao()?.insertAll(*employees.toTypedArray())
            }
        }

        fun setDatabaseCreated() {
            INSTANCE?.isDatabaseCreated?.postValue(true)
        }

        private fun updateDatabaseCreated() {
            if (WishListApplication.applicationContext().getDatabasePath(DATABASE_NAME).exists()) {
                setDatabaseCreated()
            }
        }
    }


}