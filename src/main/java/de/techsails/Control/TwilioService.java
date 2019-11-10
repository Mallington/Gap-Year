package de.techsails.Control;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class TwilioService {
    // Find your Account Sid and Token at twilio.com/console
    // DANGER! This is insecure. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACd7b678a31519b7fd4092f3a8b3bf985a";
    public static final String AUTH_TOKEN = "d28191b2fc8f90c62e2df80e4b251928";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+4917682931803"),
                new com.twilio.type.PhoneNumber("+4917682931803"),
                "Twilio Test")
            .create();

        System.out.println(message.getSid());
    }
}
