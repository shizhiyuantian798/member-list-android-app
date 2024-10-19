package com.example.memberlist

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memberlist.model.Member
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataPersistenceTest {

    @Test
    fun testSaveAndRetrieveMemberList() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val sharedPreferences =
            context.getSharedPreferences("member_list", android.content.Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.clear().apply()

        val memberList = listOf(
            Member(1, "田中 太郎", 30, "https://example.com"),
            Member(2, "田中 花子", 25, "https://example.org")
        )

        editor.putString("member_list", Gson().toJson(memberList))
        editor.apply()

        val retrievedListJson = sharedPreferences.getString("member_list", "")
        val retrievedList = Gson().fromJson(retrievedListJson, Array<Member>::class.java)?.toList()

        assertEquals(memberList, retrievedList)
    }
}