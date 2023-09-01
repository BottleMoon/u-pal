package me.project.upal.dto.member

import me.project.upal.entity.Member

data class MemberResponse(var member: Member
) {
    var nickname: String = member.nickName
    var age: Int = member.age
}