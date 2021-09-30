package com.spqrta.dynalyst_db

import com.spqrta.dynalyst_db.base.network.BaseRequestManager

object DynalistApiClient: BaseRequestManager() {

    override fun getBaseUrl() = "https://dynalist.io/api/v1/"

    val api = retrofit.create(DynalistApi::class.java)

}