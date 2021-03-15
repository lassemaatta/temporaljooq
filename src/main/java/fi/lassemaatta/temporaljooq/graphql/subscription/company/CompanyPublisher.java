package fi.lassemaatta.temporaljooq.graphql.subscription.company;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@Component
public class CompanyPublisher {

    private final Flux<List<CompanyDto>> companyFlux;

    @Autowired
    public CompanyPublisher(final ICompanyRepository repository) {
        companyFlux = Flux.interval(Duration.ofSeconds(1))
                          .flatMap(i -> Mono.fromCallable(() -> repository.findAll(false))
                                            .subscribeOn(Schedulers.boundedElastic()))
                          .share();
    }

    public Flux<List<CompanyDto>> companies() {
        return companyFlux;
    }
}
