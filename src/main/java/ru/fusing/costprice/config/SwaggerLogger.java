package ru.fusing.costprice.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerLogger {

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";
        System.out.println("Swagger UI available at: " + swaggerUrl);
    }
}
