package com.cenming.composedemo.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Cenming on 2019/7/22 0022.
 * 功能:
 */
class ViewModelFactory constructor(private val mContext: Application, private val map : HashMap<String, *>?)
    : ViewModelProvider.Factory {
    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        /*if(modelClass.isAssignableFrom(NavigationViewModel::class.java)){
            return NavigationViewModel(this.mContext) as T
        }*/
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}