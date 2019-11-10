package de.techsails.Control;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class TwilioService {
    // Find your Account Sid and Token at twilio.com/console
    // DANGER! This is insecure. See http://twil.io/secure
    public static final String ACCOUNT_SID = "**";
    public static final String AUTH_TOKEN = "**";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("**"),
                new com.twilio.type.PhoneNumber("**"),
                "Twilio Test")
            .create();

        System.out.println(message.getSid());
    }
}
