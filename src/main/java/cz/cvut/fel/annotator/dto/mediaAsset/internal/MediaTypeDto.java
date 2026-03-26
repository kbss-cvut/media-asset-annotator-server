package cz.cvut.fel.annotator.dto.mediaAsset.internal;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaTypeDto {
    IMAGE,
    VIDEO;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}