package com.rubika.archilogiciel.controller;

import com.rubika.archilogiciel.controller.dto.NameDto;
import com.rubika.archilogiciel.controller.dto.Personnage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/hello")
public class HelloController {

    private ArrayList<Personnage> PersoList = new ArrayList<Personnage>();
    public int persoIndex;





    @GetMapping("/say")
    public String sayHello(Model model){
        model.addAttribute("perso", new Personnage());
        return "hello";
    }


    @PostMapping("/say")
    public String sayHello(Model model, @ModelAttribute Personnage personnage){

        PersoList.add(personnage);
        model.addAttribute("personnages", PersoList);

        return "personalized-hello";
    }


    @GetMapping("/list")
    public String showList(Model model){
        model.addAttribute("personnages", PersoList);
        return "personalized-hello";
    }



    @GetMapping("/details/{index}")
    public String showDetails(@PathVariable("index") int index, Model model){

        Personnage perso = PersoList.get(index);
        model.addAttribute("perso", perso);
        return "detail";
    }


    @GetMapping("/details")
    public String showDetails(Model model){
        model.addAttribute("index", persoIndex);
        return "detail";
    }



    @PostMapping("/details")
    public String saveModifs(Model model, @ModelAttribute Personnage personnage, @PathVariable("index") int index){
        PersoList.set(index,personnage);
        return "personalized-hello";
    }
}
