package com.dsomorov.email.controllers;

import com.dsomorov.email.models.dtos.StatusDto;
import com.dsomorov.email.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/statuses")
public class StatusController
{
  @Autowired
  private StatusService statusService;
  
  @PutMapping("/{id}")
  public ResponseEntity<StatusDto> updateStatusById(
    @PathVariable("id") Long id,
    @RequestBody StatusDto statusDto
  )
  {
    if (!statusService.existsById(id))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    StatusDto savedStatusDto = statusService.updateStatus(id, statusDto);
    return new ResponseEntity<>(savedStatusDto, HttpStatus.ACCEPTED);
  }
}
