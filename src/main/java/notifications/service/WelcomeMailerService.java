package notifications.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WelcomeMailerService {

    @Inject
    Mailer mailer;

    public void sendWelcome(String to, String username) {
        String safeName = (username == null || username.isBlank()) ? "there" : username;

        mailer.send(
                Mail.withText(
                        to,
                        "Welcome to our app",
                        "Hi " + safeName + ",\n\n" +
                                "Welcome to our app!\n" +
                                "We're happy to have you.\n\n" +
                                "Cheers,\n" +
                                "ESSA Team"
                )
        );
    }
}
