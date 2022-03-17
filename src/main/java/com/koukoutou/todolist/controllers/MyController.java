package com.koukoutou.todolist.controllers;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.koukoutou.todolist.models.ToDoItem;
import com.koukoutou.todolist.repositories.ToDoItemRepository;

@Controller
public class MyController {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @GetMapping("/")
    public String index(Model model) {

        List<ToDoItem> items = (List<ToDoItem>) toDoItemRepository.findAll();
        model.addAttribute("items", items);

        return "index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {

        toDoItemRepository.deleteById(id);

        return "redirect:/";
    }

    @GetMapping(value = { "/item", "/item/{id}" })
    public String viewItem(Model model, @PathVariable(required = false) Long id) {

        if (id != null) {
            Optional<ToDoItem> item = toDoItemRepository.findById(id);
            if (item.isPresent()) {
                model.addAttribute("item", item.get());
            } else {
                throw new EntityNotFoundException();
            }
        } else {
            model.addAttribute("item", new ToDoItem());
        }
        return "form";
    }

    @PostMapping(value = { "/item" })
    public String createUpdateItem(@Valid @ModelAttribute("item") ToDoItem item, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        toDoItemRepository.save(item);
        return "redirect:/";
    }

}
