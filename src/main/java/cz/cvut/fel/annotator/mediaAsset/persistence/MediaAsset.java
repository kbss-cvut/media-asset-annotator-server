package cz.cvut.fel.annotator.mediaAsset.persistence;

import cz.cvut.fel.annotator.annotation.persistence.Annotation;
import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaType;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.fel.annotator.shared.persistence.model.Thing;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.XSD;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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

    @OWLObjectProperty(iri = Vocabulary.hasAnnotation, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Annotation> annotations;

    @Enumerated(EnumType.STRING)
    @OWLDataProperty(iri = Vocabulary.hasMediaType, datatype = XSD.STRING, simpleLiteral = true, fetch = FetchType.EAGER)
    private MediaType type;

    @OWLDataProperty(iri = Vocabulary.modifiedAt)
    private LocalDateTime modifiedAt;
}

