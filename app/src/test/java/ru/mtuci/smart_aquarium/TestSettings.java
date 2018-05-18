package ru.mtuci.smart_aquarium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSettings {

    @Test
    public void testSettingsFormat() {
        String settings = "12:50:56_00:00:00_10_00:00:00_00:00:00_00:00:00_01_20_40";
        ParameterFragment.AquaSettings.setSettings(settings);
        assertEquals(ParameterFragment.AquaSettings.getSettings(), settings);
    }
}
