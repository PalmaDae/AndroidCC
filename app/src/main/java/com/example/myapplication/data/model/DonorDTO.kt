package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class DonorResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<DonorPointDto>
)

data class DonorPointDto(
    val id: Int,
    val title: String,
    val address: String,
    val city: City?
) {
    fun toModel() = DonorPointModel(
        id = id,
        titleOfPoint = title,
        addressOfPoint = address,
        city = city
    )
}

data class DonorPointDetailDto(
    val id: Int,
    val title: String,
    val address: String,
    val city: City?,
    val schedule: List<ScheduleItem>? = emptyList(),
    val phoneNumbers: List<PhoneNumber>? = emptyList(),
    val acceptedComponents: AcceptedComponents? = null,
    val site: String? = null,
    val email: String? = null,
    val parserUrl: String? = null,
    @SerializedName("o_plus") val oPlus: String? = null,
    @SerializedName("o_minus") val oMinus: String? = null,
    @SerializedName("a_plus") val aPlus: String? = null,
    @SerializedName("a_minus") val aMinus: String? = null,
    @SerializedName("b_plus") val bPlus: String? = null,
    @SerializedName("b_minus") val bMinus: String? = null,
    @SerializedName("ab_plus") val abPlus: String? = null,
    @SerializedName("ab_minus") val abMinus: String? = null,
) {
    fun toDetailModel(): DonorPointDetailModel {
        val bloodStatusMap = mapOf(
            "O+" to (oPlus ?: "no_need"),
            "O-" to (oMinus ?: "no_need"),
            "A+" to (aPlus ?: "no_need"),
            "A-" to (aMinus ?: "no_need"),
            "B+" to (bPlus ?: "no_need"),
            "B-" to (bMinus ?: "no_need"),
            "AB+" to (abPlus ?: "no_need"),
            "AB-" to (abMinus ?: "no_need")
        )
        return DonorPointDetailModel(
            title = title,
            address = address,
            schedule = schedule ?: emptyList(),
            phoneNumbers = phoneNumbers ?: emptyList(),
            acceptedComponents = acceptedComponents ?: AcceptedComponents(false, false, false, false, false),
            bloodStatus = bloodStatusMap,
            site = site,
            email = email,
            parserUrl = parserUrl
        )
    }
}

data class DonorPointModel(
    val id: Int,
    val titleOfPoint: String,
    val addressOfPoint: String,
    val city: City? = null
)

data class City(
    val title: String
)

data class DonorPointDetailModel(
    val title: String,
    val address: String,
    val schedule: List<ScheduleItem> = emptyList(),
    val phoneNumbers: List<PhoneNumber> = emptyList(),
    val acceptedComponents: AcceptedComponents = AcceptedComponents(false,false,false,false,false),
    val bloodStatus: Map<String, String> = emptyMap(),
    val site: String? = null,
    val email: String? = null,
    val parserUrl: String? = null
)

data class ScheduleItem(
    val dow: String,
    val start: String,
    val end: String
)

data class PhoneNumber(
    val phone: String,
    val comment: String
)

data class AcceptedComponents(
    val blood: Boolean,
    val plasma: Boolean,
    val platelets: Boolean,
    val erythrocytes: Boolean,
    val leukocytes: Boolean
)