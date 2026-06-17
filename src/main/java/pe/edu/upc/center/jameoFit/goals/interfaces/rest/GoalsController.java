package pe.edu.upc.center.jameoFit.goals.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.center.jameoFit.goals.domain.model.aggregates.Goal;
import pe.edu.upc.center.jameoFit.goals.domain.model.queries.GetGoalByUserQuery;
import pe.edu.upc.center.jameoFit.goals.domain.services.GoalCommandService;
import pe.edu.upc.center.jameoFit.goals.domain.services.GoalQueryService;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.resources.DietTypeConfigResource;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.resources.GoalCalorieConfigResource;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.resources.GoalResource;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.transform.GoalResourceFromEntityAssembler;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.transform.UpdateDietTypeCommandFromResourceAssembler;
import pe.edu.upc.center.jameoFit.goals.interfaces.rest.transform.UpdateGoalCaloriesCommandFromResourceAssembler;

@RestController
@RequestMapping(value = "/api/v1/goals", produces = "application/json")
@Tag(name = "Goals", description = "Goals Management Endpoints")
public class GoalsController {

    private final GoalCommandService commandService;
    private final GoalQueryService queryService;

    public GoalsController(GoalCommandService commandService, GoalQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PutMapping("/calories")
    public ResponseEntity<GoalResource> updateGoalCalories(
            @RequestParam Long userId,
            @RequestBody GoalCalorieConfigResource resource) {
        try {
            Goal updated = commandService.handle(
                    UpdateGoalCaloriesCommandFromResourceAssembler.toCommand(userId, resource)
            );
            return ResponseEntity.ok(GoalResourceFromEntityAssembler.toResourceFromEntity(updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/diet-type")
    public ResponseEntity<GoalResource> updateDietType(
            @RequestParam Long userId,
            @RequestBody DietTypeConfigResource resource) {
        try {
            Goal updated = commandService.handle(
                    UpdateDietTypeCommandFromResourceAssembler.toCommand(userId, resource)
            );
            return ResponseEntity.ok(GoalResourceFromEntityAssembler.toResourceFromEntity(updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<GoalResource> getByUser(@RequestParam Long userId) {
        try {
            return queryService.handle(new GetGoalByUserQuery(userId))
                    .map(GoalResourceFromEntityAssembler::toResourceFromEntity)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
