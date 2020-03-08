package br.com.rodrigo.rastreadorcoronavirus.models;

public class LocalEstado {

    private String estado;
    private String pais;
    private int totalCasosRecentes;
    private int diferencaDiaAnterior;

    public int getDiferencaDiaAnterior() {
        return diferencaDiaAnterior;
    }

    public void setDiferencaDiaAnterior(int diferencaDiaAnterior) {
        this.diferencaDiaAnterior = diferencaDiaAnterior;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getTotalCasosRecentes() {
        return totalCasosRecentes;
    }

    public void setTotalCasosRecentes(int totalCasosRecentes) {
        this.totalCasosRecentes = totalCasosRecentes;
    }

    @Override
    public String toString() {
        return "Local Estado{" +
                "Estado: '" + estado + '\'' +
                "\n Pais: '" + pais + '\'' +
                "\n Total de Casos Recentes: " + totalCasosRecentes +
                '}';
    }
}
