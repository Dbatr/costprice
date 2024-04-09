package ru.fusing.costprice.dto;

import lombok.Getter;
import ru.fusing.costprice.entities.Instrument;


@Getter
public class InstrumentResponse {
    private Instrument instrument;
    private final String message;

    public InstrumentResponse(Instrument instrument, String message) {
        this.instrument = instrument;
        this.message = message;
    }
    public InstrumentResponse(String message) {
        this.message = message;
    }

}
