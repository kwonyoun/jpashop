package jpabook.jpashop.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    
    //autowired대신 생성자로 하는게 좋다.
    //@RequiredArgsConstructor로 했다.
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

	private void validateDuplicateMember(Member member) {
       List<Member> findMembers = memberRepository.findByName(member.getName());
       if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
       }
	}

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    } 

    //회원 조회
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
