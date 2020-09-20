package com.projet.services;

import com.projet.entities.*;
import com.projet.enumeration.ChargeDateFilterEnum;
import com.projet.enumeration.ChargeStatus;
import com.projet.utils.DateManager;
import org.apache.shiro.authc.Account;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 13/08/2020
 * Time: 19:08
 * =================================================================
 */
public class ChargeService extends Service<Charge> {


    public ChargeService(Class<?> ec) {
        super(ec);
    }

    @Override
    public Charge save(Charge charge) {
        if (charge.getId() == 0) {
            em.persist(charge);
        } else {
            charge = em.merge(charge);
        }

        return charge;
    }

    public List<Charge> getByUser(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);

        return finder.findByNamedQuery("Charge.findByUser", param);
    }

    public List<Charge> getByUserOrderByDate(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);

        return finder.findByNamedQuery("Charge.findByUserOrderByDate", param);
    }

    public List<Charge> getByUserAndUserAccount(User user, UserAccount userAccount, FinancialYear financialYear) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("userAccount", userAccount);
        param.put("financialYear", financialYear);

        return finder.findByNamedQuery("Charge.findByUserAndUserAccount", param);
    }

    public List<Charge> getByDueAtDate(Date date) {
        Map<String, Object> param = new HashMap<>();
        param.put("date", date);

        return finder.findByNamedQuery("Charge.findByDueAtDate", param);
    }

    public List<Charge> getFilteredChargeList(ChargeStatus status, Date date_period_start, Date date_period_end,
                                              int pageNumber, int pageSize, User user) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Charge> query = criteriaBuilder.createQuery(Charge.class);
        Root<Charge> charge = query.from(Charge.class);

        ParameterExpression<User> paramUser = criteriaBuilder.parameter(User.class);
        Predicate pUser = criteriaBuilder.and(criteriaBuilder.equal(charge.get("user"), paramUser));

        ParameterExpression<Date> paramDateStart = criteriaBuilder.parameter(Date.class);
        ParameterExpression<Date> paramDateEnd = criteriaBuilder.parameter(Date.class);
        Predicate pBetween = criteriaBuilder.and(criteriaBuilder.between(charge.get("createdAt"), paramDateStart, paramDateEnd));

        ParameterExpression<ChargeStatus> paramStatus = criteriaBuilder.parameter(ChargeStatus.class);
        Predicate pChargeStatus = criteriaBuilder.and(criteriaBuilder.equal(charge.get("status"), paramStatus));

        List<Predicate> predList = new LinkedList<>();

        if (date_period_start != null && date_period_end != null)
            predList.add(pBetween);

        if (status != null)
            predList.add(pChargeStatus);

        predList.add(pUser);

        Predicate[] predArray = new Predicate[predList.size()];
        predList.toArray(predArray);

        query.select(charge).where(predArray);

        TypedQuery<Charge> typedQuery = em.createQuery(query);

        typedQuery.setParameter(paramUser, user);

        if (date_period_start != null && date_period_end != null) {
            typedQuery.setParameter(paramDateStart, date_period_start, TemporalType.DATE);
            typedQuery.setParameter(paramDateEnd, date_period_end, TemporalType.DATE);
        }

        if (status != null)
            typedQuery.setParameter(paramStatus, status);

        if (pageSize != 0 && pageNumber != 0) {
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);
        }

        return typedQuery.getResultList();
    }

    public int countChargeList (ChargeStatus status, Date date_period_start, Date date_period_end, User user) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Charge> charge = query.from(Charge.class);

        ParameterExpression<User> paramUser = criteriaBuilder.parameter(User.class);
        Predicate pUser = criteriaBuilder.and(criteriaBuilder.equal(charge.get("user"), paramUser));

        ParameterExpression<Date> paramDateStart = criteriaBuilder.parameter(Date.class);
        ParameterExpression<Date> paramDateEnd = criteriaBuilder.parameter(Date.class);
        Predicate pBetween = criteriaBuilder.and(criteriaBuilder.between(charge.get("createdAt"), paramDateStart, paramDateEnd));

        ParameterExpression<ChargeStatus> paramStatus = criteriaBuilder.parameter(ChargeStatus.class);
        Predicate pChargeStatus = criteriaBuilder.and(criteriaBuilder.equal(charge.get("status"), paramStatus));

        List<Predicate> predList = new LinkedList<>();

        if (date_period_start != null && date_period_end != null)
            predList.add(pBetween);

        if (status != null)
            predList.add(pChargeStatus);

        predList.add(pUser);

        Predicate[] predArray = new Predicate[predList.size()];
        predList.toArray(predArray);

        query.select(criteriaBuilder.count(charge)).where(predArray);

        TypedQuery<Long> typedQuery = em.createQuery(query);

        typedQuery.setParameter(paramUser, user);

        if (date_period_start != null && date_period_end != null) {
            typedQuery.setParameter(paramDateStart, date_period_start, TemporalType.DATE);
            typedQuery.setParameter(paramDateEnd, date_period_end, TemporalType.DATE);
        }

        if (status != null)
            typedQuery.setParameter(paramStatus, status);

        return typedQuery.getSingleResult().intValue();
    }

    /**
     * Method create a new charge. User and financialYear is added.
     *
     * @param charge charge who need to be completed
     * @param user user who is concerned by the charge
     * @return Charge ready to be persisted
     */
    public Charge createcharge(Charge charge, User user) {

        finalizeCharge(charge, user);

        // set user and return charge
        return user.addCharge(charge);
    }

    public Charge modifyCharge(Charge charge, User user) {

        return finalizeCharge(charge, user);
    }

    /**
     * Method who complete all the required field of a charge. The returned charge
     * is ready to be persisted
     *
     * @param charge
     * @return
     */
    private Charge finalizeCharge(Charge charge, User user) {
        FinancialYearService faService = new FinancialYearService(FinancialYear.class);

        // year of the created charge
        int financialYearDate = DateManager.getYear(charge.getCreatedAt());

        // financial with same year from database
        FinancialYear year = faService.getUserFinancialYearByDate(user, financialYearDate);

        // if there is no financialYear a new one is created
        if (year == null) {
            year = faService.createFinancialYear(financialYearDate, user);
        }

        // set the charge financialYear
        charge.setFinancialYear(em.merge(year));

        // set charge status
        charge = checkStatus(charge);

        return charge;
    }

    /**
     * check if the status need to be modify
     * @param charge charge to check
     * @return charge with correct status
     */
    public Charge checkStatus(Charge charge) {
        // if there is a due date and the status is not payed then it check if the status need to be modify
        if (charge.getDueAt() != null && !charge.getStatus().equals(ChargeStatus.PAYED)) {
            // if the due date is outdated then the status is modify
            if (DateManager.compareToCurrentDate(charge.getDueAt()))
                charge.setStatus(ChargeStatus.LATE);
        }

        return charge;
    }

    /**
     * Calculate the sum of the charge in the list given in parameter. If a status is given
     * the sum depend of the status.
     * @param charges List to be calculate
     * @param status the status to sort the list
     * @return the sum of the charge
     */
    public double totalCharge(List<Charge> charges, ChargeStatus status) {
        double total = 0;

        //if no status is given return sum of all charge
        if (status == null) {
            for (Charge charge: charges) {
                total += charge.getAmount();
            }
            // return sum of charge with the status given
        } else {
            for (Charge charge: charges) {
                if (charge.getStatus().equals(status)) {
                    total += charge.getAmount();
                }
            }
        }

        return total;
    }

    public double calculate_total_deductible_amount_by_financialYear(FinancialYear financialYear) {
        AccountItemService accountItemService = new AccountItemService(AccountItem.class);

        double total = 0;

        if (financialYear != null) {
            //put in the persistence context
            financialYear = em.merge(financialYear);

            //synchronize data with DB
            em.refresh(financialYear);

            List<AccountItem> accountItems = financialYear.getAccountItems();

            if (! accountItems.isEmpty()) {
                total = accountItemService.calculate_deductible_amount_of_accountItem_list(accountItems);
            }
        }

        return total;
    }

    public Charge mark_charge_as_payed(Charge charge) {
        charge.setPayed(true);

        charge.setStatus(ChargeStatus.PAYED);

        return charge;
    }

    public Charge mark_charge_as_not_payed(Charge charge) {
        charge.setPayed(false);

        charge.setStatus(ChargeStatus.NOTPAYED);

        charge.setPaiementMethod(null);

        charge.setPayedAt(null);

        return charge;
    }


}
