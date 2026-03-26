package cz.cvut.fel.annotator.repository.model;

import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.net.URI;

@OWLClass(iri = Vocabulary.ThingWithAutoGenId)
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuperBuilder
@ToString
public class ThingWithAutoGenId extends Thing {
    @Id(generated = true)
    protected URI entityId;
}
