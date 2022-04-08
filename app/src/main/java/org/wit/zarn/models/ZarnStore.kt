package org.wit.zarn.models

interface ZarnStore {
    fun findAll() : List<ZarnModel>
    fun findById(id: Long) : ZarnModel?
    fun create(donation: ZarnModel)
}