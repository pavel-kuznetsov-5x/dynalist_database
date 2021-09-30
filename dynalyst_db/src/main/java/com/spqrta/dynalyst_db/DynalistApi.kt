package com.spqrta.dynalyst_db

import retrofit2.http.*

interface DynalistApi {

    @POST("doc/edit")
    suspend fun edit(
        @Body body: EditBody,
    ): DynalistResponse

    @POST("doc/read")
    suspend fun getDoc(
        @Body body: GetBody,
    ): GetResponse

    class DynalistResponse()
    class GetResponse(
        val nodes: List<DynalistNode>
    )

    class TokenBody(
        val token: String
    )

    class GetBody(
        val file_id: String,
        val token: String
    )

    class EditBody(
        val file_id: String,
        val changes: List<Change>,
        val token: String
    )

    open class Change(
        val action: String
    )

    class Edit(
        val node_id: String,
        val title: String,
        val note: String,
    ): Change(
        ACTION_EDIT
    )

    class Delete(
        val node_id: String,
    ): Change(
        ACTION_DELETE
    )

    class DynalistNode(
        val id: String
    )

    companion object {
        const val ACTION_EDIT = "edit"
        const val ACTION_DELETE = "delete"
    }

}