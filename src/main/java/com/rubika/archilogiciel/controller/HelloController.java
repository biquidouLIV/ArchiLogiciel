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
import java.util.UUID;

@Controller
@RequestMapping("/hello")
public class HelloController {

    private ArrayList<Personnage> PersoList = new ArrayList<Personnage>();
    @Autowired
    private MongoPersonnageDao mongoPersonnageDao;
    @Autowired
    private MongoUserDao mongoUserDao;




    @GetMapping("/create")
    public String showCreatePerso(Model model){
        model.addAttribute("perso", new Personnage());
        return "hello";
    }
    @PostMapping("/create")
    public String saveCreatePerso(@CookieValue(value = "status", defaultValue = "anonymous") String login,
                                  @ModelAttribute Personnage personnage) {
        if (!login.equals("anonymous")) {
            try {
                PersonnageMongoModel model = new PersonnageMongoModel(personnage);
                model.setOwnerId(login);
                mongoPersonnageDao.save(model);
            } catch (Exception e) {
                System.err.println(">>> ERREUR MONGO save: " + e.getMessage());
            }
            return "redirect:/hello/personnages";
        }
        PersoList.add(personnage);
        return "redirect:/hello/list";
    }


    @GetMapping("/list")
    public String showList(Model model){
        model.addAttribute("personnages", PersoList);
        return "personalized-hello";
    }



    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") UUID id, Model model) {
        PersonnageMongoModel perso = mongoPersonnageDao.findById(id).orElseThrow();
        model.addAttribute("perso", perso);
        return "detailMongo";
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

    @GetMapping("/delete/{id}")
    public String deletePerso(@PathVariable("id") UUID id) {
        mongoPersonnageDao.deleteById(id);
        return "redirect:/hello/personnages";
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

    @GetMapping("/cheatpage/{id}")
    public String showCheatPageMongo(@PathVariable("id") UUID id, Model model) {
        PersonnageMongoModel perso = mongoPersonnageDao.findById(id).orElseThrow();
        model.addAttribute("perso", perso);
        return "cheatpage";
    }


    @GetMapping("/personnages")
    public String listPersonnages(@CookieValue(value = "status", defaultValue = "anonymous") String status, Model model) {
        System.out.println(">>> status cookie: " + status);
        try {
            var persos = mongoPersonnageDao.findByOwnerId(status);
            System.out.println(">>> trouvé: " + persos.size());
            model.addAttribute("personnages", persos);
        } catch (Exception e) {
            System.err.println(">>> ERREUR MONGO: " + e.getMessage());
            model.addAttribute("personnages", new ArrayList<>());
        }
        model.addAttribute("status", status);
        return "personalized-hello";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, @CookieValue(value = "status", defaultValue = "anonymous") String status) {
        model.addAttribute("status", status);
        return "login";
    }

    @PostMapping("/register")
    public String register(@RequestParam String login, @RequestParam String password) {
        if (!mongoUserDao.existsById(login)) {
            mongoUserDao.save(new UserMongoModel(login, password));
            System.out.println("Nouvel utilisateur créé : " + login);
        }
        return "redirect:/hello/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        UserMongoModel user = mongoUserDao.findByLoginAndPassword(login, password);
        if (user != null) {
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

    @GetMapping("/addStatMongo/{id}/{stat}")
    public String addStatMongo(@PathVariable("id") UUID id, @PathVariable("stat") String stat) {
        PersonnageMongoModel perso = mongoPersonnageDao.findById(id).orElseThrow();
        if (perso.getStatsPoints() > 0) {
            switch (stat) {
                case "force"        -> perso.setForce(perso.getForce() + 1);
                case "agilite"      -> perso.setAgilite(perso.getAgilite() + 1);
                case "endurance"    -> perso.setEndurance(perso.getEndurance() + 1);
                case "rapidite"     -> perso.setRapidite(perso.getRapidite() + 1);
                case "intelligence" -> perso.setIntelligence(perso.getIntelligence() + 1);
                case "chance"       -> perso.setChance(perso.getChance() + 1);
                case "charisme"     -> perso.setCharisme(perso.getCharisme() + 1);
            }
            perso.setStatsPoints(perso.getStatsPoints() - 1);
            mongoPersonnageDao.save(perso);
        }
        return "redirect:/hello/details/" + id;
    }

    @PostMapping("/changeClasse/{id}")
    public String changeClasse(@PathVariable("id") UUID id, @RequestParam String classe) {
        PersonnageMongoModel perso = mongoPersonnageDao.findById(id).orElseThrow();
        if (perso.getLevel() >= 5) {
            perso.setClasse(classe);
            mongoPersonnageDao.save(perso);
        }
        return "redirect:/hello/details/" + id;
    }

    @PostMapping("/cheatpage/{id}")
    public String saveCheatPageMongo(@PathVariable("id") UUID id,
                                     @RequestParam String name,
                                     @RequestParam String classe,
                                     @RequestParam String race,
                                     @RequestParam int level,
                                     @RequestParam int force,
                                     @RequestParam int agilite,
                                     @RequestParam int endurance,
                                     @RequestParam int rapidite,
                                     @RequestParam int intelligence,
                                     @RequestParam int chance,
                                     @RequestParam int charisme) {
        PersonnageMongoModel perso = mongoPersonnageDao.findById(id).orElseThrow();
        perso.setName(name);
        perso.setClasse(classe);
        perso.setRace(race);
        perso.setLevel(level);
        perso.setForce(force);
        perso.setAgilite(agilite);
        perso.setEndurance(endurance);
        perso.setRapidite(rapidite);
        perso.setIntelligence(intelligence);
        perso.setChance(chance);
        perso.setCharisme(charisme);
        perso.setStatsPoints(level * 3 - force - agilite - endurance - rapidite - intelligence - chance - charisme + 20);
        mongoPersonnageDao.save(perso);
        return "redirect:/hello/details/" + id;
    }
}
