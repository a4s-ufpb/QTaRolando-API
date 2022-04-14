package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import java.time.LocalDateTime;

public class DateRangeDTO {
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;

    public DateRangeDTO(LocalDateTime initialDate, LocalDateTime finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }
}
