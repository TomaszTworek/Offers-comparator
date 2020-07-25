package pl.endproject.offerscomparator.subdomains.userRegistration.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public final class EmailUtil {

    private EmailUtil(){}

    public static void sendActivationEmail(String email, String token, String contextPath, Session mailProperties) {

        Message message = new MimeMessage(mailProperties);
        try {
            message.setFrom(new InternetAddress("captaintest@yandex.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Offers Comparator - Account Confirmation");

            String header  ="Thank you for signing up for Offers Comparator!<br><br>" +
                    "\n" +
                    "Please verify your email address by clicking the button below.<br>\n";

//            String link = "<a href=\"http://localhost:8080" + contextPath + "?token=" + token + "\">Activate your account</a>";
            String link = "<a href=\"" + contextPath + "?token=" + token + "\">Activate your account</a>";

            String bottomText = "<br>If you didn't request this, please ignore this email.\n" +
                    "<br><br>Yours, Offers Comparator Team";

            String msg = header+link+bottomText;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
