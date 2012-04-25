package HelloDrools

import org.drools.builder.
         { KnowledgeBuilder
         , KnowledgeBuilderFactory
         , ResourceType }
import org.drools.io.ResourceFactory
import org.drools.logger.
         { KnowledgeRuntimeLogger
         , KnowledgeRuntimeLoggerFactory }
import org.drools.runtime.StatefulKnowledgeSession
import org.drools.
         { KnowledgeBase
         , KnowledgeBaseFactory }

object RulesEngine {
  val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder()
  kbuilder.add(ResourceFactory.
      newClassPathResource("DocMatcher.drl"), ResourceType.DRL)
  if(kbuilder.hasErrors())
    System.out.println( kbuilder.getErrors() )
  val kbase = KnowledgeBaseFactory.newKnowledgeBase()
  kbase.addKnowledgePackages(kbuilder.getKnowledgePackages())
}
