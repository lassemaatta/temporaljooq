package fi.lassemaatta.temporaljooq;

import fi.lassemaatta.temporaljooq.config.BaseTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// We must explicitly disable graphql, otherwise
// we get NoSuchBeanDefinitionException problems with TestRestTemplate
// See: https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/544
@SpringBootTest(properties = {
        "graphql.servlet.enabled=false",
        "graphql.servlet.websocket.enabled=false"
})
@ActiveProfiles("testDatabase")
public abstract class IntegrationTest extends BaseTest {
}
