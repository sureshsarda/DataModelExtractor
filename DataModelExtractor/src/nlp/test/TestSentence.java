package nlp.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.Relationship;
import nlp.objects.Sentence;
import util.logging.LoggerSetup;

public class TestSentence extends Sentence 
{
	private List<LookupResultObject> results = null;
	public static final int LIMIT = 4;


	public TestSentence(String sentValue)
	{
		super(sentValue);
		results = new ArrayList<LookupResultObject>();
		
		LoggerSetup.setup(logger);
		logger.setLevel(Level.WARNING);
	}

	
	public void addLookupResult(LookupResultObject obj)
	{	
		getResults().add(obj);
	}

	public void sortLookupResult()
	{
		Collections.sort(getResults());
	}
	public List<LookupResultObject> getLookupResults()
	{
		return getResults();
	}

	public void discardExtraResults(int limit)
	{
		if (getResults().size() > limit) {
			List<LookupResultObject> resultsCopy = new ArrayList<LookupResultObject>(limit);
			for (int i = 0; i < limit; i++) {
				resultsCopy.add(results.get(i));
			}
			results = resultsCopy;
		}
//			setResults(getResults().subList(0, limit));
		
	}

	public void updateResultIds()
	{
		int id = 0;
		for (LookupResultObject lookupResultObject : results)
		{
			lookupResultObject.id = id++;
		}
	}

	public List<LookupResultObject> getResults()
	{
		return results;
	}

	public void setResults(List<LookupResultObject> results)
	{
		this.results = results;
	}
	public int getMinCost()
	{
		return Collections.min(getResults()).getCost();
	}
	
	
	
	public void refresh() {
		Model model = this.DataModel;
		for (Iterator<Entity> entityItr = model.getEntities().iterator(); entityItr.hasNext();)
		{
			Entity ent = (Entity) entityItr.next();
			ent.setWordIndex(this.getWordId(ent.getName()));
			ent.setLength(ent.getName().split(" ").length);
			
			for (Iterator<Attribute> attributeItr = ent.getAttributes().iterator(); attributeItr.hasNext();)
			{
				Attribute attrib = (Attribute) attributeItr.next();
				attrib.setWordIndex(this.getWordId(attrib.getName()));
				attrib.setLength(attrib.getName().split(" ").length);
				
				if (attrib.getWordIndex() == -1) {
					logger.warning("Sentence does not have the required label. Removing.");
					attributeItr.remove();
				}
				
			}
			
			if (ent.getWordIndex() == -1) {
				logger.warning("Sentence does not have the required label. Removing.");
				entityItr.remove();
			}
			
			
		}

		for (Iterator<Relationship> relationshipItr = model.getRelationships().iterator(); relationshipItr.hasNext();)
		{
			Relationship relation = (Relationship) relationshipItr.next();
			
			relation.setWordIndex(this.getWordId(relation.getName()));
			relation.setLength(relation.getName().split(" ").length);
			
			if (relation.getWordIndex() == -1) {
				logger.warning("Sentence does not have the required label. Removing.");
				relationshipItr.remove();
			}
			
		}
	}
	
	
	private Logger logger = Logger.getLogger(TestSentence.class.getName());

}
