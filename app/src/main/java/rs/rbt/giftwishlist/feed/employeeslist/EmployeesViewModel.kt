package rs.rbt.giftwishlist.feed.employeeslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.rbt.giftwishlist.data.employee.Employee
import rs.rbt.giftwishlist.data.employee.source.EmployeeDataSource
import rs.rbt.giftwishlist.data.employee.source.EmployeeRepository

/**
 * Created by Nemanja Stosic on 12/6/18.
 */
class EmployeesViewModel(application: Application, private val employeeRepository: EmployeeRepository) : AndroidViewModel(application) {

    private var mObservableEmployeesList: MutableLiveData<List<Employee>> = MutableLiveData()

    init {
        employeeRepository.loadData(object :EmployeeDataSource.LoadEmployeeCallback{
            override fun onEmployeesLoaded(employees: List<Employee>) {
                mObservableEmployeesList.postValue(employees)
            }

            override fun onDataNotAvailable() {
            }
        })
        //mObservableEmployeesList = employeeRepository.getEmployees()
    }


    fun getAllEmployees(): LiveData<List<Employee>> {
        return mObservableEmployeesList
    }
}