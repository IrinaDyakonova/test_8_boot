package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers( Model model) {
        model.addAttribute("userList", userService.findAll());
        return "users";
    }

    @PostMapping("/create")
    public String createUserPost(User user, Model model) {

        String name = user.getName();
        String password = user.getPassword();
        if (name != null && name.length() > 0
                && password != null && password.length() > 0) {
            User newPerson = new User(name, password);
            userService.add(newPerson);
            return "redirect:/users";
        }
        model.addAttribute("errorMessage", "Sorry, you didn't write correct name or password");
        return "create";
    }

    @GetMapping("/create")
    public String createUserGet(Model model) {

        User user = new User();
        model.addAttribute("user", user);
        return "create";
    }

    @PostMapping("/delete")
    public String deleteUserFromIdPost(Long id,Model model) {
        boolean deleteById = userService.deleteById(id);
        if (deleteById) {
            return  "redirect:/users";
        }
        model.addAttribute("errorMessage", "Sorry, we don't have user with this id");
        return  "delete";
    }

    @GetMapping("/delete")
    public String deleteUserFromIdGet(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "delete";
    }


    @GetMapping("/create-new-score")
    public String createNewScoreGet(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "createNewScore";
    }

    @PostMapping("/create-new-score")
    public String createNewScorePost(@RequestParam Long id,@RequestParam int count, Model model) {
        if (userService.existsById(id)) {
            userService.addNewScore(id, count);
            return "redirect:/users";
        }
        model.addAttribute("errorMessage", "Sorry, we don't have user with this id");
        return  "createNewScore";
    }

    @GetMapping("/delete-score")
    public String deleteScoreGet(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "deleteScore";
    }

    @PostMapping("/delete-score")
    public String deleteScorePost(@RequestParam Long id, @RequestParam int idScore, Model model) {
       if (userService.existsById(id)) {
           userService.deleteScore(id, idScore - 1);
           return "redirect:/users";
       }
        model.addAttribute("errorMessage", "Sorry, we don't have user with this id");
        return  "deleteScore";
    }

    @GetMapping("/swap-score")
    public String swapScoreGet(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "swapScore";
    }
    @PostMapping("/swap-score")
    public String swapScorePost(@RequestParam Long id, @RequestParam int idScore1, @RequestParam int idScore2, @RequestParam int sum, Model model) {
        if (userService.existsById(id)) {
            userService.swappingScore(id, idScore1 - 1, idScore2 - 1, sum);
            return "redirect:/users";
        }
        model.addAttribute("errorMessage", "Sorry, we don't have user with this id");
        return  "swapScore";
    }
}
