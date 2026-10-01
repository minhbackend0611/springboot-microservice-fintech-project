package minh.project.infrastructure.noti.content_html;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;

@RequiredArgsConstructor
public abstract class ContentHtmlEmailCreator {
    @Value("${app.name}")
    protected String appName;

    protected final TemplateEngine templateEngine;

}
