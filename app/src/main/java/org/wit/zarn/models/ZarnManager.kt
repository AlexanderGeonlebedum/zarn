package org.wit.zarn.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object ZarnManager : ZarnStore{

    private val zarns = ArrayList<ZarnModel>()

    override fun findAll(): List<ZarnModel> {
        return zarns
    }

    override fun findById(id: Long): ZarnModel? {
        val foundZarn: ZarnModel? = zarns.find {it.id == id}
        return foundZarn
    }

    override fun create (zarn: ZarnModel){
        zarn.id = getId()
        zarns.add(zarn)
        logAll()
    }

    fun logAll(){
        Timber.v("** Zarns List **")
        zarns.forEach { Timber.v("Zarn ${it}") }
    }
}