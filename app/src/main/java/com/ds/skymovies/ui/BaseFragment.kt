package com.ds.skymovies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.ds.skymovies.util.ProgressDialog

abstract class BaseFragment : Fragment() {

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = ProgressDialog(requireContext())
    }

    fun showProgress() {
        dialog?.show()
    }

    fun cancelProgress() {
        if (dialog!!.isShowing) dialog?.cancel()
    }

    /**
     * Use this method to bind an view element to a liveData
     *
     * Example:
     * viewModel.liveData.observe {
     *      view.doSomething(it)
     * }
     *
     * @param block The code executed on liveData change event
     */
    protected fun <T> LiveData<T>.observe(block: (t: T) -> Unit) {
        observe(viewLifecycleOwner) {
            block(it)
        }
    }
}


