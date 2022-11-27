package br.ufpb.dcx.apps4society.qtarolando.api.model.enums;

public enum Categories {
  FESTAS(1, "Festas e Shows"),
  GASTRONOMIA(2, "Gastronomia"),
  RELIGIAO(3, "Religião e Espiritualidade"),
  CURSOS(4, "Cursos e Workshops"),
  ARTE(5, "Arte, Cinema e Lazer"),
  GEEK(6, "Games e Geek"),
  CONGRESSOS(7, "Congressos e Palestras"),
  MODA(8, "Moda e Beleza"),
  ESPORTES(9, "Esportes"),
  INFANTIL(10, "Infantil"),
  SAUDE(11, "Saúde e Bem-Estar");

  private int cod;
  private String name;

  private Categories(int cod, String name) {
    this.cod = cod;
    this.name = name;
  }

  public int getCod() {
    return cod;
  }

  public String getName() {
    return name;
  }
}
