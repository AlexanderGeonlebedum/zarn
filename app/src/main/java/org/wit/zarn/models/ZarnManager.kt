package org.wit.zarn.models

import androidx.lifecycle.MutableLiveData
import org.wit.zarn.api.ZarnClient
import org.wit.zarn.api.ZarnWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

//var lastId = 0L
//
//internal fun getId(): Long {
//    return lastId++
//}

object ZarnManager : ZarnStore{

//    private var zarns = ArrayList<ZarnModel>()

    override fun findAll(zarnsList: MutableLiveData<List<ZarnModel>>) {
        val call = ZarnClient.getApi().findall()

        call.enqueue(object :Callback<List<ZarnModel>>{
            override fun onResponse(call: Call<List<ZarnModel>>,
            response: Response<List<ZarnModel>>
            ){
                zarnsList.value = response.body() as ArrayList <ZarnModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }
            override fun onFailure(call: Call<List<ZarnModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, zarnsList: MutableLiveData<List<ZarnModel>>) {

        val call = ZarnClient.getApi().findall(email)

        with(call) {

            enqueue(object : Callback<List<ZarnModel>> {
                override fun onResponse(call: Call<List<ZarnModel>>,
                                        response: Response<List<ZarnModel>>
                ){
                    zarnsList.value = response.body() as ArrayList<ZarnModel>
                    Timber.i("Retrofit findAll() = ${response.body()}")
                }

                override fun onFailure(call: Call<List<ZarnModel>>, t: Throwable) {
                    Timber.i("Retrofit findAll() Error : $t.message")
                }

            })
        }
    }

    override fun findById(email: String, id: String, zarn: MutableLiveData<ZarnModel>)   {

        val call = ZarnClient.getApi().get(email,id)

        call.enqueue(object : Callback<ZarnModel> {
            override fun onResponse(call: Call<ZarnModel>, response: Response<ZarnModel>) { //
                zarn.value = response.body() as ZarnModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<ZarnModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create (zarn: ZarnModel){
        val call = ZarnClient.getApi().post(zarn.email,zarn)
        Timber.i("Retrofit ${call.toString()}")

        call.enqueue(object : Callback<ZarnWrapper>{
            override fun onResponse(call: Call<ZarnWrapper>,
                                    response: Response<ZarnWrapper>) {
                val zarnWrapper = response.body()
                if (zarnWrapper !=null){
                    Timber.i("Retrofit ${zarnWrapper.message}")
                    Timber.i("Retrofit ${zarnWrapper.data.toString()}")
                }
            }
            override fun onFailure(call: Call<ZarnWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
                Timber.i("Retrofit create Error : $t.message")
            }

        })

//        zarn.id = getId()
//        zarns.add(zarn)
//        logAll()
    }

    override fun delete(email: String, id: String) {
        val call =ZarnClient.getApi().delete(email, id)

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

    override fun update(email: String,id: String, zarn: ZarnModel) {

        val call = ZarnClient.getApi().put(email,id,zarn)

        call.enqueue(object : Callback<ZarnWrapper> {
            override fun onResponse(call: Call<ZarnWrapper>,
                                    response: Response<ZarnWrapper>
            ) {
                val zarnWrapper = response.body()
                if (zarnWrapper != null) {
                    Timber.i("Retrofit Update ${zarnWrapper.message}")
                    Timber.i("Retrofit Update ${zarnWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<ZarnWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }

//    fun logAll(){
//        Timber.v("** Zarns List **")
//        zarns.forEach { Timber.v("Zarn ${it}") }
//    }
}