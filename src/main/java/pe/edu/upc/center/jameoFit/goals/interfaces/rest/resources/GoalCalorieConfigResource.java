package pe.edu.upc.center.jameoFit.goals.interfaces.rest.resources;

import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.ObjectiveType;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.PaceType;

public record GoalCalorieConfigResource(
        ObjectiveType objective,
        Double targetWeightKg,
        PaceType pace
) {
}
