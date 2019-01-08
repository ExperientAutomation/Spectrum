package CustomReporter;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.Test;

public class emailScheduler {
	
//	public static void main(String[] args){
//		emailReport em = new emailReport();
//		em.sendEmail();
//	}

	@Test
	public void sendEmail(String screenshotPath){

    final String username = "mahesh.mishra@experient-inc.com";
    final String password = "%6yhnbgT";

    Properties props = new Properties();
    props.put("mail.smtp.auth", false);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp2.expoexchange.com");
    props.put("mail.smtp.port", "25");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        
        message.setRecipients(Message.RecipientType.TO,
        			InternetAddress.parse("mahesh.mishra@experient-inc.com,ladukeshm@infinite.com,sreejak@infinite.com"));
//        message.setRecipients(Message.RecipientType.TO,
//    			InternetAddress.parse("mahesh.mishra@experient-inc.com"));
        
        message.setSubject("Scheduled Spectrum Login attempt was FAILED");
        //message.setText("PFA");
        
       

        //MimeBodyPart messageBodyPart = new MimeBodyPart();
        
        BodyPart messageBodyPart = new MimeBodyPart();
        BodyPart attachmentPart = new MimeBodyPart();

        Multipart multipart = new MimeMultipart();

        messageBodyPart = new MimeBodyPart();
        String file = screenshotPath;
        String fleName = "Error.png";
        DataSource source = new FileDataSource(file);
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName(fleName);
        String html = "<p>Hi,</p><p>PFA the Automation Test report for Scheduled Spectrum Login.</p><p>Thanks,</p><p>Mahesh</p>";
        messageBodyPart.setContent(html,"text/html");
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        System.out.println("Sending");

        Transport.send(message);

        System.out.println("Done");

    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}