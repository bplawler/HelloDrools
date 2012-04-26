package HelloDrools

import org.specs2.mutable._
import scala.collection.JavaConversions._

object FirstStepsTest extends Specification {

  "Creating a simple knowledge session" should {
    sequential

    val doc1 = new Document("A")
    val session = HelloDrools.RulesEngine.kbase.newStatefulKnowledgeSession()

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

  "Rudimentary matching" should {
    sequential

    val docs = List(new Document("A", "root"), 
                    new Document("B"), new Document("C"),
                    new Document("D"), new Document("E"))

    val session = HelloDrools.RulesEngine.kbase.newStatefulKnowledgeSession()

    "create new documents, insert them into the knowledge base" in {
      docs foreach { d: Document => d.factHandle = session.insert(d) }
      session.fireAllRules() must be equalTo(0)
      session.getFactCount() must be equalTo(5)
    }

    "set up some test data, but still no matches" in {
      val r = new scala.util.Random()
      // set foo to a random string in all docs...
      docs foreach ( (d:Document) => {
        d.args += ("foo" -> r.nextString(5))
        session.update(d.factHandle, d)
      } ) 
      session.fireAllRules() must be equalTo(0)
    }

    "create a potential match, but still not valid docs" in {
      // but go back and make 2 of them actually match.
      List(0, 3) map docs foreach ( (d:Document) => {
        d.args += ("foo" -> "MATCH")
        session.update(d.factHandle, d)
      } ) 
      session.fireAllRules() must be equalTo(0)
    }

    "change the docs to put them into a valid state" in {
      val r = new scala.util.Random()
      docs foreach ( (d:Document) => {
        d.args += ("bar" -> r.nextString(5))
        session.update(d.factHandle, d)
      } ) 
      session.fireAllRules() must be equalTo(
        docs.size /* valid docs */ + 1 /* matches */)
      session.getFactCount() must be equalTo(
        docs.size /* documents */ + 
        docs.size /* matchable documents */ +
        1         /* matches */)
    }

    "change the state of one end of our match" in {
      docs(3).args += ("foo" -> "NOMATCH")
      session.update(docs(3).factHandle, docs(3))
      session.fireAllRules() must be equalTo(1 /* valid doc fires */)
      session.getFactCount() must be equalTo(
        docs.size /* documents */ + 
        docs.size /* matchable documents */ +
        1 - 1     /* lost our match */)
    }

    "with the match back in, invalidate a document" in {
      docs(3).args += ("foo" -> "MATCH")
      session.update(docs(3).factHandle, docs(3))
      session.fireAllRules() must be equalTo(2 /* valid doc, match fires */)
      session.getFactCount() must be equalTo(
        docs.size /* documents */ + 
        docs.size /* matchable documents */ +
        1         /* matches */)

      docs(3).args -= "bar"
      session.update(docs(3).factHandle, docs(3))
      session.fireAllRules() must be equalTo(0 /* doc no longer valid */)
      session.getFactCount() must be equalTo(
        docs.size     /* documents */ + 
        docs.size - 1 /* matchable documents */ +
        0             /* no matches, since half the match was invalidated */)

    }
  }
}
