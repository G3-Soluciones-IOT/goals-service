package pe.edu.upc.goals_service.goals.interfaces.rest.transform;

import pe.edu.upc.goals_service.goals.domain.model.commands.UpdateGoalCaloriesCommand;
import pe.edu.upc.goals_service.goals.interfaces.rest.resources.GoalCalorieConfigResource;

public class UpdateGoalCaloriesCommandFromResourceAssembler {

    private UpdateGoalCaloriesCommandFromResourceAssembler() {
    }

    public static UpdateGoalCaloriesCommand toCommand(Long userId, GoalCalorieConfigResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Invalid request body");
        }
        return new UpdateGoalCaloriesCommand(userId, resource.objective(), resource.targetWeightKg(), resource.pace());
    }
}
