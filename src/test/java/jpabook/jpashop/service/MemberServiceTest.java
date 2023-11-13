package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void join() throws Exception{
        //given
        Member member = new Member(); 
        member.setName("jin");

        //when
        Long saveId = memberService.join(member);
        
        //then
        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void validateDuplicateJoin() throws Exception{
        //given
        Member member1 = new Member(); 
        member1.setName("jin");
        Member member2 = new Member(); 
        member2.setName("jin");

        //when
        memberService.join(member1);
        memberService.join(member2);
        
        //then
        fail("예외가 발생해야 한다.");
    }
}
