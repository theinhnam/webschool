package com.namndt.webschool.controllers;

import com.namndt.webschool.Repositories.HolidayRepository;
import com.namndt.webschool.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidayController {

    @Autowired
    HolidayRepository holidayRepo;

    @GetMapping("/holidays/{display}")
    public String displayController(@PathVariable String display, Model model){
        if(null != display && display.equals("all")){
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        }else if(null != display && display.equals("festival")){
            model.addAttribute("festival", true);
        }else if(null != display && display.equals("federal")){
            model.addAttribute("federal", true);
        }

        Iterable<Holiday> holidays = holidayRepo.findAll();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(), true).collect(Collectors.toList());
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types){
            model.addAttribute(type.toString(), holidayList.stream().filter(e -> e.getType().equals(type)).collect(Collectors.toList()));
        }

        return "holidays.html";
    }
}
