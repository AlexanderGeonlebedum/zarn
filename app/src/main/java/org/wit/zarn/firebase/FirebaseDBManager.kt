package org.wit.zarn.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.zarn.models.ZarnModel
import org.wit.zarn.models.ZarnStore
import timber.log.Timber

object FirebaseDBManager : ZarnStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance("https://zarn-82adb-default-rtdb.firebaseio.com").reference

    override fun findAll(zarnsList: MutableLiveData<List<ZarnModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, zarnsList: MutableLiveData<List<ZarnModel>>) {
        Timber.i("in DBManagre")
        Timber.i(zarnsList.toString())

        database.child("user-zarns").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Zarn error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ZarnModel>()
                    val children = snapshot.children

                    children.forEach {
                        Timber.i(it.getValue().toString())
                        val zarn = it.getValue(ZarnModel::class.java)
                        localList.add(zarn!!)
                    }
                    database.child("user-zarns").child(userid)
                        .removeEventListener(this)

                    zarnsList.value = localList
                }
            })
    }

    override fun findById(userid: String, zarnid: String, zarn: MutableLiveData<ZarnModel>) {

        database.child("user-zarns").child(userid)
            .child(zarnid).get().addOnSuccessListener {
                zarn.value = it.getValue(ZarnModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, zarn: ZarnModel) {
        Timber.i("Firebase DB Reference : $database")
        Timber.i("Zarn in Creat")
        Timber.i(zarn.toString())
        val uid = firebaseUser.value!!.uid
        val key = database.child("zarns").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        zarn.uid = key
        val zarnValues = zarn.toMap()


        val childAdd = HashMap<String, Any>()
        childAdd["/zarns/$key"] = zarnValues
        childAdd["/user-zarns/$uid/$key"] = zarnValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, zarnid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/zarns/$zarnid"] = null
        childDelete["/user-zarns/$userid/$zarnid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, zarnid: String, zarn: ZarnModel) {

        val zarnValues = zarn.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["zarns/$zarnid"] = zarnValues
        childUpdate["user-zarns/$userid/$zarnid"] = zarnValues

        database.updateChildren(childUpdate)
    }
}