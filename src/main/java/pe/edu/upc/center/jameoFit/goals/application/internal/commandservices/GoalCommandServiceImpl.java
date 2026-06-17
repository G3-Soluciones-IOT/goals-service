package pe.edu.upc.center.jameoFit.goals.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.jameoFit.goals.domain.model.aggregates.Goal;
import pe.edu.upc.center.jameoFit.goals.domain.model.commands.UpdateDietTypeCommand;
import pe.edu.upc.center.jameoFit.goals.domain.model.commands.UpdateGoalCaloriesCommand;
import pe.edu.upc.center.jameoFit.goals.domain.model.services.MacroPolicyService;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.DietPreset;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.UserId;
import pe.edu.upc.center.jameoFit.goals.domain.services.GoalCommandService;
import pe.edu.upc.center.jameoFit.goals.infrastructure.persistence.jpa.repositories.GoalRepository;

@Service
@Transactional
public class GoalCommandServiceImpl implements GoalCommandService {

    private final GoalRepository repository;
    private final MacroPolicyService macroPolicy;

    public GoalCommandServiceImpl(GoalRepository repository, MacroPolicyService macroPolicy) {
        this.repository = repository;
        this.macroPolicy = macroPolicy;
    }

    @Override
    public Goal handle(UpdateGoalCaloriesCommand command) {
        if (command.userId() == null || command.userId() <= 0) {
            throw new IllegalArgumentException("Invalid userId");
        }
        if (command.objective() == null) {
            throw new IllegalArgumentException("Invalid objective");
        }
        if (command.targetWeightKg() == null || command.targetWeightKg() <= 0) {
            throw new IllegalArgumentException("Invalid targetWeightKg");
        }
        if (command.pace() == null) {
            throw new IllegalArgumentException("Invalid pace");
        }

        var goal = repository.findByUserId_Value(command.userId())
                .orElseGet(() -> new Goal(new UserId(command.userId()),
                        command.objective(), command.targetWeightKg(), command.pace(), DietPreset.OMNIVORE));

        goal.updateGoalCalories(command.objective(), command.targetWeightKg(), command.pace());
        int[] macros = macroPolicy.macrosFor(goal.getDietPreset());
        goal.updateDietPreset(goal.getDietPreset(), macros[0], macros[1], macros[2]);
        return repository.save(goal);
    }

    @Override
    public Goal handle(UpdateDietTypeCommand command) {
        if (command.userId() == null || command.userId() <= 0) {
            throw new IllegalArgumentException("Invalid userId");
        }
        if (command.preset() == null) {
            throw new IllegalArgumentException("Invalid preset");
        }

        var goal = repository.findByUserId_Value(command.userId())
                .orElseThrow(() -> new IllegalStateException("Goal not found for userId=" + command.userId()));

        int[] macros = macroPolicy.macrosFor(command.preset());
        goal.updateDietPreset(command.preset(), macros[0], macros[1], macros[2]);
        return repository.save(goal);
    }
}
