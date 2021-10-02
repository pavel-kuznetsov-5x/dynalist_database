package com.spqrta.dynalyst_db

@Suppress("IfThenToElvis")
class DynalistDatabase(
    val apiKey: String,
    val documentId: String,
) {
    private val api by lazy { DynalistApiClient.api }
    private lateinit var dataNodeId: String
    private var initialized = false

    suspend fun init() {
        val nodes = getChildren(documentId).nodes.also {
            println(it.map { it.id })
        }
        val root = nodes.first { it.id == ROOT }
        val dataNode = nodes.firstOrNull { it.id == root.note }
        dataNodeId = if (root.note == null || dataNode == null) {
            _insert(ROOT, "Data").first().also {
                _edit(ROOT, note = it)
            }
        } else {
            root.note!!
        }
        initialized = true
    }

    suspend fun edit(nodeId: String? = null, content: String? = null, note: String? = null) {
        check(initialized) { "db is not initialized" }
        _edit(nodeId = nodeId ?: dataNodeId, content = content, note = note)
    }

    private suspend fun _edit(nodeId: String, content: String? = null, note: String? = null) {
        api.edit(
            DynalistApi.EditBody(
                file_id = documentId,
                changes = listOf(
                    DynalistApi.Edit(
                        node_id = nodeId,
                        content = content,
                        note = note
                    )
                ),
                token = apiKey
            )
        ).apply {
            if(errorMessage != null) {
                throw DynalistApiError(errorMessage)
            }
        }
    }

    suspend fun insert(content: String, note: String? = null, nodeId: String? = null): List<String> {
        check(initialized) { "db is not initialized" }
        return _insert(nodeId = nodeId  ?: dataNodeId, content = content, note = note)
    }

    private suspend fun _insert(
        nodeId: String,
        content: String,
        note: String? = null
    ): List<String> {
        return api.edit(
            DynalistApi.EditBody(
                file_id = documentId,
                changes = listOf(
                    DynalistApi.Insert(
                        parent_id = nodeId,
                        content = content,
                        note = note
                    )
                ),
                token = apiKey
            )
        ).apply {
            if(errorMessage != null) {
                throw DynalistApiError(errorMessage)
            }
        }.new_node_ids!!
    }

    suspend fun delete(nodeId: String) {
        api.edit(
            DynalistApi.EditBody(
                file_id = documentId,
                changes = listOf(
                    DynalistApi.Delete(
                        node_id = nodeId,
                    )
                ),
                token = apiKey
            )
        ).apply {
            if(errorMessage != null) {
                throw DynalistApiError(errorMessage)
            }
        }
    }

    suspend fun getData(): DynalistApi.DynalistNode {
        check(initialized) { "db is not initialized" }
        return getChildren(documentId).nodes.first {
            it.id == dataNodeId
        }
    }

    private suspend fun getChildren(fileId: String): DynalistApi.GetResponse {
        return api.getDoc(
            DynalistApi.GetBody(
                file_id = fileId,
                token = apiKey
            )
        ).apply {
            if(errorMessage != null) {
                throw DynalistApiError(errorMessage)
            }
        }
    }

    companion object {
        const val ROOT = "root"
    }

}

class DynalistApiError(message: String): Exception(message)