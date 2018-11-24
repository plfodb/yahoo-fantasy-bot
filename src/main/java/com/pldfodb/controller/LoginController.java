package com.pldfodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {


    @RequestMapping(value="/", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        return "login";
    }

    @RequestMapping(value="/oauth2/code/yahoo", method = RequestMethod.GET)
    public @ResponseBody String yahooOauth(@RequestParam("code") String code) {

       return code;
    }

}
