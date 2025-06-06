package com.email_scanner.service;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class GmailImapService {

    private static final String IMAP_HOST = "imap.gmail.com";
    private static final String IMAP_PORT = "993";

    public void readInbox(String email, String appPassword){
        try{
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", IMAP_HOST);
            props.put("mail.imaps.ssl.enable", "true");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");

            System.out.println("Conectando ao Gmail...");
            store.connect(IMAP_HOST, email, appPassword);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            System.out.println("Total de mensagens na caixa de entrada: " + messages.length);

            for (int i = 0; i < Math.min(messages.length, 10); i++){
                Message msg = messages[messages.length - 1 - i];
                System.out.println("Assunto: " + msg.getSubject());
            }
            inbox.close(false);
            store.close();
        } catch (Exception e){
            System.out.println("Erro ao acessar Gmail via IMAP");
            e.printStackTrace();
        }
    }
}
