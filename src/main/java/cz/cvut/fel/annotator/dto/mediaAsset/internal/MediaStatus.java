package cz.cvut.fel.annotator.dto.mediaAsset.internal;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaStatus {
    ANNOTATED,
    PENDING;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
