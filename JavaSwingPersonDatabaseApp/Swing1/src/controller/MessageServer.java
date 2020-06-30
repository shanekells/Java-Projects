package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Message;

// This is a simulated message server

public class MessageServer implements Iterable<Message> {
	private Map<Integer, List<Message>> messages; // data collection to store messages
	private List<Message> selected; // list of messages

	public MessageServer() {
		selected = new ArrayList<Message>(); // selected is an ArrayList of messages
		messages = new TreeMap<Integer, List<Message>>(); // data structure that stores the messages

		List<Message> list = new ArrayList<Message>();
		// add messages to the data structure
		list.add(new Message("The cat is missing", "Have you seen Felix anywhere?"));
		list.add(new Message("See you later?", "Are we stil meeting in the pub?"));
		list.add(new Message("When is the football back?", "We really miss the Premier League!"));
		messages.put(0, list);

		list.add(new Message("How about dinner later", "Are you doing anything later?"));
		messages.put(1, list);
	}

	public void setSelectedServers(Set<Integer> servers) {
		selected.clear();

		for (Integer id : servers) {
			if (messages.containsKey(id)) {
				selected.addAll(messages.get(id));
			}
		}
	}

	public int getMessageCount() {
		return selected.size();
	}

	public Iterator<Message> iterator() {
		return new MessageIterator(selected);
	}
}

class MessageIterator implements Iterator { // MessageIterator that iterates through the messages

	private Iterator<Message> iterator;

	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Object next() {
		try {
			Thread.sleep(400); // slows down displaying messages
		} catch (InterruptedException e) {
		}

		return iterator.next();
	}

	public void remove() {
		iterator.remove();
	}
}
