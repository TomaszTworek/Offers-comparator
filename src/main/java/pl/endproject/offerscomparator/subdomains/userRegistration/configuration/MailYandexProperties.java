package pl.endproject.offerscomparator.subdomains.userRegistration.configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class MailYandexProperties {
    private MailYandexProperties() {
    }

    public static Session config(){
    Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.yandex.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.trust", "smtp.yandex.com");
        prop.put("mail.smtp.ssl.enable", "true");

        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("captaintest@yandex.com", "AhoyCaptain");
            }
        });
}
}

