package pl.coderslab.charity.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model, ResourceNotFoundException e) {
        model.addAttribute("prompt", e.getMessage());
        return "result-prompt";
    }

    @ExceptionHandler(ActionForbiddenException.class)
    public String handleForbidden(Model model, ActionForbiddenException e) {
        model.addAttribute("prompt", e.getMessage());
        return "result-prompt";
    }
}
