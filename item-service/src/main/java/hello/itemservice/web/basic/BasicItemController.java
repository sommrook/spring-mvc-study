package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    public BasicItemController(ItemRepository itemRepository){
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        // item 화면 목록 보여주는 컨트롤러
        List<Item> items = itemRepository.findAl();
        for (Item item : items){
            log.info("item name {}", item.getItemName());
        }

        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        // item 상세 화면 보여주는 컨트롤러
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        // 원래는 컨트롤러에서 모델에 직접 담고 값을 꺼내야 한다.
        // 그런데 쿼리 파라미터는 자주 사용해서 타입리프에서 직접 지원한다.
        return "basic/item";
    }


    @GetMapping("/add")
    public String addForm(){
        // item을 추가할 때 보여주는 화면 컨트롤러
        return "basic/addForm";
    }

    //th:action이 비어있으면 현재 url로 간다.
//    @PostMapping("/add")
    public String save(@ModelAttribute Item item, Model model){
        Item savedItem = itemRepository.save(item);

        model.addAttribute("item", savedItem);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item); //model.addAttribute("item", item); //자동 추가, 생략 가능
        return "basic/item";
    }

    //ModelAttribute에 Model에 보내야 하는 이름과 똑같이 적용하면 addAttribute를 해주지 않아도 된다.
    // 하는일이 2개 => 1. request로 받기 2. Model에 보내주기
    // 이름을 생략하면 클래스명을 소문자화 한것으로 지정된다.
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        itemRepository.save(item);

        return "basic/item";
    }

    // ModelAttribute 생략 가능
    // 문제! -> 새로고침하면 계속 add가 된다.
    // 웹 브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송함
//    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);

        return "basic/item";
    }

//   @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        // item 추가 화면에서 저장(추가 실행)을 눌렀을 때 form post로 인해 실행되는 컨트롤러
        /*
        새로운 item을 저장한 후에 저장한 새로운 item의 상세화면으로 return한다.
        이때 주의할점은 그냥 바로 return /basic/items로 하게 되면 (바로 뷰 화면 호출)
        제일 마지막 호출 된 url은 현재 메소드인 저장 메소드이기 때문에
        새로고침 할 때마다 저장 요청을 하게 된다.
        그래서 redirect로 GET /basic/items/{itemId}를 호출한다.
        => ***view화면 호출 시 return으로 경로 설정을 하지만 이것은 사용자가 입력하는 url정보는 아님 (동적 view여서 모든 페이지가 컨트롤러를 통해 나옴)
        */

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        //redirectAttribute에 넣은 itemId값으로 치환한다.
        //남은 애들은 쿼리 파라미터로 들어감
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    //생성자 생성 후에 호출 - 반대: @PreDestroy
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }


}
