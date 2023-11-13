package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        
        //엔티티 조회
        Item item = itemRepository.findOne(itemId);
        Member member = memberRepository.findOne(memberId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        /*
        //Order order2 = new Order();
         * 위처럼 new 객체를 만드는 것은 불가능하다. 
         * 직접 생성하는 것이 아닌 다른 스타일로 생성해야한다. OrderItem.createOrderItem();
         * Order객체에서 @NoArgsConstructor(access = AccessLevel.PROTECTED) 롬복을 사용했기 때문이다. 
         * Protected로 설정하는 것이 
         */
    
        //주문 저장
        orderRepository.save(order);
    
        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    // //검색
    // @Transactional
    // public List<Order> findOrders(OrderSearch OrderSearch){
    //     return orderRepository.findAll(OrderSearch);
    // }

}
