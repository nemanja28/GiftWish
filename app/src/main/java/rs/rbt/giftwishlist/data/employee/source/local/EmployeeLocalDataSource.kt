package rs.rbt.giftwishlist.data.employee.source.local

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
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
    private var employeeListObserver: Observer<List<Employee>>? = null

    override fun loadData(loadEmployeeCallback: EmployeeDataSource.LoadEmployeeCallback) {
        employeeListObserver = Observer {
            loadEmployeeCallback.onEmployeesLoaded(it)
        }
        employeeListObserver?.apply { observableEmployeeList.observeForever(this) }

        val allEmployees = employeeDao.getAll()
        observableEmployeeList.addSource(allEmployees) { employeeList ->
            observableEmployeeList.postValue(employeeList)
        }
    }

    override fun findEmployeeByName(
        firstName: String,
        lastName: String,
        getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback
    ) {
        employeeDao.findByName(firstName, lastName)
    }

    override fun findEmployeeByEmail(email: String, getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
        employeeDao.findByEmail(email)
    }

    override fun findMeAsEmployee(getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
        findEmployeeByEmail("nemanja.stosic@rbt.rs", getEmployeeCallback)
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