package pl.bartek.coachingtools.register.event.type;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
