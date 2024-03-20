package com.example.demo.controller;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Controllers {

    private final ClientService clientService;

    public Controllers(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String clientListView(Model model) {
        var clients = clientService.getAll().stream().map(ClientDto::new).toList();

        model.addAttribute("clients", clients);
        model.addAttribute("client", new ClientDto());

        return "clients";
    }

    @PostMapping("/client/save")
    public String createClient(ClientDto client) {
        clientService.saveClient(client);
        return "redirect:/clients";
    }
}
