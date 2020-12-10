package br.com.jeffersonbraga.tickers.model.card;

import java.util.Date;

public class TickerCard {

    private String ticker = null;

    private Date data = null;

    private Long precoMedio = null;

    private Long valorAtual = null;

    private Long diferencaPreco = null;

    private Long percentualDiferenca = null;

    private Long quantidade = null;

    private Long valorTotalInvestido = null;

    private Long valorTotalAtual = null;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(Long precoMedio) {
        this.precoMedio = precoMedio;
    }

    public Long getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(Long valorAtual) {
        this.valorAtual = valorAtual;
    }

    public Long getDiferencaPreco() {
        return diferencaPreco;
    }

    public void setDiferencaPreco(Long diferencaPreco) {
        this.diferencaPreco = diferencaPreco;
    }

    public Long getPercentualDiferenca() {
        return percentualDiferenca;
    }

    public void setPercentualDiferenca(Long percentualDiferenca) {
        this.percentualDiferenca = percentualDiferenca;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Long getValorTotalInvestido() {
        return valorTotalInvestido;
    }

    public void setValorTotalInvestido(Long valorTotalInvestido) {
        this.valorTotalInvestido = valorTotalInvestido;
    }

    public Long getValorTotalAtual() {
        return valorTotalAtual;
    }

    public void setValorTotalAtual(Long valorTotalAtual) {
        this.valorTotalAtual = valorTotalAtual;
    }
}
