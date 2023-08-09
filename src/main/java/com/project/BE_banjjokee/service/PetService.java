package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.AddPetDTO;
import com.project.BE_banjjokee.dto.PetDTO;
import com.project.BE_banjjokee.dto.UploadImageDTO;
import com.project.BE_banjjokee.image.ImageManager;
import com.project.BE_banjjokee.model.Pet;
import com.project.BE_banjjokee.model.PetImage;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.ImageRepository;
import com.project.BE_banjjokee.repository.PetRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ImageManager imageManager;
    private final ImageRepository imageRepository;

    @Transactional
    public String createPet(AddPetDTO addPetDTO, MultipartFile imgFile, String email) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));

        UploadImageDTO uploadImageDTO = imageManager.uploadImage(imgFile, user.getUuid());
        addPetDTO.setImgUrl(uploadImageDTO.getUrl());

        Pet pet = new Pet(user, addPetDTO);
        //첫 번째 반려동물인 경우 => 무조건 활성화
        if(petRepository.findAllByUserUuid(user.getUuid()).size() == 0)
            pet.changeActivate();

        petRepository.save(pet);
        imageRepository.save(PetImage.createPetImage(uploadImageDTO.getKey(), pet));
        return pet.toString();
    }

    public PetDTO getPet(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));
        List<Pet> pets  = petRepository.findAllByUserUuid(user.getUuid());

        Optional<Pet> optionalPet = pets.stream()
                .filter(p -> p.getIsActivated())
                .findFirst();
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            //Walk에서 목표 달성량 계산해서 PetDTO의 인자로 넘겨줘야함.
            return new PetDTO(pet.getId(), pet.getName(), pet.getImgUrl(),0);
        }
        else {
            return null;
        }
    }

    public AddPetDTO getUpdatePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 반려동물이 존재하지 않습니다."));
        return new AddPetDTO(pet);
    }

    @Transactional
    public String updatePet(Long petId, AddPetDTO addPetDTO, MultipartFile imgFile) throws IOException {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 반려동물이 존재하지 않습니다."));

        PetImage deleteImg = imageRepository.findByPetId(petId)
                .orElseThrow(() -> new RuntimeException("찾는 이미지가 존재하지 않습니다."));
        imageManager.delete(deleteImg.getKey());
        imageRepository.deleteImage(deleteImg);

        UploadImageDTO uploadImageDTO = imageManager.uploadImage(imgFile, pet.getUser().getUuid());
        addPetDTO.setImgUrl(uploadImageDTO.getUrl());
        pet.change(addPetDTO);
        imageRepository.save(PetImage.createPetImage(uploadImageDTO.getKey(), pet));

        return "반려동물 갱신 완료";
    }

    @Transactional
    public String deletePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 반려동물이 존재하지 않습니다."));
        petRepository.delete(pet);
        return "반려동물 삭제 완료";
    }

    @Transactional
    public String activatePet(String email, Long petId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));
        List<Pet> pets  = petRepository.findAllByUserUuid(user.getUuid());

        Optional<Pet> optionalPet = pets.stream()
                .filter(p -> p.getIsActivated())
                .findFirst();
        if (optionalPet.isPresent()) {
            Pet oldPet = optionalPet.get();
            oldPet.changeActivate();
        }

        Pet newPet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 반려동물이 존재하지 않습니다."));
        newPet.changeActivate();
        return "대표 동물 변경 완료";
    }
}
