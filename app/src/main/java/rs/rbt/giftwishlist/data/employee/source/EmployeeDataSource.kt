package rs.rbt.giftwishlist.data.employee.source

import androidx.lifecycle.LiveData
import rs.rbt.giftwishlist.data.employee.Employee

/**
 * Created by Nemanja Stosic on 1/11/19.
 */
interface EmployeeDataSource {
    fun loadData()
    fun getEmployees(): LiveData<List<Employee>>
    fun findEmployeeByName(firstName: String, lastName: String): Employee?
    fun findEmployeeByEmail(email: String): Employee?
    fun findMeAsEmployee(): Employee?
    fun saveEmployee(employee: Employee)
    fun saveEmployees(employees: MutableList<Employee>)

}