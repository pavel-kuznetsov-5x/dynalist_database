package com.spqrta.dynalyst_db

import com.google.gson.annotations.SerializedName
import com.spqrta.dynalyst_db.utility.pure.nullIfEmpty
import retrofit2.http.*

interface DynalistApi {

    @POST("doc/edit")
    suspend fun edit(
        @Body body: EditBody,
    ): EditResponse

    @POST("doc/read")
    suspend fun getDoc(
        @Body body: GetBody,
    ): GetResponse

    class EditResponse(
        val new_node_ids: List<String>?,
        @SerializedName("_msg")
        val errorMessage: String?
    )
    class GetResponse(
        val nodes: List<DynalistNode>,
        @SerializedName("_msg")
        val errorMessage: String?
    )

    class InsertResponse(

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
        val content : String?,
        val note: String?,
    ): Change(
        "edit"
    )

    class Delete(
        val node_id: String,
    ): Change(
        "delete"
    )

    class Insert(
        val parent_id: String,
        val content: String,
        val note: String?,
        val index: Int = 0,
    ): Change(
        "insert"
    )

    class DynalistNode(
        val id: String,
        @SerializedName("note")
        val _note: String?
    ) {
        val note: String?
            get() = _note.nullIfEmpty()
    }

}