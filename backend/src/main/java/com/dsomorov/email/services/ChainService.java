package com.dsomorov.email.services;

import com.dsomorov.email.repositories.ChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChainService
{
  @Autowired
  private ChainRepository chainRepository;
  
  public boolean existsById(Long chainId)
  {
    return chainRepository.existsById(chainId);
  }
}
