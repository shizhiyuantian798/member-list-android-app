package com.example.memberlist.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.memberlist.R
import com.example.memberlist.data.SharedPreferencesManager
import com.example.memberlist.model.Member
import com.example.memberlist.ui.form.FormActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var memberList: ArrayList<Member>
    private lateinit var adapter: MemberAdapter
    private lateinit var addButton: Button
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesManager = SharedPreferencesManager(this)

        listView = findViewById(R.id.list_view)
        addButton = findViewById(R.id.add_button)

        loadMembers()
        setupListView()
        setupAddButton()
    }

    private fun loadMembers() {
        memberList = ArrayList(sharedPreferencesManager.getMemberList())
    }

    private fun setupListView() {
        adapter = MemberAdapter(this, memberList)
        listView.adapter = adapter
    }

    private fun setupAddButton() {
        addButton.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateListView() {
        loadMembers()
        adapter.clear()
        adapter.addAll(memberList)
        adapter.notifyDataSetChanged()
    }

    fun deleteMember(member: Member) {
        memberList.remove(member)
        sharedPreferencesManager.saveMemberList(memberList)
        updateListView()
    }

    override fun onResume() {
        super.onResume()
        updateListView()
    }
}