package HelloDrools

import org.drools.event.rule.{ DefaultWorkingMemoryEventListener
                             , WorkingMemoryEvent
                             , ObjectInsertedEvent
                             , ObjectUpdatedEvent
                             , ObjectRetractedEvent}

class HelloEventListener extends DefaultWorkingMemoryEventListener {
  override def objectInserted(e: ObjectInsertedEvent) = 
    objectTouched(e, "inserted")

  override def objectRetracted(e: ObjectRetractedEvent) = 
    objectTouched(e, "retracted")

  override def objectUpdated(e: ObjectUpdatedEvent) = 
    objectTouched(e, "updated")

  def objectTouched(e: WorkingMemoryEvent, s: String) = {
    {
      e match {
        case e: ObjectInsertedEvent => e.getObject()
        case e: ObjectRetractedEvent => e.getOldObject()
        case e: ObjectUpdatedEvent => e.getObject()
      } 
    } match {
      case d: Document => 
        System.out.println("%s document %s".format(s, d.name))
      case d: MatchableDocument => 
        System.out.println("%s matchable document %s".format(s, d.doc.name))
      case m: Match => 
        System.out.println("%s match from %s to %s".
                           format(s, m.root.doc.name, m.dep.doc.name))
    }
  }
}
