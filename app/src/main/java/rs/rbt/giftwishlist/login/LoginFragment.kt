package rs.rbt.giftwishlist.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.login_fragment.view.*
import rs.rbt.giftwishlist.R

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        initView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as LoginActivity).obtainViewModel()
        // TODO: Use the ViewModel
    }

    private fun initView(view: View) {
        view.buttonLogin.setOnClickListener {
            val username = view.editTextUsername.text.toString()
            val password = view.editTextPassword.text.toString()
            viewModel.loginUser(username, password)
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

}
