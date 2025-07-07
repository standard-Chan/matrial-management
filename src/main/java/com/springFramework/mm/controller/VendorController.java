package com.springFramework.mm.controller;

import com.springFramework.mm.dto.VendorCreationRequest;
import com.springFramework.mm.dto.vendor.CompanyCreationRequest;
import com.springFramework.mm.service.VendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping()
    public String createVendor(VendorCreationRequest request) {
        vendorService.createVendor(request.toEntity());

        return "index.html";
    }

    @GetMapping("/company/new")
    public String companyCreationForm(Model model) {
        model.addAttribute(new CompanyCreationRequest());
        return "/vendor/createCompanyForm";
    }

    @PostMapping("/company")
    public void createCompany(CompanyCreationRequest request) {

    }
}
