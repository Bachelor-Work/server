package com.mosaics.mosaicsback.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosaics.mosaicsback.constant.MuseumConsts;
import com.mosaics.mosaicsback.dto.CreateMuseumDTO;
import com.mosaics.mosaicsback.dto.MuseumDTO;
import com.mosaics.mosaicsback.dto.MuseumModelsDTO;
import com.mosaics.mosaicsback.entity.museum.Museum;
import com.mosaics.mosaicsback.entity.museum.MuseumImg;
import com.mosaics.mosaicsback.mapper.MuseumMapper;
import com.mosaics.mosaicsback.mapper.MuseumModelsMapper;
import com.mosaics.mosaicsback.repository.MuseumImgRepository;
import com.mosaics.mosaicsback.repository.MuseumRepository;
import com.mosaics.mosaicsback.repository.MuseumTypeRepository;
import com.mosaics.mosaicsback.service.MuseumService;
import com.mosaics.mosaicsback.service.UserService;
import com.mosaics.mosaicsback.util.ImgDominantColor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MuseumServiceImpl implements MuseumService {

    private final MuseumRepository museumRepository;
    private final MuseumTypeRepository museumTypeRepository;
    private final MuseumImgRepository museumImgRepository;
    private final UserService userService;

    @Override
    public Map<String, Object> getBackAllMuseums(int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, MuseumConsts.museumsOnPage);

        Page<MuseumDTO> page = museumRepository.findAll(pageable)
                .map(MuseumMapper::toDto);
        List<String> museumsName = museumRepository.getAllMuseumsName();
        List<String> museumType = museumTypeRepository.getListOfTypes();

        List<MuseumDTO> museumModelsDTOS = ImgDominantColor.getDominantPixel(page.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("museums", museumModelsDTOS);
        response.put("names", museumsName);
        response.put("museumTypes", museumType);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }

    @Override
    public MuseumModelsDTO getBackMuseumById(Long museumId) {
        Museum museum = museumRepository.getMuseumById(museumId);

        return MuseumModelsMapper.toDto(museum);
    }

    @Override
    public Map<String, Object> getBackMuseumsByName(int pageNum, String name, String category) {
        Pageable pageable = PageRequest.of(pageNum, MuseumConsts.museumsOnPage);

        Page<MuseumDTO> page = museumRepository.getAllMuseumsByName(name, pageable, category)
                .map(MuseumMapper::toDto);

        List<MuseumDTO> museumModelsDTOS = ImgDominantColor.getDominantPixel(page.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("museums", museumModelsDTOS);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }

    @Override
    public boolean createMuseum(MultipartFile multipartFile, String museumInfo, Authentication authentication) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateMuseumDTO createdMuseumDTO = objectMapper.readValue(museumInfo, CreateMuseumDTO.class);
        Museum museum = null;

        if (museumRepository.findAll().stream().filter(element -> element.getMuseumName().equals(createdMuseumDTO.getMuseumName())).toList().isEmpty()) {
             museum = museumRepository.save(Museum.builder()
                    .museumName(createdMuseumDTO.getMuseumName())
                    .museumDescription(createdMuseumDTO.getMuseumDescription())
                    .instagramURL(createdMuseumDTO.getInstagramURL())
                    .twitterURL(createdMuseumDTO.getTwitterURL())
                    .user(userService.findByEmail(authentication.getName()))
                    .museumType(museumTypeRepository.getByType(createdMuseumDTO.getMuseumType()))
                    .build());
        }

        MuseumImg img = new MuseumImg(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                museum
        );

        Optional<MuseumImg> museumImg = Optional.ofNullable(museum.getMuseumImg());
        museumImg.ifPresent(value -> museumImgRepository.deleteById(value.getId()));

        museumImgRepository.save(img);

        return true;
    }
}
