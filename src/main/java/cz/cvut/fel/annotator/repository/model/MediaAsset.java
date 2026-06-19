package cz.cvut.fel.annotator.repository.model;

import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.XSD;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@OWLClass(iri = Vocabulary.MediaAsset)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
public class MediaAsset extends Thing implements Serializable {

    @OWLDataProperty(iri = Vocabulary.hasReferenceId, datatype = XSD.STRING, simpleLiteral = true, fetch = FetchType.EAGER)
    private String referenceId;

    @OWLDataProperty(iri = Vocabulary.hasSource, datatype = XSD.STRING, simpleLiteral = true, fetch = FetchType.EAGER)
    private String source;

    @OWLDataProperty(iri = Vocabulary.hasName, datatype = XSD.STRING, simpleLiteral = true, fetch = FetchType.EAGER)
    private String name;

    @OWLDataProperty(iri = Vocabulary.hasDescription, datatype = XSD.STRING, simpleLiteral = true, fetch = FetchType.EAGER)
    private String description;

    @OWLObjectProperty(iri = Vocabulary.hasAnnotation, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Annotation> annotations = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @OWLDataProperty(iri = Vocabulary.hasMediaType, datatype = XSD.STRING, simpleLiteral = true, fetch = FetchType.EAGER)
    private MediaType type;

    @OWLDataProperty(iri = Vocabulary.modifiedAt)
    private LocalDateTime modifiedAt;

    /**
     * Ensures entityId is always derived from source.
     */
    @PrePersist
    @PreUpdate
    private void syncIdWithSource() {
        Objects.requireNonNull(source, "source must not be null");
        this.entityId = URI.create(source);
    }

}

