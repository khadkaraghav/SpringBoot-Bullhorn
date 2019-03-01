package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    BullhornRepository bullhornRepository;

    @RequestMapping("/")
    public String listBullhorn(Model model){
        model.addAttribute("bullhorns", bullhornRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String bullhornForm(Model model){

        model.addAttribute("bullhorn", new Bullhorn());
        return "bullhornform";

    }

    @PostMapping("/process")
    public String processForm(@Valid Bullhorn bullhorn, BindingResult result) {

        if (result.hasErrors()) {

            return "bullhornform";
        }

        bullhornRepository.save(bullhorn);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showBullhorn(@PathVariable("id") long id, Model model){
        model.addAttribute("bullhorn",bullhornRepository.findById(id).get());
        return "show";

    }

    @RequestMapping("/update/{id}")
    public String updateBullhorn(@PathVariable("id") long id, Model model){
        model.addAttribute("bullhorn",bullhornRepository.findById(id));
        return "bullhornform";

    }




    @RequestMapping("/delete/{id}")
    public String delBullhorn(@PathVariable("id") long id, Model model){

        bullhornRepository.deleteById(id);
        return "redirect:/";

    }

}