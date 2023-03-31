package hello.itemservice2.web;


import hello.itemservice2.domain.Item;
import hello.itemservice2.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class FormController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("TestDATA", 10000, 10));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model)
    {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "item";
    }

    @GetMapping("/clear")
    public String clear()
    {
        itemRepository.clear();
        return "items";
    }

    @GetMapping("/add")
    public String addForm()
    {
        return "addForm";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        itemRepository.save(item);
        redirectAttributes.addAttribute("status",true);
        redirectAttributes.addAttribute("itemid",item.getId());
        return "redirect:/basic/items/{itemid}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model)
    {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item)
    {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }
}
