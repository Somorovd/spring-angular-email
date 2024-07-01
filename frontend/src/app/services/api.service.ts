import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Address } from '../types/models';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  getHello(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/hello`);
  }

  getAllAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(`${environment.apiUrl}/addresses`);
  }

  createAddress(address: Address): Observable<Address> {
    return this.http.post<Address>(`${environment.apiUrl}/addresses`, address);
  }
}
