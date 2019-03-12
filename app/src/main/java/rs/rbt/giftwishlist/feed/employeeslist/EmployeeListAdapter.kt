package rs.rbt.giftwishlist.feed.employeeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_item_employee.view.*
import org.jetbrains.anko.AnkoLogger
import rs.rbt.giftwishlist.R
import rs.rbt.giftwishlist.data.employee.Employee
import rs.rbt.giftwishlist.util.DateHelper

/**
 * Created by Nemanja Stosic on 11/30/18.
 */
class EmployeeListAdapter : RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>(), AnkoLogger {

    private var employees: MutableList<Employee>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EmployeeViewHolder(
            inflater.inflate(R.layout.rv_item_employee, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return employees?.size ?: 0
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employeesFinal = employees ?: return
        val gift = employeesFinal[position]
        holder.setData(gift)
    }

    fun setEmployeeList(newEmployeeList: List<Employee>?) {
        if (employees == null) {
            employees = newEmployeeList?.toMutableList()
            notifyItemRangeInserted(0, newEmployeeList?.size ?: 0)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return employees?.size ?: 0
                }

                override fun getNewListSize(): Int {
                    return newEmployeeList?.size ?: 0
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return employees!![oldItemPosition].uid == newEmployeeList?.get(newItemPosition)?.uid
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newEmployee = newEmployeeList?.get(newItemPosition)
                    val oldEmployee = employees?.get(oldItemPosition)
                    return (newEmployee?.uid == oldEmployee?.uid
                            && newEmployee?.firsName == oldEmployee?.firsName
                            && newEmployee?.lastName == oldEmployee?.lastName
                            && newEmployee?.email == oldEmployee?.email
                            && newEmployee?.birthday == oldEmployee?.birthday)
                }
            })
            employees = newEmployeeList?.toMutableList()
            result.dispatchUpdatesTo(listUpdateCallback)
        }
    }


    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(position, count)
        }
    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(employee: Employee) {
            itemView.textViewEmployeeFullName.text = "${employee.firsName} ${employee.lastName}"
            itemView.textViewBirthday.text = DateHelper.formatNextBirtday(employee.birthday)
            //itemView.textViewWishes.text = "7"
        }
    }
}
