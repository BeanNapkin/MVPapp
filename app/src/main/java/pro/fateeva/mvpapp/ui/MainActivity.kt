package pro.fateeva.mvpapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import pro.fateeva.mvpapp.R
import pro.fateeva.mvpapp.app
import pro.fateeva.mvpapp.databinding.ActivityMainBinding
import pro.fateeva.mvpapp.domain.Response

class MainActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: MainViewModel? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = restoreViewModel()

        binding.logInButton.setOnClickListener {
            viewModel?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.signUpButton.setOnClickListener {
            viewModel?.onSignUp(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.forgetPasswordButton.setOnClickListener {
            viewModel?.onForgetPassword(
                binding.loginEditText.text.toString()
            )
        }

        viewModel?.response?.subscribe(handler) { response ->
            if (response != null) {
                setResponse(response.response)
            }
        }

        viewModel?.remindPasswordResponse?.subscribe(handler){
            binding.responseTextView.setText(getString(R.string.your_password_is, it))
        }

        viewModel?.isShouldShowProgress?.subscribe(handler){
            if (it == true){
                showProgress()
            } else {
                hideProgress()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.response?.unsubscribeAll()
    }

    private fun restoreViewModel(): MainViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? MainViewModel
        return viewModel ?: MainViewModel(app.loginUsecase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }

    @MainThread
    override fun setResponse(response: Int) {
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