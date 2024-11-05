package com.subi.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;

//    @Autowired
//    public ItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping("/list")
    String list(Model model) {
        List<Item> items = itemRepository.findAll();
        //  List<Notification> notifications = notificationRepository.findAll();
        model.addAttribute("items", items);

        return "list.html";
    }

    @GetMapping("/list/page/{page}")
    String getListPage(Model model, @PathVariable Integer page) {
        Page<Item> items = itemRepository.findPageBy(PageRequest.of(page - 1, 5));
        model.addAttribute("items", items);

        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
    // @RequestParam Map formData // 하나의 데이터로 받고 싶을때
    // @ModelAttribute Item item // 유저가 보낸 데이터를 모델로 만들어서 가져와줌
    String addPost(@RequestParam(name="title") String title,
                   @RequestParam(name="price") Integer price){
        itemService.saveItem(title, price);
        return "redirect:/list";
    }

//    @GetMapping("/detail/{id}")
//    ResponseEntity<String> detail(@PathVariable Long id, Model model) {
//        try {
//            Optional<Item> result = itemRepository.findById(id);
//            //  result.ifPresent(System.out::println);
//            if (result.isPresent()) {
//                model.addAttribute("data", result.get());
//                return "detail.html";
//            } else {
//                return "redirect:/list";
//            }
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//            return "redirect:/list";
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("서버 잘못")
//        }
//    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {

        Optional<Item> result = itemRepository.findById(id);
            //  result.ifPresent(System.out::println);
            if (result.isPresent()) {
                model.addAttribute("data", result.get());
                return "detail.html";
            } else {
                return "redirect:/list";
            }
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
            model.addAttribute("data",  result.get());
            return "edit.html";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit")
    String editItem(String title, Integer price, Long id) {
        itemService.editItem(title, price, id);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPreSignedUrl("test/" + filename);
        return result;
    }

    @GetMapping("/search")
    String search(@RequestParam String searchText) {
        var result = itemRepository.rawQuery2(searchText);

        return "list.html";
    }
}
