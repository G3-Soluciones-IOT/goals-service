package pe.edu.upc.center.jameoFit.goals.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.jameoFit.goals.domain.model.aggregates.Goal;
import pe.edu.upc.center.jameoFit.goals.domain.model.queries.GetGoalByUserQuery;
import pe.edu.upc.center.jameoFit.goals.domain.services.GoalQueryService;
import pe.edu.upc.center.jameoFit.goals.infrastructure.persistence.jpa.repositories.GoalRepository;

import java.util.Optional;

@Service
public class GoalQueryServiceImpl implements GoalQueryService {

    private final GoalRepository repository;

    public GoalQueryServiceImpl(GoalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Goal> handle(GetGoalByUserQuery query) {
        if (query.userId() == null || query.userId() <= 0) {
            throw new IllegalArgumentException("Invalid userId");
        }
        return repository.findByUserId_Value(query.userId());
    }
}
