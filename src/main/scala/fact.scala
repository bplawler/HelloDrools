package HelloDrools

/**
 * The Document class represents the thing that I am putting into the 
 * rules engine that will be matched up with other documents.  In the real
 * world, this is backed by a document store or other persistence mechanism.
 */
class Document (val name: String, var isValidated: Boolean)
