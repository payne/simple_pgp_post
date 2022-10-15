package org.mattpayne.simple.controller;

import javax.validation.Valid;
import org.mattpayne.simple.model.MessageDTO;
import org.mattpayne.simple.service.MessageService;
import org.mattpayne.simple.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("messages", messageService.findAll());
        return "message/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("message") final MessageDTO messageDTO) {
        return "message/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("message") @Valid final MessageDTO messageDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "message/add";
        }
        messageService.create(messageDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("message.create.success"));
        return "redirect:/messages";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("message", messageService.get(id));
        return "message/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("message") @Valid final MessageDTO messageDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "message/edit";
        }
        messageService.update(id, messageDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("message.update.success"));
        return "redirect:/messages";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        messageService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("message.delete.success"));
        return "redirect:/messages";
    }

}
