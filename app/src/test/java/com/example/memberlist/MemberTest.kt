package com.example.memberlist

import com.example.memberlist.model.Member
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MemberTest {
    @Test
    fun testMemberCreation() {
        val member = Member(1, "田中 太郎", 30, "https://example.com")
        assertEquals(1, member.id)
        assertEquals("田中 太郎", member.name)
        assertEquals(30, member.age)
        assertEquals("https://example.com", member.url)
    }

    @Test
    fun testMemberWithNullAge() {
        val member = Member(1, "田中 太郎", null, "https://example.com")
        assertEquals(1, member.id)
        assertEquals("田中 太郎", member.name)
        assertNull(member.age)
        assertEquals("https://example.com", member.url)
    }

    @Test
    fun testMemberEquality() {
        val member1 = Member(1, "田中 太郎", 30, "https://example.com")
        val member2 = Member(1, "田中 太郎", 30, "https://example.com")
        val member3 = Member(2, "田中 花子", 25, "https://example.com")

        assertEquals(member1, member2)
        assertNotEquals(member1, member3)
    }
}