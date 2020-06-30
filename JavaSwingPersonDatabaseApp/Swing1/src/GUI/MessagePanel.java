package GUI;

import java.awt.BorderLayout;			//imports
import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controller.MessageServer;
import model.Message;

public class MessagePanel extends JPanel implements ProgressDialogListener{		//message panel class

	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;
	private ProgressDialog progressDialog;			//declare components

	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	private SwingWorker<List<Message>, Integer> worker;
	
	private TextPanel textPanel;
	private JList messageList;
	private JSplitPane lowerPane;
	private JSplitPane upperPane;
	private DefaultListModel  messageListModel;
	
	public MessagePanel(JFrame parent) {

		messageListModel = new DefaultListModel();
		progressDialog = new ProgressDialog(parent, "Messages Downloading...");		//initialize components
		messageServer = new MessageServer();
		
		progressDialog.setListener(this);
		
		selectedServers = new TreeSet<Integer>();

		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();

		serverTree = new JTree(createTree());		//create server tree as editable
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);

		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);//selecting one tree node at a time.
		
		messageServer.setSelectedServers(selectedServers);
		
		// listening to clicks on the checkbox branches
		treeCellEditor.addCellEditorListener(new CellEditorListener() {

			public void editingStopped(ChangeEvent e) {
				ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue(); // get server info of checkbox
																					// clicked

				int serverId = info.getId();

				if (info.isChecked()) { // if checked then add server id to the list
					selectedServers.add(serverId);
				} else {
					selectedServers.remove(serverId);
				}

				messageServer.setSelectedServers(selectedServers);

				retrieveMessages();

			}

			public void editingCanceled(ChangeEvent e) {
			}
		});

		setLayout(new BorderLayout());
		
		textPanel = new TextPanel();
		messageList = new JList(messageListModel);
		messageList.setCellRenderer(new MessageListRenderer());
		
		messageList.addListSelectionListener(new ListSelectionListener() {		//add listener to listen for message selection
			public void valueChanged(ListSelectionEvent e) {
				Message message = (Message)messageList.getSelectedValue();
			
				textPanel.setText(message.getContents());		//add the selected message to the textPanel
			}
			
		});
		
		lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,new JScrollPane(messageList), textPanel);		//have the panes on each other vertically
		upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,new JScrollPane(serverTree), lowerPane);		//server tree on top, lowerPane on bottom
		
		textPanel.setMinimumSize(new Dimension(10,100));
		messageList.setMinimumSize(new Dimension(10,100));
		
		upperPane.setResizeWeight(0.5);		//divide the panes equally
		lowerPane.setResizeWeight(0.5);
		
		add(upperPane, BorderLayout.CENTER);
	}
	
	public void refresh() {
		retrieveMessages();
	}

	private void retrieveMessages() {

		progressDialog.setMax(messageServer.getMessageCount()); // sets the max no of messages waiting to be retrieved

		progressDialog.setVisible(true);

		worker = new SwingWorker<List<Message>, Integer>() { // multithreading

			protected List<Message> doInBackground() throws Exception {

				List<Message> retrievedMessages = new ArrayList<Message>();

				int count = 0; // count the amount of messages

				for (Message message : messageServer) {
					
					if(isCancelled()) break;		//if thread is cancelled then break.
					
					retrievedMessages.add(message);
					count++;

					publish(count); // this invokes process and will receive count
				}
				return retrievedMessages;
			}

			protected void process(List<Integer> counts) { // process method get feedback from SwingWorker
				int retrieved = counts.get(counts.size() - 1);
				progressDialog.setValue(retrieved); // this will set the progress according to the no of messages
													// retrieved
			}

			protected void done() { // done is called when thread finishes
				progressDialog.setVisible(false);
				
				if(isCancelled()) return;
				
				try {
					List<Message> retrievedMessages = get(); // get will return the specified type
					messageListModel.removeAllElements();		// remove contents of list
					
					for(Message message: retrievedMessages) {
						messageListModel.addElement(message);	//add messages to list
					}
					
					messageList.setSelectedIndex(0);
					
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};

		worker.execute();
	}

	private DefaultMutableTreeNode createTree() {		//creates tree structure
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Services");

		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(
				new ServerInfo("New York", 0, selectedServers.contains(0)));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(
				new ServerInfo("Boston", 1, selectedServers.contains(1)));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(
				new ServerInfo("Los Angeles", 2, selectedServers.contains(2)));

		branch1.add(server1);
		branch1.add(server2);
		branch1.add(server3);

		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("Ireland");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(
				new ServerInfo("Dublin", 3, selectedServers.contains(3)));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(
				new ServerInfo("Sligo", 4, selectedServers.contains(4)));
		DefaultMutableTreeNode server6 = new DefaultMutableTreeNode(
				new ServerInfo("Galway", 5, selectedServers.contains(5)));

		branch2.add(server4);
		branch2.add(server5);
		branch2.add(server6);

		top.add(branch1);
		top.add(branch2);

		return top;
	}

	private ImageIcon createIcon(String path) {		//creates icon for component

		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url);

		return icon;
	}

	public void progressDialogCancelled() {
		if(worker != null) {
			worker.cancel(true);		//cancel the thread if its been executed
		}
	}
}
