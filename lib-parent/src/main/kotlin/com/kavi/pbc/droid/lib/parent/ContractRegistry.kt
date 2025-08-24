package com.kavi.pbc.droid.lib.parent

class ContractRegistry {
    private val contractRegistry = mutableMapOf<String, CommonContract>()

    fun registerContract(contractName: String, contract: CommonContract) {
        contractRegistry.put(contractName, contract)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T>getContract(contractName: String): T {
        return contractRegistry[contractName] as T
    }
}