package com.projet.job;

import com.projet.entities.Charge;
import com.projet.services.ChargeService;
import com.projet.utils.DateManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 12/09/2020
 * Time: 21:40
 * =================================================================
 */
public class UpdateChargeStatusJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date currentDate = new Date();

        ChargeService service = new ChargeService(Charge.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            List<Charge> chargesToUpdate = service.getByDueAtDate(currentDate);

            for (Charge charge : chargesToUpdate) {

                charge = service.checkStatus(charge);

                service.save(charge);
            }

            transaction.commit();
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }
}
