package com.fehtystudio.twitterproject
import com.google.gson.annotations.SerializedName

data class ApiListResultData(
    @SerializedName("name") var name: String,
    @SerializedName("topLevelDomain") var topLevelDomain: List<String>,
    @SerializedName("alpha2Code") var alpha2Code: String,
    @SerializedName("alpha3Code") var alpha3Code: String,
    @SerializedName("callingCodes") var callingCodes: List<String>,
    @SerializedName("capital") var capital: String,
    @SerializedName("altSpellings") var altSpellings: List<String>,
    @SerializedName("region") var region: String,
    @SerializedName("subregion") var subregion: String,
    @SerializedName("population") var population: Int,
    @SerializedName("latlng") var latlng: List<Double>,
    @SerializedName("demonym") var demonym: String,
    @SerializedName("area") var area: Double,
    @SerializedName("gini") var gini: Any,
    @SerializedName("timezones") var timezones: List<String>,
    @SerializedName("borders") var borders: List<String>,
    @SerializedName("nativeName") var nativeName: String,
    @SerializedName("numericCode") var numericCode: String,
    @SerializedName("currencies") var currencies: List<Currency>,
    @SerializedName("languages") var languages: List<Language>,
    @SerializedName("translations") var translations: Translations,
    @SerializedName("flag") var flag: String,
    @SerializedName("regionalBlocs") var regionalBlocs: List<RegionalBloc>,
    @SerializedName("cioc") var cioc: String
)

data class RegionalBloc(
    @SerializedName("acronym") var acronym: String,
    @SerializedName("name") var name: String,
    @SerializedName("otherAcronyms") var otherAcronyms: List<Any>,
    @SerializedName("otherNames") var otherNames: List<String>
)

data class Translations(
    @SerializedName("de") var de: String,
    @SerializedName("es") var es: String,
    @SerializedName("fr") var fr: String,
    @SerializedName("ja") var ja: String,
    @SerializedName("it") var it: String,
    @SerializedName("br") var br: String,
    @SerializedName("pt") var pt: String,
    @SerializedName("nl") var nl: String,
    @SerializedName("hr") var hr: String,
    @SerializedName("fa") var fa: String
)

data class Language(
    @SerializedName("iso639_1") var iso6391: String,
    @SerializedName("iso639_2") var iso6392: String,
    @SerializedName("name") var name: String,
    @SerializedName("nativeName") var nativeName: String
)

data class Currency(
    @SerializedName("code") var code: String,
    @SerializedName("name") var name: Any,
    @SerializedName("symbol") var symbol: Any
)