package br.ufpb.dcx.apps4society.qtarolando.api.model.enums;

public enum Roles {

    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private int cod;
    private String description;

    private Roles(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static Roles toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Roles x : Roles.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

    public static Roles toEnum(String cod) {

        if (cod == null) {
            return null;
        }

        for (Roles x : Roles.values()) {
            if (cod.equals(x.getDescription())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Name inválido: " + cod);
    }

}
