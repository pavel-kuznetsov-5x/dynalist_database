package com.spqrta.dynalyst_db

class DynalistDatabase(
    val apiKey: String,
    val rootNodeId: String,
) {
    private val api by lazy { DynalistApiClient.api }
    private var initialized = false

    suspend fun init() {
        getChildren(rootNodeId).forEach {
            delete(it.id)
        }
        initialized = true
    }

    suspend fun edit(nodeId: String, title: String, note: String) {
        check(initialized) { "db is not initialized" }
        api.edit(
            DynalistApi.EditBody(
                file_id = rootNodeId,
                changes = listOf(
                    DynalistApi.Edit(
                        node_id = nodeId,
                        title = title,
                        note = note
                    )
                ),
                token = apiKey
            )
        )
    }

    suspend fun delete(nodeId: String) {
        api.edit(
            DynalistApi.EditBody(
                file_id = rootNodeId,
                changes = listOf(
                    DynalistApi.Delete(
                        node_id = nodeId,
                    )
                ),
                token = apiKey
            )
        )
    }

    suspend fun getChildren(nodeId: String): List<DynalistApi.DynalistNode> {
        return api.getDoc(
            DynalistApi.GetBody(
                file_id = rootNodeId,
                token = apiKey
            )
        ).nodes.filter { it.id != "root" }
    }



}