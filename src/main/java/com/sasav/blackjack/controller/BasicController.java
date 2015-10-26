/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.controller;

import com.sasav.blackjack.dao.CommonDao;
import com.sasav.blackjack.model.security.LoginDetails;
import com.sasav.blackjack.service.AccountMaster;
import com.sasav.blackjack.service.GameCore;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Vadim
 */
@Controller
public class BasicController {

    private static final Logger LOG = Logger.getLogger(BasicController.class.getName());

    @Autowired
    CommonDao commonDao;

    @Autowired
    GameCore gameCore;

    @Autowired
    AccountMaster accountMaster;

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public String mainPage() {
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initPage() {
        List users = commonDao.getAll(LoginDetails.class);
        if (users.isEmpty()) {
            File file = new File(getClass().getClassLoader().getResource("sql/insert-data.sql").getFile());
            StringBuilder query = new StringBuilder("");
            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine() + "\n";
                    query.append(line);
                }

                scanner.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            commonDao.runQuery(query.toString());
        }
        return "login";
    }

}
