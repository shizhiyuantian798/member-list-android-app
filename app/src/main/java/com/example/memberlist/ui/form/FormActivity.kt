package com.example.memberlist.ui.form

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.memberlist.R
import com.example.memberlist.data.SharedPreferencesManager
import com.example.memberlist.model.Member
import com.example.memberlist.util.FormValidator
import com.google.android.material.textfield.TextInputEditText

class FormActivity : AppCompatActivity() {
    private lateinit var memberNameInput: TextInputEditText
    private lateinit var memberAgeInput: TextInputEditText
    private lateinit var memberUrlInput: TextInputEditText
    private lateinit var saveButton: Button

    private val formValidator = FormValidator()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        sharedPreferencesManager = SharedPreferencesManager(this)

        memberNameInput = findViewById(R.id.member_name_input)
        memberAgeInput = findViewById(R.id.member_age_input)
        memberUrlInput = findViewById(R.id.member_url_input)
        saveButton = findViewById(R.id.save_button)

        loadMemberData()
        setupSaveButton()
    }

    private fun loadMemberData() {
        val memberName = intent.getStringExtra(EXTRA_MEMBER_NAME)
        val memberAge = intent.getIntExtra(EXTRA_MEMBER_AGE, -1)
        val memberUrl = intent.getStringExtra(EXTRA_MEMBER_URL)

        memberNameInput.setText(memberName)
        memberAgeInput.setText(if (memberAge != -1) memberAge.toString() else "")
        memberUrlInput.setText(memberUrl)
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            if (validateInputs()) {
                saveMember()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val name = memberNameInput.text.toString().trim()
        if (!formValidator.validateName(name)) {
            memberNameInput.error = getString(R.string.error_name_required)
            isValid = false
        }

        val ageString = memberAgeInput.text.toString().trim()
        if (ageString.isNotEmpty() && !formValidator.validateAge(ageString)) {
            memberAgeInput.error = getString(R.string.error_invalid_age)
            isValid = false
        }

        val url = memberUrlInput.text.toString().trim()
        if (!formValidator.validateUrl(url)) {
            memberUrlInput.error = getString(R.string.error_invalid_url)
            isValid = false
        } else {
            memberUrlInput.setText(url)
        }

        return isValid
    }

    private fun saveMember() {
        val name = memberNameInput.text.toString().trim()
        val ageString = memberAgeInput.text.toString().trim()
        val age = if (ageString.isNotEmpty()) ageString.toInt() else null
        val url = memberUrlInput.text.toString().trim()

        val memberList = sharedPreferencesManager.getMemberList().toMutableList()

        val memberId = intent.getIntExtra(EXTRA_MEMBER_ID, -1)
        val member = memberList.find { it.id == memberId }

        if (member != null) {
            // Update the existing member
            val updatedMember = Member(member.id, name, age, url)
            memberList.remove(member)
            memberList.add(updatedMember)
        } else {
            // Add the new member to the list
            val newMember = Member(memberList.size, name, age, url)
            memberList.add(newMember)
        }

        sharedPreferencesManager.saveMemberList(memberList)

        Toast.makeText(this, R.string.member_saved, Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        const val EXTRA_MEMBER_ID = "member_id"
        const val EXTRA_MEMBER_NAME = "member_name"
        const val EXTRA_MEMBER_AGE = "member_age"
        const val EXTRA_MEMBER_URL = "member_url"
    }
}