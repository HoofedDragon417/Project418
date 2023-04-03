package com.example.project418.common

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes resLayoutId: Int) : super(resLayoutId)

}