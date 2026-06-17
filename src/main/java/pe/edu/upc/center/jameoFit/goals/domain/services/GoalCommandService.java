package pe.edu.upc.center.jameoFit.goals.domain.services;

import pe.edu.upc.center.jameoFit.goals.domain.model.aggregates.Goal;
import pe.edu.upc.center.jameoFit.goals.domain.model.commands.UpdateDietTypeCommand;
import pe.edu.upc.center.jameoFit.goals.domain.model.commands.UpdateGoalCaloriesCommand;

public interface GoalCommandService {
    Goal handle(UpdateGoalCaloriesCommand command);

    Goal handle(UpdateDietTypeCommand command);
}
