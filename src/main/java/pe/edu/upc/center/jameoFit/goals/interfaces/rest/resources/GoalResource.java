package pe.edu.upc.center.jameoFit.goals.interfaces.rest.resources;

import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.DietPreset;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.ObjectiveType;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.PaceType;

public record GoalResource(
        Long userId,
        ObjectiveType objective,
        Double targetWeightKg,
        PaceType pace,
        DietPreset dietPreset,
        Integer proteinPct,
        Integer carbsPct,
        Integer fatPct
) {
}
