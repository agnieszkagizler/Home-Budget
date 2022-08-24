package com.example.homebudgetsda.userFront;

import com.example.homebudgetsda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class userRegister {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/front/user/add")
    public String newRegisterForm (Model model){
        model.addAttribute("objectForm", new UserRegistryForm());
        return "register_user";
    }

    @PostMapping(path = "/front/user/add")
    public String newUserAdd (Model model){
    return "";
    }
}
