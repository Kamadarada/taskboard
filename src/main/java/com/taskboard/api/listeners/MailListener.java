package com.taskboard.api.listeners;

import com.taskboard.api.database.entity.user.UserEntity;
import com.taskboard.api.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailListener {

    private final EmailService mailService;

    @Value("${app.base-url}")
    private String baseUrl;

    @EventListener
    public void handleUserRegistered(UserEntity user) {
        String verifyUrl = baseUrl + "/v1/auth/verify?token=" + user.getToken();
        String html = buildWelcomeEmail(user.getEmail(), verifyUrl);
        try {
            mailService.sendHtml(user.getEmail(), "Welcome to Taskboard — verify your account", html);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildWelcomeEmail(String email, String verifyUrl) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>Welcome to Taskboard</title>
                </head>
                <body style="margin:0;padding:0;background-color:#f1f5f9;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,Oxygen,sans-serif;">
                  <table width="100%%" cellpadding="0" cellspacing="0" style="padding:48px 0;">
                    <tr>
                      <td align="center">
                        <table width="560" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:12px;overflow:hidden;box-shadow:0 4px 24px rgba(0,0,0,0.07);">

                          <!-- Header -->
                          <tr>
                            <td style="background:linear-gradient(135deg,#1d4ed8 0%%,#2563eb 100%%);padding:44px 48px;text-align:center;">
                              <table cellpadding="0" cellspacing="0" style="margin:0 auto 16px;">
                                <tr>
                                  <td style="background:rgba(255,255,255,0.15);border-radius:12px;padding:10px 18px;">
                                    <span style="color:#ffffff;font-size:20px;font-weight:700;letter-spacing:1px;">&#9632; TASKBOARD</span>
                                  </td>
                                </tr>
                              </table>
                              <p style="margin:0;color:#bfdbfe;font-size:14px;letter-spacing:0.3px;">Task management made simple</p>
                            </td>
                          </tr>

                          <!-- Body -->
                          <tr>
                            <td style="padding:48px 48px 32px;">
                              <h1 style="margin:0 0 8px;color:#0f172a;font-size:24px;font-weight:700;">Welcome aboard! &#127881;</h1>
                              <p style="margin:0 0 24px;color:#64748b;font-size:15px;line-height:1.6;">
                                Your account <strong style="color:#0f172a;">%s</strong> has been created successfully.
                                Click the button below to verify your email and start organizing your tasks.
                              </p>

                              <!-- Divider -->
                              <table width="100%%" cellpadding="0" cellspacing="0" style="margin-bottom:32px;">
                                <tr><td style="height:1px;background:#e2e8f0;"></td></tr>
                              </table>

                              <!-- CTA Button -->
                              <table cellpadding="0" cellspacing="0" style="margin:0 auto;">
                                <tr>
                                  <td style="border-radius:8px;background:#2563eb;box-shadow:0 4px 12px rgba(37,99,235,0.35);">
                                    <a href="%s"
                                       style="display:inline-block;padding:15px 40px;color:#ffffff;font-size:15px;font-weight:600;text-decoration:none;border-radius:8px;letter-spacing:0.2px;">
                                      &#10003;&nbsp; Verify my account
                                    </a>
                                  </td>
                                </tr>
                              </table>

                              <!-- Fallback link -->
                              <p style="margin:32px 0 0;color:#94a3b8;font-size:12px;line-height:1.6;text-align:center;">
                                Button not working? Paste this link in your browser:<br>
                                <a href="%s" style="color:#2563eb;font-size:11px;word-break:break-all;">%s</a>
                              </p>
                            </td>
                          </tr>

                          <!-- Footer -->
                          <tr>
                            <td style="padding:20px 48px 32px;text-align:center;">
                              <p style="margin:0;color:#cbd5e1;font-size:11px;line-height:1.6;">
                                This link expires in <strong>24 hours</strong>.<br>
                                If you didn't create this account, you can safely ignore this email.
                              </p>
                            </td>
                          </tr>

                        </table>
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
                """.formatted(email, verifyUrl, verifyUrl, verifyUrl);
    }
}
