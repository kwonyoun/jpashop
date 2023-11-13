package jpabook.jpashop.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        Member member = em.find(Member.class, id);
        return member; 
    }

    //sql은 테이블을 대상으로 쿼리하는데 jpql은 entity객체를 대상으로 쿼리한다. 
    //entity객체에 대한 alias를 m 으로 주고 entity를 조회한다. 

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
        .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
        .setParameter("name", name)
        .getResultList();
    }
}
