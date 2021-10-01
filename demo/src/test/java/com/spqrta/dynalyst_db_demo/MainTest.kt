package com.spqrta.dynalyst_db_demo

import com.spqrta.dynalyst_db.DynalistDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MainTest {

    @Test
    fun test() {
        val db = DynalistDatabase(
            apiKey = BuildConfig.DYNALIST_API_KEY.toString(),
            documentId = "wyvZlLM6m5lqOyZixI4tLbLr"
        )

        runBlocking {
            db.init()
        }
    }

}