package cz.cvut.fel.annotator.repository.model;

import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.vocabulary.XSD;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.Annotation)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
public abstract class Annotation extends ThingWithAutoGenId implements Serializable {

    @OWLDataProperty(iri = Vocabulary.hasColor, simpleLiteral = true, datatype = XSD.STRING, fetch = FetchType.EAGER)
    protected String color;

    @OWLDataProperty(iri = Vocabulary.hasOpacity, fetch = FetchType.EAGER)
    protected Double opacity;

    @OWLDataProperty(iri = Vocabulary.hasStartTime, fetch = FetchType.EAGER)
    protected Double startTime;

    @OWLDataProperty(iri = Vocabulary.hasEndTime, fetch = FetchType.EAGER)
    protected Double endTime;

    @OWLDataProperty(iri = Vocabulary.hasGeometryPoints, simpleLiteral = true, datatype = XSD.STRING, fetch = FetchType.EAGER)
    protected String geometryPoints;

}
