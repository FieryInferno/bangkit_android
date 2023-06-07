package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.DiseaseModel
import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.example.bangkitandroid.data.remote.model.MetaModel
import com.google.gson.annotations.SerializedName

data class DiseaseHistoryResponse(

	@field:SerializedName("result")
	val result: List<HistoryModel>,

	@field:SerializedName("meta")
	val meta: MetaModel
)
