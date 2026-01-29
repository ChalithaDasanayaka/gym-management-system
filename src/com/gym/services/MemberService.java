package com.gym.services;

import com.gym.dao.MemberDao;
import com.gym.models.Member;

import java.util.List;

public class MemberService {
    private MemberDao memberDao;
//constructor
    public MemberService() {
        this.memberDao = new MemberDao();
    }

    public boolean addMember(Member member) {
        return memberDao.insert(member);
    }

    public Member getMemberById(int memberId) {
        return memberDao.getById(memberId);
    }

    public List<Member> getAllMembers() {
        return memberDao.getAll();
    }
//update member
    public boolean updateMember(Member member) {
        return memberDao.update(member);
    }
  //delete member
    public boolean deleteMember(int memberId) {
        return memberDao.delete(memberId);
    }
}