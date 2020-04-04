package tn.esprit.consomitounsi.services.intrf.gestionlivraison;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.gestionlivraison.Decision;
import tn.esprit.consomitounsi.entities.gestionlivraison.Exchange;
import tn.esprit.consomitounsi.entities.gestionlivraison.Reclamation;
import tn.esprit.consomitounsi.entities.gestionlivraison.ReclamationState;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repair;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repayment;

@Remote
public interface ReclamationServiceRemote {

	// reclamation
	public int addReclamation(Reclamation reclamation);

	public Reclamation getReclamationById(int reclamationId);

	public void updateReclamation(Reclamation newReclamation);

	public void deleteReclamationById(int reclamationId);

	public List<Reclamation> getAllReclamations();

	public List<Reclamation> getAllReclamationsByYear(int year);

	public List<Reclamation> getReclamationByState(ReclamationState state);

	public List<Reclamation> getReclamationByDecision(Decision decision);

	public long numberOfReclamationByYear(int year);

	public long numberOfReclamationPerMonth(int month, int year);

	public long numberOfReclamationByStatePerYear(ReclamationState state, int year);

	public long numberOfReclamationByStatePerMonth(ReclamationState state, int month, int year);

	public long numberOfReclamationByDecisionByYear(Decision decision, int year);

	public long numberOfReclamationByDecisionPerMonth(Decision decision, int month, int year);

	public long numberOfReclamationByProductByYear(int productId, int year);

	public long numberOfReclamationByProductPerMonth(int productId, int month, int year);

	public long numberOfReclamationByCategoryByYear(int categoryId, int year);

	public long numberOfReclamationByCategoryPerMonth(int categoryId, int month, int year);

	// exchange
	public String addExchange(Exchange exchange);

	public Exchange getExchangeByCode(String code);

	public void updateExchange(Exchange newExchange);
	
	public void validateExchange(String code);

	public void deleteExchangeByCode(String code);

	public List<Exchange> getAllExchanges();

	public List<Exchange> getAllExchangesByState(boolean state);
	
	public List<Exchange> getAllExchangesByYear(int year);

	public boolean checkExchangeCode(String code);

	public List<Product> getExchangedProductByYear(int year);

	public long numberOfExchangeByYear(int year);

	public long numberOfExchangeByProductByYear(int productId, int year);

	public long numberOfExchangeByProductPerMonth(int productId, int year, int month);

	public long numberOfExchangeByCategoryByYear(int categoryId, int year);

	public long numberOfExchangeByCategoryPerMonth(int categoryId, int year, int month);

	// repayments

	public int addRepayment(Repayment repayment);

	public Repayment getRepaymentById(int repayment);

	public void updateRepayment(Repayment newRepayment);

	public void deleteRepaymentById(int repaymentId);
	
	public void validateRepayment(int repaymentId, double amount, String description);

	public List<Repayment> getAllRepayment();

	public List<Repayment> getAllRepaymentByYear(int year);

	public List<Repayment> getAllRepaymentByState(boolean state);

	public long numberOfRepaymentPerYear(int year);

	public long numberOfRepaymentPerMonth(int month, int year);

	// repair

	public int addRepair(Repair repair);

	public Repair getRepairById(int repairId);

	public void updateRepair(Repair newRepair);

	public void deleteRepairById(int repairId);
	
	public void validateRepair(int repairId, double cost, String description);

	public List<Repair> getAllRepairs();

	public List<Repair> getAllRepairByYear(int year);

	public List<Repair> getAllRepairByState(boolean state);

	public long numberOfRepairByYear(int year);
	
	public long numberOfRepairPerMonth(int year, int month);

	public long numberOfRepairByProductPerYear(int productId, int year);

	public double costOfRepairsByYear(int year);

	public double costOfRepairByProductPerYear(int productId, int year);

	public double costOfRepairByProductPerMonth(int productId, int month, int year);

	// Date
	public Date getCurrentDate();

}
