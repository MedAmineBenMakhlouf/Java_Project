import { map, switchMap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { User } from "../models/user.model";

@Injectable({
  providedIn: 'root'
})

export class UsersService {
  private apiUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) { }



  registerUser(user: User, file: File): Observable<User> {
    const formData: FormData = new FormData();
    formData.append('user', new Blob([JSON.stringify(user)], { type: 'application/json' }));
    formData.append('file', file);

    return this.http.post<User>(`${this.apiUrl}/register`, formData, { withCredentials: true });
  }

  getDashboard(): Observable<any> {
    return this.http.get(`${this.apiUrl}/dashboardUser`, { withCredentials: true });
  }
  getLoggedUser(): Observable<any> {
    return this.http.get(`${this.apiUrl}/loggedUser`, { withCredentials: true });
  }
  getUser(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/user/${userId}/edit`, { withCredentials: true });
  }
  getOneUser(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/user/${userId}/show`, { withCredentials: true });
  }

  loginUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/login`, user, { withCredentials: true });
  }

  sellerDashboard(): Observable<any> {
    return this.http.get(`${this.apiUrl}/sellerDashboard`, { withCredentials: true });
  }

  changepictureprofile(userId: number, formData: FormData): Observable<any> {
    console.log(formData);
    
    return this.http.put(`${this.apiUrl}/user/${userId}/editPic`, formData, { withCredentials: true });
  }

  logoutUser(): Observable<any> {
    return this.http.get(`${this.apiUrl}/logout`, { withCredentials: true });
  }
  editInfo(id:any,user: any): Observable<any> {
    console.log(user, "service user");
    return this.http.put(`${this.apiUrl}/user/${id}/edit`, user, { withCredentials: true })
  }
}