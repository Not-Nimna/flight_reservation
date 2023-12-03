package ca.ucalgary.ensf480.flightapp.email.services;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ca.ucalgary.ensf480.flightapp.DTO.ReceiptEmailDTO;
import jakarta.mail.internet.MimeMessage;
import ca.ucalgary.ensf480.flightapp.DTO.BookingDTO;

@Service
public class EmailService {
  
  @Autowired
  private JavaMailSender emailSender;

   public void sendTicketEmail(BookingDTO bookingDTO) {
        String templatePath = "templates/ticket.html";
        try {
            String content = loadTemplateAndReplacePlaceholdersForTicket(templatePath, bookingDTO);
            sendEmail(bookingDTO.getEmail(), "Your Ticket Details", content);
        } catch (Exception e) {
            throw new RuntimeException("Error sending ticket email", e);
        }
    }

    private String loadTemplateAndReplacePlaceholdersForTicket(String templatePath, BookingDTO bookingDTO) throws Exception {

        Resource resource = new ClassPathResource(templatePath);
        InputStream inputStream = resource.getInputStream();
        String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        content = content.replace("{{name}}", bookingDTO.getName());
        content = content.replace("{{seatNumber}}", bookingDTO.getSeatNumber());
        content = content.replace("{{seatClass}}", bookingDTO.getSeatClass().toString());
        content = content.replace("{{flightNumber}}", bookingDTO.getFlightDTO().getFlightNumber());

        return content;
    }

    private void sendEmail(String to, String subject, String content) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // true indicates the content is HTML

        emailSender.send(message);
    }

    public void sendReceiptEmail(ReceiptEmailDTO receiptEmailDto) {
      String templatePath = "templates/receipt.html";
      try {
          String content = loadTemplateAndReplacePlaceholdersForReceipt(templatePath, receiptEmailDto);
          sendEmail(receiptEmailDto.getRecipient(), "Your Payment Receipt", content);
      } catch (Exception e) {
          throw new RuntimeException("Error sending receipt email", e);
      }
  }

  private String loadTemplateAndReplacePlaceholdersForReceipt(String templatePath, ReceiptEmailDTO receiptEmailDto) throws Exception {

      Resource resource = new ClassPathResource(templatePath);
      InputStream inputStream = resource.getInputStream();
      String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

      content = content.replace("{{amountPaid}}", receiptEmailDto.getAmountPaid());
      content = content.replace("{{paymentDate}}", receiptEmailDto.getPaymentDate());

      return content;
  }
}
