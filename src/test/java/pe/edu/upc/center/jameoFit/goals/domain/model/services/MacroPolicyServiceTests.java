package pe.edu.upc.center.jameoFit.goals.domain.model.services;

import org.junit.jupiter.api.Test;
import pe.edu.upc.center.jameoFit.goals.domain.model.valueobjects.DietPreset;

import static org.assertj.core.api.Assertions.assertThat;

class MacroPolicyServiceTests {

    private final MacroPolicyService macroPolicyService = new MacroPolicyService();

    @Test
    void macrosForOmnivoreReturnsConfiguredPercentages() {
        assertThat(macroPolicyService.macrosFor(DietPreset.OMNIVORE))
                .containsExactly(30, 40, 30);
    }

    @Test
    void everyDietPresetReturnsOneHundredTotalPercentage() {
        for (DietPreset preset : DietPreset.values()) {
            int total = 0;
            for (int value : macroPolicyService.macrosFor(preset)) {
                total += value;
            }
            assertThat(total).isEqualTo(100);
        }
    }
}
