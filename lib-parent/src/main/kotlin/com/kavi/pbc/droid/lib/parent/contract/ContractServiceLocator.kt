package com.kavi.pbc.droid.lib.parent.contract

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

object ContractServiceLocator {

    private val contractMap = ConcurrentHashMap<String, () -> CommonContract>()

    fun <C: CommonContract, I: C, T: KClass<C>> register(contract: T, provider: () -> I) {
        verifyNamed(contract = contract)
        contractMap[contract.qualifiedName!!] = provider
    }

    @Suppress("UNCHECKED_CAST")
    fun <C: CommonContract, T: KClass<C>> locate(contract: T): C {
        verifyNamed(contract = contract)
        return contractMap[contract.qualifiedName]!!() as C
    }

    private fun <C: CommonContract, T: KClass<C>> verifyNamed(contract: T) {
        if (contract.qualifiedName.isNullOrEmpty()) throw IllegalArgumentException("Qualified Contract Class Expected. Class should inherit from 'CommonContract'")
    }
}