package com.springframework.mm.controller.page;

import com.springframework.mm.domain.Facility;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.repository.FacilityRepository;
import com.springframework.mm.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/facility-storages")
public class FacilityStorageController {

    private final FacilityRepository facilityRepository;
    private final StorageRepository storageRepository;

    @GetMapping
    public String view(Model model) {
        model.addAttribute("storages", storageRepository.findAll());
        model.addAttribute("facilities", facilityRepository.findAll());
        model.addAttribute("newFacility", new Facility());
        model.addAttribute("newStorage", new Storage());
        return "facilityStorage";
    }

    @PostMapping("/facilities")
    public String createFacility(@ModelAttribute Facility facility) {
        facilityRepository.save(facility);
        return "redirect:/facility-storages";
    }

    @PostMapping("/storages")
    public String createStorage(@ModelAttribute Storage storage) {
        storageRepository.save(storage);
        return "redirect:/facility-storages";
    }
}