package tn.esprit.consomitounsi.services.impl.gestionlivraison;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.gestionlivraison.Decision;
import tn.esprit.consomitounsi.entities.gestionlivraison.Exchange;
import tn.esprit.consomitounsi.entities.gestionlivraison.Reclamation;
import tn.esprit.consomitounsi.entities.gestionlivraison.ReclamationState;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repair;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repayment;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.ReclamationServiceRemote;

@Stateless
@LocalBean
public class ReclamationService implements ReclamationServiceRemote {

	@PersistenceContext
	EntityManager em;

	@Override
	public int addReclamation(Reclamation reclamation) {

		reclamation.setCreated(getCurrentDate());
		reclamation.setState(ReclamationState.Pending);
		em.persist(reclamation);
		return reclamation.getId();
	}

	@Override
	public Reclamation getReclamationById(int reclamationId) {
		Reclamation reclamation = em.find(Reclamation.class, reclamationId);
		return reclamation;
	}

	@Override
	public void updateReclamation(Reclamation newReclamation) {
		Reclamation reclamation = em.find(Reclamation.class, newReclamation.getId());
		reclamation.setDecision(newReclamation.getDecision());
		reclamation.setAnswered(getCurrentDate());
		reclamation.setState(newReclamation.getState());
		reclamation.setResponseDescription(newReclamation.getResponseDescription());

	}

	@Override
	public void deleteReclamationById(int reclamationId) {
		em.remove(em.find(Reclamation.class, reclamationId));

	}

	@Override
	public List<Reclamation> getAllReclamations() {
		List<Reclamation> recl = em.createQuery("Select rec from Reclamation rec", Reclamation.class).getResultList();
		return recl;
	}

	@Override
	public List<Reclamation> getAllReclamationsByYear(int year) {
		TypedQuery<Reclamation> query = em.createQuery(
				"Select r" + " from Reclamation r " + "where EXTRACT(YEAR FROM r.created)=:year", Reclamation.class);

		query.setParameter("year", year);

		return query.getResultList();
	}

	@Override
	public List<Reclamation> getReclamationByState(ReclamationState state) {
		TypedQuery<Reclamation> query = em.createQuery("Select rec from Reclamation rec where rec.state=:state",
				Reclamation.class);

		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public List<Reclamation> getReclamationByDecision(Decision decision) {
		TypedQuery<Reclamation> query = em.createQuery("Select rec from Reclamation rec where rec.decision=:decision",
				Reclamation.class);

		query.setParameter("decision", decision);
		return query.getResultList();
	}

	@Override
	public long numberOfReclamationByYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec)" + " from Reclamation rec " + "where EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);

		query.setParameter("year", year);
		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationPerMonth(int month, int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec)" + " from Reclamation rec "
						+ "where EXTRACT(MONTH FROM rec.created)=:month AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByStatePerYear(ReclamationState state, int year) {
		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec "
				+ "where  EXTRACT(YEAR FROM rec.answered)=:year and rec.state=:state", Long.class);
		query.setParameter("year", year);
		query.setParameter("state", state);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByStatePerMonth(ReclamationState state, int month, int year) {

		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec "
				+ "where EXTRACT(MONTH FROM rec.answered)=:month AND EXTRACT(YEAR FROM rec.answered)=:year and rec.state=:state",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("state", state);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByDecisionByYear(Decision decision, int year) {
		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec "
				+ "where  EXTRACT(YEAR FROM rec.answered)=:year and rec.decision=:decision", Long.class);
		query.setParameter("year", year);
		query.setParameter("decision", decision);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByDecisionPerMonth(Decision decision, int month, int year) {
		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec "
				+ "where EXTRACT(MONTH FROM rec.answered)=:month AND EXTRACT(YEAR FROM rec.answered)=:year and rec.decision=decision",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("decision", decision);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByProductByYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec "
				+ "where  rec.product=:product AND EXTRACT(YEAR FROM rec.created)=:year", Long.class);
		query.setParameter("year", year);
		query.setParameter("product", product);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByProductPerMonth(int productId, int month, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec "
				+ "where EXTRACT(MONTH FROM rec.created)=:month AND rec.product=:product AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("product", product);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByCategoryByYear(int categoryId, int year) {
		tn.esprit.consomitounsi.entities.Category category = em.find(tn.esprit.consomitounsi.entities.Category.class,
				categoryId);
		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec  Join rec.product product"
				+ "where  product.category=:category AND EXTRACT(YEAR FROM rec.created)=:year", Long.class);
		query.setParameter("year", year);
		query.setParameter("category", category);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfReclamationByCategoryPerMonth(int categoryId, int month, int year) {
		tn.esprit.consomitounsi.entities.Category category = em.find(tn.esprit.consomitounsi.entities.Category.class,
				categoryId);
		TypedQuery<Long> query = em.createQuery("Select count(rec)" + " from Reclamation rec  Join rec.product product"
				+ "where EXTRACT(MONTH FROM rec.created)=:month AND  product.category=:category AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("category", category);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public String addExchange(Exchange exchange) {
		exchange.setCreated(getCurrentDate());
		exchange.setDone(false);
		em.persist(exchange);
		return exchange.getCode();
	}

	@Override
	public Exchange getExchangeByCode(String code) {
		Exchange exchange = em.find(Exchange.class, code);
		return exchange;
	}

	@Override
	public void updateExchange(Exchange newExchange) {
		Exchange exchange = em.find(Exchange.class, newExchange.getCode());
		exchange.setProduct(newExchange.getProduct());

	}

	@Override
	public void validateExchange(String code) {
		Exchange exchange = em.find(Exchange.class, code);
		exchange.setDone(true);
		exchange.setDoneOn(getCurrentDate());

	}

	@Override
	public void deleteExchangeByCode(String code) {
		em.remove(em.find(Exchange.class, code));

	}

	@Override
	public List<Exchange> getAllExchanges() {
		List<Exchange> exc = em.createQuery("Select exc from Exchange exc", Exchange.class).getResultList();
		return exc;
	}

	@Override
	public List<Exchange> getAllExchangesByYear(int year) {
		TypedQuery<Exchange> query = em.createQuery(
				"Select e" + " from Exchange e " + "where EXTRACT(YEAR FROM e.created)=:year", Exchange.class);

		query.setParameter("year", year);

		return query.getResultList();
	}

	@Override
	public List<Exchange> getAllExchangesByState(boolean state) {
		TypedQuery<Exchange> query = em.createQuery("Select exc from Exchange exc where exc.done=:state",
				Exchange.class);

		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public boolean checkExchangeCode(String code) {
		Exchange exchange = em.find(Exchange.class, code);
		if (exchange != null) {
			return exchange.isDone();
		}
		return false;
	}

	@Override
	public List<Product> getExchangedProductByYear(int year) {
		TypedQuery<Product> query = em.createQuery(
				"select exc.product from Exchange exc" + "where EXTRACT(YEAR FROM exc.doneOn)=:year ", Product.class);
		query.setParameter("year", year);
		return query.getResultList();
	}

	@Override
	public long numberOfExchangeByYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(exc)" + " from Exchange exc " + "where EXTRACT(YEAR FROM exc.created)=:year", Long.class);

		query.setParameter("year", year);
		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfExchangeByProductByYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery("Select count(exc)" + " from Exchange exc "
				+ "where exc.product=:product AND EXTRACT(YEAR FROM exc.created)=:year", Long.class);

		query.setParameter("year", year);
		query.setParameter("product", product);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfExchangeByProductPerMonth(int productId, int year, int month) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery("Select count(exc)" + " from Exchange exc "
				+ "where exc.product=:product AND EXTRACT(YEAR FROM exc.created)=:year AND EXTRACT(MONTH FROM exc.created)=:month",
				Long.class);

		query.setParameter("year", year);
		query.setParameter("product", product);
		query.setParameter("month", month);
		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfExchangeByCategoryByYear(int categoryId, int year) {
		Category category = em.find(Category.class, categoryId);
		TypedQuery<Long> query = em.createQuery("Select count(exc)" + " from Exchange exc Join exc.product product "
				+ "where product.category=:category AND EXTRACT(YEAR FROM exc.created)=:year", Long.class);

		query.setParameter("year", year);
		query.setParameter("category", category);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfExchangeByCategoryPerMonth(int categoryId, int year, int month) {
		Category category = em.find(Category.class, categoryId);
		TypedQuery<Long> query = em.createQuery("Select count(exc)" + " from Exchange exc Join exc.product product "
				+ "where product.category=:category AND EXTRACT(YEAR FROM exc.created)=:year AND EXTRACT(MONTH FROM exc.created)=:month",
				Long.class);

		query.setParameter("year", year);
		query.setParameter("category", category);
		query.setParameter("month", month);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public int addRepayment(Repayment repayment) {
		repayment.setCreated(getCurrentDate());
		repayment.setDone(false);
		em.persist(repayment);
		return repayment.getId();
	}

	@Override
	public Repayment getRepaymentById(int repaymentId) {
		Repayment repayment = em.find(Repayment.class, repaymentId);
		return repayment;
	}

	@Override
	public void updateRepayment(Repayment newRepayment) {
		Repayment repayment = em.find(Repayment.class, newRepayment.getId());
		repayment.setUser(newRepayment.getUser());

	}

	@Override
	public void deleteRepaymentById(int repaymentId) {
		em.remove(em.find(Repayment.class, repaymentId));

	}

	@Override
	public void validateRepayment(int repaymentId, double amount, String description) {
		Repayment repayment = em.find(Repayment.class, repaymentId);
		repayment.setAmount(amount);
		repayment.setDone(true);
		repayment.setDoneOn(getCurrentDate());
		repayment.setDescription(description);

	}

	@Override
	public List<Repayment> getAllRepayment() {
		List<Repayment> rep = em.createQuery("Select rep from Repayment rep", Repayment.class).getResultList();
		return rep;
	}

	@Override
	public List<Repayment> getAllRepaymentByYear(int year) {
		TypedQuery<Repayment> query = em.createQuery(
				"Select r" + " from Repayment r " + "where EXTRACT(YEAR FROM r.created)=:year", Repayment.class);

		query.setParameter("year", year);

		return query.getResultList();
	}

	@Override
	public List<Repayment> getAllRepaymentByState(boolean state) {
		TypedQuery<Repayment> query = em.createQuery("Select r from Repayment r where r.done=:state", Repayment.class);

		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public long numberOfRepaymentPerYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(r)" + " r from Repayment r " + "where EXTRACT(YEAR FROM r.created)=:year", Long.class);

		query.setParameter("year", year);
		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfRepaymentPerMonth(int month, int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(r)" + " from Repayment r "
						+ "where EXTRACT(MONTH FROM r.created)=:month AND EXTRACT(YEAR FROM r.created)=:year",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public int addRepair(Repair repair) {
		repair.setCreated(getCurrentDate());
		repair.setDone(false);
		em.persist(repair);
		return repair.getId();
	}

	@Override
	public Repair getRepairById(int repairId) {
		Repair repair = em.find(Repair.class, repairId);
		return repair;
	}

	@Override
	public void updateRepair(Repair newRepair) {
		Repair repair = em.find(Repair.class, newRepair.getId());
		if (newRepair.getUser() != null) {

			repair.setUser(newRepair.getUser());
		}
		if (newRepair.getProduct() != null) {
			repair.setProduct(newRepair.getProduct());
		}

	}

	@Override
	public void deleteRepairById(int repairId) {
		em.remove(em.find(Repair.class, repairId));

	}

	@Override
	public void validateRepair(int repairId, double cost, String description) {
		Repair repair = em.find(Repair.class, repairId);
		repair.setCost(cost);
		repair.setDescription(description);
		repair.setDone(true);
		repair.setDoneOn(getCurrentDate());

	}

	@Override
	public List<Repair> getAllRepairs() {
		List<Repair> rep = em.createQuery("Select rep from Repair rep", Repair.class).getResultList();
		return rep;
	}

	@Override
	public List<Repair> getAllRepairByYear(int year) {
		TypedQuery<Repair> query = em
				.createQuery("Select r" + " from Repair r " + "where EXTRACT(YEAR FROM r.created)=:year", Repair.class);

		query.setParameter("year", year);

		return query.getResultList();
	}

	@Override
	public List<Repair> getAllRepairByState(boolean state) {
		TypedQuery<Repair> query = em.createQuery("Select r from Repair r where r.done=:state", Repair.class);

		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public long numberOfRepairByYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(r)" + " r from Repair r " + "where EXTRACT(YEAR FROM r.created)=:year", Long.class);

		query.setParameter("year", year);
		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfRepairByProductPerYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery("Select count(r)" + " from Repair r "
				+ "where r.product=:product AND EXTRACT(YEAR FROM r.created)=:year", Long.class);

		query.setParameter("year", year);
		query.setParameter("product", product);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public long numberOfRepairPerMonth(int year, int month) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(r)" + " from Repair r "
						+ "where EXTRACT(MONTH FROM r.created)=:month AND EXTRACT(YEAR FROM r.created)=:year",
				Long.class);

		query.setParameter("month", month);
		query.setParameter("year", year);

		long result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public double costOfRepairsByYear(int year) {
		TypedQuery<Double> query = em.createQuery(
				"Select sum(r.cost)" + " r from Repair r " + "where EXTRACT(YEAR FROM r.doneOn)=:year", Double.class);

		query.setParameter("year", year);
		double result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public double costOfRepairByProductPerYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Double> query = em.createQuery("Select sum(r.cost)" + " from Repair r "
				+ "where r.product=:product AND EXTRACT(YEAR FROM r.created)=:year", Double.class);

		query.setParameter("year", year);
		query.setParameter("product", product);

		double result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public double costOfRepairByProductPerMonth(int productId, int month, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Double> query = em.createQuery("Select sum(r.cost)" + " from Repair r "
				+ "where r.product=:product  AND EXTRACT(YEAR FROM r.created)=:year AND EXTRACT(MONTH FROM r.created)=:month ",
				Double.class);

		query.setParameter("year", year);
		query.setParameter("product", product);
		query.setParameter("month", month);

		double result = query.getSingleResult() != null ? query.getSingleResult() : 0;
		return result;
	}

	@Override
	public Date getCurrentDate() {
		Date date = java.sql.Date.valueOf(LocalDate.now());
		return date;
	}

}
