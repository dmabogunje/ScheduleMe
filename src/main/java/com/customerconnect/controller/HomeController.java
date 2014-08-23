package com.customerconnect.controller;

import com.customerconnect.form.ScheduleAppointment;
import com.customerconnect.model.CompanyConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String loadHomePage() {
        return "home/index";
    }

    @RequestMapping("/pricing")
    public String loadPricingPage() {
        return "home/pricing";
    }

    @RequestMapping("/features")
    public String loadFeaturesPage() {
        return "home/features";
    }

    @RequestMapping("/register")
    public String loadRegisterPage() {
        return "home/register";
    }

    @RequestMapping("/{companyId}")
    public String loadCompanyPage(Model model, @PathVariable String companyId) {

        CompanyConfig config = getCompanyConfig(companyId);

        if (config==null) {
            return "home/index";
        }

        String clientName = config.getCompanyName();
        String welcomeMessage = config.getWelcomeMessage();
        String instructions = config.getInstructions();
        String categoryLabel = config.getCategoryLabel();
        List<String> categories = config.getCategories();

        model.addAttribute("companyId", companyId);
        model.addAttribute("clientName", clientName);
        model.addAttribute("welcomeMessage", welcomeMessage);
        model.addAttribute("instructions", instructions);
        model.addAttribute("categoryLabel", categoryLabel);
        model.addAttribute("categories", categories);
        model.addAttribute("scheduleAppointment", new ScheduleAppointment());

        return "schedule/company";
    }

    @RequestMapping(value="/scheduleAppointment", method= RequestMethod.POST)
    public String scheduleAppointment(Model model, @ModelAttribute ScheduleAppointment scheduleAppointment) {

        CompanyConfig config = getCompanyConfig(scheduleAppointment.getCompanyId());
        String clientName = config.getCompanyName();
        model.addAttribute("clientName", clientName);
        return "schedule/confirmation";

    }

    private CompanyConfig getCompanyConfig(String companyId) {
        CompanyConfig config = new CompanyConfig();
        if ("tmc".equalsIgnoreCase(companyId)) {
            config.setCompanyName("Toki Mabogunje & Company");
            config.setWelcomeMessage("Welcome Message");
            config.setInstructions("Instructions");
            config.setCategoryLabel("Reason");
            config.setCategories(Arrays.asList("Consultation", "Procedure", "Routine"));
            return config;
        }
        else if ("isdl".equalsIgnoreCase(companyId)) {
            config.setCompanyName("Integrated Services & Devices Limited");
            config.setWelcomeMessage("Welcome Message");
            config.setInstructions("Instructions");
            config.setCategoryLabel("Issue");
            config.setCategories(Arrays.asList("System Failure", "Report Security Incident"));
            return config;
        }
        return null;
    }

}
