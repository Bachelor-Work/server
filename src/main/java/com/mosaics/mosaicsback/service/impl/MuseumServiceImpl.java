package com.mosaics.mosaicsback.service.impl;

import com.mosaics.mosaicsback.constant.MuseumConsts;
import com.mosaics.mosaicsback.dto.MuseumDTO;
import com.mosaics.mosaicsback.dto.MuseumModelsDTO;
import com.mosaics.mosaicsback.entity.museum.Museum;
import com.mosaics.mosaicsback.mapper.MuseumMapper;
import com.mosaics.mosaicsback.mapper.MuseumModelsMapper;
import com.mosaics.mosaicsback.repository.MuseumRepository;
import com.mosaics.mosaicsback.repository.MuseumTypeRepository;
import com.mosaics.mosaicsback.service.MuseumService;
import com.mosaics.mosaicsback.util.ImgDominantColor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MuseumServiceImpl implements MuseumService {

    private MuseumRepository museumRepository;
    private MuseumTypeRepository museumTypeRepository;

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
    public Map<String, Object> getBackMuseumsByName(int pageNum, String name) {
        Pageable pageable = PageRequest.of(pageNum, MuseumConsts.museumsOnPage);

        Page<MuseumDTO> page = museumRepository.getAllMuseumsByName(name, pageable)
                .map(MuseumMapper::toDto);

        List<MuseumDTO> museumModelsDTOS = ImgDominantColor.getDominantPixel(page.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("museums", museumModelsDTOS);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }
}
