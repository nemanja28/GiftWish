package rs.rbt.giftwishlist.feed.employeeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_employee_list.view.*
import kotlinx.coroutines.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import rs.rbt.giftwishlist.R
import rs.rbt.giftwishlist.data.employee.Employee
import rs.rbt.giftwishlist.feed.FeedActivity
import kotlin.coroutines.CoroutineContext

/**
 * Created by Nemanja Stosic on 11/30/18.
 */
class EmployeeListFragment : Fragment(), CoroutineScope, AnkoLogger {


    //region CoroutineScope
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job
    //endregion

    private lateinit var employeesViewModel: EmployeesViewModel
    private var employeeListAdapter: EmployeeListAdapter? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_employee_list, container, false)
        employeesViewModel = (activity as FeedActivity).obtainEmployeesViewModel()
        initView(view)
        return view
    }

    private fun initView(view: View) {
        employeeListAdapter = EmployeeListAdapter()
        view.recyclerViewEmployeeList.layoutManager = LinearLayoutManager(context)
        view.recyclerViewEmployeeList.adapter = employeeListAdapter
        subscribeUi(employeesViewModel.getAllEmployees())
    }

    private fun subscribeUi(liveData: LiveData<List<Employee>>) {
        // Update the list when the data changes
        liveData.observe(this, Observer { employeesList ->
            employeeListAdapter?.setEmployeeList(employeesList)
        })
    }


    companion object {
        fun newInstance(): EmployeeListFragment {
            val fragment = EmployeeListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}