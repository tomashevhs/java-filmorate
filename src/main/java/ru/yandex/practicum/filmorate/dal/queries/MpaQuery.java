package ru.yandex.practicum.filmorate.dal.queries;

import lombok.Getter;

@Getter
public enum MpaQuery {
    QUERY_FOR_ALL_MPA("SELECT * FROM rating_MPA"),
    QUERY_FOR_BY_ID("SELECT * FROM rating_MPA WHERE rating_MPA_id = ?");
    private final String query;

    MpaQuery(String query) {
        this.query = query;
    }
}
