package com.example.memberlist.data

import android.content.Context
import android.content.SharedPreferences
import com.example.memberlist.model.Member
import com.example.memberlist.util.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveMemberList(memberList: List<Member>) {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(memberList)
        editor.putString(Constants.KEY_MEMBER_LIST, json)
        editor.apply()
    }

    fun getMemberList(): List<Member> {
        val json = sharedPreferences.getString(Constants.KEY_MEMBER_LIST, null)
        return if (json != null) {
            val type = object : TypeToken<List<Member>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}