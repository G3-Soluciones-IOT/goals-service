package pe.edu.upc.center.jameoFit.goals.interfaces.rest.transform;

import pe.edu.upc.center.jameoFit.goals.domain.model.aggregates.Goal;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.resources.GoalResource;

public class GoalResourceFromEntityAssembler {

    private GoalResourceFromEntityAssembler() {
    }

    public static GoalResource toResourceFromEntity(Goal entity) {
        return new GoalResource(
                entity.getUserId().getValue(),
                entity.getObjective(),
                entity.getTargetWeightKg(),
                entity.getPace(),
                entity.getDietPreset(),
                entity.getProteinPct(),
                entity.getCarbsPct(),
                entity.getFatPct()
        );
    }
}
