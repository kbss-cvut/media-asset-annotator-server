package cz.cvut.fel.annotator.repository.model;

import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.RDFS;
import cz.cvut.kbss.jopa.vocabulary.XSD;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.Thing)
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@ToString
public abstract class Thing
        implements Serializable {

    @Id
    protected URI entityId;

    @OWLDataProperty(iri = RDFS.LABEL, datatype = XSD.STRING, fetch = FetchType.EAGER)
    protected String label;

    @OWLAnnotationProperty(iri = cz.cvut.kbss.jopa.vocabulary.DC.Terms.DESCRIPTION)
    protected String description;

}