package com.spqrta.dynalyst_db_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spqrta.dynalyst_db.DynalistDatabase
import com.spqrta.dynalyst_db.utility.Logg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DynalistDatabase(
            apiKey = BuildConfig.VERSION_CODE.toString(),
            rootNodeId = "wyvZlLM6m5lqOyZixI4tLbLr"
        )

        GlobalScope.launch {
            db.init()
            Logg.d(db.getChildren("wyvZlLM6m5lqOyZixI4tLbLr"))
        }
    }
}