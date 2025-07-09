package com.springframework.mm.controller.page;

import com.springframework.mm.dto.MaterialCreationRequest;
import com.springframework.mm.dto.MaterialRequestListWrapper;
import com.springframework.mm.enums.QuantityUnit;
import com.springframework.mm.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialRepository materialRepository;

    @GetMapping
    public String listMaterials(Model model) {
        model.addAttribute("materials", materialRepository.findAll());
        model.addAttribute("units", Arrays.stream(QuantityUnit.values())
                .map(u -> Map.of("name", u.name(), "description", u.getDescription()))
                .collect(Collectors.toList()));
        return "material";
    }

    @PostMapping("/bulk")
    public String saveMaterials(@ModelAttribute MaterialRequestListWrapper wrapper) {
        List<MaterialCreationRequest> requests = wrapper.getMaterials();
        materialRepository.saveAll(
                requests.stream()
                        .map(MaterialCreationRequest::toEntity)
                        .toList()
        );
        return "redirect:/materials";
    }
}
