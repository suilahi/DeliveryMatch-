import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class Authservice{

  constructor(private http: HttpClient) { }
  private apiUrl = 'http://localhost:8080/api/auth';

  register(userData: {
    nom: string;
    email: string;
    password: string;
    role: 'CONDUCTEUR' | 'EXPEDITEUR';
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  login(credentials: {
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/authenticate`, credentials);
  }

}
