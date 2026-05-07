package com.flyaway.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateManyRequest {
    @NotNull
    @Valid
    private List<FlightCreateRequest> inputs;

    public List<FlightCreateRequest> getInputs() { return inputs; }
    public void setInputs(List<FlightCreateRequest> inputs) { this.inputs = inputs; }
}
