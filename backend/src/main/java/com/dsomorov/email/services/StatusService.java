package com.dsomorov.email.services;

import com.dsomorov.email.mappers.StatusMapper;
import com.dsomorov.email.models.dtos.StatusDto;
import com.dsomorov.email.models.entities.Status;
import com.dsomorov.email.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StatusService
{
  @Autowired
  private StatusRepository statusRepository;
  @Autowired
  private StatusMapper     statusMapper;

  public Optional<StatusDto> findStatusById(Long id)
  {
    Optional<Status> foundStatus = statusRepository.findById(id);
    return foundStatus.map(statusMapper::mapTo);
  }
  
  public StatusDto updateStatus(Long id, StatusDto updateDto)
  {
    Status savedStatus = statusRepository
      .findById(id)
      .map(foundStatus -> {
        Optional.ofNullable(updateDto.getIsRead()).ifPresent(foundStatus::setRead);
        Optional.ofNullable(updateDto.getIsStarred()).ifPresent(foundStatus::setStarred);
        Optional.ofNullable(updateDto.getLocation()).ifPresent(foundStatus::setLocation);
        return statusRepository.save(foundStatus);
      })
      .orElseThrow(() -> new RuntimeException("Status does not exist"));
    
    return statusMapper.mapTo(savedStatus);
  }
  
  public boolean existsById(Long id)
  {
    return statusRepository.existsById(id);
  }
}
