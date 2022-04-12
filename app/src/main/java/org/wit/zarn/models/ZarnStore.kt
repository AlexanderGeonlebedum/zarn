package org.wit.zarn.models

import androidx.lifecycle.MutableLiveData


interface ZarnStore {
    fun findAll(zarnsList: MutableLiveData<List<ZarnModel>>)
    fun findById(id: String) : ZarnModel?
    fun create(donation: ZarnModel)
    fun delete(id: String)
}