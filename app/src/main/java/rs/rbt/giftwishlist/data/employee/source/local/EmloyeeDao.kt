package rs.rbt.giftwishlist.data.employee.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rs.rbt.giftwishlist.data.employee.Employee


/**
 * Created by Nemanja Stosic on 11/29/18.
 */
@Dao
interface EmloyeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): LiveData<List<Employee>>

    @Query("SELECT * FROM employee WHERE uid IN (:employeeIds)")
    fun loadAllByIds(employeeIds: IntArray): List<Employee>

    @Query("SELECT * FROM employee WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Employee?

    @Query("SELECT * FROM employee WHERE email = :email LIMIT 1")
    fun findByEmail(email: String): Employee?

    @Insert
    fun insertAll(vararg employees: Employee)

    @Delete
    fun delete(user: Employee)

}