package br.ufpb.dcx.apps4society.qtarolando.api.model.enums;

public enum EventModality {

    PRESENCIAL(1, "PRESENCIAL"),
    ONLINE(2, "ONLINE");

    private int cod;
    private String description;

    private EventModality(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static EventModality toEnum(String description) {

        if (description == null) {
            return null;
        }

        for (EventModality eventModality : EventModality.values()) {
            if (description.equals(eventModality.getDescription())) {
                return eventModality;
            }
        }

        throw new IllegalArgumentException("Enum inválido: " + description);
    }

    public static String validate(String description) {
        for (EventModality eventModality : EventModality.values()) {
            if (description.equals(eventModality.getDescription())) {
                return description;
            }
        }

        throw new IllegalArgumentException("Enum inválido: " + description);
    }

}
