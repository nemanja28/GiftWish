package rs.rbt.giftwishlist.data.employee.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rs.rbt.giftwishlist.data.employee.Employee
import rs.rbt.giftwishlist.data.employee.source.EmployeeDataSource

/**
 * Created by Nemanja Stosic on 1/22/19.
 */
object EmployeeRemoteDataSource : EmployeeDataSource {

    override fun loadData(loadEmployeeCallback: EmployeeDataSource.LoadEmployeeCallback) {
    }

    override fun findEmployeeByName(firstName: String, lastName: String, getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
    }

    override fun findEmployeeByEmail(email: String, getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
    }

    override fun findMeAsEmployee(getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
    }

    override fun saveEmployee(employee: Employee) {
        
    }

    override fun saveEmployees(employees: MutableList<Employee>) {
        
    }
}