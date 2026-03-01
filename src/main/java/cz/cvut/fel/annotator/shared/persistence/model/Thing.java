package cz.cvut.fel.annotator.shared.persistence.model;

import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.RDFS;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;
import java.util.Set;

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

    @Id(generated = true)
    protected URI entityId;

    @OWLAnnotationProperty(iri = cz.cvut.kbss.jopa.vocabulary.DC.Terms.DESCRIPTION)
    protected String description;

    @Properties(fetchType = FetchType.EAGER)
    protected Map<URI, Set<Object>> properties;

}