package cz.cvut.fel.annotator.repository.model;

import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.vocabulary.XSD;
import lombok.*;
import lombok.experimental.SuperBuilder;

@OWLClass(iri = Vocabulary.TextAnnotation)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
public class TextAnnotation extends Annotation {

    @OWLDataProperty(iri = Vocabulary.hasAnnotationType, simpleLiteral = true, fetch = FetchType.EAGER)
    private final String descriptor = Constants.Annotation.TEXT_ANNOTATION_DESCRIPTOR;
    @OWLDataProperty(iri = Vocabulary.hasText, simpleLiteral = true, datatype = XSD.STRING, fetch = FetchType.EAGER)
    private String text;
    @OWLDataProperty(iri = Vocabulary.hasFontSize, fetch = FetchType.EAGER)
    private Double fontSize;
    @OWLDataProperty(iri = Vocabulary.hasFontWeight, fetch = FetchType.EAGER)
    private Integer fontWeight;
}