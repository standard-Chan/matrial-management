package com.springframework.mm.controller.page.purchaseOrder;

import com.springframework.mm.dto.purchaseOrder.PurchaseOrderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderHeaderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemCreationRequest;
import com.springframework.mm.repository.MaterialRepository;
import com.springframework.mm.repository.StorageRepository;
import com.springframework.mm.repository.vendor.VendorCompanyRepository;
import com.springframework.mm.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchase-orders")
/** 구매오더 헤더/아이템을 동시에 관리 */
public class PurchaseOrderController {

    private final MaterialRepository materialRepository;
    private final StorageRepository storageRepository;
    private final PurchaseOrderService purchaseOrderService;
    private final VendorCompanyRepository vendorCompanyRepository;

    @GetMapping("/new")
    public String createPurchaseOrderForm(Model model) {
        model.addAttribute("purchaseOrderHeader", new PurchaseOrderHeaderCreationRequest());
        model.addAttribute("purchaseOrderItem", new PurchaseOrderItemCreationRequest());

        model.addAttribute("vendorCompanies", vendorCompanyRepository.findAllWithVendor());
        model.addAttribute("materials", materialRepository.findAll());
        model.addAttribute("storages", storageRepository.findAll());

        return "purchaseOrder/createForm";
    }

    @PostMapping()
    public String createPurchaseOrder(@ModelAttribute PurchaseOrderCreationRequest request) {
        purchaseOrderService.createPurchaseOrder(request);
        return "redirect:/purchase-orders/new";
    }

    @GetMapping
    public String showPurchaseOrders(Model model) {
        model.addAttribute("headers", purchaseOrderService.getAllHeaders());
        model.addAttribute("items", purchaseOrderService.getAllItems());

        model.addAttribute("vendorCompanies", vendorCompanyRepository.findAllWithVendor());
        model.addAttribute("materials", materialRepository.findAll());
        model.addAttribute("storages", storageRepository.findAll());
        return "purchaseOrder/purchaseOrderList";
    }
}
