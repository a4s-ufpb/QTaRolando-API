package br.ufpb.dcx.apps4society.qtarolando.api.model.enums;

public enum EventModality {

    IN_PERSON(1, "IN_PERSON"),
    REMOTE(2, "REMOTE");

    private int cod;
    private String description;

    private EventModality(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription () {
        return description;
    }

    public static EventModality toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (EventModality eventModality : EventModality.values()) {
            if (cod.equals(eventModality.getCod())) {
                return eventModality;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

    public static Integer validate(Integer cod){
        for (EventModality eventModality : EventModality.values()) {
            if (cod.equals(eventModality.getCod())) {
                return cod;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
