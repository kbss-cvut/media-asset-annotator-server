package cz.cvut.fel.annotator.shared.onto;

/**
 * Central registry of SPARQL query templates.
 */
public final class SparqlQueries {

    public static final String FIND_ALL = """
            SELECT DISTINCT ?x WHERE {
              GRAPH ?g {
                ?x a ?type .
              }
            }
            """;
    public static final String EXISTS = """
            ASK {
              ?x a ?type .
            }
            """;
    public static final String FIND_BY_PROPERTY = """
            SELECT DISTINCT ?x WHERE {
              GRAPH ?g {
                ?x a ?type ;
                   ?refProp ?value .
              }
            }
            """;
    public static final String FIND_ANNOTATIONS_BY_MEDIA_REFERENCE_ID = """
            SELECT ?ann WHERE {
              GRAPH ?g {
                ?media a ?mediaType ;
                       ?refProp ?refId ;
                       ?hasAnnotation ?ann .
              }
            }
            """;
    public static final String COUNT_ANNOTATIONS_BY_MEDIA_REFERENCE_ID = """
            SELECT ?refId (COUNT(?ann) AS ?count) WHERE {
              GRAPH ?g {
                ?media a ?mediaType ;
                       ?refProp ?refId ;
                       ?hasAnnotation ?ann .
              }
            }
            GROUP BY ?refId
            """;


    private SparqlQueries() {
    }
}