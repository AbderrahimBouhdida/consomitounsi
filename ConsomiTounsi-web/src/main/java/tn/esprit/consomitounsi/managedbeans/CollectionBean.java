package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.List;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.util.Date;
//import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



//import org.primefaces.PrimeFaces;
//import org.primefaces.component.graphicimage.GraphicImage;
//import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.event.FileUploadEvent;
//import org.primefaces.json.JSONArray;
//import org.primefaces.model.DefaultStreamedContent;
//import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import tn.esprit.consomitounsi.entities.Collection;
import tn.esprit.consomitounsi.entities.Collectiontype;
import tn.esprit.consomitounsi.services.impl.CollectionServices;

@ManagedBean(name = "collectionbean")
@SessionScoped
public class CollectionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idcollection;
	private String name;
	@Enumerated(EnumType.STRING)
	private Collectiontype type;
	private String description;
	private String image;
	private int goal;
	private int collectedamount;
	private Integer collectionIdToBeUpdated;
	private Collection colToMod;

	private String destination = "F:\\REV\\NIDS\\J2EE\\workspace\\pidevnids\\pidevnids-web\\src\\main\\webapp\\resources\\uploads\\";
	private UploadedFile file;
	

	@EJB
	CollectionServices collectionservice;
	
	

	public Collection getColToMod() {
		return colToMod;
	}

	public void setColToMod(Collection colToMod) {
		this.colToMod = colToMod;
	}

	public int getIdcollection() {
		return idcollection;
	}

	public void setIdcollection(int idcollection) {
		this.idcollection = idcollection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collectiontype getType() {
		return type;
	}

	public void setType(Collectiontype type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getCollectedamount() {
		return collectedamount;
	}

	public void setCollectedamount(int collectedamount) {
		this.collectedamount = collectedamount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCollectionIdToBeUpdated() {
		return collectionIdToBeUpdated;
	}

	public void setCollectionIdToBeUpdated(Integer collectionIdToBeUpdated) {
		this.collectionIdToBeUpdated = collectionIdToBeUpdated;
	}

	public void addCollection() throws IOException {

		upload();
		System.out.println("file" + file.getFileName() + "input" + file.getInputstream().toString());

		TransferTile(file.getFileName(), file.getInputstream());
		collectionservice
				.addCollection(new Collection(idcollection, name, type, description, file.getFileName(), goal, 0));
	}

	// Collection list
	private List<Collection> cols;

	public List<Collection> getCollections() {
		cols = collectionservice.findAllCollection();
		return cols;
	}

	// employee remove
	public void removeCollection(int id) {
		collectionservice.removeCollection(id);
	}

	// displayCollection

	public void displayCollection(Collection col) {
		this.setName(col.getName());
		this.setType(col.getType());
		this.setDescription(col.getDescription());
		this.setImage(col.getImage());
		this.setGoal(col.getGoal());
		this.setCollectedamount(col.getCollectedamount());
		this.setCollectionIdToBeUpdated(col.getIdcollection());
	}

	// Connection update

	public void updateCollection() throws IOException {
		upload();
		TransferTile(file.getFileName(), file.getInputstream());
		collectionservice.updateCollectionv2(new Collection(collectionIdToBeUpdated, name, type, description,
				file.getFileName(), goal, collectedamount));
	}
	
	public String donate(Collection col) {
		colToMod = col;
		
		//this.idcollection=col.getIdcollection();
		//this.collectedamount=col.getCollectedamount();
		//this.goal=col.getGoal();
		
		
		return "donate?faces-redirect=true";
	}
	public void addmoney()  {
		//System.out.println("funding");
		//System.out.println("collectionIdToBeUpdated"+collectionIdToBeUpdated);
		//System.out.println("collectedamount"+collectedamount);

	//	collectionservice.updateCollectionv2(new Collection(collectionIdToBeUpdated,collectedamount));
		//collectionservice.updateCollection(new Collection(collectionIdToBeUpdated,collectedamount));
		collectionservice.updateCollection(colToMod);
	}
	

	// upload

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {

		if (file != null) {
			FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void TransferTile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(destination + fileName));
			int reader = 0;
			byte[] bytes = new byte[(int) file.getSize()];
			while ((reader = in.read(bytes)) != -1) {
				out.write(bytes, 0, reader);
			}
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
