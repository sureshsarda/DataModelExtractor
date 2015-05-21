package trie.serial;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.RelationEntity;
import nlp.objects.Relationship;
import nlp.objects.Sentence;
import nlp.objects.Word;
import nlp.processing.EditDistance;
import nlp.processing.EditDistance.Operation;
import nlp.test.LookupResultObject;
import nlp.test.TestParagraph;
import nlp.test.TestSentence;

@XmlRootElement(name = "Model")
public class SerialTrie
{
	@XmlElement(name = "Branch", type = Branch.class)
	List<Branch> branches;

	public SerialTrie()
	{
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (Branch branch : branches)
		{
			sb.append(branch.toString("[%-4s] "));
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	public void saveAsXml(File file)
	{
		Marshaller marshaller = null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(SerialTrie.class);
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setEventHandler(new ValidationEventHandler()
			{
				@Override
				public boolean handleEvent(ValidationEvent event)
				{
					throw new RuntimeException(event.getMessage(), event.getLinkedException());
				}
			});
			marshaller.marshal(this, file);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void saveAsXml(String filename)
	{
		saveAsXml(new File(filename));
	}
	public static SerialTrie loadFromXml(String filename)
	{
		return loadFromXml(new File(filename));
	}
	public static SerialTrie loadFromXml(File file)
	{
		
		Unmarshaller unmarshaller = null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(SerialTrie.class);
			unmarshaller = jc.createUnmarshaller();

			unmarshaller.setEventHandler(new ValidationEventHandler()
			{
				@Override
				public boolean handleEvent(ValidationEvent event)
				{
					throw new RuntimeException(event.getMessage(), event.getLinkedException());
				}
			});
			
			SerialTrie trie = new SerialTrie();
			trie = (SerialTrie) unmarshaller.unmarshal(file);
			System.out.println("Branches Loaded: "  + trie.branches.size());
			return trie;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

//		this.branches = new ArrayList<Branch>();
//		this.branches.addAll(trie.branches);
		return null;
		
	}
	
	
	
	public static void updateTrie(String filename, TestParagraph para) {
		SerialTrie trie = loadFromXml(filename);
		for (TestSentence sent : para.getSentences())
		{
			if (trie.contains(sent) == false) {
				trie.add(sent);
			}
		}
		
		trie.saveAsXml(filename);
		System.out.println("Saved Branches: " + trie.branches.size());
	}
	
	
	
	private boolean contains(TestSentence sentence) {
		for (Branch branch : branches)
		{
			int cost = EditDistance.editDistance(sentence, branch);
			if (cost == 0) {
				return true;
			}
		}
		return false;
	}
	private void add(TestSentence sent) {
		sent.refresh();
		
		Branch branch = new Branch();
		branch.nodes = new ArrayList<SerialNode>();
		
		for (Word word : sent.getTokens())
		{
			SerialNode node = new SerialNode();
			node.tag = word.getTag();
			branch.nodes.add(node);
		}
		
		branch.leafInformation = new LeafNode();
		branch.leafInformation.setDataModel(sent.getDataModel());
		branch.leafInformation.sentences = new ArrayList<String>();
		branch.leafInformation.sentences.add(sent.getValue());
		
		this.branches.add(branch);
	}
	
	
	
	
	public void assignLookupResults(TestSentence sentence)
	{
		for (Branch branch : branches)
		{
			int cost = EditDistance.editDistance(sentence, branch);
			// if (cost < 10)
			{
				List<Operation> ops = EditDistance.editDistanceExtended(sentence, branch).second();
				Sentence sent_copy = EditDistance.updateWordIndexes(sentence, ops);

				Model model_copy = updateDataModel(sent_copy, branch.leafInformation.getDataModel());

				LookupResultObject obj = new LookupResultObject(cost, model_copy, branch);
				sentence.addLookupResult(obj);

				// System.out.println(branch.leafInformation.getDataModel());
				// System.out.println(branch.sentences.get(0));
				// System.out.println(branch);
				// System.out.println(sentence);
				// System.out.println(ops);
				// System.out.println("---------------");
			}
		}

	}

	public static Model updateDataModel(Sentence sent, Model model)
	{
		Model copy = null;
		try
		{
			copy = (Model) model.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Entity entity : copy.getEntities())
		{
			int wordId = entity.getWordIndex();
			int length = entity.getLength();
			entity.setName(getLabelWith(sent, wordId, length));
			for (Attribute attribute : entity.getAttributes())
			{
				wordId = attribute.getWordIndex();
				length = attribute.getLength();
				attribute.setName(getLabelWith(sent, wordId, length));
			}
		}

		for (Relationship relationship : copy.getRelationships())
		{
			int wordId = relationship.getWordIndex();
			int length = relationship.getLength();
			relationship.setName(getLabelWith(sent, wordId, length));
			for (RelationEntity relation : relationship.getConnects())
			{
				try
				{
					String name = relation.getLemmName();
					Entity ent = model.getEntityByName(name);
					Entity ent1 = copy.getEntityById(ent.getId());
					relation.setName(ent1.getLemmName());
				}
				catch (NullPointerException e)
				{
				}
			}
		}
		return copy;

	}
	private static String getLabelWith(Sentence sent, int wordIndex, int length)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<Word> itr = sent.getTokens().iterator();
		while (itr.hasNext())
		{
			Word curr = itr.next();
			if (curr.getId() == wordIndex)
			{
				sb.append(curr.getLemmatizedName() + " ");
				length--;
				break;
			}
			else if (wordIndex < curr.getId())
			{
				return "!Failure";
			}
			else
				;
		}
		while (length > 0 && itr.hasNext())
		{
			Word curr = itr.next();
			if (curr.getId() != -1)
			{
				sb.append(curr.getLemmatizedName() + " ");
				length--;
			}
		}

		return sb.toString();
	}

}
