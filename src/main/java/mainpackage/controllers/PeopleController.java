package mainpackage.controllers;

import jakarta.validation.Valid;
import mainpackage.dao.PersonDAO;
import mainpackage.models.Person;
import mainpackage.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private PersonValidator personValidator;

    @GetMapping()
    public String allPeople(Model model){
        model.addAttribute("people", personDAO.allPeople());
        return "people/all";
    }
    @GetMapping("/{id}")
    public String idPeople(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.idPeople(id));

        return "people/id";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
       // personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        else
            personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.idPeople(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);
       if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }



}
