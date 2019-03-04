package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    BullhornRepository bullhornRepository;


    @Autowired
    CloudinaryConfig cloudc;


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
    public String processForm(@Valid @ModelAttribute("bullhorn") Bullhorn bullhorn, BindingResult result, @RequestParam("file") MultipartFile file) {

        if (result.hasErrors()) {

            return "bullhornform";
        }

        if (file.isEmpty()){
            return "redirect:/add";
        }
        try {

            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
       bullhorn.setHeadshot(uploadResult.get("url").toString());
        bullhornRepository.save(bullhorn);
        }catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }

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