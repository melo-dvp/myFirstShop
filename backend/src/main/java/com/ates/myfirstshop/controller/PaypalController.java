package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaypalController {

    @Autowired
    private PaypalService paypalService;


}
