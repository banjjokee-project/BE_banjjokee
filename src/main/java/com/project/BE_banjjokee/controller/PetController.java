package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.AddPetDTO;
import com.project.BE_banjjokee.dto.PetDTO;
import com.project.BE_banjjokee.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {
    private final PetService petService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createPet(@RequestPart AddPetDTO addPetDTO, @RequestPart MultipartFile imgFile,
                                    @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        return ResponseEntity.ok(petService.createPet(addPetDTO, imgFile, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<PetDTO> getPet(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(petService.getPet(userDetails.getUsername()));
    }

    @GetMapping(path = "/{petId}")
    public ResponseEntity<AddPetDTO> updateFormPet(@PathVariable Long petId) {
        return ResponseEntity.ok(petService.getUpdatePet(petId));
    }

    @PatchMapping(path = "/{petId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updatePet(@PathVariable Long petId, @RequestPart AddPetDTO addPetDTO, @RequestPart MultipartFile imgFile) throws IOException {
        return ResponseEntity.ok(petService.updatePet(petId, addPetDTO, imgFile));
    }

    @DeleteMapping("/{petId}")
    private ResponseEntity<String> deletePet(@PathVariable Long petId) {
        return ResponseEntity.ok(petService.deletePet(petId));
    }

    @PostMapping("/activate/{petId}")
    private ResponseEntity<String> activatePet(@PathVariable Long petId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(petService.activatePet(userDetails.getUsername(), petId));
    }
}
