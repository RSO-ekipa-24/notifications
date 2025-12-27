package notifications.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WelcomeMailerService {

    private static final Logger LOGGER = Logger.getLogger(WelcomeMailerService.class);

    @Inject
    Mailer mailer;

    public void sendWelcome(String to, String username) {
        if (to == null || to.isBlank()) {
            LOGGER.warn("Skipping welcome email: recipient address is null/blank");
            return;
        }

        String safeName = (username == null || username.isBlank()) ? "there" : username.trim();

        try {
            mailer.send(
                    Mail.withText(
                            to.trim(),
                            "Welcome to our app",
                            "Hi " + safeName + ",\n\n" +
                                    "Welcome to our app!\n" +
                                    "We're happy to have you.\n\n" +
                                    "Cheers,\n" +
                                    "ESSA Team"
                    )
            );
            LOGGER.infof("Welcome email queued for sending to %s (username=%s)", to.trim(), safeName);
        } catch (Exception e) {
            LOGGER.errorf(e, "Failed to send welcome email to %s (username=%s)", to.trim(), safeName);
        }
    }
}