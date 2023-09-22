package com.luxfacta.vernyy.api.shared;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;



public class EmailUtils {

    @Autowired
    private Environment env;

    
    public EmailMessage novaMensagem() {
        return new EmailMessage();
    }
    
    public void sendMessage(EmailMessage msg) {

        boolean habilitaEnvio = env.getProperty("email.enabled", Boolean.class);
        if (!habilitaEnvio)
            return;


        String mode = env.getProperty("email.transport", String.class);
        
    	try {
	        if ("SENDGRID".equalsIgnoreCase(mode)) {
				sendSendGrid(msg);
	        } else if ("SMTP".equalsIgnoreCase(mode)) {
	        	sendSmtp(msg);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
    }

    private void sendSendGrid(EmailMessage _emailMessage) throws IOException {
        String sendgridKey = env.getProperty("sendgrid.key");
        
        Email emailFrom = new Email(_emailMessage.getFrom());
        if (_emailMessage.getFromName()!= null)
            emailFrom.setName(_emailMessage.getFromName());
        
        Email emailTo = new Email(_emailMessage.getTo());
        if (_emailMessage.getToName()!= null)
            emailTo.setName(_emailMessage.getToName());
        
        
        
        Content content = new Content("text/html", _emailMessage.getBody());
        Mail mail = new Mail(emailFrom, _emailMessage.getSubject(), emailTo, content);

      Request request = new Request();
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      
      SendGrid sg = new SendGrid(sendgridKey);
      sg.api(request);

    }
    
    
    private void sendSmtp(EmailMessage _emailMessage) throws MessagingException {
    	final Authenticator auth = new SMTPAuthenticator(env.getProperty("smtp.username"), env.getProperty("smtp.password"));

    	final Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", env.getProperty("smtp.server"));
        props.setProperty("mail.smtp.port", env.getProperty("smtp.port"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        
        if (env.getProperty("smtp.tls") != null &&  env.getProperty("smtp.tls", Boolean.class).booleanValue())
        	props.put("mail.smtp.starttls.enable","true");
        
        if (env.getProperty("smtp.ssl") != null && env.getProperty("smtp.ssl", Boolean.class).booleanValue()) {
	        props.put("mail.smtp.socketFactory.port", env.getProperty("smtp.port"));
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback", "false");
        }
        
        Session mailSession = Session.getInstance(props, auth);
        
    	Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(_emailMessage.getFrom(), false));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(_emailMessage.getTo(), false));
        msg.setSubject(_emailMessage.getSubject());
        msg.setSentDate(new Date());
        msg.addHeader("Content-type", "text/html; charset=utf-8");
	    msg.addHeader("format", "flowed");
	    msg.addHeader("Content-Transfer-Encoding", "8bit");        
	    msg.setContent(_emailMessage.getBody(), "text/html; charset=utf-8");
        Transport.send(msg);
    }

    
    public class EmailMessage {
        private String subject;
        private String body;
        private String from;
        private String fromName;
        private String toName;
        private String to;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String assunto) {
            this.subject = assunto;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String mensagem) {
            this.body = mensagem;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String emailOrigem) {
            this.from = emailOrigem;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String nomeOrigem) {
            this.fromName = nomeOrigem;
        }

        public String getToName() {
            return toName;
        }

        public void setToName(String nomeDestinatario) {
            this.toName = nomeDestinatario;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String emailDestinatario) {
            this.to = emailDestinatario;
        }

    }

    private class SMTPAuthenticator extends Authenticator
    {
    	String username = null;
    	String password = null;
    	
    	public SMTPAuthenticator(String username, String password) {
    		super();
    		this.password=password;
    		this.username=username;
    		
    	}
    	
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
    
}
