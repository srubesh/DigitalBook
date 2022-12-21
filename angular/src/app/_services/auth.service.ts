import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

// const AUTH_API = 'http://localhost:8081/digitalbooks/';
const AUTH_API = 'https://671e0i80l8.execute-api.ap-northeast-1.amazonaws.com/DEV/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API+'digitalbook/user/login', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string,phone: string,role: any, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'digitalbook/user/signup', {
      username,
      email,
      phone,
      role,
      password
    }, httpOptions);
  }
}
