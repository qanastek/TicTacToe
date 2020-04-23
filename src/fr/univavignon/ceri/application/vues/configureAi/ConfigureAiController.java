package fr.univavignon.ceri.application.vues.configureAi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.univavignon.ceri.application.config.DefaultModel;
import fr.univavignon.ceri.application.services.GenerateDataset;
import fr.univavignon.ceri.application.services.Threads.HardTrain;
import fr.univavignon.ceri.application.services.Threads.MediumTrain;
import fr.univavignon.ceri.application.services.Threads.RunningThreads;

public class ConfigureAiController implements Initializable {
		
	/**
	 * Medium
	 */
	@FXML
	private VBox progressionMedium;
	
	@FXML
	private ProgressBar progressBarMedium;
	
	@FXML
	private Label progressTextMedium;
	
	@FXML
	private TextField learningRateMedium;
	
	@FXML
	private TextField epochsMedium;
	
	@FXML
	private TextField batchSizeMedium;
	
	/**
	 * Hard
	 */
	@FXML
	private VBox progressionHard;
	
	@FXML
	private ProgressBar progressBarHard;
	
	@FXML
	private Label progressTextHard;
	
	@FXML
	private TextField learningRateHard;
	
	@FXML
	private TextField epochsHard;
	
	@FXML
	private TextField batchSizeHard;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Initialize the GUI
		this.initGui();
	}

    /**
	 * Initialize the GUI
	 */
	private void initGui() {
		
		System.out.println("init gui");
		
		File file = new File(DefaultModel.FILE_NAME);
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
		        .newInstance();
		
		DocumentBuilder documentBuilder;
		Document document = null;
		
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			this.loadDefaultsSettings();
			return;
		}
		
		// Check if empty
		if (document != null) {
			
			/**
			 * Root
			 */
			Element root = (Element) document.getElementsByTagName("models").item(0);	
			
			// Check if empty
			if (root == null) {
				System.out.println("Root null");
				this.loadDefaultsSettings();
				return;
			}
			
			/**
			 * Medium
			 */
			Element medium = (Element) root.getElementsByTagName("medium").item(0);	
						
			this.learningRateMedium.setText(medium.getElementsByTagName(DefaultModel.LEARNING_RATE).item(0).getTextContent());
			this.epochsMedium.setText(medium.getElementsByTagName(DefaultModel.EPOCHS).item(0).getTextContent());
			this.batchSizeMedium.setText(medium.getElementsByTagName(DefaultModel.BATCH_SIZE).item(0).getTextContent());
			
			/**
			 * Hard
			 */
			Element hard = (Element) root.getElementsByTagName("hard").item(0);	
			
			this.learningRateHard.setText(hard.getElementsByTagName(DefaultModel.LEARNING_RATE).item(0).getTextContent());
			this.epochsHard.setText(hard.getElementsByTagName(DefaultModel.EPOCHS).item(0).getTextContent());
			this.batchSizeHard.setText(hard.getElementsByTagName(DefaultModel.BATCH_SIZE).item(0).getTextContent());
			
		} else {
			this.loadDefaultsSettings();
			return;
		}

	}
    
	/**
	 * Load the default settings
	 */
	private void loadDefaultsSettings() {

		/**
		 * Medium
		 */
		this.learningRateMedium.setText(Double.toString(DefaultModel.LEARNING_RATE_MEDIUM));
		this.epochsMedium.setText(Integer.toString(DefaultModel.EPOCHS_MEDIUM));
		this.batchSizeMedium.setText(Integer.toString(DefaultModel.BATCH_SIZE_MEDIUM));
		
		/**
		 * Hard
		 */
		this.learningRateHard.setText(Double.toString(DefaultModel.LEARNING_RATE_HARD));
		this.epochsHard.setText(Integer.toString(DefaultModel.EPOCHS_HARD));
		this.batchSizeHard.setText(Integer.toString(DefaultModel.BATCH_SIZE_HARD));
		
	}

	/**
	 * Generate the dataset
	 * @param event {@code ActionEvent} Event
	 */
    @FXML
    void generateDataSet(ActionEvent event) {
    	
    	// Generate the dataset
		GenerateDataset.Generate(1000);
    }
    
    /**
     * Reset the medium model settings
     * @param event {@code ActionEvent} Event
     */
    @FXML
    void resetMedium(ActionEvent event) {
    	
		/**
		 * Medium
		 */
		this.learningRateMedium.setText(Double.toString(DefaultModel.LEARNING_RATE_MEDIUM));
		this.epochsMedium.setText(Integer.toString(DefaultModel.EPOCHS_MEDIUM));
		this.batchSizeMedium.setText(Integer.toString(DefaultModel.BATCH_SIZE_MEDIUM));
    }
    
    /**
     * Reset the hard model settings
     * @param event {@code ActionEvent} Event
     */
    @FXML
    void resetHard(ActionEvent event) {
		
		/**
		 * Hard
		 */
		this.learningRateHard.setText(Double.toString(DefaultModel.LEARNING_RATE_HARD));
		this.epochsHard.setText(Integer.toString(DefaultModel.EPOCHS_HARD));
		this.batchSizeHard.setText(Integer.toString(DefaultModel.BATCH_SIZE_HARD));
    }

    /**
     * Train the medium difficulty model
     * @param event {@code ActionEvent}
     */
    @FXML
	void trainMedium(ActionEvent event) {
    	
    	// Save
    	this.save();
    	
    	System.out.println("Train medium");
    	
    	// Set visible
    	this.progressionMedium.setVisible(true);
    	
    	// If is running stop It else run It
    	if (RunningThreads.MEDIUM_TRAIN != null && RunningThreads.MEDIUM_TRAIN.isRunning() == true) {
    		RunningThreads.MEDIUM_TRAIN.cancel();			
    	} else {
    		RunningThreads.MEDIUM_TRAIN = new MediumTrain();
    	}
    	
    	// Bind progress
    	progressBarMedium.progressProperty().bind(RunningThreads.MEDIUM_TRAIN.progressProperty());
    	progressTextMedium.textProperty().bind(RunningThreads.MEDIUM_TRAIN.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.MEDIUM_TRAIN).start();
    }

	/**
     * Train the hard difficulty model
     * @param event {@code ActionEvent}
     */
    @FXML
    void trainHard(ActionEvent event) {
    	
    	// Save
    	this.save();
    	
    	System.out.println("Train hard");
    	
    	// Set visible
    	this.progressionHard.setVisible(true);
    	
    	// If is running stop It else run It
    	if (RunningThreads.HARD_TRAIN != null && RunningThreads.HARD_TRAIN.isRunning() == true) {
    		RunningThreads.HARD_TRAIN.cancel();			
    	} else {
    		RunningThreads.HARD_TRAIN = new HardTrain();
    	}
    	
    	// Bind progress
    	progressBarHard.progressProperty().bind(RunningThreads.HARD_TRAIN.progressProperty());
    	progressTextHard.textProperty().bind(RunningThreads.HARD_TRAIN.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.HARD_TRAIN).start();
    }

    /**
	 * Save the current model into the settings file
	 */
	private void save() {	

    	// TODO: Save both currents models
		
    	System.out.println("Save medium and hard");
    	
    	Document dom;
    	
        Element root = null;
        
        Element e = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
        	
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();
            
            root = dom.createElement("models");

            /**
             * Medium
             */
            Element medium = dom.createElement("medium");
        	
            /**
             * Learning rate
             */
            e = dom.createElement(DefaultModel.LEARNING_RATE);

            if (this.learningRateMedium.getText().isEmpty() != true) {
                e.appendChild(dom.createTextNode(this.learningRateMedium.getText()));                
			} else {
				e.appendChild(dom.createTextNode(Double.toString(DefaultModel.LEARNING_RATE_MEDIUM)));   
			}
            medium.appendChild(e);
            
            /**
             * Epochs
             */
            e = dom.createElement(DefaultModel.EPOCHS);
            if (this.epochsMedium.getText().isEmpty() != true) {
                e.appendChild(dom.createTextNode(this.epochsMedium.getText()));             
			} else {
				e.appendChild(dom.createTextNode(Integer.toString(DefaultModel.EPOCHS_MEDIUM)));   
			}
            medium.appendChild(e);
            
            /**
             * Batch size
             */
            e = dom.createElement(DefaultModel.BATCH_SIZE);
            if (this.batchSizeMedium.getText().isEmpty() != true) {
                e.appendChild(dom.createTextNode(this.batchSizeMedium.getText()));             
			} else {
				e.appendChild(dom.createTextNode(Integer.toString(DefaultModel.BATCH_SIZE_MEDIUM)));   
			}
            medium.appendChild(e);

            // Add to the root
            root.appendChild(medium);
            
            /**
             * Hard
             */
            Element hard = dom.createElement("hard");

            /**
             * Learning rate
             */
            e = dom.createElement(DefaultModel.LEARNING_RATE);
            if (this.learningRateHard.getText().isEmpty() != true) {
                e.appendChild(dom.createTextNode(this.learningRateHard.getText()));             
			} else {
				e.appendChild(dom.createTextNode(Double.toString(DefaultModel.LEARNING_RATE_HARD)));   
			}
            hard.appendChild(e);

            /**
             * Epochs
             */
            e = dom.createElement(DefaultModel.EPOCHS);
            if (this.epochsHard.getText().isEmpty() != true) {
                e.appendChild(dom.createTextNode(this.epochsHard.getText()));             
			} else {
				e.appendChild(dom.createTextNode(Double.toString(DefaultModel.EPOCHS_HARD)));   
			}
            hard.appendChild(e);

            /**
             * Batch size
             */
            e = dom.createElement(DefaultModel.BATCH_SIZE);
            if (this.batchSizeHard.getText().isEmpty() != true) {
                e.appendChild(dom.createTextNode(this.batchSizeHard.getText()));             
			} else {
				e.appendChild(dom.createTextNode(Double.toString(DefaultModel.BATCH_SIZE_HARD)));   
			}
            hard.appendChild(e);

            // Add to the root
            root.appendChild(hard);
            
            /**
             * Add to the root of the document
             */
            dom.appendChild(root);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), 
                                     new StreamResult(new FileOutputStream(DefaultModel.FILE_NAME)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    	
    	
	}
	
}
