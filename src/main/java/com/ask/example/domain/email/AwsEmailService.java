package com.ask.example.domain.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("email")
public class AwsEmailService implements EmailService {

    @Override
    public boolean sendEmail(EmailAddress emailAddress) {
        System.out.println("Send Email using AWS : " + emailAddress.toString());
        return true;
    }
}

