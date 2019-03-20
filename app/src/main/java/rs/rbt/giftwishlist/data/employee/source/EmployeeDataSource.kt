package rs.rbt.giftwishlist.data.employee.source

import androidx.lifecycle.LiveData
import rs.rbt.giftwishlist.data.employee.Employee

/**
 * Created by Nemanja Stosic on 1/11/19.
 */
interface EmployeeDataSource {

    interface LoadEmployeeCallback {

        fun onEmployeesLoaded(employees: List<Employee>)

        fun onDataNotAvailable()
    }

    interface GetEmployeeCallback {

        fun onEmployeeLoaded(employee: Employee)

        fun onDataNotAvailable()
    }


    fun loadData(loadEmployeeCallback: LoadEmployeeCallback)

    fun findEmployeeByName(firstName: String, lastName: String, getEmployeeCallback: GetEmployeeCallback)

    fun findEmployeeByEmail(email: String, getEmployeeCallback: GetEmployeeCallback)

    fun findMeAsEmployee(getEmployeeCallback: GetEmployeeCallback)

    fun saveEmployee(employee: Employee)

    fun saveEmployees(employees: MutableList<Employee>)
}