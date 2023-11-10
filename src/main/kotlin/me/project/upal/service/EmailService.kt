package me.project.upal.service

import me.project.upal.entity.EmailAuthCode
import me.project.upal.repository.EmailAuthCodeRepository
import me.project.upal.repository.MemberRepository
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.ResponseEntity
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
        private val javaMailSender: JavaMailSender,
        private val emailAuthCodeRepository: EmailAuthCodeRepository,
        private val memberRepository: MemberRepository
) {

    fun sendMail(to: String): ResponseEntity<Map<String, String>> {
        if (memberRepository.findByEmail(to).isPresent) {
            return ResponseEntity.badRequest().body(mapOf("message" to "EMAIL_DUPLICATE"))
        }

        val message: SimpleMailMessage = SimpleMailMessage()
        val subject = "Authentication code for U-pal"
        val text = "Your authentication code is "
        val code = RandomStringUtils.randomAlphanumeric(6).uppercase()

        val emailAuthCode = EmailAuthCode(email = to, code = code)
        emailAuthCodeRepository.save(emailAuthCode)

        message.setTo(to)
        message.subject = subject
        message.text = text + code

        try {
            javaMailSender.send(message)
        } catch (e: MailException) {
            print(e.message)
        }
        return ResponseEntity.ok().build()
    }

    fun authenticateEmail(email: String, code: String): ResponseEntity<Void> {
        if (emailAuthCodeRepository.findById(email).orElseThrow().code == code.uppercase()) {
            //TODO: 인증 완료
            return ResponseEntity.ok().build()
        } else {
            //TODO: 실패
            return ResponseEntity.badRequest().build()
        }
    }
}