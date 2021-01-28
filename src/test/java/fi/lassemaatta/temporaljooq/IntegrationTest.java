package fi.lassemaatta.temporaljooq;

import fi.lassemaatta.temporaljooq.config.BaseTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("testDatabase")
public abstract class IntegrationTest extends BaseTest {
}
