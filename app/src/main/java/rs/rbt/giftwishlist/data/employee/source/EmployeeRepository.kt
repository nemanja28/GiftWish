package rs.rbt.giftwishlist.data.employee.source

import androidx.lifecycle.LiveData
import rs.rbt.giftwishlist.data.employee.Employee

/**
 * Created by Nemanja Stosic on 1/22/19.
 */
class EmployeeRepository(
    val localEmployeeDataSource: EmployeeDataSource,
    val remoteEmployeeDataSource: EmployeeDataSource
) : EmployeeDataSource {

    override fun loadData(loadEmployeeCallback: EmployeeDataSource.LoadEmployeeCallback) {
        //TODO: Add implementation for remote data source
        localEmployeeDataSource.loadData(loadEmployeeCallback)
    }

    override fun findEmployeeByName(firstName: String, lastName: String, getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
        //TODO: Add implementation for remote data source
        return localEmployeeDataSource.findEmployeeByName(firstName, lastName, getEmployeeCallback)
    }

    override fun findEmployeeByEmail(email: String, getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
        //TODO: Add implementation for remote data source
        return localEmployeeDataSource.findEmployeeByEmail(email, getEmployeeCallback)
    }

    override fun findMeAsEmployee(getEmployeeCallback: EmployeeDataSource.GetEmployeeCallback) {
        //TODO: Add implementation for remote data source
        return localEmployeeDataSource.findMeAsEmployee(getEmployeeCallback)
    }

    override fun saveEmployee(employee: Employee) {
        //TODO: Add implementation for remote data source
        localEmployeeDataSource.saveEmployee(employee)
    }

    override fun saveEmployees(employees: MutableList<Employee>) {
        //TODO: Add implementation for remote data source
        localEmployeeDataSource.saveEmployees(employees)
    }

    companion object {
        private var INSTANCE: EmployeeRepository? = null

        @JvmStatic
        fun getInstance(employeeLocalDataSource: EmployeeDataSource, employeeRemoteDataSource: EmployeeDataSource) = INSTANCE
            ?: synchronized(EmployeeRepository::class.java) {
                INSTANCE ?: EmployeeRepository(
                    employeeLocalDataSource,
                    employeeRemoteDataSource
                ).also { INSTANCE = it }
            }
    }
}