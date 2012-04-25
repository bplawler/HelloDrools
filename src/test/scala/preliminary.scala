package HelloDrools

import org.specs2.mutable._

object FirstStepsTest extends Specification {
  val doc1 = new Document("A")
  val session = HelloDrools.RulesEngine.kbase.newStatefulKnowledgeSession()

  "Creating a simple knowledge session" should {
    sequential

    "create new documents, insert them into the knowledge base" in {
      doc1.factHandle = session.insert(doc1)
      session.fireAllRules() must be equalTo(0)
      session.getFactCount() must be equalTo(1)
    }

    "validate the documents and see the Matchable fact inserted" in {
      doc1.args += ("foo" -> "12", "bar" -> "12")
      session.update(doc1.factHandle, doc1)
      session.fireAllRules() must be equalTo(1)
      session.getFactCount() must be equalTo(2)
    }

    "invalidate the document, and verify the Matchable fact is removed" in {
      doc1.args -= "foo"
      session.update(doc1.factHandle, doc1)
      session.fireAllRules() must be equalTo(0)
      session.getFactCount() must be equalTo(1)
    }
  }
}
