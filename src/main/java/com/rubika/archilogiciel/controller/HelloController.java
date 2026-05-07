package com.rubika.archilogiciel.controller;

import com.rubika.archilogiciel.controller.dto.Personnage;
import com.rubika.archilogiciel.controller.dto.UserList;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hello")
public class HelloController {

    private ArrayList<Personnage> PersoList = new ArrayList<Personnage>();
    @Autowired
    private MongoPersonnageDao mongoPersonnageDao;





    @GetMapping("/create")
    public String showCreatePerso(Model model){
        model.addAttribute("perso", new Personnage());
        return "hello";
    }
    @PostMapping("/create")
    public String saveCreatePerso(@CookieValue(value = "status", defaultValue = "anonymous") String login,
                                  @ModelAttribute Personnage personnage) {
        if (!login.equals("anonymous")) {
            PersonnageMongoModel model = new PersonnageMongoModel(personnage);
            model.setOwnerId(login); // Lie le personnage au compte connecté
            mongoPersonnageDao.save(model); // Sauvegarde persistante
        }
        return "redirect:/hello/list";
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


    @GetMapping("/personnages")
    public String listPersonnages(@CookieValue(value = "status", defaultValue = "anonymous") String status, Model model) {
        model.addAttribute("status",status);

        if (!status.equals("anonymous")) {
            model.addAttribute("personnages", mongoPersonnageDao.findByOwnerId(status));
        }
        else {
            model.addAttribute("personnages", new ArrayList<PersonnageMongoModel>());
        }
        return "personalized-hello";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, @CookieValue(value = "status", defaultValue = "anonymous") String status) {
        model.addAttribute("status", status);
        return "login";
    }

    @PostMapping("/register")
    public String register(@RequestParam String login, @RequestParam String password) {
        // On utilise le nom complet pour éviter toute confusion avec une variable
        boolean exists = com.rubika.archilogiciel.controller.dto.UserList.userList.stream()
                .anyMatch(u -> u.getLogin().equals(login));

        if (!exists) {
            com.rubika.archilogiciel.controller.dto.User newUser = new com.rubika.archilogiciel.controller.dto.User();
            newUser.setLogin(login); // Attention au L majuscule ici
            newUser.setPassword(password);
            com.rubika.archilogiciel.controller.dto.UserList.userList.add(newUser);
            System.out.println("Nouvel utilisateur créé : " + login);
        }
        return "redirect:/hello/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        // Vérification dans la liste statique de la classe UserList
        boolean authSuccess = com.rubika.archilogiciel.controller.dto.UserList.userList.stream()
                .anyMatch(u -> u.getLogin().equals(login) && u.getPassword().equals(password));

        if (authSuccess) {
            Cookie cookie = new Cookie("status", login);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            return "redirect:/hello/personnages";
        }
        return "redirect:/hello/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("status", "anonymous");
        cookie.setPath("/");
        cookie.setMaxAge(0); // Supprime le cookie
        response.addCookie(cookie);
        return "redirect:/hello/login";
    }

}
