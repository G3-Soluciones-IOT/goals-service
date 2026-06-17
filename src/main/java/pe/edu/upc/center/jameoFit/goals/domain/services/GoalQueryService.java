package pe.edu.upc.center.jameoFit.goals.domain.services;

import pe.edu.upc.center.jameoFit.goals.domain.model.aggregates.Goal;
import pe.edu.upc.center.jameoFit.goals.domain.model.queries.GetGoalByUserQuery;

import java.util.Optional;

public interface GoalQueryService {
    Optional<Goal> handle(GetGoalByUserQuery query);
}
