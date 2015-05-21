package nlp.test;

import java.util.ArrayList;
import java.util.List;

import nlp.objects.Model;
import nlp.processing.StanfordProcessor;
import trie.serial.SerialTrie;

public class TestParagraph
{
	private List<TestSentence> sentences;

	public TestParagraph()
	{
	}
	public TestParagraph(String paragraph)
	{
		setSentences(new ArrayList<TestSentence>());
		List<String> rawSentences = StanfordProcessor.getInstance().paragraphToSentences(paragraph);

		int id = 0;
		for (String sentence : rawSentences)
		{
			TestSentence tSent = new TestSentence(sentence);
			tSent.setId(id++);
			getSentences().add(tSent);
		}
	}

	public void generateLookupResults(SerialTrie sTrie)
	{
		for (TestSentence testSentence : getSentences())
		{
			sTrie.assignLookupResults(testSentence);
			testSentence.sortLookupResult();
			testSentence.discardExtraResults(4);
			testSentence.updateResultIds();
		}

	}

	public List<TestSentence> getSentences()
	{
		if (sentences == null)
		{
			sentences = new ArrayList<TestSentence>();
		}
		return sentences;
	}

	public void setSentences(List<TestSentence> sentences)
	{
		this.sentences = sentences;
	}

	public List<String> getSentenceList()
	{
		List<String> sents = new ArrayList<String>(this.sentences.size());
		for (TestSentence sent : this.sentences)
		{
			sents.add(sent.getValue());
		}

		return sents;
	}
	
	public TestSentence getSentenceByValue(String value) {
		int index = getSentenceList().indexOf(value);
		return this.sentences.get(index);
	}

	public List<Model> getModelAsList()
	{
		return getModelAsList(this.getSentences());
	}
	
	public static List<Model> getModelAsList(List<TestSentence> sentences) {
		List<Model> models = new ArrayList<Model>(sentences.size());
		for (TestSentence sentence : sentences)
		{
			if (sentence.getResults() != null)
				models.add(sentence.getResults().get(0).dataModel);		
			else
				models.add(sentence.getDataModel());
		}
		return models;
	}
	public void removeSentence(String name)
	{
		for (int i = 0; i < sentences.size(); i++)
		{
			if (sentences.get(i).getValue().equals(name) == true)
			{
				sentences.remove(i);
				break;
			}
		}
	}
	
	public void updateSentenceModel(String sentence, Model model) {
		int index = this.getSentenceList().indexOf(sentence);
		sentences.get(index).setDataModel(model);
	}
}
