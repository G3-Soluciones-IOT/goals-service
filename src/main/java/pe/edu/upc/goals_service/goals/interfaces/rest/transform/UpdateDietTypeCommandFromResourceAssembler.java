package pe.edu.upc.goals_service.goals.interfaces.rest.transform;

import pe.edu.upc.goals_service.goals.domain.model.commands.UpdateDietTypeCommand;
import pe.edu.upc.goals_service.goals.interfaces.rest.resources.DietTypeConfigResource;

public class UpdateDietTypeCommandFromResourceAssembler {

    private UpdateDietTypeCommandFromResourceAssembler() {
    }

    public static UpdateDietTypeCommand toCommand(Long userId, DietTypeConfigResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Invalid request body");
        }
        return new UpdateDietTypeCommand(userId, resource.preset());
    }
}
