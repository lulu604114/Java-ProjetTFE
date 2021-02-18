package com.projet.controllers;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.AccountItemService;
import com.projet.services.ChargeService;
import com.projet.utils.DateManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 16/09/2020
 * Time: 20:09
 * =================================================================
 */
@Named("chargeReportPdfGenerator")
@RequestScoped
public class chargeReportPdfGenerator implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String CONTENTTYPE = "application/pdf";
    private final String FILENAME = "Dépenses";

    private List<UserSupplier> userSupplierList;
    private List<UserAccount> userAccountList;
    private User user;
    private FinancialYear financialYear;

    private int top = 510;

    @Inject
    private I18n i18n;


    @PostConstruct
    public void init() {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        userAccountList = user.getUserAccounts();
        userSupplierList = user.getUserSuppliers();
    }

    public void generateReport(FinancialYear financialYear) {
        this.financialYear = financialYear;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        String templatePath = externalContext.getRealPath("/WEB-INF/pdf/charge-template/template.pdf");

        externalContext.responseReset();
        externalContext.setResponseContentType(CONTENTTYPE);
        externalContext.setResponseHeader("Content-Disposition", "attachement; filename=\"" + FILENAME + " " + financialYear.getBeginAt() + ".pdf\"");

        try(PdfReader reader = new PdfReader(templatePath);
            PdfWriter writer = new PdfWriter(externalContext.getResponseOutputStream());
            PdfDocument document = new PdfDocument(reader, writer)) {

            PdfPage page = document.getPage(1);
            PdfCanvas canvas = new PdfCanvas(page);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            canvas.beginText();

            addTitle(canvas);
            addTotalCharge(canvas, font);
            addTotalDeductibleAmount(canvas, font);
            addSupplierHeader(canvas);
            addSupplierTable(canvas);
            addMonthHeader(canvas);
            addMonthTable(canvas);
            addUserAccountHeader(canvas);
            addUserAccountTable(canvas);

            canvas.endText();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            facesContext.responseComplete();
        }

    }

    private void addTitle(PdfCanvas canvas) throws IOException {

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        canvas.setFontAndSize(font, 16);
        canvas.setTextMatrix(260, 772);

        canvas.showText(String.valueOf(financialYear.getBeginAt()));

        font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        canvas.setFontAndSize(font, 14);
        canvas.setTextMatrix(44, 750);
        canvas.showText(user.getFirstName() + " " + user.getLastName());

    }

    private void addTotalCharge(PdfCanvas canvas, PdfFont font) {
        ChargeService service = new ChargeService();

        canvas.setFontAndSize(font, 18);
        canvas.setTextMatrix(51, 637);

        double amount = service.totalCharge(financialYear.getCharges(), null);
        canvas.showText(String.format("%.2f", amount) + " €");

        canvas.setFontAndSize(font, 12);
        canvas.setTextMatrix(86, 619);
        canvas.showText(String.valueOf(financialYear.getCharges().size()));
    }

    private void addTotalDeductibleAmount(PdfCanvas canvas, PdfFont font) {
        AccountItemService service = new AccountItemService();

        canvas.setFontAndSize(font, 18);
        canvas.setTextMatrix(320, 637);

        double amount = service.calculate_deductible_amount_of_accountItem_list(financialYear.getAccountItems());
        canvas.showText(String.format("%.2f", amount) + " €");
    }

    private void addSupplierHeader(PdfCanvas canvas) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        canvas.setFontAndSize(font, 12);
        canvas.setTextMatrix(44, 547);
        canvas.showText("Par fournisseur");
    }

    private void addSupplierTable(PdfCanvas canvas) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        canvas.setFontAndSize(font, 10);
        canvas.setTextMatrix(44, 527);
        canvas.showText("Nom du fournisseur");

        canvas.setTextMatrix(350, 527);
        canvas.showText("# de dépense");

        canvas.setTextMatrix(480, 527);
        canvas.showText("Total");

        font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        canvas.setFontAndSize(font, 10);

        List<Charge> chargeList = financialYear.getCharges();

        for (UserSupplier userSupplier : userSupplierList) {
            List<Charge> results = new ArrayList<>();

            for (Charge charge : chargeList) {
                if (charge.getUserSupplier().getId() == userSupplier.getId()) {
                    results.add(charge);
                }
            }

            if (!results.isEmpty()) {
                ChargeService service = new ChargeService();

                canvas.setTextMatrix(44, top);
                canvas.showText(userSupplier.getSupplier().getLabel());

                canvas.setTextMatrix(350, top);
                canvas.showText(String.valueOf(results.size()));


                canvas.setTextMatrix(480, top);
                canvas.showText(String.format("%.2f", service.totalCharge(results, null)) + " €");

                top -= 12;
            }
        }
    }

    private void addMonthHeader(PdfCanvas canvas) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        top -= 40;

        canvas.setFontAndSize(font, 12);
        canvas.setTextMatrix(44, top);
        canvas.showText("Par Mois");
    }

    private void addMonthTable(PdfCanvas canvas) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        top -= 20;

        canvas.setFontAndSize(font, 10);
        canvas.setTextMatrix(44, top);
        canvas.showText("Mois");

        canvas.setTextMatrix(350, top);
        canvas.showText("# de dépense");

        canvas.setTextMatrix(480, top);
        canvas.showText("Total");

        font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        canvas.setFontAndSize(font, 10);

        List<Charge> chargeList = financialYear.getCharges();

        top -= 17;

        for (int i = 1; i <= 12; i++) {
            List<Charge> results = new ArrayList<>();

            for (Charge charge : chargeList) {
                if (DateManager.getMonth(charge.getCreatedAt()) + 1 == i) {
                    results.add(charge);
                }
            }

            if (!results.isEmpty()) {
                ChargeService service = new ChargeService();

                canvas.setTextMatrix(44, top);
                canvas.showText(DateManager.formatMonth(i, i18n.getLocale()));

                canvas.setTextMatrix(350, top);
                canvas.showText(String.valueOf(results.size()));

                canvas.setTextMatrix(480, top);
                canvas.showText(String.format("%.2f", service.totalCharge(results, null)) + " €");

                top -= 12;
            }
        }
    }

    private void addUserAccountHeader(PdfCanvas canvas) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        top -= 40;

        canvas.setFontAndSize(font, 12);
        canvas.setTextMatrix(44, top);
        canvas.showText("Par charge");
    }

    private void addUserAccountTable(PdfCanvas canvas) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        ChargeService service = new ChargeService();
        AccountItemService accountItemService = new AccountItemService();

        top -= 20;

        canvas.setFontAndSize(font, 10);
        canvas.setTextMatrix(44, top);
        canvas.showText("Charge");

        canvas.setTextMatrix(350, top);
        canvas.showText("# d'imputation");

        canvas.setTextMatrix(480, top);
        canvas.showText("Total");

        font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        canvas.setFontAndSize(font, 10);

        top -= 17;

        for (UserAccount userAccount: userAccountList) {

            List<AccountItem> accountItems = accountItemService.getByUserAccountAndFinancialYear(userAccount, financialYear);

            if (accountItems != null && ! accountItems.isEmpty()) {

                canvas.setTextMatrix(44, top);
                canvas.showText(userAccount.getFinancialAccount().getCode());

                canvas.setTextMatrix(90, top);
                canvas.showText(userAccount.getFinancialAccount().getLabel());

                canvas.setTextMatrix(350, top);
                canvas.showText(String.valueOf(accountItems.size()));

                canvas.setTextMatrix(480, top);
                canvas.showText(String.format("%.2f", accountItemService.calculate_deductible_amount_of_accountItem_list(accountItems)) + " €");

                top -= 12;
            }
        }
    }
}