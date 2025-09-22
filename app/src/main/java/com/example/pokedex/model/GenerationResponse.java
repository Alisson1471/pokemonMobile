package com.example.pokedex.model;

public class GenerationResponse {

    private String nome;

    private String imageUrl;
    private int offset;

    private int limit;

    public GenerationResponse(String nome, String imageUrl, int offset, int limit) {
        this.nome = nome;
        this.imageUrl = imageUrl;
        this.offset = offset;
        this.limit = limit;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "GenerationResponse{" +
                "nome='" + nome + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
