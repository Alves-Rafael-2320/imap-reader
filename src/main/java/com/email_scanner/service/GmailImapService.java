package com.email_scanner.service;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                Object content = msg.getContent();
                String body = extractTextFromContent(content);

                List<String> trackingCodes = extractTrackingCodes(body);
                trackingCodes.forEach(System.out::println);

            }
            inbox.close(false);
            store.close();
        } catch (Exception e){
            System.out.println("Erro ao acessar Gmail via IMAP");
            e.printStackTrace();
        }
    }

    private String extractTextFromContent(Object content) throws Exception{
        if (content instanceof String){
            return (String) content;
        } else if (content instanceof jakarta.mail.Multipart){
            jakarta.mail.Multipart multipart = (jakarta.mail.Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++){
                jakarta.mail.BodyPart part = multipart.getBodyPart(i);
                if (part.getContentType().toLowerCase().startsWith("text/plain")){
                return (String) part.getContent();
                }
            }
            return multipart.getBodyPart(0).getContent().toString();
        }
        return "conteúdo não suportado";
    }

    private List<String> extractTrackingCodes(String content){

        List<String> foundCodes = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\b(127|577|957)\\d{8}\\b");
        Matcher matcher = pattern.matcher(content);


        while (matcher.find()){
            foundCodes.add(matcher.group());
        }
        return foundCodes;
    }
}
