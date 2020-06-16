package tn.esprit.consomitounsi.services.impl.gestionlivraison;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.Category;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.entities.gestionlivraison.Decision;
import tn.esprit.consomitounsi.entities.gestionlivraison.Exchange;
import tn.esprit.consomitounsi.entities.gestionlivraison.Reclamation;
import tn.esprit.consomitounsi.entities.gestionlivraison.ReclamationState;
import tn.esprit.consomitounsi.entities.gestionlivraison.ReclamationTreatment;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repair;
import tn.esprit.consomitounsi.entities.gestionlivraison.Repayment;
import tn.esprit.consomitounsi.services.intrf.gestionlivraison.ReclamationServiceRemote;

@Stateless
@LocalBean
public class ReclamationService implements ReclamationServiceRemote {

	@PersistenceContext
	EntityManager em;
	private static final String MONTH = "month";
	private static final String YEAR = "year";
	private static final String STATE = "state";
	private static final String DECISION = "decision";
	private static final String PRODUCT = "product";
	private static final String CATEGORY = "category";

	@Override
	public int addReclamation(Reclamation reclamation) {

		reclamation.setCreated(getCurrentDate());
		reclamation.setState(ReclamationState.PENDING);
		em.persist(reclamation);
		return reclamation.getId();
	}

	@Override
	public boolean reclamationExist(int id) {
		Reclamation rec = em.find(Reclamation.class, id);
		return rec != null;

	}

	@Override
	public Reclamation getReclamationById(int reclamationId) {

		return em.find(Reclamation.class, reclamationId);
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

		return em.createQuery("Select rec from Reclamation rec order by rec.created desc", Reclamation.class)
				.getResultList();
	}

	@Override
	public List<Reclamation> getReclamationByUserId(int userid) {
		User user = em.find(User.class, userid);
		TypedQuery<Reclamation> query = em.createQuery(
				"Select rec from Reclamation rec where rec.user=:user order by rec.created desc", Reclamation.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	@Override
	public List<Reclamation> getAllReclamationsByYear(int year) {
		TypedQuery<Reclamation> query = em.createQuery(
				"Select r from Reclamation r where EXTRACT(YEAR FROM r.created)=:year order by r.created desc",
				Reclamation.class);

		query.setParameter(YEAR, year);

		return query.getResultList();
	}

	@Override
	public List<Reclamation> getAllReclamationsByMonth(int year, int month) {
		TypedQuery<Reclamation> query = em.createQuery(
				"Select r from Reclamation r where EXTRACT(MONTH FROM r.created)=:month AND EXTRACT(YEAR FROM r.created)=:year order by r.created desc",
				Reclamation.class);

		query.setParameter(YEAR, year);
		query.setParameter(MONTH, month);

		return query.getResultList();
	}

	@Override
	public List<Reclamation> getReclamationByState(ReclamationState state) {
		TypedQuery<Reclamation> query = em.createQuery(
				"Select rec from Reclamation rec where rec.state=:state order by rec.created desc", Reclamation.class);

		query.setParameter(STATE, state);
		return query.getResultList();
	}

	@Override
	public List<Reclamation> getReclamationByDecision(Decision decision) {
		TypedQuery<Reclamation> query = em.createQuery(
				"Select rec from Reclamation rec where rec.decision=:decision order by rec.created desc",
				Reclamation.class);

		query.setParameter(DECISION, decision);
		return query.getResultList();
	}

	@Override
	public long numberOfReclamationByYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec where EXTRACT(YEAR FROM rec.created)=:year", Long.class);

		query.setParameter(YEAR, year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationPerMonth(int month, int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec where EXTRACT(MONTH FROM rec.created)=:month AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);

		query.setParameter(MONTH, month);
		query.setParameter(YEAR, year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByStatePerYear(ReclamationState state, int year) {
		TypedQuery<Long> query = em.createQuery("Select count(rec) from Reclamation rec "
				+ "where  EXTRACT(YEAR FROM rec.answered)=:year and rec.state=:state", Long.class);
		query.setParameter(YEAR, year);
		query.setParameter(STATE, state);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByStatePerMonth(ReclamationState state, int month, int year) {

		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec where EXTRACT(MONTH FROM rec.answered)=:month AND EXTRACT(YEAR FROM rec.answered)=:year and rec.state=:state",
				Long.class);

		query.setParameter(MONTH, month);
		query.setParameter(YEAR, year);
		query.setParameter(STATE, state);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByDecisionByYear(Decision decision, int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec where  EXTRACT(YEAR FROM rec.answered)=:year and rec.decision=:decision",
				Long.class);
		query.setParameter(YEAR, year);
		query.setParameter(DECISION, decision);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByDecisionPerMonth(Decision decision, int month, int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec where EXTRACT(MONTH FROM rec.answered)=:month AND EXTRACT(YEAR FROM rec.answered)=:year and rec.decision=:decision",
				Long.class);

		query.setParameter(MONTH, month);
		query.setParameter(YEAR, year);
		query.setParameter(DECISION, decision);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByProductByYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec where  rec.product=:product AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);
		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByProductPerMonth(int productId, int month, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec where EXTRACT(MONTH FROM rec.created)=:month AND rec.product=:product AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);

		query.setParameter(MONTH, month);
		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByCategoryByYear(int categoryId, int year) {
		Category category = em.find(Category.class, categoryId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec  join rec.product product where product.category=:category AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);
		query.setParameter(YEAR, year);
		query.setParameter(CATEGORY, category);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfReclamationByCategoryPerMonth(int categoryId, int month, int year) {
		Category category = em.find(Category.class, categoryId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(rec) from Reclamation rec  join rec.product product where product.category=:category and EXTRACT(MONTH FROM rec.created)=:month AND EXTRACT(YEAR FROM rec.created)=:year",
				Long.class);

		query.setParameter(MONTH, month);
		query.setParameter(YEAR, year);
		query.setParameter(CATEGORY, category);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public String addExchange(Exchange exchange) {
		exchange.setCreated(getCurrentDate());
		em.persist(exchange);
		return exchange.getCode();

	}

	@Override
	public Exchange getExchangeByCode(String code) {

		TypedQuery<Exchange> query = em.createQuery("select e from exchange where e.code=:code", Exchange.class);
		query.setParameter("code", code);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public void updateExchange(Exchange newExchange) {
		Exchange exchange = em.find(Exchange.class, newExchange.getId());
		Optional.ofNullable(newExchange.getCode()).ifPresent(c -> {
			if (!c.equals("")) {
				exchange.setCode(c);
			}
		});

	}

	@Override
	public void deleteExchangeById(int id) {
		em.remove(em.find(Exchange.class, id));

	}

	@Override
	public List<Exchange> getAllExchanges() {

		return em.createQuery("Select exc from Exchange exc order by exc.created desc", Exchange.class).getResultList();
	}

	@Override
	public List<Exchange> getAllExchangesByYear(int year) {
		TypedQuery<Exchange> query = em.createQuery("Select e from Exchange e where EXTRACT(YEAR FROM e.created)=:year",
				Exchange.class);

		query.setParameter(YEAR, year);

		return query.getResultList();
	}

	@Override
	public boolean exchangeCodeExist(String code) {
		TypedQuery<Exchange> query = em.createQuery("select exc from Exchange exc where exc.code=:code ",
				Exchange.class);
		query.setParameter("code", code.toUpperCase());
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}

	}

	@Override
	public boolean exchangeExist(int exchangeId) {
		Exchange e = em.find(Exchange.class, exchangeId);
		return e != null;

	}

	@Override
	public List<Product> getExchangedProductByYear(int year) {
		TypedQuery<Product> query = em.createQuery(
				"select r.product from Exchange  exc join exc.reclamation r where EXTRACT(YEAR FROM exc.created)=:year ",
				Product.class);
		query.setParameter(YEAR, year);
		return query.getResultList();
	}

	@Override
	public long numberOfExchangeByYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(exc)  from Exchange exc  where EXTRACT(YEAR FROM exc.created)=:year", Long.class);

		query.setParameter(YEAR, year);

		if (query.getSingleResult() != null) {
			return query.getSingleResult();
		}
		return 0;

	}

	@Override
	public long numberOfExchangePerMonth(int year, int month) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(exc) from Exchange exc where EXTRACT(YEAR FROM exc.created)=:year AND EXTRACT(MONTH FROM exc.created)=:month",
				Long.class);

		query.setParameter(YEAR, year);
		query.setParameter(MONTH, month);

		if (query.getSingleResult() != null) {
			return query.getSingleResult();
		}
		return 0;
	}

	@Override
	public long numberOfExchangeByProductByYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(exc) from Exchange exc join exc.reclamation r where r.product=:product AND EXTRACT(YEAR FROM exc.created)=:year",
				Long.class);

		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfExchangeByProductPerMonth(int productId, int year, int month) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(exc) from Exchange exc join exc.reclamation r where r.product=:product AND EXTRACT(YEAR FROM exc.created)=:year AND EXTRACT(MONTH FROM exc.created)=:month",
				Long.class);

		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);
		query.setParameter(MONTH, month);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfExchangeByCategoryByYear(int categoryId, int year) {
		Category category = em.find(Category.class, categoryId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(exc) from Exchange exc join exc.reclamation r join r.product product where product.category=:category AND EXTRACT(YEAR FROM exc.created)=:year",
				Long.class);

		query.setParameter(YEAR, year);
		query.setParameter(CATEGORY, category);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfExchangeByCategoryPerMonth(int categoryId, int year, int month) {
		Category category = em.find(Category.class, categoryId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(exc)from Exchange exc join exc.reclamation r join r.product product where product.category=:category AND EXTRACT(YEAR FROM exc.created)=:year AND EXTRACT(MONTH FROM exc.created)=:month",
				Long.class);

		query.setParameter(YEAR, year);
		query.setParameter(CATEGORY, category);
		query.setParameter(MONTH, month);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public int addRepayment(Repayment repayment) {
		repayment.setCreated(getCurrentDate());
		em.persist(repayment);
		return repayment.getId();
	}

	@Override
	public Repayment getRepaymentById(int repaymentId) {

		return em.find(Repayment.class, repaymentId);
	}

	@Override
	public void updateRepayment(Repayment newRepayment) {
		Repayment repayment = em.find(Repayment.class, newRepayment.getId());
		repayment.setAmount(newRepayment.getAmount());
		Optional.ofNullable(newRepayment.getDescription()).ifPresent(d -> {
			if (!d.equals("")) {
				repayment.setDescription(d);

			}
		});

	}

	@Override
	public void deleteRepaymentById(int repaymentId) {
		em.remove(em.find(Repayment.class, repaymentId));

	}

	@Override
	public List<Repayment> getAllRepayment() {

		return em.createQuery("Select rep from Repayment rep order by rep.id desc", Repayment.class).getResultList();
	}

	@Override
	public List<Repayment> getAllRepaymentByYear(int year) {
		TypedQuery<Repayment> query = em
				.createQuery("Select r from Repayment r where EXTRACT(YEAR FROM r.created)=:year", Repayment.class);

		query.setParameter(YEAR, year);

		return query.getResultList();
	}

	@Override
	public long numberOfRepaymentPerYear(int year) {
		TypedQuery<Long> query = em
				.createQuery("Select count(r) from Repayment r where EXTRACT(YEAR FROM r.created)=:year", Long.class);

		query.setParameter(YEAR, year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfRepaymentPerMonth(int month, int year) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(r) from Repayment r where EXTRACT(MONTH FROM r.created)=:month AND EXTRACT(YEAR FROM r.created)=:year",
				Long.class);

		query.setParameter(MONTH, month);
		query.setParameter(YEAR, year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public int addRepair(Repair repair) {
		repair.setCreated(getCurrentDate());
		em.persist(repair);
		return repair.getId();
	}

	@Override
	public Repair getRepairById(int repairId) {

		return em.find(Repair.class, repairId);
	}

	@Override
	public void updateRepair(Repair newRepair) {
		Repair repair = em.find(Repair.class, newRepair.getId());
		repair.setCost(newRepair.getCost());
		Optional.ofNullable(newRepair.getDescription()).ifPresent(d -> {
			if (!d.equals("")) {
				repair.setDescription(d);

			}
		});

	}

	@Override
	public void deleteRepairById(int repairId) {
		em.remove(em.find(Repair.class, repairId));

	}

	@Override
	public List<Repair> getAllRepairs() {

		return em.createQuery("Select rep from Repair rep order by rep.id desc", Repair.class).getResultList();
	}

	@Override
	public List<Repair> getAllRepairByYear(int year) {
		TypedQuery<Repair> query = em.createQuery("Select r from Repair r where EXTRACT(YEAR FROM r.created)=:year",
				Repair.class);

		query.setParameter(YEAR, year);

		return query.getResultList();
	}

	@Override
	public List<Repair> getRepairByProductId(int id) {
		Product product = em.find(Product.class, id);
		TypedQuery<Repair> query = em
				.createQuery("Select r from Repair r join r.reclamation rec where rec.product=:product ", Repair.class);
		query.setParameter(PRODUCT, product);

		return query.getResultList();
	}

	@Override
	public long numberOfRepairByYear(int year) {
		TypedQuery<Long> query = em
				.createQuery("Select count(r) from Repair r where EXTRACT(YEAR FROM r.created)=:year", Long.class);

		query.setParameter("year", year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfRepairByProductPerYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(r) from Repair r join r.reclamation rec  where rec.product=:product AND EXTRACT(YEAR FROM r.created)=:year",
				Long.class);

		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfRepairByProductPerMonth(int productId, int year, int month) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Long> query = em.createQuery(
				"Select count(r) from Repair r join r.reclamation rec  where rec.product=:product AND EXTRACT(YEAR FROM r.created)=:year  AND EXTRACT(MONTH FROM r.created)=:month",
				Long.class);

		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);
		query.setParameter(MONTH, month);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public long numberOfRepairPerMonth(int year, int month) {
		TypedQuery<Long> query = em.createQuery(
				"Select count(r) from Repair r where EXTRACT(MONTH FROM r.created)=:month AND EXTRACT(YEAR FROM r.created)=:year",
				Long.class);

		query.setParameter(MONTH, month);
		query.setParameter(YEAR, year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public double costOfRepairsByYear(int year) {
		TypedQuery<Double> query = em
				.createQuery("select sum(r.cost) from Repair r where EXTRACT(YEAR FROM r.created)=:year", Double.class);

		query.setParameter(YEAR, year);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public double costOfRepairByProductPerYear(int productId, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Double> query = em.createQuery(
				"Select sum(r.cost) from Repair r join r.reclamation rec  where rec.product=:product AND EXTRACT(YEAR FROM r.created)=:year",
				Double.class);

		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public double costOfRepairByProductPerMonth(int productId, int month, int year) {
		Product product = em.find(Product.class, productId);
		TypedQuery<Double> query = em.createQuery(
				"Select sum(r.cost) from Repair r join r.reclamation rec  where rec.product=:product  AND EXTRACT(YEAR FROM r.created)=:year AND EXTRACT(MONTH FROM r.created)=:month ",
				Double.class);

		query.setParameter(YEAR, year);
		query.setParameter(PRODUCT, product);
		query.setParameter(MONTH, month);

		return query.getSingleResult() != null ? query.getSingleResult() : 0;
	}

	@Override
	public Date getCurrentDate() {

		return java.sql.Date.valueOf(LocalDate.now());
	}

	@Override
	public boolean isReclamationTreated(int reclamationId) {
		Reclamation reclamation = em.find(Reclamation.class, reclamationId);
		TypedQuery<ReclamationTreatment> query = em.createQuery(
				"select r from ReclamationTreatment r where r.reclamation=:rec", ReclamationTreatment.class);
		query.setParameter("rec", reclamation);

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}

	}

	@Override
	public List<Reclamation> nonTreated() {
		List<Reclamation> accepted = getReclamationByState(ReclamationState.ACCEPTED);
		List<Reclamation> nonTreated = new ArrayList<>();
		for (Reclamation rec : accepted) {
			if (!isReclamationTreated(rec.getId())) {
				nonTreated.add(rec);
			}
		}
		return nonTreated;
	}

}
