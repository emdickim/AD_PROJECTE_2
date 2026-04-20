package com.ra34.projecte2.Service;

import com.ra34.projecte2.Model.Invoice;
import com.ra34.projecte2.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> getInvoice(Long id) {
        return invoiceRepository.findById(id);
    }

    public Optional<Invoice> getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }

    public Optional<Invoice> getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            Invoice existingInvoice = invoice.get();
            existingInvoice.setOrder(invoiceDetails.getOrder());
            existingInvoice.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
            existingInvoice.setIssueDate(invoiceDetails.getIssueDate());
            existingInvoice.setTaxAmount(invoiceDetails.getTaxAmount());
            existingInvoice.setTotalWithTax(invoiceDetails.getTotalWithTax());
            return invoiceRepository.save(existingInvoice);
        }
        return null;
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
