package com.rsaad.service.impl;

import com.rsaad.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender {
    @Override
    public void sentEmail(String message) {
        log.info("Email Sent");
        System.out.println("Email Sent");
    }
}
