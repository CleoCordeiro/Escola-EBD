package br.com.cleo.escolaebd.domain.enumeration;

/**
 * The FaixaEtaria enumeration.
 */
public enum FaixaEtaria {
    CRIANCA(3, 7),
    JOVEM(8, 11),
    PRE_ADOLESCENTE(12, 14),
    ADOLESCENTE(15, 17),
    ADULTO(18, 90);

    private final Integer idadeMinima;
    private final Integer idadeMaxima;

    private FaixaEtaria(Integer idadeMinima, Integer idadeMaxima) {
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
    }

    public Integer getIdadeMinima() {
        return idadeMinima;
    }

    public Integer getIdadeMaxima() {
        return idadeMaxima;
    }
}
