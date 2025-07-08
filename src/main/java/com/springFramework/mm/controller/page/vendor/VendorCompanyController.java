package com.springFramework.mm.controller.page.vendor;

import com.springFramework.mm.dto.vendor.CompanyCreationRequest;
import com.springFramework.mm.service.VendorCompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendor-companies")
public class VendorCompanyController {

    private final VendorCompanyService vendorCompanyService;

    public VendorCompanyController(VendorCompanyService vendorCompanyService) {
        this.vendorCompanyService = vendorCompanyService;
    }

    @GetMapping("/new")
    public String companyCreationForm(Model model) {
        model.addAttribute("company", new CompanyCreationRequest());
        return "vendor/company/createCompanyForm";
    }

    @GetMapping()
    public String getCompanyList(Model model) {
        model.addAttribute("companies", vendorCompanyService.getAll());
        return "vendor/company/companyList";
    }

    @PostMapping()
    public String createCompany(CompanyCreationRequest request) {
        vendorCompanyService.createVendorCompany(request);
        return "index.html";
    }
}
