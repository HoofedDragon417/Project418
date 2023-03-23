package com.example.project418.common

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router

abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes resLayoutId: Int) : super(resLayoutId)

    protected var router: Router? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        router = tryToGetActivityRouter()
    }

    private fun tryToGetActivityRouter(): Router? {
        return (activity as? MainActivity)?.router
    }

}