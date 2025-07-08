package com.springFramework.mm.controller.page.vendor;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.dto.vendor.VendorCreationRequest;
import com.springFramework.mm.dto.vendor.VendorUpdateRequest;
import com.springFramework.mm.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/new")
    public String vendorCreationForm(Model model) {
        model.addAttribute("vendor", new VendorCreationRequest());
        return "vendor/createForm";
    }

    @GetMapping()
    public String getVendorList(Model model) {
        model.addAttribute("vendors", vendorService.getAllVendors());
        return "vendor/vendorList";
    }

    @PostMapping()
    public String createVendor(VendorCreationRequest request) {
        vendorService.createVendor(request);
        return "index";
    }
}
