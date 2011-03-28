package de.moonshade.osbe.analyse;

import java.util.ArrayList;
import java.util.ListIterator;

import de.moonshade.osbe.gui.GUI;

public class AnalyseStructureThread extends Thread {

	// private LinkedList<OsuLine> bufferedLines;
	private ArrayList<String> bufferedLines;
	private GUI gui;

	public AnalyseStructureThread(GUI gui) {
		// bufferedLines = new LinkedList<OsuLine>();
		bufferedLines = new ArrayList<String>();
		this.gui = gui;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			String[] lines = gui.getContentArea().getText().split("\n");
			int bufferedSize = bufferedLines.size();
			ListIterator<String> currentIterator = bufferedLines.listIterator();

			ArrayList<String> newLines = new ArrayList<String>();

			int bufferedPosition = 0;
			for (int a = 0; a < lines.length; a++) {
				if (bufferedPosition > bufferedSize - 1) {
					newLines.add(lines[a]);
					System.out.println("neu ende: " + lines[a]);
					// hier schließt sich eine evt. aufwändige Analyse des
					// Objekts
					// an, daher sollte sie nur bei neuen Zeilen erfolgen
				} else {
					if (bufferedLines.get(bufferedPosition).equals(lines[a])) {
						// buffered Zeile und echte Zeile stimmen überein
						newLines.add(bufferedLines.get(bufferedPosition));
						bufferedPosition++;
						System.out.println("gleich: " + lines[a]);
					} else {
						// buffered Zeile und echte Zeile stimmen nicht überein
						// Um Zeit zu sparen wird nach einem bereits gebufferten
						// Objekt gesucht
						int searchBuffered = bufferedLines.indexOf(lines[a]);
						if (searchBuffered == -1) {
							// Es wurde kein gebuffertes Objekt gefunden, also
							// muss
							// ein neues hinzugefügt werden
							System.out.println("neu: " + lines[a]);
							newLines.add(lines[a]);
							// hier schließt sich eine evt. aufwändige Analyse
							// des
							// Objekts an, daher sollte sie nur bei neuen Zeilen
							// erfolgen

						} else {
							// Es wurde ein gebuffertes Objekt gefunden (Zeilen
							// wurden gelöscht). Es kann einfach übernommen
							// werden
							newLines.add(bufferedLines.get(searchBuffered));
							bufferedPosition = searchBuffered +1;
							System.out.println("gleiches gefunden: " + lines[a]);
						}
					}
				}
			}

			bufferedLines = newLines;

			try {
				sleep(3000);
				System.out.println("zeilen gebuffert: " + bufferedLines.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Der Thread wurde beendet!");

		/*
		 * 
		 * for (int a = 0; a < lines.length; a++) { if
		 * (!currentIterator.hasNext()) { // Wenn am Ende neue Zeilen
		 * hinzugefügt wurden, werden sie jetzt gebuffert
		 * currentIterator.add(lines[a]); //bufferedIterator.next(); } else { if
		 * (!currentIterator.next().equals(lines[a])) {
		 * currentIterator.previous(); List<String> tempList =
		 * bufferedLines.subList(currentIterator.nextIndex() +1,
		 * bufferedLines.size() -1); if (tempList.indexOf(lines[a]) == -1) { //
		 * An dieser Stelle wurde also etwas eingefügt } else { // An dieser
		 * Stelle wurde also etwas }
		 * 
		 * } } }
		 */
		/*
		 * bufferedSize = bufferedLines.size(); if (bufferedSize > lines.length)
		 * { for (int a = lines.length; a < bufferedSize; a++) {
		 * bufferedLines.remove(lines.length); } }
		 */

	}

}
