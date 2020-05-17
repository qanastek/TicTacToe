package fr.univavignon.ceri.application.vues.help;

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

import fr.univavignon.ceri.application.JavaIA.GenerateDataset;
import fr.univavignon.ceri.application.config.DefaultModel;
import fr.univavignon.ceri.application.services.Threads.HardTrain;
import fr.univavignon.ceri.application.services.Threads.MediumTrain;
import fr.univavignon.ceri.application.services.Threads.RunningThreads;

public class helpController implements Initializable {
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}	
}
