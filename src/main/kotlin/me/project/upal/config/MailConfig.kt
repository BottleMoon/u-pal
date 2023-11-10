package me.project.upal.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig {

    @Bean
    fun javaMailSender(): JavaMailSender {

        val javaMailSender = JavaMailSenderImpl();
        javaMailSender.host = "smtp.gmail.com"
        javaMailSender.port = 587
        javaMailSender.password = "fiwqheebjghpanzv"
        javaMailSender.username = "universal.penpal.sup@gmail.com"
        javaMailSender.javaMailProperties.setProperty("mail.smtp.auth", "true")
        javaMailSender.javaMailProperties.setProperty("mail.smtp.starttls.enable", "true")
        javaMailSender.javaMailProperties.setProperty("mail.smtp.starttls.required", "true")


        return javaMailSender

//        host: smtp.gmail.com
//        port: 587
//        username: universal.penpal.sup@gmail.com
//        password: fiwqheebjghpanzv
//        properties:
//        mail:
//        smtp:
//        auth: true
//        starttls:
//        enable: true
//        required: true
    }
}