package mainpackage.springwithdatabase.controllers;

import jakarta.validation.Valid;
import mainpackage.springwithdatabase.dao.PersonDAO;
import mainpackage.springwithdatabase.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){//получить всех людей из DAO и передать на страницу
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){//человек по id
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute ("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())//если будут ошибки, то отобразится страница создания человека, но в объект person будут помещены ошибки с помощью @Valid
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";//перейти на страницу
    }
    @ModelAttribute
    public String populateHeaderMessage(){
        return "Welcome to website!";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")//Delete
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";//перенаправление на главную страницу
    }
}
