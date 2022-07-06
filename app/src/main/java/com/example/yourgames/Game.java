package com.example.yourgames;

public class Game {
    public Game(String name, String descricao, String url, String preco) {
        this.name = name;
        this.descricao = descricao;
        this.url = url;
        this.preco = preco;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    private String name;
    private String descricao;
    private String url;
    private String preco;

}
