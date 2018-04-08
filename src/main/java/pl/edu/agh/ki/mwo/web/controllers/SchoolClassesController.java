package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class SchoolClassesController {

    @RequestMapping(value="/SchoolClasses")
    public String listSchoolClasses(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());

        return "schoolClassesList";    
    }
    
    @RequestMapping(value="/AddSchoolClass")
    public String displayAddSchoolClassForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
        return "schoolClassForm";    
    }

    @RequestMapping(value="/CreateSchoolClass", method=RequestMethod.POST)
    public String createSchoolClass(@RequestParam(value="schoolClassProfile", required=false) String profile,
    		@RequestParam(value="schoolClassStartYear", required=true) int startYear,
    		@RequestParam(value="schoolClassCurrentYear", required=true) int currentYear,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	SchoolClass schoolClass = new SchoolClass();
    	schoolClass.setProfile(profile);
    	schoolClass.setStartYear(startYear);
    	schoolClass.setCurrentYear(currentYear);
    	
    	DatabaseConnector.getInstance().addSchoolClass(schoolClass);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Nowa klasa została dodana");
         	
    	return "schoolClassesList";
    }
    
    @RequestMapping(value="/DeleteSchoolClass", method=RequestMethod.POST)
    public String deleteSchoolClass(@RequestParam(value="schoolClassId", required=true) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchoolClass(schoolClassId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Klasa została usunięta");
         	
    	return "schoolClassesList";
    }
    
    @RequestMapping(value="/ModifySchoolClass")
    public String modifySchoolClass(@RequestParam(value="schoolClassId", required=true) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("schoolClass", DatabaseConnector.getInstance().getSchoolClass(schoolClassId));
    	
    	return "schoolClassModifyForm";
    }
    
    @RequestMapping(value="/UpdateSchoolClass", method=RequestMethod.POST)
    public String updateSchool(@RequestParam(value="schoolClassProfile", required=false) String profile,
    		@RequestParam(value="schoolClassStartYear", required=false) int startYear,
    		@RequestParam(value="schoolClassCurrentYear", required=false) int currentYear,
    		@RequestParam(value="schoolClassId", required=false) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().updateSchoolClass(schoolClassId, profile, startYear, currentYear);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Dane klasy zostały zaktualizowane");
         	
    	return "schoolClassesList";
    }

}
