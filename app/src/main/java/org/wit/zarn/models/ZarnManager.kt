package org.wit.zarn.models

import androidx.lifecycle.MutableLiveData
import org.wit.zarn.api.ZarnClient
import org.wit.zarn.api.ZarnWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object ZarnManager : ZarnStore{

    private val zarns = ArrayList<ZarnModel>()

    override fun findAll(zarnsList: MutableLiveData<List<ZarnModel>>) {
        val call = ZarnClient.getApi().getall()

        call.enqueue(object :Callback<List<ZarnModel>>{
            override fun onResponse(call: Call<List<ZarnModel>>,
            response: Response<List<ZarnModel>>
            ){
                zarnsList.value = response.body() as ArrayList <ZarnModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }
            override fun onFailure(call: Call<List<ZarnModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id: String): ZarnModel? {
        val foundZarn: ZarnModel? = zarns.find {it._id == id}
        return foundZarn
    }

    override fun create (zarn: ZarnModel){
        val call = ZarnClient.getApi().post(zarn)

        call.enqueue(object : Callback<ZarnWrapper>{
            override fun onResponse(call: Call<ZarnWrapper>, response: Response<ZarnWrapper>) {
                val zarnWrapper = response.body()
                if (zarnWrapper !=null){
                    Timber.i("Retrofit ${zarnWrapper.message}")
                    Timber.i("Retrofit ${zarnWrapper.data.toString()}")
                }
            }
            override fun onFailure(call: Call<ZarnWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }

        })

//        zarn.id = getId()
//        zarns.add(zarn)
//        logAll()
    }

    override fun delete(id: String) {
        val call =ZarnClient.getApi().delete(id)

        call.enqueue(object : Callback<ZarnWrapper>{
            override fun onResponse(call: Call<ZarnWrapper>, response: Response<ZarnWrapper>) {
                val zarnWrapper = response.body()
                if (zarnWrapper != null){
                    Timber.i("Retrofit Delete ${zarnWrapper.message}")
                    Timber.i("Retrofit Delete ${zarnWrapper.data.toString()}")
                }
            }
            override fun onFailure(call: Call<ZarnWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }

        })
    }

    fun logAll(){
        Timber.v("** Zarns List **")
        zarns.forEach { Timber.v("Zarn ${it}") }
    }
}