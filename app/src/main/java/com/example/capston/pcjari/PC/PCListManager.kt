package com.example.capston.pcjari.PC

import com.example.capston.pcjari.Util.GettingPHP
import com.example.capston.pcjari.Activity.A2_MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class PCListManager {
    var gPHP = GettingPHP()
    val gson = Gson()

    /**
     * 주소로 검색
     */
    fun getPCListByAddress(gu: String, dong: String, name: String?) : ArrayList<PCListItem> {
        var url = A2_MainActivity.server + "pclist_search.php?"
        url += "code=0&gu=$gu&dong=$dong"

        if(name != null)
            url += "&namesearch=$name"

        gPHP = GettingPHP()
        val jsonData = gPHP.execute(url).get()

        if(jsonData != null)
            return parsePCList(jsonData)

        return ArrayList<PCListItem>()
    }

    /**
     * 내 위치로부터 검색
     */
    fun getPCListByGps(lat : Double, lng : Double, dist : String, name : String?) : ArrayList<PCListItem> {
        var url = A2_MainActivity.server + "pclist_search.php?"
        url += "code=1&lat=$lat&lng=$lng&dist=$dist"

        if(name != null)
            url += "&namesearch=$name"

        gPHP = GettingPHP()
        val jsonData = gPHP.execute(url).get()

        if(jsonData != null)
            return parsePCList(jsonData)

        return ArrayList<PCListItem>()
    }


    /**
     * 즐겨찾기로 검색
     */
    fun getPCListByFavorite(favoriteList : ArrayList<Int>, name : String?) : ArrayList<PCListItem> {
        var favorite = ""
        for (i in favoriteList.indices) {
            if (i != 0) favorite += "," + favoriteList[i] else favorite = favoriteList[i].toString()
        }

        var url = A2_MainActivity.server + "pclist_search.php?"
        url += "code=2&favorite=$favorite"

        if(name != null)
            url += "&namesearch=$name"

        gPHP = GettingPHP()
        val jsonData = gPHP.execute(url).get()

        if(jsonData != null)
            return parsePCList(jsonData)

        return ArrayList<PCListItem>()
    }


    /**
     * PC방 데이터 파싱
     */
    private fun parsePCList(jsonData: String) : ArrayList<PCListItem> {
        val json = JSONObject(jsonData)

        if(json.has("status")) {
            if(json.getString("status") == "OK") {
                val data = json.getString("results")

                return gson.fromJson(data, object : TypeToken<ArrayList<PCListItem>?>() {}.type)
            }
        }

        return ArrayList<PCListItem>()
    }
}