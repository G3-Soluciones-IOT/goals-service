package pe.edu.upc.center.jameoFit.goals.domain.model.aggregates;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.DietPreset;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.ObjectiveType;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.PaceType;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.UserId;
import pe.edu.upc.center.jameoFit.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "goals", uniqueConstraints = {
        @UniqueConstraint(name = "uk_goals_user", columnNames = "user_id")
})
public class Goal extends AuditableAbstractAggregateRoot<Goal> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    })
    private UserId userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "objective", nullable = false)
    private ObjectiveType objective;

    @NotNull
    @DecimalMin(value = "1.0", inclusive = true, message = "Target weight must be greater than zero")
    @Column(name = "target_weight_kg", nullable = false)
    private Double targetWeightKg;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pace", nullable = false)
    private PaceType pace;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "diet_preset", nullable = false)
    private DietPreset dietPreset;

    @Column(name = "protein_pct")
    private Integer proteinPct;

    @Column(name = "carbs_pct")
    private Integer carbsPct;

    @Column(name = "fat_pct")
    private Integer fatPct;

    public Goal(UserId userId, ObjectiveType objective, Double targetWeightKg, PaceType pace, DietPreset dietPreset) {
        this.userId = userId;
        this.objective = objective;
        this.targetWeightKg = targetWeightKg;
        this.pace = pace;
        this.dietPreset = dietPreset;
    }

    public void updateGoalCalories(ObjectiveType objective, Double targetWeightKg, PaceType pace) {
        this.objective = objective;
        this.targetWeightKg = targetWeightKg;
        this.pace = pace;
    }

    public void updateDietPreset(DietPreset preset, Integer proteinPct, Integer carbsPct, Integer fatPct) {
        this.dietPreset = preset;
        this.proteinPct = proteinPct;
        this.carbsPct = carbsPct;
        this.fatPct = fatPct;
    }
}
