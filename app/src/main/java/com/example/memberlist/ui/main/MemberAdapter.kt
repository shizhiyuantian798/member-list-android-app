package com.example.memberlist.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.memberlist.R
import com.example.memberlist.model.Member
import com.example.memberlist.ui.form.FormActivity
import com.example.memberlist.ui.webview.WebViewActivity
import com.example.memberlist.util.UrlConverter

class MemberAdapter(
    context: Context,
    private val memberList: List<Member>
) : ArrayAdapter<Member>(context, 0, memberList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item, parent, false)

        val member = memberList[position]
        view.findViewById<TextView>(R.id.member_name).text = member.name
        view.findViewById<TextView>(R.id.member_age).text =
            if (member.age != null && member.age >= 0) context.getString(
                R.string.age_format,
                member.age
            ) else ""

        val urlTextView = view.findViewById<TextView>(R.id.member_url)
        urlTextView.text = member.url
        urlTextView.setOnClickListener {
            if (member.url.isNotEmpty()) {
                val processedUrl = UrlConverter.convertHttpsUrl(member.url)
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_URL, processedUrl)
                context.startActivity(intent)
            }
        }

        view.findViewById<Button>(R.id.delete_button).setOnClickListener {
            (context as MainActivity).deleteMember(member)
        }

        view.setOnClickListener {
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra(FormActivity.EXTRA_MEMBER_ID, member.id)
            intent.putExtra(FormActivity.EXTRA_MEMBER_NAME, member.name)
            intent.putExtra(FormActivity.EXTRA_MEMBER_AGE, member.age)
            intent.putExtra(FormActivity.EXTRA_MEMBER_URL, member.url)
            context.startActivity(intent)
        }

        return view
    }
}