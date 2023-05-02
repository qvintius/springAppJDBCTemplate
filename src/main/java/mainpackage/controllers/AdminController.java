package mainpackage.controllers;

import mainpackage.dao.PersonDAO;
import mainpackage.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("people/admin")
public class AdminController {
    @Autowired
    private PersonDAO personDAO;

    @GetMapping("")
    public String adminPage(Model model, @ModelAttribute("person") Person person){
        model.addAttribute("people", personDAO.allPeople());
        return "people/adminPage";
    }
    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("person") Person person){
        System.out.println(person.getId());
        return "redirect:/people";
    }
}
