package com.springframework.mm.controller.page.vendor;

import com.springframework.mm.dto.vendor.PurchasingCreationRequest;
import com.springframework.mm.service.vendor.VendorPurchasingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendor-purchasings")
public class VendorPurchasingController {
    private final VendorPurchasingService vendorPurchasingService;

    public VendorPurchasingController(VendorPurchasingService vendorPurchasingService) {
        this.vendorPurchasingService = vendorPurchasingService;
    }

    @GetMapping("/new")
    public String purchasingCreationForm(Model model) {
        model.addAttribute("purchasing", new PurchasingCreationRequest());
        return "vendor/purchasing/createPurchasingForm";
    }

    @GetMapping()
    public String getPurchasingList(Model model) {
        model.addAttribute("purchasingList", vendorPurchasingService.getAllPurchasing());
        return "vendor/purchasing/purchasingList";
    }

    @PostMapping()
    public String createPurchasing(PurchasingCreationRequest request) {
        vendorPurchasingService.createVendorPurchasing(request);
        return "index.html";
    }
}
