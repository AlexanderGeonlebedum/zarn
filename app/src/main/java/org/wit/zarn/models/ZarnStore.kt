package org.wit.zarn.models

import androidx.lifecycle.MutableLiveData


interface ZarnStore {
//    fun findAll(zarnsList: MutableLiveData<List<ZarnModel>>)
//    fun findById(id: String) : ZarnModel?
//    fun create(zarn: ZarnModel)
//    fun delete(id: String)
    fun findAll(zarnsList: MutableLiveData<List<ZarnModel>>)
    fun findAll(email:String,
                zarnsList:
                MutableLiveData<List<ZarnModel>>)
    fun findById(email:String, id: String,
                 zarn: MutableLiveData<ZarnModel>)
    fun create(zarn: ZarnModel)
    fun delete(email:String,id: String)
    fun update(email:String,id: String,zarn: ZarnModel)
}