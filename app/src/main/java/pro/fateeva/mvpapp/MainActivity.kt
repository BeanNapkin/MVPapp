package pro.fateeva.mvpapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import pro.fateeva.mvpapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.logInButton.setOnClickListener {
            presenter?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.signUpButton.setOnClickListener {
            presenter?.onSignin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.forgetPasswordButton.setOnClickListener {
            presenter?.onForgetPassword(
                binding.loginEditText.text.toString()
            )
        }
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    @MainThread
    override fun setResponse(response: Int) {
        hideProgress()
        binding.responseTextView.setText(this.getString(response))
    }

    @MainThread
    override fun setResponse(response: Int, arg: String) {
        hideProgress()
        binding.responseTextView.setText(this.getString(response))
    }

    @MainThread
    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    @MainThread
    override fun hideProgress() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}