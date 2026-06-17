package pe.edu.upc.center.jameoFit.goals.interfaces.rest.transform;

import pe.edu.upc.center.jameoFit.goals.domain.model.commands.UpdateDietTypeCommand;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.resources.DietTypeConfigResource;

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
