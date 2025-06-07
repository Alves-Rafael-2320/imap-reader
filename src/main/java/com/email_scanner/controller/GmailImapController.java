package com.email_scanner.controller;

import com.email_scanner.service.GmailImapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gmail")
public class GmailImapController {

    private final GmailImapService gmailImapService;

    @Autowired
    public GmailImapController(GmailImapService gmailImapService){
        this.gmailImapService = gmailImapService;
    }

    @PostMapping("/read")
    public String readInbox(@RequestParam String email, @RequestParam String appPassword){
        gmailImapService.readInbox(email, appPassword);
        return "Varredura da caixa de entrada concluída com persistência dos códigos de rastreio.";
    }
}
