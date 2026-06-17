package pe.edu.upc.center.jameoFit.goals.domain.model.commands;

import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.ObjectiveType;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.PaceType;

public record UpdateGoalCaloriesCommand(
        Long userId,
        ObjectiveType objective,
        Double targetWeightKg,
        PaceType pace
) {
}
