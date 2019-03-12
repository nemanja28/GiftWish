package rs.rbt.giftwishlist.data.employee.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rs.rbt.giftwishlist.data.employee.Employee
import rs.rbt.giftwishlist.data.employee.source.EmployeeDataSource

/**
 * Created by Nemanja Stosic on 1/22/19.
 */
object EmployeeRemoteDataSource : EmployeeDataSource {

    override fun loadData() {
        
    }

    override fun getEmployees(): LiveData<List<Employee>> {
        return MutableLiveData()
    }

    override fun findEmployeeByName(firstName: String, lastName: String): Employee? {
        return null
    }

    override fun findEmployeeByEmail(email: String): Employee? {
        return null
    }

    override fun findMeAsEmployee(): Employee? {
        return null
    }

    override fun saveEmployee(employee: Employee) {
        
    }

    override fun saveEmployees(employees: MutableList<Employee>) {
        
    }
}