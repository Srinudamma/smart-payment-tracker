package com.SmartPaymentTracker.smart_payment_tracker.service;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Transaction;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {


    public byte[] generateExpenseReport(User user, List<Transaction> transactions) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Expense Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("User: " + user.getEmail()));
            document.add(new Paragraph("Generated on: " + java.time.LocalDate.now()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2f, 4f, 3f, 2f});
            table.setSpacingBefore(10);

            table.addCell("Date");
            table.addCell("Description");
            table.addCell("Category");
            table.addCell("Amount");

            double total = 0;

            for (Transaction t : transactions) {
                if (!"EXPENSE".equalsIgnoreCase(t.getType())) continue;

                table.addCell(String.valueOf(t.getDate()));
                table.addCell(t.getDescription() != null ? t.getDescription() : "-");
                table.addCell(t.getCategory() != null ? t.getCategory() : "-");
                table.addCell("₹" + t.getAmount());

                total += t.getAmount();
            }

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "Total Expense: ₹" + total,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD)
            ));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        return out.toByteArray();
    }

}


