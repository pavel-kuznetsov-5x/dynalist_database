package com.spqrta.dynalyst_db

import retrofit2.http.*

interface DynalistApi {

    @POST("doc/edit")
    suspend fun edit(
        @Body body: EditBody,
    ): DynalistResponse

    class DynalistResponse()

    class TokenBody(
        val token: String = ""
    )

    class EditBody(
        val file_id: String,
        val changes: List<Change>,
        val token: String = ""
    )

    open class Change(
        val action: String
    )

    class Edit(
        action: String,
        val node_id: String,
        val title: String,
        val note: String,
    ): Change(
        action
    )

}