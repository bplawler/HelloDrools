package HelloDrools

/**
 * The Document class represents the thing that I am putting into the 
 * rules engine that will be matched up with other documents.  In the real
 * world, this is backed by a document store or other persistence mechanism.
 */
class Document (val name: String, val docType: String = "dependent") {
  var factHandle: org.drools.runtime.rule.FactHandle = null

  private val requiredFields = Set("foo", "bar")

  val args = scala.collection.mutable.Map[String, String]()

  def checkValid = requiredFields subsetOf args.keySet
}

abstract class MatchableDocument(val doc:Document)

class MatchableRootDocument(doc: Document) extends MatchableDocument(doc)

class MatchableDependentDocument(doc: Document) extends MatchableDocument(doc)

class Match(val root: MatchableRootDocument, 
            val dep: MatchableDependentDocument)
