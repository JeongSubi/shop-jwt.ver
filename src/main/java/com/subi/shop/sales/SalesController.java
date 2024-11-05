package com.subi.shop.sales;

import com.subi.shop.member.CustomUser;
import com.subi.shop.member.Member;
import com.subi.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/order")
    String postOrder(String title,
                     Integer price,
                     Integer count,
                     Authentication auth) {

        Sales sales = new Sales();

        sales.setPrice(price);
        sales.setCount(count);
        sales.setItemName(title);

        CustomUser user = (CustomUser) auth.getPrincipal();
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);

        salesRepository.save(sales);
        return "list.html";
    }

    @GetMapping("/order/all")
    String getOrder(Authentication auth) {
        List<Sales> result = salesRepository.customFindAll();
        System.out.println(result.get(0));
        var salesDto = new SalesDto();
        salesDto.itemName = result.get(0).getItemName();
        salesDto.price = result.get(0).getPrice();
        salesDto.username = result.get(0).getMember().getUsername();
        return "list.html";
    }
}

class SalesDto {
    public String itemName;
    public Integer price;
    public String username;
}