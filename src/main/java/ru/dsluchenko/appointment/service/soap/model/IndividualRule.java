package ru.dsluchenko.appointment.service.soap.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class IndividualRule {
    private final List<GeneralRule> rules = new ArrayList<>();
    private final UUID doctorUuid;

    public IndividualRule(UUID doctorUuid) {
        this.doctorUuid = doctorUuid;
    }
}
