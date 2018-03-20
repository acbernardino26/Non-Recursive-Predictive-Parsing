import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;

public class index {

	private JFrame frame;
	private JTextField textField;
	private FileInputStream fstream;
	private DataInputStream in;
	private BufferedReader br;
	private Vector prod = new Vector();
	private Vector parseTable = new Vector();
	private JLabel Production_FileName,Parse_Filename,Load_Filename,Result;
	private tableComponents tables;
	String name, directory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					index window = new index();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public index() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(192, 192, 192));
		frame.setBackground(new Color(173, 216, 230));
		frame.setBounds(100, 100, 584, 750);
		frame.setExtendedState(JFrame.MAXIMIZED_VERT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//LABELS
		//Production Labels
		JLabel Production = new JLabel("Production:");
		Production.setFont(new Font("Tahoma", Font.BOLD, 15));
		Production.setBounds(10, 11, 166, 24);
		frame.getContentPane().add(Production);
		
		Production_FileName = new JLabel("");
		Production_FileName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Production_FileName.setBounds(10, 46, 166, 29);
		frame.getContentPane().add(Production_FileName);
		
		//Parse Labels
		JLabel Parse = new JLabel("Parse Table:");
		Parse.setFont(new Font("Tahoma", Font.BOLD, 15));
		Parse.setBounds(242, 11, 212, 24);
		frame.getContentPane().add(Parse);
		
		Parse_Filename = new JLabel("");
		Parse_Filename.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Parse_Filename.setBounds(242, 46, 179, 24);
		frame.getContentPane().add(Parse_Filename);
		
		//Load Labels
		JLabel Load = new JLabel("Loaded:");
		Load.setFont(new Font("Tahoma", Font.BOLD, 15));
		Load.setBounds(242, 278, 67, 29);
		frame.getContentPane().add(Load);
		
		Load_Filename = new JLabel("");
		Load_Filename.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Load_Filename.setBounds(308, 278, 160, 24);
		frame.getContentPane().add(Load_Filename);
		
		//Input Labels
		JLabel Input = new JLabel("INPUT:");
		Input.setFont(new Font("Tahoma", Font.BOLD, 15));
		Input.setBounds(10, 318, 59, 29);
		frame.getContentPane().add(Input);
		
		//Parsing Labels
		JLabel lblParsing = new JLabel("PARSING:");
		lblParsing.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblParsing.setBounds(10, 354, 76, 29);
		frame.getContentPane().add(lblParsing);
		
		Result = new JLabel("");
		Result.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Result.setBounds(96, 354, 462, 29);
		frame.getContentPane().add(Result);
		
		//PANELS
		
		JPanel Production_Table = new JPanel();
		Production_Table.setBackground(new Color(176, 224, 230));
		Production_Table.setBounds(10, 86, 212, 221);
		frame.getContentPane().add(Production_Table);
		
		JPanel Parse_Table = new JPanel();
		Parse_Table.setBackground(Color.CYAN);
		Parse_Table.setForeground(Color.BLACK);
		Parse_Table.setBounds(242, 86, 314, 181);
		frame.getContentPane().add(Parse_Table);
		
		JButton btnLoad = new JButton("LOAD");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenProduction();
				tableComponents;
			}
		});
		
		JPanel Output_Table = new JPanel();
		Output_Table.setBackground(Color.BLUE);
		Output_Table.setForeground(Color.BLACK);
		Output_Table.setBounds(10, 388, 548, 312);
		frame.getContentPane().add(Output_Table);
		btnLoad.setBackground(new Color(100, 149, 237));
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLoad.setBounds(467, 279, 89, 29);
		frame.getContentPane().add(btnLoad);
		
		JButton btnParse = new JButton("PARSE");
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnParse.setBackground(new Color(100, 149, 237));
		btnParse.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnParse.setBounds(467, 315, 89, 29);
		frame.getContentPane().add(btnParse);
		
		textField = new JTextField();
		textField.setBounds(75, 318, 379, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
	
	/*Initiates the JFileChooser file browser and returns the chosen file.*/
	public File Browser() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents","prod");
		JFileChooser chooser = new JFileChooser();
		System.out.print(filter.getDescription());
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		name=f.getName();
		directory=f.getAbsolutePath();
        return f;
    }
	
	/*Collects the input file of the productions and converting content into Streams*/
	public void OpenProduction() {
		try {
    		fstream = new FileInputStream(Browser());
    		if(fstream!=null){
    			in = new DataInputStream(fstream);
        		br = new BufferedReader(new InputStreamReader(in));	//to make input stream easily readable 
        		Production_FileName.setText(name);
        		Load_Filename.setText(name);
        		loadProduction();
    		} 		  
    	} 
        catch (Exception e) {
        	System.err.println(e);
    	}
		
	}
	
	/*Prints the input file of production from the chosen file in the screen*/
	public void loadProduction(){
    	String str;
    	br = new BufferedReader(new InputStreamReader(in));
    	System.out.println("\nProductions:");
    	try {
			while ((str = br.readLine()) != null) {
				prod.addElement(str);
				System.out.print(" "+str);
			}

			System.out.println("\n\nProd = " + prod + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	OpenPTbl();
    }
	
	/*Collects the input file of the Parse Table and converting content into Streams*/
	public void OpenPTbl() {
		String PTName,PTDir;
		int dotPos = directory.lastIndexOf(".");
		int dot = name.lastIndexOf(".");
  	  	PTDir = directory.substring(0, dotPos)+".ptbl";
  	  	PTName = name.substring(0, dot)+".ptbl";
		try {
    		fstream = new FileInputStream(PTDir);
    		if(fstream!=null){
    			in = new DataInputStream(fstream);
        		br = new BufferedReader(new InputStreamReader(in));	//to make input stream easily readable 
        		Parse_Filename.setText(PTName);
        		loadPTbl();
    		} 		  
    	} 
        catch (Exception e) {
        	System.err.println(e);
    	}
	}
	
	/*Prints the input file of parse table from the chosen file in the screen*/
	public void loadPTbl() {
    	String str;
    	br = new BufferedReader(new InputStreamReader(in));
    	System.out.println("Parse table");
    	try {
			while ((str = br.readLine()) != null) {
				parseTable.addElement(str);
				System.out.print(" "+str);
			}

			System.out.println("\n\nParse Table = " + parseTable + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}