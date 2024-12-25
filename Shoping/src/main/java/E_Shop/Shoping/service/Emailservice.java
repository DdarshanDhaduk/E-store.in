package E_Shop.Shoping.service;


import E_Shop.Shoping.model.Finalorder;
import E_Shop.Shoping.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Emailservice {

    @Autowired
    private JavaMailSender javaMailSender;

    private final String subject = "Order Placed Successfully";

    private final String bodyTemplate = "<div style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; line-height: 1.6;\">" +
            "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\">" +
            "<div style=\"background-color: #28a745; color: #fff; padding: 10px 0; text-align: center; border-radius: 8px 8px 0 0;\">" +
            "<h2>Order Placed Successfully</h2></div>" +
            "<div style=\"padding: 20px;\"><p>Dear $CUSTOMER_NAME$,</p>" +
            "<p>Thank you for your order. Here are the details of your purchase:</p>" +
            "<p><strong>Bill_id:</strong> $BILL_ID$</p>" +
            "<p><strong>Order_otp:</strong> $OTP$</p>" +
            "<p><strong>Order Date:</strong> $ORDER_DATE$</p>" +
            "<p><strong>Delivery Date:</strong> $DELIVERY_DATE$</p>" +
            "<p><strong>Payment method:</strong> $PAYMENET_METHODE$</p>" +
            "<p>We hope you enjoy your purchase! If you have any questions, feel free to contact us.</p>" +
            "</div><div style=\"text-align: center; color: #666;\"><p>Thank you,</p><p>[Your Application Team]</p></div></div></div>";

    @Async
    public void senderOrderSuccessEmail(Finalorder finalorder, User user){
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getUsername());
            helper.setSubject(subject);

            String body = bodyTemplate
                    .replace("$CUSTOMER_NAME$",user.getName())
                    .replace("$BILL_ID$",String.format("%d",finalorder.getBill_id()))
                    .replace("$OTP$",(finalorder.getOtp()))
                    .replace("$ORDER_DATE$",finalorder.getDate().toString())
                    .replace("$DELIVERY_DATE$",finalorder.getDeliverydate().toString())
                    .replace("$PAYMENET_METHODE$",finalorder.getPaymenttype());

            helper.setText(body, true);
            javaMailSender.send(message);
        }
        catch (MessagingException e){
            e.printStackTrace();
        }
    }

    // Generate a random 6-digit OTP
    public String generateOTP() {
        Random rand = new Random();
        int otp = rand.nextInt(999999);
        // Ensure OTP has 6 digits
        return String.format("%06d", otp);
    }

    // Send the OTP to the user's email
    public void sendOTP(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP for Password Reset");
        message.setText("Your OTP for resetting your password is: " + otp );
        javaMailSender.send(message);
    }
}
