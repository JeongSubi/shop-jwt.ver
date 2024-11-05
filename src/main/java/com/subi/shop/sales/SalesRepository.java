package com.subi.shop.sales;

import com.subi.shop.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository  extends JpaRepository<Sales, Long>  {

    //JPQL 써야함: SQL을 좀 더 쓰기 쉽게 만든 문법 // nativeQuery = true 지워줌
    // 왜냐하면 JPA가 적절한 SQL문으로 번역하기 떄문에 N+1 문제가 또 일어남 // JOIN FETCH
    @Query(value = "SELECT s FROM Sales s JOIN FETCH s.member")
    List<Sales> customFindAll();
}
