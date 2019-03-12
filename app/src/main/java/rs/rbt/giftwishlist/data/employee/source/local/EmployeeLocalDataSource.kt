package rs.rbt.giftwishlist.data.employee.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import rs.rbt.giftwishlist.data.employee.Employee
import rs.rbt.giftwishlist.data.employee.source.EmployeeDataSource
import kotlin.coroutines.CoroutineContext

/**
 * Created by Nemanja Stosic on 1/17/19.
 */
class EmployeeLocalDataSource(private val employeeDao: EmloyeeDao) : EmployeeDataSource, CoroutineScope, AnkoLogger {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private var observableEmployeeList: MediatorLiveData<List<Employee>> = MediatorLiveData()

    override fun loadData() {
        val allEmployees = employeeDao.getAll()
        observableEmployeeList.addSource(allEmployees) { employeeList ->
            warn { "loadData = ${employeeList.size}" }
            observableEmployeeList.postValue(employeeList)
        }
    }

    override fun getEmployees(): LiveData<List<Employee>> = observableEmployeeList

    override fun findEmployeeByName(firstName: String, lastName: String): Employee? {
        return employeeDao.findByName(firstName, lastName)
    }

    override fun findEmployeeByEmail(email: String): Employee? {
        return employeeDao.findByEmail(email)
    }

    override fun findMeAsEmployee(): Employee? {
        return findEmployeeByEmail("nemanja.stosic@rbt.rs")
    }

    override fun saveEmployee(employee: Employee) {
        launch { employeeDao.insertAll(employee) }
    }

    override fun saveEmployees(employees: MutableList<Employee>) {
        launch { employeeDao.insertAll(*employees.toTypedArray()) }
    }

    companion object {
        private var INSTANCE: EmployeeLocalDataSource? = null
        @JvmStatic
        fun getInstance(employeeDao: EmloyeeDao) = INSTANCE
            ?: synchronized(EmployeeLocalDataSource::javaClass) {
                EmployeeLocalDataSource(employeeDao)
            }.also { INSTANCE = it }
    }

}