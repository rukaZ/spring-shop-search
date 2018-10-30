package handson.example.springshopsearch.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("about")
    public String getAbout() {
        return "about";
    }
    @Autowired
    ItemRepository itemRepository;

    @GetMapping
    public String index(
            Model model,
            @RequestParam(name = "keyword", required = false) Optional<String> keyword,
            @RequestParam(name = "keyword", required = false) Optional<String> keyword2,
            @RequestParam(name = "request", required = false) Optional<String> selectVal) {

        List<Item> list = itemRepository.findAll();

            if(selectVal.isPresent() && keyword.isPresent()) {
                if(selectVal.get().equals("itemname")) {
                    list = itemRepository.findByNameContainsOrderByIdAsc(keyword.get());
                }else if(selectVal.get().equals("description")) {
                    list = itemRepository.findByDescriptionContainsOrderByIdAsc(keyword.get());
                }else {
                    list = itemRepository.findByNameContainsOrDescriptionContainsOrderByIdAsc(keyword.get(),keyword2.get());
                    }
}
            model.addAttribute("items", list);
            return "index";
    }


}
