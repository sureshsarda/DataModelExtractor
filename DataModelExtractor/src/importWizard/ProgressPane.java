/**
 * ProgressPane: Progress Pane actually extracts the Entity-Relationship
 * information from text using Stanford processor. Refer the algorithm to how it
 * is achieved. Briefly, there is a progress bar and extraction is done in
 * background.
 * 
 * @author SureshSarda
 */

package importWizard;

import java.awt.Dimension;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import nlp.objects.TagDataLoader;
import nlp.processing.StanfordProcessor;
import nlp.test.LookupResultObject;
import nlp.test.TestParagraph;
import nlp.test.TestSentence;
import trie.serial.SerialTrie;

@SuppressWarnings("serial")
public class ProgressPane extends ImportWizardPageTemplate
{
	JProgressBar progressbar;
	String text;

	static String trieFile = "data/model.xml";

	TestParagraph para;

	public ProgressPane()
	{
		super();
		super.initComponents();
		nextButton.setText("Finish");

		titleLabelMessage("Loading Part-Of-Speech Tagger");

		progressbar = new JProgressBar();
		progressbar.setIndeterminate(true);
		progressbar.setPreferredSize(new Dimension(500, 20));

		center.add(progressbar);
		backgroundWorker();

	}

	private void backgroundWorker()
	{
		SwingWorker<TestParagraph, Void> worker = new SwingWorker<TestParagraph, Void>()
		{

			public List<TestSentence> processSentencesParallel(List<String> sentences,
					SerialTrie trie) throws InterruptedException, ExecutionException
			{

				int threads = Runtime.getRuntime().availableProcessors();
				ExecutorService service = Executors.newFixedThreadPool(threads);

				List<Future<TestSentence>> futures = new ArrayList<Future<TestSentence>>();
				for (final String sent : sentences)
				{
					Callable<TestSentence> callable = new Callable<TestSentence>()
					{
						public TestSentence call() throws Exception
						{
							TestSentence output = new TestSentence(sent);
							trie.assignLookupResults(output);
							output.sortLookupResult();
							output.discardExtraResults(4);
							return output;
						}
					};
					futures.add(service.submit(callable));
				}

				service.shutdown();

				int id = 0;
				List<TestSentence> outputs = new ArrayList<TestSentence>();
				for (Future<TestSentence> future : futures)
				{
					future.get().setId(id++);
					outputs.add(future.get());
				}
				return outputs;
			}

			@Override
			protected TestParagraph doInBackground() throws Exception
			{
				// profiling purposes
				long startTime = System.currentTimeMillis();

				// Load Stanford processor
				StanfordProcessor instance = StanfordProcessor.getInstance();

				titleLabelMessage("Parsing document text");
				List<String> rawSentences = instance.paragraphToSentences(text);

				titleLabelMessage("Loading tagging model");
				SerialTrie trie = SerialTrie.loadFromXml(trieFile);
				TagDataLoader.getInstance().load();

				para = new TestParagraph();

				titleLabelMessage("Tagging " + (rawSentences.size() + 1) + " sentences");
				para.setSentences(processSentencesParallel(rawSentences, trie));
				titleLabelMessage("Tagging Complete");

				// enable/disable components when tagging is complete
				nextButton.setEnabled(true);
				nextButton.requestFocus();
				progressbar.setIndeterminate(false);
				progressbar.setValue(100);

				// For profiling purposes
				long endTime = System.currentTimeMillis();
				long timeRequired = endTime - startTime;
				System.out.println("Total Time Required: " + timeRequired);
				System.out.println("Time per sentence: " + timeRequired
						/ para.getSentences().size());

				return para;

			}
			/*
			 * @Override protected TestParagraph doInBackground() throws
			 * Exception { long startTime = System.currentTimeMillis();
			 * StanfordProcessor instance = StanfordProcessor.getInstance();
			 * 
			 * titleLabelMessage("Parsing document text");
			 * 
			 * List<String> rawSentences = instance.paragraphToSentences(text);
			 * 
			 * titleLabelMessage("Loading tagging model"); SerialTrie trie =
			 * SerialTrie.loadFromXml(trieFile);
			 * TagDataLoader.getInstance().load();
			 * 
			 * progressbar.setIndeterminate(false); para = new TestParagraph();
			 * titleLabelMessage("Tagging " + (rawSentences.size() + 1) +
			 * " sentences"); for (int i = 0; i < rawSentences.size(); i++) {
			 * //Update progress bar int val = (i * 100 )/ rawSentences.size();
			 * progressbar.setValue(val);
			 * 
			 * // TestSentence tSent = new TestSentence(rawSentences.get(i));
			 * tSent.setId(i); trie.assignLookupResults(tSent);
			 * tSent.sortLookupResult(); tSent.discardExtraResults(4);
			 * 
			 * para.getSentences().add(tSent); } progressbar.setValue(100);
			 * titleLabelMessage("Tagging Complete");
			 * 
			 * System.setOut(new PrintStream(new
			 * File("C://Users//SureshSarda//Desktop//file.txt"))); for
			 * (TestSentence sent : para.getSentences()) {
			 * System.out.println(sent.getValue()); for (LookupResultObject obj
			 * : sent.getLookupResults()) { System.out.println(obj); }
			 * System.out.println(
			 * "---------------------------------------------------------------------------"
			 * ); }
			 * 
			 * long endTime = System.currentTimeMillis(); long timeRequired =
			 * endTime - startTime;
			 * 
			 * System.out.println("Total Time Required: " + timeRequired);
			 * System.out.println("Time per sentence: " + timeRequired /
			 * para.getSentences().size());
			 * 
			 * nextButton.setEnabled(true); nextButton.requestFocus();
			 * 
			 * return para; }
			 */
		};
		worker.execute();
	}

	protected TestParagraph getTestParagraph()
	{
		return para;
	}

	private void titleLabelMessage(String message)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
		sb.append("<b>Hang tight! Extracting Entity-Relationship information</b>");
		// sb.append("<p>Import wizard lets you extract entity-relation diagram from a text document. Browse for a file or start typing text.</p>");
		sb.append("<br>");
		sb.append("<br>");

		sb.append("<p>");
		sb.append(message);
		sb.append("</p>");

		sb.append("</body></html>");

		title.setText(sb.toString());
	}
}
