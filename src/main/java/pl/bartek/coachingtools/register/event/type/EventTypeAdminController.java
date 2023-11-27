package pl.bartek.coachingtools.register.event.type;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/eventType")
public class EventTypeAdminController {
    private final EventTypeRepository eventTypeRepository;

    public EventTypeAdminController(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("eventTypes",eventTypeRepository.findAll());
        return "admin/eventType/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("eventType", new EventType());
        return "admin/eventType/add";
    }
    @PostMapping("/add")
    public String add(@Valid EventType eventType, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("eventType",eventType);
            return "admin/eventType/add";
        }
        return "redirect:/admin/eventType/" + eventTypeRepository.save(eventType).getId() + "/details";
    }
}
