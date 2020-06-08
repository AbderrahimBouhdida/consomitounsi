package tn.esprit.consomitounsi.managedbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.services.impl.ProductService;

//import tn.esprit.Pidev.intrf.ProductRemote;


@ManagedBean(name = "productBean")
@SessionScoped
public class ProductBean implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Product userToMod = new Product();
	private Integer barecode;
	private String nameProduct;
	private String picture;
	private int price;
	private String description; 
	private Date dateExpire;
	private Date dateAdd;
	private double weight;
	private int quantity;
	private List<Product> product;
	private String destination = System.getenv("UPLOAD_TEST");
	private UploadedFile file;
	private List<Product> selectedProducts = new ArrayList<>();
	private Product newProduct = new Product();
	private Integer productBaracodeToBeUpdated;
	private Product selectedprod = new Product();////////////
	@EJB
	ProductService productService;
	
	public Integer getProductBarecodeToBeUpdated() {
		return productBaracodeToBeUpdated;
	}
	
	public void setProductBarecodeToBeUpdated(Integer productBaracodeToBeUpdated) {
		this.productBaracodeToBeUpdated = productBaracodeToBeUpdated;
	}

	
	
	
	public Integer getBarecode() {
		return barecode;
	}

	public void setBarecode(Integer barecode) {
		this.barecode = barecode;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getPicture() {
		return picture;
		
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}



	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Product> getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(List<Product> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public Product getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Product newProduct) {
		this.newProduct = newProduct;
	}

	public String getDescription() {
		return description;
	}

	public Integer getProductBaracodeToBeUpdated() {
		return productBaracodeToBeUpdated;
	}

	public void setProductBaracodeToBeUpdated(Integer productBaracodeToBeUpdated) {
		this.productBaracodeToBeUpdated = productBaracodeToBeUpdated;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateExpire() {
		return dateExpire;
	}

	public void setDateExpire(Date dateExpire) {
		this.dateExpire = dateExpire;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<Product> getProduct() {
		return productService.findAllProduct();
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}
	
	
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	

	public Product getUserToMod() {
		return userToMod;
	}

	public void setUserToMod(Product userToMod) {
		this.userToMod = userToMod;
	}

	public void addProduct() {
		Product product = new Product();
		product.setBarecode(barecode);
		product.setDateAdd(dateAdd);
		product.setDateExpire(dateExpire);
		product.setNameProduct(nameProduct);
		product.setPicture(picture);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setWeight(weight);
		product.setDescription(description);
		
		try {
			upload();
			TransferTile(file.getFileName(), file.getInputstream());
			product.setPicture(file.getFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productService.addProduct(product);
		
	}
	
	public String removeProduct(int barecode) {
		System.out.println(barecode);
		productService.removeProduct(barecode);
		return "";
	}
	
	public void updateProduct() {
		Product prod = new Product();
		prod.setBarecode(barecode);
		prod.setDateAdd(dateAdd);
		prod.setDateExpire(dateExpire);
		prod.setNameProduct(nameProduct);
		prod.setPicture(picture);
		prod.setPrice(price);
		prod.setQuantity(quantity);
		prod.setWeight(weight);
		productService.updateProduct(prod);	
	
	}
	 public Product findProductById(int Barecode) {
		productService.findAllProduct();
		return null;
	 }
	 
	 
	 public void upload() {

			if (file != null) {
				FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else
				System.out.println("file is null");
		}

		public void handleFileUpload(FileUploadEvent event) {
			FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		public void TransferTile(String fileName, InputStream in) {
			try {
				System.out.println("name "+destination+fileName);
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
		
		private List<Product> products;
		
		public  List<Product> getProduct1() {
			return product;
		}

		public Product getSelectedprod() {
			return selectedprod;
		}

		public void setSelectedprod(Product selectedprod) {
			this.selectedprod = selectedprod;
		}
		
		public String navToMod(Product user) {
			userToMod = user;
			return "updateProduct?faces-redirect=true";
		}
		public String modProd() {
			try {
				if (file.getSize() != 0) {
					upload();
					TransferTile(file.getFileName(), file.getInputstream());
					userToMod.setPicture(file.getFileName());
				}
			} catch (Exception e) {

			}
			productService.updateProduct(userToMod);
			userToMod = new Product();

			file = null;
			return "allProducts?faces-redirect=true";
		}
		
}
