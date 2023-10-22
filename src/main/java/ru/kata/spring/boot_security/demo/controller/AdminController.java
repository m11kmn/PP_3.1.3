package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UsersService;

@Controller
@RequestMapping("/admin") // users - это начальная страница
public class AdminController {
    @Autowired
    private UsersService usersService;

    @GetMapping()
    public String showListOfUsers(Model model) {
        model.addAttribute("users", usersService.showListOfUsers());
        return "admin";
    }

    @GetMapping("/user/{id}")
    public String showUserInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("user", usersService.findUserById(id));
        return "/user";
    }

    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping()
    public String createdUser(@ModelAttribute("user") User user) {

        user.addRole(new Role("ROLE_USER"));
        user.addRole(new Role("ROLE_ADMIN"));
        usersService.saveUser(user);

        return "redirect:/admin";
    }

    @PostMapping ("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        usersService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String updateUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", usersService.findUserById(id));
        return "/edit";
    }

    @PostMapping ("/edit")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") long id) {
        usersService.updateUser(id, user);
        return "redirect:/admin";
    }
}
