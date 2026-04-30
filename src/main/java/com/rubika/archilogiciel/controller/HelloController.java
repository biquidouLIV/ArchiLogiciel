package com.rubika.archilogiciel.controller;

import com.rubika.archilogiciel.controller.dto.Personnage;
import com.rubika.archilogiciel.controller.dto.UserList;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/hello")
public class HelloController {

    private ArrayList<Personnage> PersoList = new ArrayList<Personnage>();





    @GetMapping("/create")
    public String showCreatePerso(Model model){
        model.addAttribute("perso", new Personnage());
        return "hello";
    }
    @PostMapping("/create")
    public String saveCreatePerso(Model model, @ModelAttribute Personnage personnage){

        personnage.setStatsPoints(personnage.getLevel()*3);

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
        model.addAttribute("index",index);
        return "detail";
    }


    @PostMapping("/details/{index}")
    public String saveModifs(Model model, @ModelAttribute Personnage personnage, @PathVariable("index") int index){
        personnage.setStatsPoints(personnage.getLevel()*3 - personnage.getForce() - personnage.getAgilite() -
                personnage.getEndurance() - personnage.getRapidite() -
                personnage.getIntelligence() - personnage.getChance() -
                personnage.getCharisme() +20);
        model.addAttribute("personnages", PersoList);
        PersoList.set(index,personnage);
        return "personalized-hello";
    }

    @GetMapping("/delete/{index}")
    public String deletePerso(Model model, @PathVariable("index") int index){
        model.addAttribute("personnages", PersoList);
        PersoList.remove(index);
        return "personalized-hello";
    }

    @GetMapping("/addStat/{index}/{stats}")
    public String addStat(@PathVariable("index") int index, @PathVariable("stats") String stat ){
        Personnage perso = PersoList.get(index);


        if(perso.getStatsPoints() > 0){
            switch (stat) {
                case "force":
                    perso.setForce(perso.getForce()+1);
                    break;
                case "agilite":
                    perso.setAgilite(perso.getAgilite()+1);
                    break;
                case "endurance":
                    perso.setEndurance(perso.getEndurance()+1);
                    break;
                case "rapidite":
                    perso.setRapidite(perso.getRapidite()+1);
                    break;
                case "intelligence":
                    perso.setIntelligence(perso.getIntelligence()+1);
                    break;
                case "chance":
                    perso.setChance(perso.getChance()+1);
                    break;
                case "charisme":
                    perso.setCharisme(perso.getCharisme()+1);
                    break;

            }
            perso.setStatsPoints(perso.getStatsPoints()-1);
        }
        return "redirect:/hello/details/" + index;
    }

    @GetMapping("/cheatpage/{index}")
    public String showCheatPage(Model model, @PathVariable("index") int index){
    Personnage perso = PersoList.get(index);
    model.addAttribute("perso", perso);
    model.addAttribute("index",index);

    return "cheatpage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response)
    {
        boolean userExists = UserList.userList.stream().anyMatch(user -> user.getlogin().equals(login) && user.getlogin().equals(password));

        if(userExists)
        {
            Cookie cookie = new Cookie("status", login);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/personnages";
        }

        return "login";
    }

    @GetMapping("/personnages")
    public String listPersonnages(@CookieValue(value = "status", defaultValue = "anonymous") String status, Model model) {
        if (status.equals("loggedin")) {
            // model.addAttribute("personnages", repo.findByOwner(currentLogin));
        } else {
            // renvoyer une liste vide[cite: 1]
        }
        return "liste_personnages";
    }


}
