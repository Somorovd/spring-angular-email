package com.dsomorov.email.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum Location
{
  INBOX("inbox"),
  TRASH("trash"),
  ;
  
  private final String location;
  
  Location(String location)
  {
    this.location = location;
  }
  
  @JsonValue
  public String getLocation()
  {
    return location;
  }
  
  @JsonCreator
  public static Location fromValue(String value)
  {
    for (Location location : values())
    {
      String currentLocation = location.getLocation();
      if (currentLocation.equals(value.toLowerCase()))
      {
        return location;
      }
    }
    
    throw new IllegalArgumentException("Invalid value for Location type Enum: " + value);
  }
}
