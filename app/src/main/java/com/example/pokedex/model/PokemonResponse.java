package com.example.pokedex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


import lombok.ToString;

public class PokemonResponse {

    private int id;
    private String name;
    private String imageUrl;
    private List<Stats> stats;
    private List<Enconteur> enconteurs;
    private List<String> types;

    @ToString
    public static class Stats {
        @JsonProperty("base_stat")
        private int baseStat;
        private Stat stat;

        @ToString
        public static class Stat {
            private String name;

            public Stat() {}

            public Stat(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }

        public Stats() {}

        public Stats(int baseStat, Stat stat) {
            this.baseStat = baseStat;
            this.stat = stat;
        }

        public int getBaseStat() {
            return baseStat;
        }

        public void setBaseStat(int baseStat) {
            this.baseStat = baseStat;
        }

        public Stat getStat() {
            return stat;
        }

        public void setStat(Stat stat) {
            this.stat = stat;
        }
    }

    public static class Enconteur {
        private String locationName;
        private int maxChance;

        public Enconteur(String locationName, int maxChance) {
            this.locationName = locationName;
            this.maxChance = maxChance;
        }

        public Enconteur() {}

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public int getMaxChance() {
            return maxChance;
        }

        public void setMaxChance(int maxChance) {
            this.maxChance = maxChance;
        }
    }
    public PokemonResponse() {}

    public PokemonResponse(int id, String name, String imageUrl, List<Stats> stats, List<String> types) {
        this.id = id;
        this.name = name;
        this.imageUrl= imageUrl;
        this.stats = stats;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public List<Enconteur> getEnconteurs() {
        return enconteurs;
    }

    public void setEnconteurs(List<Enconteur> enconteurs) {
        this.enconteurs = enconteurs;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "PokemonResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", stats=" + stats +
                ", enconteurs=" + enconteurs +
                ", types=" + types +
                '}';
    }
}

