package notifications.consumer;

import notifications.messaging.UserCreatedEvent;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import notifications.service.WelcomeMailerService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserCreatedConsumer {

    private static final Logger LOGGER = Logger.getLogger("notifications");

    @Inject
    WelcomeMailerService welcomeMailerService;

    @Incoming("user-created-in")
    @Blocking
    public void onUserCreated(UserCreatedEvent event) {
        if (event == null) {
            LOGGER.warn("Received null UserCreatedEvent");
            return;
        }

        if (event.email == null || event.email.isBlank()) {
            LOGGER.warnf("UserCreatedEvent missing email (username=%s)", event.username);
            return;
        }

        LOGGER.infof("Sending welcome email to %s (username=%s)",
                event.email, event.username);

        welcomeMailerService.sendWelcome(event.email, event.username);
    }
}