package pe.edu.upc.center.jameoFit.goals.domain.model.commands;

import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.DietPreset;

public record UpdateDietTypeCommand(Long userId, DietPreset preset) {
}
